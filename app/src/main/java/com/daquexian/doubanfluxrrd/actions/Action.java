package com.daquexian.doubanfluxrrd.actions;

import android.os.Bundle;

/**
 * Created by jianhao on 16-7-13.
 * Flux中的Action
 */
public abstract class Action {
    private String type;
    private Bundle data;

    public String getType() {
        return type;
    }

    public Bundle getData() {
        return data;
    }

    protected Action(String type, Bundle data){
        this.type = type;
        this.data = data;
    }
}
