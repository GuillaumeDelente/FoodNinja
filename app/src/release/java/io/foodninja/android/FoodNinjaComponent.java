package io.foodninja.android;

import javax.inject.Singleton;

import dagger.Component;
import io.foodninja.android.ui.MainActivity;


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