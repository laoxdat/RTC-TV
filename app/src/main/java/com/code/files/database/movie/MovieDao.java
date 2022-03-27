package com.code.files.database.movie;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.code.files.model.MovieList;


@Dao
public interface MovieDao {
    @Insert(onConflict = REPLACE)
    void insert(MovieList movieList);

    @Update
    void update(MovieList movieList);

    @Query("DELETE FROM movie_table")
    void deleteAll();

    @Query("SELECT * FROM movie_table")
    LiveData<MovieList> getMoviesLiveData();

}
