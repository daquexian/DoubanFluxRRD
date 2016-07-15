package com.daquexian.doubanfluxrrd.dispatcher;

import android.support.annotation.NonNull;
import android.util.Log;

import com.daquexian.doubanfluxrrd.actions.Action;
import com.daquexian.doubanfluxrrd.stores.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianhao on 16-7-13.
 * Flux中的Dispatcher
 */
public class Dispatcher {
    private final List<Store> mStoreList = new ArrayList<>();
    private static Dispatcher mInstance;

    private Dispatcher(){}

    public void register(Store store) {
        mStoreList.add(store);
    }

    public void unregister(Store store){
        mStoreList.remove(store);
    }

    public static Dispatcher getInstance(){
        if(mInstance == null) mInstance = new Dispatcher();
        return mInstance;
    }

    public void dispatch(@NonNull Action action) {
        if (null == action) {
            throw new IllegalArgumentException("the action can't be null");
        }
        post(action);
    }

    private void post(Action action){
        for (Store store :
                mStoreList) {
            store.onAction(action);
        }
    }
}
