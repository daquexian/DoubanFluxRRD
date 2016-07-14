package com.daquexian.doubanfluxrrd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daquexian.doubanfluxrrd.actions.ActionCreator;
import com.daquexian.doubanfluxrrd.dispatcher.Dispatcher;
import com.daquexian.doubanfluxrrd.inject.component.AppComponent;
import com.daquexian.doubanfluxrrd.model.Book;
import com.daquexian.doubanfluxrrd.model.BookList;
import com.daquexian.doubanfluxrrd.stores.BookListStore;
import com.daquexian.doubanfluxrrd.stores.Store;
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
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.State;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {
    final static String TAG = "MainActivity";
    final Context mContext = this;

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
    public BookListStore getBookListStore() {
        return bookListStore;
    }

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
                // mSearchView.close(false);
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
                // mSearchView.close(false);
            }
        });
        searchView.setAdapter(searchAdapter);
    }
    private void getData(String text, int position) {
        mHistoryDatabase.addItem(new SearchItem(text));
        ActionCreator.getInstance(Dispatcher.getInstance()).getBookList(text);
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

    private void enterDetail(Book book){
        Bundler.bookDetailActivity(book).start(this);
    }

    //@Override
    //protected void onSaveInstanceState(Bundle outState) {
    //    super.onSaveInstanceState(outState);
    //    Bundler.saveState(this, outState);
    //}

    /*@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Bundler.restoreState(this, savedInstanceState);
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!mEnterDetailSubscription.isUnsubscribed()) mEnterDetailSubscription.unsubscribe();
        if(!mBookListSubscription.isUnsubscribed()) mBookListSubscription.unsubscribe();
        Dispatcher.getInstance().unregister(bookListStore);
    }
}
