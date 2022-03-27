package com.code.files.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.VerticalGridPresenter;


import com.code.files.AppConfig;
import com.oxootv.spagreen.R;
import com.code.files.model.Movie;
import com.code.files.model.api.ApiService;
import com.code.files.utils.RetrofitClient;
import com.code.files.view.ItemCountryActivity;
import com.code.files.view.VideoDetailsActivity;
import com.code.files.view.presenter.VerticalCardPresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemCountryFragment extends VerticalGridSupportFragment {
    private static final String TAG = ItemCountryFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 8;
    private List<Movie> movies = new ArrayList<>();
    private ArrayObjectAdapter mAdapter;
    //private BackgroundHelper bgHelper;
    public static final String MOVIE = "movie";
    private int pageCount = 1;
    private boolean dataAvailable = true;
    private Context mContext;
    private String title;
    private String id = "";
    private ItemCountryActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();
        title = getActivity().getIntent().getStringExtra("title");
        id = getActivity().getIntent().getStringExtra("id");
        activity = (ItemCountryActivity) getActivity();

        setTitle(title);
        //bgHelper = new BackgroundHelper(getActivity());

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        setupFragment();
    }

    private void setupFragment() {
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);
        // mAdapter = new ArrayObjectAdapter(new CardPresenter());
        mAdapter = new ArrayObjectAdapter(new VerticalCardPresenter(MOVIE));
        setAdapter(mAdapter);

        fetchMovieData(id, pageCount, true);

    }

    private void fetchMovieData(String id, int pageCount
    , boolean isFirstLoad) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService api = retrofit.create(ApiService.class);
        Call<List<Movie>> call = api.getMovieByCountry(AppConfig.API_KEY, id, pageCount);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                Log.e(TAG, "onResponse: " + response.code() + ", id = " + id);
                Log.e(TAG, "onResponse: size: " +  response.body().size());
                if (response.code() == 200) {
                    if (response.body().size() == 0 && isFirstLoad) {
                        dataAvailable = false;
                        Toast.makeText(activity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                    List<Movie> movieList = response.body();
                    for (Movie movie : movieList) {
                        mAdapter.add(movie);
                    }
                    mAdapter.notifyArrayItemRangeChanged(movieList.size() - 1, movieList.size() + movies.size());
                    movies.addAll(movieList);
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage() );
            }
        });
    }

    // click listener
    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder viewHolder, Object o,
                                      RowPresenter.ViewHolder viewHolder2, Row row) {
                Movie movie = (Movie) o;
                Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
                intent.putExtra("id", movie.getVideosId());
                intent.putExtra("type", "movie");
                intent.putExtra("thumbImage", movie.getThumbnailUrl());
                startActivity(intent);
            }
        };
    }
    

    // selected listener for setting blur background each time when the item will select.
    protected OnItemViewSelectedListener getDefaultItemSelectedListener() {
        return new OnItemViewSelectedListener() {
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, final Object item,
                                       RowPresenter.ViewHolder rowViewHolder, Row row) {
                // pagination
                if (dataAvailable) {
                    int itemPos = mAdapter.indexOf(item);
                    if (itemPos == movies.size() - 1) {
                        pageCount++;
                        fetchMovieData(id, pageCount, false);
                    }
                }
            }
        };
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        movies = new ArrayList<>();
        pageCount = 1;
        dataAvailable = true;
    }
}
