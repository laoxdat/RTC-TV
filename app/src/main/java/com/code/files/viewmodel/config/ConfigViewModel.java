package com.code.files.viewmodel.config;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.code.files.model.config.Configuration;
import com.code.files.viewmodel.ConfigurationRepository;

public class ConfigViewModel extends AndroidViewModel {
    private static final String TAG = "ConfigViewModel";
    private ConfigurationRepository repository;
    private Configuration configuration;


    public ConfigViewModel(@NonNull Application application){
        super(application);
        repository = new ConfigurationRepository(application);
        configuration = repository.getConfiguration();
    }

    public void insert(Configuration configuration){
        repository.insertConfigData(configuration);
    }

    public void update(Configuration configuration){
        repository.update(configuration);
    }

    public void delete(){
        repository.deleteConfigData();
    }

    public Configuration getConfiguration(){
        return repository.getConfiguration();
    }
}
