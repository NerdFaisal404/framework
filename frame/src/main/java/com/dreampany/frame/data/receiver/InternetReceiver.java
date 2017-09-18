package com.dreampany.frame.data.receiver;

import android.content.Context;
import android.content.Intent;

import com.dreampany.frame.data.callback.NetworkCallback;
import com.dreampany.frame.data.util.LogKit;
import com.dreampany.frame.data.enums.NetworkStatus;
import com.dreampany.frame.data.util.NetworkUtil;

/**
 * Created by air on 4/20/17.
 */

public final class InternetReceiver extends BaseReceiver {

    public final static String INTENT =
            "network.intent.action.INTERNET_CONNECTION_STATE_CHANGED";
    public final static String INTENT_EXTRA = "network.intent.extra.CONNECTED_TO_INTERNET";

    public InternetReceiver(Context context, NetworkCallback callback) {
        super(context, callback);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(INTENT)) {
            boolean connectedToInternet = intent.getBooleanExtra(INTENT_EXTRA, false);
            onPostReceive(context, connectedToInternet);
        }
    }

    public void onPostReceive(Context context, boolean connectedToInternet) {
        NetworkStatus networkStatus = (connectedToInternet) ? NetworkStatus.WIFI_CONNECTED_HAS_INTERNET
                : NetworkStatus.WIFI_CONNECTED_HAS_NO_INTERNET;

        LogKit.logInfo(getClass().getSimpleName(), networkStatus.toString());

        if (!hasChanged(networkStatus)) {
            return;
        }

        if (context != null && !NetworkUtil.isConnectedToWifi(context)) {
            return;
        }

        postStatus(networkStatus);
    }
}
