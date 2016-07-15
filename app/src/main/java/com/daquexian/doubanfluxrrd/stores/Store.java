package com.daquexian.doubanfluxrrd.stores;

import android.os.Parcelable;

import com.daquexian.doubanfluxrrd.RxBus;
import com.daquexian.doubanfluxrrd.actions.Action;

/**
 * Created by jianhao on 16-7-13.
 * Flux中的Store
 */
public abstract class Store {
    private ChangeEvent mChangeEvent;
    public abstract void onAction(Action action);

    public ChangeEvent getChangeEvent(){
        return mChangeEvent;
    }
    protected void emitStoreChange(){
        RxBus.getDefault().send(getChangeEvent());
    }

    public abstract static class ChangeEvent implements Parcelable {}
}
