package com.example.popularmovies.data.local;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.popularmovies.data.api.MoviesSyncIntentService;
import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.data.model.Review;
import com.example.popularmovies.data.model.Trailer;

@Database(entities = {Movie.class, Trailer.class, Review.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "movies_db";
    private static final Object LOCK = new Object();
    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            synchronized (LOCK) {
                appDatabase = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                Intent intent = new Intent(context, MoviesSyncIntentService.class);
                                context.startService(intent);
                            }
                        })
                        .build();
            }
        }
        return appDatabase;
    }

    public abstract MovieDao movieDao();

    public abstract ReviewDao reviewDao();

    public abstract TrailerDao trailerDao();
}
