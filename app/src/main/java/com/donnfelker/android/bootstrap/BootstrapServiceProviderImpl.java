
package com.donnfelker.android.bootstrap;

import android.accounts.AccountsException;
import android.app.Activity;

import com.donnfelker.android.bootstrap.core.BootstrapService;

import java.io.IOException;

import retrofit.RestAdapter;

/**
 * Provider for a {@link com.donnfelker.android.bootstrap.core.BootstrapService} instance
 */
public class BootstrapServiceProviderImpl implements BootstrapServiceProvider {

    private RestAdapter restAdapter;

    public BootstrapServiceProviderImpl(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    /**
     * Get service for configured key provider
     * <p/>
     * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
     *
     * @return bootstrap service
     * @throws IOException
     * @throws AccountsException
     */
    @Override
    public BootstrapService getService(final Activity activity) {
        return new BootstrapService(restAdapter);
    }
}
