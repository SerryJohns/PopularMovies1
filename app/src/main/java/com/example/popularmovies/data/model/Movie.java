package com.example.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    private String title;
    private String overview;
    private boolean favorite = false;

    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @Ignore
    public Movie(Integer movieId, String title, LocalDate releaseDate, String posterPath, String overview, Double voteAverage) {
        this.movieId = movieId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.posterPath = this.setPosterPath(posterPath);
    }

    protected Movie(Parcel in) {
        movieId = in.readInt();
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
        dest.writeInt(movieId);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeDouble(voteAverage);
        dest.writeSerializable(releaseDate);
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
}
