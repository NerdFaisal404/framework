package com.dreampany.frame.data.callback;

import com.dreampany.frame.data.event.NetworkEvent;

/**
 * Created by nuc on 6/11/2017.
 */

public interface NetworkCallback {
    void onInternet(boolean internet);
    void onNetworkEvent(NetworkEvent event);
}
