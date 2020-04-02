package com.example.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "movie")
public class Movie implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    private Integer movieId;

    @ColumnInfo(name = "release_date")
    private LocalDate releaseDate;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @ColumnInfo(name = "top_rated")
    private boolean topRated;

    private boolean favorite;
    private String title;
    private String overview;

    public Movie(Integer movieId,
                 String title,
                 LocalDate releaseDate,
                 String posterPath,
                 String overview,
                 Double voteAverage,
                 boolean favorite,
                 boolean topRated
    ) {
        this.movieId = movieId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.posterPath = this.setPosterPath(posterPath);
        this.favorite = favorite;
        this.topRated = topRated;
    }

    protected Movie(Parcel in) {
        movieId = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        voteAverage = in.readDouble();
        releaseDate = (LocalDate) in.readSerializable();
        favorite = in.readByte() != 0;
        topRated = in.readByte() != 0;
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
        dest.writeInt(movieId);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeDouble(voteAverage);
        dest.writeSerializable(releaseDate);
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeByte((byte) (topRated ? 1 : 0));
    }

    public Integer getMovieId() {
        return movieId;
    }

    private String setPosterPath(String posterPath) {
        return "https://image.tmdb.org/t/p/w342/" + posterPath;
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

    public boolean isTopRated() {
        return topRated;
    }
}
