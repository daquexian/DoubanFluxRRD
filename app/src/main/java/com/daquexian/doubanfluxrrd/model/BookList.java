package com.daquexian.doubanfluxrrd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by jianhao on 16-7-12.
 */
public class BookList implements Parcelable{
    int count;
    int start;
    int total;
    List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(count);
        parcel.writeInt(start);
        parcel.writeInt(total);
        parcel.writeTypedList(books);
    }

    public static final Creator<BookList> CREATOR = new Creator<BookList>() {
        @Override
        public BookList createFromParcel(Parcel parcel) {
            BookList bookList = new BookList();
            bookList.count = parcel.readInt();
            bookList.start = parcel.readInt();
            bookList.total = parcel.readInt();
            bookList.books = parcel.createTypedArrayList(Book.CREATOR);
            return bookList;
        }

        @Override
        public BookList[] newArray(int i) {
            return new BookList[i];
        }
    };
}
