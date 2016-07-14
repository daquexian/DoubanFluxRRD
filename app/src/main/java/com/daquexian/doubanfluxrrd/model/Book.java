package com.daquexian.doubanfluxrrd.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jianhao on 16-7-12.
 */
public class Book implements Parcelable{
    Rating rating;

    String subtitle;
    List<String> author;
    @SerializedName("pubdate")
    String pubDate;
    @SerializedName("origin_title")
    String originTitle;
    String image;
    String binding;
    String catalog;
    List<Tag> tags;
    String pages;
    List<String> translator;
    String alt;
    String id;
    String publisher;
    String isbn10;
    String isbn13;
    String title;
    String url;

    @SerializedName("alt_title")
    String altTitle;
    @SerializedName("author_intro")
    String authorIntro;
    String summary;
    String price;
    Images images;

    public static class Rating implements Parcelable{
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(max);
            parcel.writeInt(numRaters);
            parcel.writeString(average);
            parcel.writeInt(min);
        }

        public static final Creator<Rating> CREATOR = new Creator<Rating>() {
            @Override
            public Rating createFromParcel(Parcel parcel) {
                Rating rating = new Rating();
                rating.max = parcel.readInt();
                rating.numRaters = parcel.readInt();
                rating.average = parcel.readString();
                rating.min = parcel.readInt();
                return rating;
            }

            @Override
            public Rating[] newArray(int i) {
                return new Rating[i];
            }
        };

        int max;
        int numRaters;
        String average;
        int min;
    }

    public static class Images implements Parcelable{
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(small);
            parcel.writeString(medium);
            parcel.writeString(large);
        }

        public static final Creator<Images> CREATOR = new Creator<Images>() {
            @Override
            public Images createFromParcel(Parcel parcel) {
                Images images = new Images();
                images.small = parcel.readString();
                images.medium = parcel.readString();
                images.large = parcel.readString();
                return images;
            }

            @Override
            public Images[] newArray(int i) {
                return new Images[i];
            }
        };

        String small;
        String medium;

        public String getLarge() {
            return large;
        }

        public String getSmall() {
            return small;
        }

        public String getMedium() {
            return medium;
        }

        String large;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(rating, 0);
        parcel.writeString(subtitle);
        parcel.writeStringList(author);
        parcel.writeString(pubDate);
        parcel.writeString(originTitle);
        parcel.writeString(image);
        parcel.writeString(binding);
        parcel.writeString(catalog);
        parcel.writeTypedList(tags);
        parcel.writeString(pages);
        parcel.writeStringList(translator);
        parcel.writeString(alt);
        parcel.writeString(id);
        parcel.writeString(publisher);
        parcel.writeString(isbn10);
        parcel.writeString(isbn13);
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(altTitle);
        parcel.writeString(authorIntro);
        parcel.writeString(summary);
        parcel.writeString(price);
        parcel.writeParcelable(images, 0);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel parcel) {
            Book book = new Book();
            book.rating = parcel.readParcelable(Rating.class.getClassLoader());
            book.subtitle = parcel.readString();
            book.author = parcel.createStringArrayList();
            book.pubDate = parcel.readString();
            book.originTitle = parcel.readString();
            book.image = parcel.readString();
            book.binding = parcel.readString();
            book.catalog = parcel.readString();
            book.tags = parcel.createTypedArrayList(Tag.CREATOR);
            book.pages = parcel.readString();
            book.translator = parcel.createStringArrayList();
            book.alt = parcel.readString();
            book.id = parcel.readString();
            book.publisher = parcel.readString();
            book.isbn10 = parcel.readString();
            book.isbn13 = parcel.readString();
            book.title = parcel.readString();
            book.url = parcel.readString();
            book.altTitle = parcel.readString();
            book.authorIntro = parcel.readString();
            book.summary = parcel.readString();
            book.price = parcel.readString();
            book.images = parcel.readParcelable(Images.class.getClassLoader());
            return book;
        }

        @Override
        public Book[] newArray(int i) {
            return new Book[i];
        }
    };

    public static class Tag implements Parcelable{
        int count;
        String name;
        String title;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(count);
            parcel.writeString(name);
            parcel.writeString(title);
        }

        public static final Creator<Tag> CREATOR = new Creator<Tag>() {
            @Override
            public Tag createFromParcel(Parcel parcel) {
                Tag tag = new Tag();
                tag.count = parcel.readInt();
                tag.name = parcel.readString();
                tag.title = parcel.readString();
                return tag;
            }

            @Override
            public Tag[] newArray(int i) {
                return new Tag[i];
            }
        };
    }

    public String getAltTitle() {
        return altTitle;
    }

    public String getAuthorIntro() {
        return authorIntro;
    }

    public Rating getRating() {
        return rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public List<String> getAuthor() {
        return author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getOriginTitle() {
        return originTitle;
    }

    public String getImage() {
        return image;
    }

    public String getBinding() {
        return binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getPages() {
        return pages;
    }

    public List<String> getTranslator() {
        return translator;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getSummary() {
        return summary;
    }

    public String getPrice() {
        return price;
    }

    public Images getImages() {
        return images;
    }

}
