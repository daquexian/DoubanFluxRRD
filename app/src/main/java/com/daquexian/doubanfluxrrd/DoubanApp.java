package com.daquexian.doubanfluxrrd;

import android.app.Application;
import android.content.Context;

import com.daquexian.doubanfluxrrd.inject.component.AppComponent;
import com.daquexian.doubanfluxrrd.inject.component.DaggerAppComponent;
import com.daquexian.doubanfluxrrd.inject.module.FluxModule;

/**
 * Created by jianhao on 16-7-13.
 */
public class DoubanApp extends Application {
    private Context mContext;

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initializeInjector();
    }

    private void initializeInjector() {
        AppComponent.Instance.init(DaggerAppComponent.builder()
                .fluxModule(new FluxModule())
                .build());
        AppComponent.Instance.getInstance().inject(this);
    }
}
