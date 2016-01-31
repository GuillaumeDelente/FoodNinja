

package com.donnfelker.android.bootstrap.ui;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.donnfelker.android.bootstrap.BootstrapApplication;
import com.donnfelker.android.bootstrap.BootstrapServiceProvider;
import com.donnfelker.android.bootstrap.BuildConfig;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.DishesWrapper;
import com.donnfelker.android.bootstrap.core.PlacesWrapper;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import pl.tajchert.nammu.Nammu;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;


/**
 * Initial activity for the application.
 * <p/>
 * If you need to remove the authentication from the application please see
 */
public class MainActivity extends BootstrapActivity {

  private static final int REQUEST_CHECK_SETTINGS = 1010;
  private static final int PICK_PLACE = 1011;

  @Bind(R.id.recycler_view)
  RecyclerView recyclerView;
  @Bind(R.id.recycler_view_places)
  RecyclerView placesRecyclerView;
  @Bind(R.id.toolbar)
  Toolbar toolbar;
  @Bind(R.id.progress)
  ProgressBar progress;
  @Inject
  protected BootstrapServiceProvider serviceProvider;
  private DishAdapter dishAdapter;
  private PlacesAdapter placesAdapter;
  private Location lastLocation;
  private PlacesWrapper.Data.Place currentPlace;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!Nammu.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
      startActivity(new Intent(this, LocationPermissionActivity.class));
      return;
    }
    BootstrapApplication.component().inject(this);
    setContentView(R.layout.fragment_carousel);
    // View injection with Butterknife
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    dishAdapter = new DishAdapter(this);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(dishAdapter);
    placesAdapter = new PlacesAdapter(this, new PlacesAdapter.OnPlaceSelectedListener() {
      @Override
      public void onPlaceSelected(PlacesWrapper.Data.Place place) {
        onPlaceChanged(place);
      }
    });
    placesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    placesRecyclerView.setAdapter(placesAdapter);
    fetchDishes();
  }

  private void onPlaceChanged(PlacesWrapper.Data.Place place) {
    currentPlace = place;
    dishAdapter.clear();
    hidePlacesList();
    progress.setVisibility(View.VISIBLE);
    serviceProvider.getService(MainActivity.this).getDishes(place.getId())
        .subscribeOn(Schedulers.io())
        .observeOn(mainThread())
    .subscribe(new Subscriber<DishesWrapper>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(DishesWrapper dishesWrapper) {
        progress.setVisibility(View.GONE);
        dishAdapter.setItems(dishesWrapper.getSightings());
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);
    MenuItem item = menu.findItem(R.id.search);
    if (item != null) {
      item.setVisible(lastLocation != null && placesRecyclerView.getVisibility() != View.VISIBLE);
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.search:
        showPlacesList();
        return true;
      case android.R.id.home:
        hidePlacesList();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void showPlacesList() {
    placesRecyclerView.setVisibility(View.VISIBLE);
    getSupportActionBar().setTitle(R.string.places_around_you);
    ViewCompat.setNestedScrollingEnabled(placesRecyclerView, false);
    supportInvalidateOptionsMenu();
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
  }

  private void hidePlacesList() {
    placesRecyclerView.setVisibility(View.GONE);
    getSupportActionBar().setTitle(currentPlace.getName());
    supportInvalidateOptionsMenu();
    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
  }

  private void fetchDishes() {
    Log.d("Request", "Fetch dishes");
    final ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(getApplicationContext());
    final LocationRequest locationRequest = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setNumUpdates(1)
        .setInterval(100);
    locationProvider
        .checkLocationSettings(
            new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)  //Refrence: http://stackoverflow.com/questions/29824408/google-play-services-locationservices-api-new-option-never
                .build()
        ).doOnNext(new Action1<LocationSettingsResult>() {
      @Override
      public void call(LocationSettingsResult locationSettingsResult) {
        Status status = locationSettingsResult.getStatus();
        if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
          try {
            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
          } catch (IntentSender.SendIntentException th) {
            Log.e("MainActivity", "Error opening settings activity.", th);
          }
        }
      }
    })
        .flatMap(new Func1<LocationSettingsResult, Observable<Location>>() {
          @Override
          public Observable<Location> call(LocationSettingsResult locationSettingsResult) {
            return locationProvider.getUpdatedLocation(locationRequest);
          }
        })
        .flatMap(new Func1<Location, Observable<PlacesWrapper>>() {
          @Override
          public Observable<PlacesWrapper> call(Location location) {
            lastLocation = location;
            return serviceProvider.getService(MainActivity.this).getPlaces(
                String.valueOf(location.getLatitude()),
                String.valueOf(location.getLongitude()));
          }
        })
        .observeOn(mainThread())
        .doOnNext(new Action1<PlacesWrapper>() {
          @Override
          public void call(PlacesWrapper placesWrapper) {
            placesAdapter.setItems(placesWrapper.getData().getPlaces());
            placesAdapter.notifyDataSetChanged();
          }
        })
        .observeOn(Schedulers.io())
        .flatMap(new Func1<PlacesWrapper, Observable<PlacesWrapper.Data.Place>>() {
          @Override
          public Observable<PlacesWrapper.Data.Place> call(PlacesWrapper placesWrapper) {
            return Observable.just(placesWrapper.getData().getPlaces().get(0));
          }
        })
        .observeOn(mainThread())
        .doOnNext(new Action1<PlacesWrapper.Data.Place>() {
          @Override
          public void call(PlacesWrapper.Data.Place place) {
            currentPlace = place;
            getSupportActionBar().setTitle(place.getName());
            supportInvalidateOptionsMenu();
          }
        })
        .observeOn(Schedulers.io())
        .flatMap(new Func1<PlacesWrapper.Data.Place, Observable<DishesWrapper>>() {
          @Override
          public Observable<DishesWrapper> call(PlacesWrapper.Data.Place place) {
            return serviceProvider.getService(MainActivity.this).getDishes(place.getId());
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(mainThread())
        .subscribe(new Subscriber<DishesWrapper>() {
          @Override
          public void onCompleted() {
            if (BuildConfig.DEBUG) {
              Log.d("Request", "Request onCompleted");
            }
          }

          @Override
          public void onError(Throwable throwable) {
            throwable.printStackTrace();
            if (BuildConfig.DEBUG) {
              Log.d("Request", "Request onError");
            }
          }

          @Override
          public void onNext(DishesWrapper dishesWrapper) {
            progress.setVisibility(View.GONE);
            dishAdapter.setItems(dishesWrapper.getSightings());
            if (BuildConfig.DEBUG) {
              Log.d("Request", "Request onNext");
            }
          }
        });
  }
}
