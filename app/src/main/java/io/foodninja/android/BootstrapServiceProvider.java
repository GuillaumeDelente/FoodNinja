package io.foodninja.android;

import android.app.Activity;

import io.foodninja.android.core.BootstrapService;

public interface BootstrapServiceProvider {
    BootstrapService getService(Activity activity);
}
