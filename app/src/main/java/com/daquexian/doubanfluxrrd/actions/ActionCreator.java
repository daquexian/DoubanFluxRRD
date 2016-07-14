package com.daquexian.doubanfluxrrd.actions;

import android.os.Bundle;

import com.daquexian.doubanfluxrrd.dispatcher.Dispatcher;
import com.daquexian.doubanfluxrrd.inject.component.AppComponent;
import com.daquexian.doubanfluxrrd.model.Book;
import com.daquexian.doubanfluxrrd.model.BookList;
import com.daquexian.doubanfluxrrd.retrofit.DoubanService;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by jianhao on 16-7-13.
 */
public class ActionCreator {
    private final static String TAG = "ActionCreator";

    private Dispatcher mDispatcher;
    private static ActionCreator sInstance;
    @Inject
    DoubanService doubanService;

    private ActionCreator(Dispatcher dispatcher) {
        AppComponent.Instance.getInstance().inject(this);
        this.mDispatcher = dispatcher;
    }

    public Dispatcher getDispatcher() {
        return mDispatcher;
    }

    public static ActionCreator getInstance(Dispatcher dispatcher) {
        if(sInstance == null) sInstance = new ActionCreator(dispatcher);
        return sInstance;
    }

    public void getBookList(String keyword){
        doubanService.searchBook(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        getDispatcher().dispatch(new BookListAction(BookListAction.ACTION_GET_BOOK_LIST_START, null));
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BookList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getDispatcher().dispatch(new BookListAction(BookListAction.ACTION_GET_BOOK_LIST_ERROR, null));
                    }

                    @Override
                    public void onNext(BookList bookList) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("bookList", bookList);
                        getDispatcher().dispatch(new BookListAction(BookListAction.ACTION_GET_BOOK_LIST_FINISH, bundle));
                    }
                });
    }

    public void enterDetail(Book book){
        Bundle data = new Bundle();
        data.putParcelable("book", book);
        getDispatcher().dispatch(new EnterDetailAction(data));
    }


}
