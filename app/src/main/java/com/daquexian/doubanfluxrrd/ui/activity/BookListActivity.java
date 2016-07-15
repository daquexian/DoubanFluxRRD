package com.daquexian.doubanfluxrrd.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daquexian.doubanfluxrrd.R;
import com.daquexian.doubanfluxrrd.RxBus;
import com.daquexian.doubanfluxrrd.actions.ActionCreator;
import com.daquexian.doubanfluxrrd.dispatcher.Dispatcher;
import com.daquexian.doubanfluxrrd.inject.component.AppComponent;
import com.daquexian.doubanfluxrrd.model.Book;
import com.daquexian.doubanfluxrrd.model.BookList;
import com.daquexian.doubanfluxrrd.stores.BookListStore;
import com.daquexian.doubanfluxrrd.ui.adapter.BookListAdapter;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchHistoryTable;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.workarounds.bundler.Bundler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class BookListActivity extends BaseActivity {
    final static String TAG = "BookListActivity";
    final Context mContext = this;

    /**
     * 使用Dagger2初始化BookListStore
     */
    @Inject
    public BookListStore bookListStore;
    BookListAdapter mBookListAdapter;

    @BindView(R.id.book_list_rv)
    RecyclerView bookListRv;
    @BindView(R.id.search_view)
    SearchView searchView;

    SearchHistoryTable mHistoryDatabase;

    Subscription mBookListSubscription;
    Subscription mEnterDetailSubscription;

    /**
     * 初始化，包括
     * 在RxBus（用于Store分发Action）和Dispatcher上注册
     * bookListRv的配置
     * SearchView的初始化
     */
    private void init(){
        mBookListSubscription = RxBus.getDefault().toObservable(BookListStore.BookListChangeEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BookListStore.BookListChangeEvent>() {
                    @Override
                    public void call(BookListStore.BookListChangeEvent changeEvent) {
                        render();
                    }
                });
        mEnterDetailSubscription = RxBus.getDefault().toObservable(BookListStore.EnterDetailChangeEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BookListStore.EnterDetailChangeEvent>() {
                    @Override
                    public void call(BookListStore.EnterDetailChangeEvent enterDetailChangeEvent) {
                        enterDetail(getBookListStore().getClickBook());
                    }
                });

        Dispatcher.getInstance().register(getBookListStore());

        bookListRv.setLayoutManager(new LinearLayoutManager(this));
        mBookListAdapter = new BookListAdapter(this);
        mBookListAdapter.setOnItemClickListener(new BookListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ActionCreator.getInstance(Dispatcher.getInstance()).enterDetail(getBookListStore().getBookList().getBooks().get(position));
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });
        bookListRv.setAdapter(mBookListAdapter);

        initSearchView();
    }

    /** SearchView的配置
     *  基本照搬了GitHub主页上的实例的代码
     */
    protected void initSearchView() {
        mHistoryDatabase = new SearchHistoryTable(this);

        searchView.setVersion(SearchView.VERSION_TOOLBAR);
        searchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);
        searchView.setHint(R.string.search);
        searchView.setTextSize(16);
        searchView.setDivider(false);
        searchView.setVoice(false);

        searchView.setAnimationDuration(SearchView.ANIMATION_DURATION);
        searchView.setShadowColor(ContextCompat.getColor(this, R.color.search_shadow_layout));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getData(query, 0);
                searchView.close(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        List<SearchItem> suggestionsList = new ArrayList<>();

        SearchAdapter searchAdapter = new SearchAdapter(this, suggestionsList);
        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView textView = (TextView) view.findViewById(R.id.textView_item_text);
                String query = textView.getText().toString();
                getData(query, position);
                searchView.close(true);
            }
        });
        searchView.setAdapter(searchAdapter);
    }

    /**
     * 从搜索框搜索时调用
     * @param text 搜索的关键字
     * @param position 由SearchView的SearchItem点击事件开始搜索时表示SearchItem的position，暂时无用
     */
    private void getData(String text, int position) {
        mHistoryDatabase.addItem(new SearchItem(text));
        ActionCreator.getInstance(Dispatcher.getInstance()).getBookList(text);
    }

    public BookListStore getBookListStore() {
        return bookListStore;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        AppComponent.Instance.getInstance().inject(this);

        init();

        ActionCreator.getInstance(Dispatcher.getInstance()).getBookList("Android");
    }

    /**
     * 刷新UI
     * 没有实现Loading和Error时的UI变化 ：）
     */
    private void render(){
        if(getBookListStore().isFinish()){
            BookList bookList = getBookListStore().getBookList();
            mBookListAdapter.update(bookList);
            mBookListAdapter.notifyDataSetChanged();
        }
        if(getBookListStore().isLoading()){
            Toast.makeText(mContext, "loading", Toast.LENGTH_SHORT).show();
        }
        if(getBookListStore().isError()){
            Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 使用Bundler在Activity间跳转、传递变量
     * @param book 需要展示详情的书籍
     */
    private void enterDetail(Book book){
        Bundler.bookDetailActivity(book).start(this);
    }

    /**
     * 十分重要，否则会导致一个Action被收到多次
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!mEnterDetailSubscription.isUnsubscribed()) mEnterDetailSubscription.unsubscribe();
        if(!mBookListSubscription.isUnsubscribed()) mBookListSubscription.unsubscribe();
        Dispatcher.getInstance().unregister(bookListStore);
    }
}
