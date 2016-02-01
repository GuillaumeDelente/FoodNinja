package io.foodninja.android;

import io.foodninja.android.AndroidModule;
import io.foodninja.android.BootstrapApplication;
import io.foodninja.android.BootstrapModule;
import io.foodninja.android.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AndroidModule.class,
                BootstrapModule.class
        }
)
public interface BootstrapComponent {

    void inject(BootstrapApplication target);

    void inject(MainActivity target);

}