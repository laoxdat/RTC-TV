package com.code.files.model.api;


import com.code.files.model.CountryModel;
import com.code.files.model.FavoriteModel;
import com.code.files.model.Genre;
import com.code.files.model.HomeContent;
import com.code.files.model.LiveTv;
import com.code.files.model.Movie;
import com.code.files.model.config.Configuration;
import com.code.files.model.movieDetails.MovieSingleDetails;
import com.code.files.model.subscription.ActiveStatus;
import com.code.files.model.subscription.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("config")
    Call<Configuration> getConfiguration(@Header("API-KEY") String apiKey);

    @GET("home_content")
    Call<List<HomeContent>> getHomeContent(@Header("API-KEY") String apiKey);

    @GET("check_user_subscription_status")
    Call<ActiveStatus> getActiveStatus(@Header("API-KEY") String apiKey,
                                       @Query("user_id") String userId);

    @GET("movies")
    Call<List<Movie>> getMovies(@Header("API-KEY") String apiKey,
                                @Query("page") int page);

    @GET("tvseries")
    Call<List<Movie>> getTvSeries(@Header("API-KEY") String apiKey,
                                  @Query("page") int page);

    @GET("all_tv_channel_by_category")
    Call<List<LiveTv>> getLiveTvCategories(@Header("API-KEY") String apiKey);

    @GET("all_genre")
    Call<List<Genre>> getGenres(@Header("API-KEY") String apiKey,
                                @Query("page") int page);

    @GET("all_country")
    Call<List<CountryModel>> getAllCountry(@Header("API-KEY") String apiKey);

    @GET("favorite")
    Call<List<Movie>> getFavoriteList(@Header("API-KEY") String apiKey,
                                      @Query("user_id") String userId,
                                      @Query("page") int page);

    @GET("movies")
    Single<List<Movie>> getMoviesSingle(@Header("API-KEY") String apiKey,
                                        @Query("page") int page);

    @GET("content_by_genre_id")
    Call<List<Movie>> getMovieByGenre(@Header("API-KEY") String apiKey,
                                      @Query("id") String id,
                                      @Query("page") int page_num);


    @GET("content_by_country_id")
    Call<List<Movie>> getMovieByCountry(@Header("API-KEY") String apiKey,
                                        @Query("id") String id,
                                        @Query("page") int page_number);

    @GET("single_details")
    Call<MovieSingleDetails> getSingleDetail(@Header("API-KEY") String apiKey,
                                             @Query("type") String videoType,
                                             @Query("id") String videoId);

    @GET("add_favorite")
    Call<FavoriteModel> addToFavorite(@Header("API-KEY") String apiKey,
                                      @Query("user_id") String userId,
                                      @Query("videos_id") String videoId);

    @GET("remove_favorite")
    Call<FavoriteModel> removeFromFavorite(@Header("API-KEY") String apiKey,
                                           @Query("user_id") String userId,
                                           @Query("videos_id") String videoId);

    @GET("verify_favorite_list")
    Call<FavoriteModel> verifyFavoriteList(@Header("API-KEY") String apiKey,
                                           @Query("user_id") String userId,
                                           @Query("videos_id") String videoId);

    @FormUrlEncoded
    @POST("firebase_auth")
    Call<User> getPhoneAuthStatus(@Header("API-KEY") String apiKey,
                                  @Field("uid") String uid,
                                  @Field("phone") String phoneNo);

    @FormUrlEncoded
    @POST("firebase_auth")
    Call<User> getGoogleAuthStatus(@Header("API-KEY") String apiKey,
                                   @Field("uid") String uid,
                                   @Field("email") String phoneNo,
                                   @Field("name") String name);


    @FormUrlEncoded
    @POST("firebase_auth")
    Call<User> getFacebookAuthStatus(@Header("API-KEY") String apiKey,
                                     @Field("uid") String uid,
                                     @Field("name") String name,
                                     @Field("email") String email,
                                     @Field("photo_url") String photoUrl);

    @FormUrlEncoded
    @POST("signup")
    Call<User> signUp(@Header("API-KEY") String apiKey,
                      @Field("email") String email,
                      @Field("password") String password,
                      @Field("name") String name);

    @FormUrlEncoded
    @POST("login")
    Call<User> postLoginStatus(@Header("API-KEY") String apiKey,
                               @Field("email") String email,
                               @Field("password") String password);

}
