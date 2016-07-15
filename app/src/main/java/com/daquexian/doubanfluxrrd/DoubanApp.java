package com.daquexian.doubanfluxrrd;

import android.app.Application;

import com.daquexian.doubanfluxrrd.inject.component.AppComponent;
import com.daquexian.doubanfluxrrd.inject.component.DaggerAppComponent;
import com.daquexian.doubanfluxrrd.inject.module.FluxModule;

/**
 * Created by jianhao on 16-7-13.
 * 为了使用Dagger2，重写Application的onCreate方法，初始化AppComponent
 */
public class DoubanApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        AppComponent.Instance.init(DaggerAppComponent.builder()
                .fluxModule(new FluxModule())
                .build());
        AppComponent.Instance.getInstance().inject(this);
    }
}
