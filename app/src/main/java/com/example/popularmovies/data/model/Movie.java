package com.example.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;

public class Movie implements Parcelable {
    private Integer ID;
    private String title;
    private LocalDate releaseDate;
    private String posterPath;
    private String overview;
    private boolean favorite = false;
    private Double voteAverage;

    public Movie(Integer ID, String title, LocalDate releaseDate, String posterPath, String overview, Double voteAverage) {
        this.ID = ID;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.setPosterPath(posterPath);
    }

    protected Movie(Parcel in) {
        ID = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        favorite = in.readByte() != 0;
        voteAverage = in.readDouble();
        releaseDate = (LocalDate) in.readSerializable();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeDouble(voteAverage);
        dest.writeSerializable(releaseDate);
    }

    public Integer getID() {
        return ID;
    }

    private void setPosterPath(String posterPath) {
        this.posterPath = "https://image.tmdb.org/t/p/w342/" + posterPath;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }
}
