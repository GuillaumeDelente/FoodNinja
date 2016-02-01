

package io.foodninja.android;

import android.app.Application;

import com.bugsnag.android.Bugsnag;

import pl.tajchert.nammu.Nammu;

/**
 * Android Bootstrap application
 */
public abstract class FoodNinjaApplication extends Application {

  private static FoodNinjaApplication instance;
  private FoodNinjaComponent component;

  /**
   * Create main application
   */
  public FoodNinjaApplication() {
  }


  @Override
  public void onCreate() {
    super.onCreate();
    Bugsnag.init(this);
    init();

    instance = this;

    // Perform injection
    //Injector.init(this, )
    component = DaggerComponentInitializer.init();

    onAfterInjection();
    Nammu.init(getApplicationContext());
  }

  public static FoodNinjaComponent component() {
    return instance.component;
  }

  protected abstract void onAfterInjection();

  protected abstract void init();

  public static FoodNinjaApplication getInstance() {
    return instance;
  }

  public FoodNinjaComponent getComponent() {
    return component;
  }

  public final static class DaggerComponentInitializer {

    public static FoodNinjaComponent init() {
      return DaggerFoodNinjaComponent.builder()
          .androidModule(new AndroidModule())
          .foodNinjaModule(new FoodNinjaModule())
          .build();
    }

  }
}
