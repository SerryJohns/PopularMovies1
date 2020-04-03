package com.example.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "review")
public class Review implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "movie_creator_id")
    private int movieCreatorId;

    private String author;
    private String content;

    @Ignore
    public Review(int movieCreatorId, String author, String content) {
        this.movieCreatorId = movieCreatorId;
        this.author = author;
        this.content = content;
    }

    public Review(int id, int movieCreatorId, String author, String content) {
        this.id = id;
        this.movieCreatorId = movieCreatorId;
        this.author = author;
        this.content = content;
    }

    protected Review(Parcel in) {
        id = in.readInt();
        movieCreatorId = in.readInt();
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public int getMovieCreatorId() {
        return movieCreatorId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(movieCreatorId);
        dest.writeString(author);
        dest.writeString(content);
    }
}
