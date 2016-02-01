package com.donnfelker.android.bootstrap;

import com.donnfelker.android.bootstrap.core.BootstrapService;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
public class BootstrapModule {
    
    @Provides
    BootstrapService provideBootstrapService(RestAdapter restAdapter) {
        return new BootstrapService(restAdapter);
    }

    @Provides
    BootstrapServiceProvider provideBootstrapServiceProvider(RestAdapter restAdapter) {
        return new BootstrapServiceProviderImpl(restAdapter);
    }

    @Provides
    RestAdapter provideRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint("https://www.foodspotting.com/api/v1")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new Gson()))
                .build();
    }

}
