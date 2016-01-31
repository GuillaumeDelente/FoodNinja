package com.donnfelker.android.bootstrap;

import com.donnfelker.android.bootstrap.ui.BootstrapActivity;
import com.donnfelker.android.bootstrap.ui.BootstrapFragmentActivity;
import com.donnfelker.android.bootstrap.ui.MainActivity;

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

    void inject(BootstrapFragmentActivity target);

    void inject(BootstrapActivity target);

}
