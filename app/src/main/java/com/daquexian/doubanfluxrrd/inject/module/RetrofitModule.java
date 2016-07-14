package com.daquexian.doubanfluxrrd.inject.module;

import com.daquexian.doubanfluxrrd.retrofit.DoubanService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jianhao on 16-7-13.
 */
@Module
public class RetrofitModule {
    @Provides
    @Singleton
    public Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.douban.com/v2/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public DoubanService provideDoubanService(Retrofit retrofit) {
        return retrofit.create(DoubanService.class);
    }
}
