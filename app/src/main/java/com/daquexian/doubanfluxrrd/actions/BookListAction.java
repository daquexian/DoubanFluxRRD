package com.daquexian.doubanfluxrrd.actions;

import android.os.Bundle;

/**
 * Created by jianhao on 16-7-13.
 * 与获取图书列表相关的Action
 */
public class BookListAction extends Action{
    public static final String ACTION_GET_BOOK_LIST_START = "action_get_book_list_start";
    public static final String ACTION_GET_BOOK_LIST_FINISH = "action_get_book_list_finish";
    public static final String ACTION_GET_BOOK_LIST_ERROR = "action_get_book_list_error";

    public BookListAction(String type, Bundle data) {
        super(type, data);
    }
}
