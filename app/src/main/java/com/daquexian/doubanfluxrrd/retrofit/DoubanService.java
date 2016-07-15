package com.daquexian.doubanfluxrrd.retrofit;

import com.daquexian.doubanfluxrrd.model.Book;
import com.daquexian.doubanfluxrrd.model.BookList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jianhao on 16-7-12.
 * 用于Retrofit，根据豆瓣提供的API执行网络请求
 */
public interface DoubanService {
    @GET("book/search")
    Observable<BookList> searchBook(@Query("q") String keyword);
    @GET("book/{id}")
    Observable<Book> getBookById(@Path("id") String id);
}
