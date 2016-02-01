package com.donnfelker.android.bootstrap.core;


import com.donnfelker.android.bootstrap.BuildConfig;
import com.donnfelker.android.bootstrap.model.DishesWrapper;
import com.donnfelker.android.bootstrap.model.PlacesWrapper;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by guillaume on 1/13/16.
 */
public interface DishService {

  @GET("/places.json?api_key=" + BuildConfig.FOODSPOTTING_API_KEY)
  Observable<PlacesWrapper> getPlaces(@Query("latitude") String latitude, @Query("longitude") String longitude);

  @GET("/sightings.json?&sort=best&api_key=" + BuildConfig.FOODSPOTTING_API_KEY)
  Observable<DishesWrapper> getDishes(@Query("place_id") String placeId);
}
