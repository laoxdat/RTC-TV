package com.code.files.database.TvSeries;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.code.files.model.MovieList;


public class TvSeriesViewModel extends AndroidViewModel {
    private static final String TAG = "TvSeriesViewModel";
    private TvSeriesRepository repository;
    private LiveData<MovieList> tvSeriesLiveData;

    public TvSeriesViewModel(@NonNull Application application) {
        super(application);
        repository = new TvSeriesRepository(application);
        tvSeriesLiveData = repository.getTvSeriesLiveData();
    }

    public void insert(MovieList tvSeriesList){
        repository.insert(tvSeriesList);
    }

    public void update(MovieList movie){
        repository.update(movie);
    }

    public void delete(){
        repository.deleteAll();
    }

    public LiveData<MovieList> getTvSeriesLiveData(){
        return tvSeriesLiveData;
    }
}
