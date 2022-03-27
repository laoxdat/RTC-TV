package com.code.files.database.movie;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.code.files.model.Movie;


import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class MovieConverter implements Serializable {
    @TypeConverter
    public static String fromList(List<Movie> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Movie> jsonList(String value){
        Type listType = new TypeToken<List<Movie>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }

}