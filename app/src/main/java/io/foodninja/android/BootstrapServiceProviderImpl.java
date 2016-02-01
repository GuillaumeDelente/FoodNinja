
package io.foodninja.android;

import android.accounts.AccountsException;
import android.app.Activity;

import java.io.IOException;

import io.foodninja.android.core.BootstrapService;
import retrofit.RestAdapter;

/**
 * Provider for a {@link BootstrapService} instance
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
