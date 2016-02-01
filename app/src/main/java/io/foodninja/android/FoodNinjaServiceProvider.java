package io.foodninja.android;

import android.app.Activity;

import io.foodninja.android.core.BootstrapService;

public interface FoodNinjaServiceProvider {
    BootstrapService getService(Activity activity);
}
