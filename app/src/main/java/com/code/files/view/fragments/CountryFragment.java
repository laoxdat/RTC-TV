package com.code.files.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
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
import com.code.files.model.CountryModel;
import com.code.files.model.api.ApiService;
import com.code.files.utils.NetworkInst;
import com.code.files.utils.RetrofitClient;
import com.code.files.view.ErrorActivity;
import com.code.files.view.HomeActivity;
import com.code.files.view.ItemCountryActivity;
import com.code.files.view.presenter.CountryPresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CountryFragment extends VerticalGridSupportFragment {
    public static final String COUNTRY = "country";
    private static final String TAG = CountryFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 8;
    private int pageCount = 1;
    private boolean dataAvailable = true;
    //private BackgroundHelper bgHelper;
    private List<CountryModel> countries = new ArrayList<>();
    private ArrayObjectAdapter mAdapter;
    private HomeActivity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (HomeActivity) getActivity();
        activity.hideLogo();
        setTitle(getResources().getString(R.string.country));

        //bgHelper = new BackgroundHelper(getActivity());

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
        setOnItemViewSelectedListener(getDefaultItemSelectedListener());

        // setup
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);

        //mAdapter = new ArrayObjectAdapter(new CountryCardPresenter());
        mAdapter = new ArrayObjectAdapter(new CountryPresenter());
        setAdapter(mAdapter);

        // Fetch data
        fetchCountryData();
    }

    private void fetchCountryData() {
        if (!new NetworkInst(activity).isNetworkAvailable()) {
            Intent intent = new Intent(activity, ErrorActivity.class);
            startActivity(intent);
            activity.finish();
            return;
        }

        final SpinnerFragment mSpinnerFragment = new SpinnerFragment();
        final FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(R.id.custom_frame_layout, mSpinnerFragment).commit();

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService api = retrofit.create(ApiService.class);
        Call<List<CountryModel>> call = api.getAllCountry(AppConfig.API_KEY);
        call.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                Log.e(TAG, "onResponse: " + response.code());
                if (response.code() == 200) {
                    List<CountryModel> countryList = response.body();
                    if (countryList.size() <= 0) {
                        dataAvailable = false;
                        Toast.makeText(activity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }

                    for (CountryModel country : countryList) {
                        mAdapter.add(country);
                    }
                    mAdapter.notifyArrayItemRangeChanged(countryList.size() - 1, countryList.size() + countries.size());
                    countries.addAll(countryList);

                    // hide the spinner
                    fm.beginTransaction().remove(mSpinnerFragment).commitAllowingStateLoss();
                }
            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {
                Log.e(TAG, "error: " + t.getLocalizedMessage());

            }
        });
    }

    // click listener
    private OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder viewHolder, Object o,
                                      RowPresenter.ViewHolder viewHolder2, Row row) {
                CountryModel countryModel = (CountryModel) o;
                Intent intent = new Intent(getActivity(), ItemCountryActivity.class);
                intent.putExtra("id", countryModel.getCountryId());
                intent.putExtra("title", countryModel.getName());
                startActivity(intent);

            }
        };
    }

    // selected listener for setting blur background each time when the item will select.
    protected OnItemViewSelectedListener getDefaultItemSelectedListener() {
        return new OnItemViewSelectedListener() {
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, final Object item,
                                       RowPresenter.ViewHolder rowViewHolder, Row row) {

                if (item instanceof CountryModel) {
                    /*bgHelper = new BackgroundHelper(getActivity());
                    bgHelper.prepareBackgroundManager();
                    bgHelper.startBackgroundTimer(((CountryModel) item).getImageUrl());*/

                }

            }
        };
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        countries = new ArrayList<>();
        pageCount = 1;
        dataAvailable = true;

    }
}
