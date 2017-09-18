package com.dreampany.frame.data.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Looper;

import com.dreampany.frame.data.callback.NetworkCallback;
import com.dreampany.frame.data.manager.NetworkManager;
import com.dreampany.frame.data.util.AndroidUtil;
import com.dreampany.frame.data.enums.NetworkStatus;
import com.dreampany.frame.data.event.ConnectivityChanged;
import com.dreampany.frame.data.event.NetworkEvent;

public abstract class BaseReceiver extends BroadcastReceiver {
    private final Context context;
    private NetworkCallback callback;

    public BaseReceiver(Context context, NetworkCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    protected boolean hasChanged(NetworkStatus networkStatus) {
        return NetworkManager.networkStatus != networkStatus;
    }

    protected void postStatus(NetworkStatus networkStatus) {
        NetworkManager.networkStatus = networkStatus;
        post(new ConnectivityChanged(context, networkStatus));
    }

    protected void post(final NetworkEvent event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            callback.onNetworkEvent(event);
        } else {
            AndroidUtil.post(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkEvent(event);
                }
            });
        }
    }
}