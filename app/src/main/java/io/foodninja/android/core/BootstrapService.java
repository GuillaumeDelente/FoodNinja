
package io.foodninja.android.core;

import io.foodninja.android.model.DishesWrapper;
import io.foodninja.android.model.PlacesWrapper;
import retrofit.RestAdapter;
import rx.Observable;

/**
 * Bootstrap API service
 */
public class BootstrapService {

    private RestAdapter restAdapter;

    /**
     * Create bootstrap service
     * Default CTOR
     */
    public BootstrapService() {
    }

    /**
     * Create bootstrap service
     *
     * @param restAdapter The RestAdapter that allows HTTP Communication.
     */
    public BootstrapService(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    private DishService getDishService() {
        return getRestAdapter().create(DishService.class);
    }

    private RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public Observable<DishesWrapper> getDishes(String placeId) {
        return getDishService().getDishes(placeId);
    }

    public Observable<PlacesWrapper> getPlaces(String latitude, String longitude) {
        return getDishService().getPlaces(latitude, longitude);
    }
}