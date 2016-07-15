package com.daquexian.doubanfluxrrd.stores;

import android.os.Parcel;
import android.os.Parcelable;

import com.daquexian.doubanfluxrrd.actions.Action;
import com.daquexian.doubanfluxrrd.actions.BookListAction;
import com.daquexian.doubanfluxrrd.actions.EnterDetailAction;
import com.daquexian.doubanfluxrrd.model.Book;
import com.daquexian.doubanfluxrrd.model.BookList;

/**
 * Created by jianhao on 16-7-13.
 * 起到保存UI信息和状态并根据收到的Action更新的作用，对应于BookListActivity，大部分代码都是实现Parcelable的
 */
public class BookListStore extends Store implements Parcelable {
    private BookList mBookList;
    private Status mStatus = Status.GET_LIST_INIT;

    private Book mClickBook;
    private ChangeEvent mChangeEvent;

    @Override
    public void onAction(Action action) {
        switch (action.getType()){
            case BookListAction.ACTION_GET_BOOK_LIST_START:
                mStatus = Status.GET_LIST_LOADING;
                mChangeEvent = new BookListChangeEvent();
                break;
            case BookListAction.ACTION_GET_BOOK_LIST_FINISH:
                mStatus = Status.GET_LIST_FINISH;
                mBookList = action.getData().getParcelable("bookList");
                mChangeEvent = new BookListChangeEvent();
                break;
            case BookListAction.ACTION_GET_BOOK_LIST_ERROR:
                mStatus = Status.GET_LIST_ERROR;
                mChangeEvent = new BookListChangeEvent();
                break;
            case EnterDetailAction.ACTION_ENTER_DETAIL:
                mClickBook = action.getData().getParcelable("book");
                mChangeEvent = new EnterDetailChangeEvent();
        }
        emitStoreChange();
    }

    public boolean isInit(){
        return mStatus == Status.GET_LIST_INIT;
    }
    public boolean isFinish(){
        return mStatus == Status.GET_LIST_FINISH;
    }

    public boolean isLoading(){
        return mStatus == Status.GET_LIST_LOADING;
    }
    public boolean isError(){
        return mStatus == Status.GET_LIST_ERROR;
    }

    public BookList getBookList() {
        return mBookList;
    }

    public static class BookListChangeEvent extends ChangeEvent{
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

        }

        public static final Creator<BookListChangeEvent> CREATOR = new Creator<BookListChangeEvent>() {
            @Override
            public BookListChangeEvent createFromParcel(Parcel parcel) {
                return new BookListChangeEvent();
            }

            @Override
            public BookListChangeEvent[] newArray(int i) {
                return new BookListChangeEvent[i];
            }
        };
    }

    public static class EnterDetailChangeEvent extends ChangeEvent{
        @Override
        public void writeToParcel(Parcel parcel, int i) {}
        public static final Creator<EnterDetailChangeEvent> CREATOR = new Creator<EnterDetailChangeEvent>() {
            @Override
            public EnterDetailChangeEvent createFromParcel(Parcel parcel) {
                return new EnterDetailChangeEvent();
            }

            @Override
            public EnterDetailChangeEvent[] newArray(int i) {
                return new EnterDetailChangeEvent[i];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }
    }

    public Book getClickBook() {
        return mClickBook;
    }

    @Override
    public ChangeEvent getChangeEvent() {
        return mChangeEvent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mBookList, 0);
        parcel.writeParcelable(mStatus, 0);
        parcel.writeParcelable(mClickBook, 0);
        parcel.writeParcelable(mChangeEvent, 0);
    }

    public static final Creator<BookListStore> CREATOR = new Creator<BookListStore>() {
        @Override
        public BookListStore createFromParcel(Parcel parcel) {
            BookListStore bookListStore = new BookListStore();
            bookListStore.mBookList = parcel.readParcelable(BookList.class.getClassLoader());
            bookListStore.mStatus = parcel.readParcelable(Status.class.getClassLoader());
            bookListStore.mClickBook = parcel.readParcelable(Book.class.getClassLoader());
            bookListStore.mChangeEvent = parcel.readParcelable(ChangeEvent.class.getClassLoader());
            return bookListStore;
        }

        @Override
        public BookListStore[] newArray(int i) {
            return new BookListStore[i];
        }
    };

    private enum Status implements Parcelable {GET_LIST_INIT, GET_LIST_LOADING, GET_LIST_FINISH, GET_LIST_ERROR;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(ordinal());
        }

        public static final Creator<Status> CREATOR = new Creator<Status>() {
            @Override
            public Status createFromParcel(Parcel parcel) {
                return Status.values()[parcel.readInt()];
            }

            @Override
            public Status[] newArray(int i) {
                return new Status[i];
            }
        };
    }
}
