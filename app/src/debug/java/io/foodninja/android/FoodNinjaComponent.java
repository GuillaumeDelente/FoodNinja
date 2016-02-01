package io.foodninja.android;

import io.foodninja.android.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AndroidModule.class,
                FoodNinjaModule.class
        }
)
public interface FoodNinjaComponent {

    void inject(FoodNinjaApplication target);

    void inject(MainActivity target);

}