package io.foodninja.android;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.foodninja.android.core.BootstrapService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
public class FoodNinjaModule {
    
    @Provides
    BootstrapService provideBootstrapService(RestAdapter restAdapter) {
        return new BootstrapService(restAdapter);
    }

    @Provides
    FoodNinjaServiceProvider provideBootstrapServiceProvider(RestAdapter restAdapter) {
        return new FoodNinjaServiceProviderImpl(restAdapter);
    }

    @Provides
    RestAdapter provideRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint("https://www.foodspotting.com/api/v1")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new Gson()))
                .build();
    }

    @Provides
    @Singleton
    Tracker provideGoogleAnalyticsTracker(Context context) {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
        return analytics.newTracker(R.xml.ga_tracker);
    }

}
