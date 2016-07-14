package com.daquexian.doubanfluxrrd.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daquexian.doubanfluxrrd.R;
import com.daquexian.doubanfluxrrd.model.Book;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.State;

/**
 * Created by jianhao on 16-7-14.
 */
@RequireBundler
public class BookDetailActivity extends BaseActivity {
    @Arg
    @State
    Book book;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.book_img)
    ImageView bookImg;
    @BindView(R.id.book_title)
    TextView titleTv;
    @BindView(R.id.book_author)
    TextView authorTv;
    @BindView(R.id.book_summary)
    TextView summaryTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        Bundler.inject(this);

        init();
    }

    private void init(){
        toolbar.setTitle(R.string.book_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Glide.with(this)
                .load(book.getImages().getLarge())
                .into(bookImg);
        titleTv.setText(book.getTitle());
        authorTv.setText(book.getAuthor().toString());
        summaryTv.setText(book.getSummary());
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundler.saveState(this, outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Bundler.restoreState(this, savedInstanceState);
    }

}
