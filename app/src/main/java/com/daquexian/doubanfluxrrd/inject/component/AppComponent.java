package com.daquexian.doubanfluxrrd.inject.component;

import com.daquexian.doubanfluxrrd.DoubanApp;
import com.daquexian.doubanfluxrrd.ui.activity.BookListActivity;
import com.daquexian.doubanfluxrrd.actions.ActionCreator;
import com.daquexian.doubanfluxrrd.inject.module.FluxModule;
import com.daquexian.doubanfluxrrd.inject.module.RetrofitModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jianhao on 16-7-13.
 * Dagger2中的Component
 */
@Singleton
@Component(modules = {FluxModule.class, RetrofitModule.class})
public interface AppComponent {
    void inject(DoubanApp app);

    void inject(BookListActivity activity);

    void inject(ActionCreator actionCreator);

    class Instance{
        private static AppComponent appComponent;
        public static void init(AppComponent appComponent){
            Instance.appComponent = appComponent;
        }
        public static AppComponent getInstance(){
            return appComponent;
        }
    }
}
