package com.daquexian.doubanfluxrrd.inject.module;

import com.daquexian.doubanfluxrrd.dispatcher.Dispatcher;
import com.daquexian.doubanfluxrrd.stores.BookListStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jianhao on 16-7-13.
 * 用于Dagger2，提供与AndroidFlux架构相关的组件
 */
@Module
public class FluxModule {
    @Singleton
    @Provides
    public Dispatcher provideDispatcher(){
        return Dispatcher.getInstance();
    }

    @Singleton
    @Provides
    public BookListStore provideBookListStore(){
        return new BookListStore();
    }
}
