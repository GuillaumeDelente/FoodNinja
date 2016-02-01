package io.foodninja.android;


import timber.log.Timber;

public class FoodNinjaApplicationImpl extends FoodNinjaApplication {

    @Override
    protected void onAfterInjection() {

    }

    @Override
    protected void init() {
        Timber.plant(new Timber.DebugTree());
    }
}
