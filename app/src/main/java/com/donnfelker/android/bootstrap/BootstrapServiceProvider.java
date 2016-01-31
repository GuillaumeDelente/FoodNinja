package com.donnfelker.android.bootstrap;

import android.app.Activity;

import com.donnfelker.android.bootstrap.core.BootstrapService;

public interface BootstrapServiceProvider {
    BootstrapService getService(Activity activity);
}
