package com.example.popularmovies.data.local;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class DateConverter {
    @TypeConverter
    public Long fromDate(LocalDate date) {
        return date.toEpochDay();
    }

    @TypeConverter
    public LocalDate toDate(long date) {
        return LocalDate.ofEpochDay(date);
    }
}
