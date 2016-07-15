package com.daquexian.doubanfluxrrd.actions;

import android.os.Bundle;

import com.daquexian.doubanfluxrrd.model.Book;

/**
 * Created by jianhao on 16-7-14.
 * 进入图书详情页面的Action
 */
public class EnterDetailAction extends Action{
    public static final String ACTION_ENTER_DETAIL = "action_enter_detail";

    public EnterDetailAction(Bundle data){
        super(ACTION_ENTER_DETAIL, data);
    }
}
