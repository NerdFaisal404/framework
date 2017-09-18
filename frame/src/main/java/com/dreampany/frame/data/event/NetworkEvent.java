package com.dreampany.frame.data.event;

import com.dreampany.frame.data.enums.NetworkStatus;

/**
 * Created by nuc on 6/11/2017.
 */

public class NetworkEvent {
    protected final NetworkStatus networkStatus;

    protected NetworkEvent(NetworkStatus networkStatus) {
        this.networkStatus = networkStatus;
    }

    public NetworkStatus getNetworkStatus() {
        return networkStatus;
    }
}
