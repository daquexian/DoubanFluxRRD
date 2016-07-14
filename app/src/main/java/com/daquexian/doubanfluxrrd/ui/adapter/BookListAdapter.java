package com.daquexian.doubanfluxrrd.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daquexian.doubanfluxrrd.R;
import com.daquexian.doubanfluxrrd.model.BookList;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jianhao on 16-7-13.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookListVH> {
    Context mContext;
    private BookList mBookList;
    private onItemClickListener mOnItemClickListener;

    public BookListAdapter(Context context, BookList bookList) {
        mContext = context;
        mBookList = bookList;
    }
    public BookListAdapter(Context context) {
        this(context, null);
    }

    public void update(BookList bookList) {
        mBookList = bookList;
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final BookListVH holder, final int position) {
        String title = mBookList.getBooks().get(position).getTitle();
        String author = mBookList.getBooks().get(position).getAuthor().toString();
        String uri = mBookList.getBooks().get(position).getImage();
        holder.titleTv.setText(title);
        holder.authorTv.setText(author);
        Glide.with(mContext).load(uri).into(holder.img);

        if(mOnItemClickListener != null){
            holder.bookRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(pos);
                }
            });
            holder.bookRl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBookList == null ? 0 : mBookList.getCount();
    }

    @Override
    public BookListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookListVH(LayoutInflater.from(mContext).inflate(R.layout.book_item, parent, false));
    }

    public interface onItemClickListener{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
    public static class BookListVH extends RecyclerView.ViewHolder{
        @BindView(R.id.book_rl)
        RelativeLayout bookRl;
        @BindView(R.id.book_title)
        TextView titleTv;
        @BindView(R.id.book_author)
        TextView authorTv;
        @BindView(R.id.book_img)
        ImageView img;
        BookListVH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
