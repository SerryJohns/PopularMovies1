package com.example.popularmovies.data.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "trailer")
public class Trailer implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "movie_creator_id")
    private int movieCreatorId;

    @ColumnInfo(name = "trailer_path")
    private String trailerPath;
    private String name;
    private String type;

    @Ignore
    public Trailer(int movieCreatorId, String trailerPath, String name, String type) {
        this.trailerPath = trailerPath;
        this.name = name;
        this.type = type;
        this.movieCreatorId = movieCreatorId;
    }

    public Trailer(int id, int movieCreatorId, String trailerPath, String name, String type) {
        this.id = id;
        this.movieCreatorId = movieCreatorId;
        this.trailerPath = trailerPath;
        this.name = name;
        this.type = type;
    }

    protected Trailer(Parcel in) {
        id = in.readInt();
        movieCreatorId = in.readInt();
        trailerPath = in.readString();
        name = in.readString();
        type = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public static String getUri(String path) {
        String baseUrl = "https://www.youtube.com/watch";
        return Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("v", path)
                .build()
                .toString();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getMovieCreatorId() {
        return movieCreatorId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getTrailerPath() {
        return trailerPath;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(movieCreatorId);
        dest.writeString(trailerPath);
        dest.writeString(name);
        dest.writeString(type);
    }
}
