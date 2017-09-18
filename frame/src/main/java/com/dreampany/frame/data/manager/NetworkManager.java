package com.dreampany.frame.data.manager;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.dreampany.frame.data.callback.NetworkCallback;
import com.dreampany.frame.data.enums.NetworkStatus;
import com.dreampany.frame.data.event.NetworkEvent;
import com.dreampany.frame.data.receiver.InternetReceiver;
import com.dreampany.frame.data.receiver.NetworkReceiver;
import com.dreampany.frame.data.receiver.WifiSignalStrengthReceiver;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by nuc on 6/10/2017.
 */

public final class NetworkManager extends Manager implements NetworkCallback {

    private static NetworkManager instance;
    private Context context;
    private InternetManager internetManager;
    private NetworkReceiver networkReceiver;
    private InternetReceiver internetReceiver;
    private WifiSignalStrengthReceiver wifiSignalStrengthReceiver;
    private boolean wifiAccessPointsScanEnabled;
    public static NetworkStatus networkStatus = NetworkStatus.NONE;

    private Set<NetworkCallback> callbacks;

    private NetworkManager(Context context) {
        this.context = context.getApplicationContext();
        this.internetManager = new InternetManager(this.context);
        this.networkReceiver = new NetworkReceiver(this.context, internetManager, this);
        this.internetReceiver = new InternetReceiver(this.context, this);
        this.wifiSignalStrengthReceiver = new WifiSignalStrengthReceiver(this.context, this);
        callbacks = new HashSet<>();
    }

    synchronized public static NetworkManager onInstance(Context context) {
        if (instance == null) {
            instance = new NetworkManager(context);
        }
        return instance;
    }


    @Override
    public void start() {
        super.start();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        context.registerReceiver(networkReceiver, filter);

        filter = new IntentFilter();
        filter.addAction(InternetReceiver.INTENT);
        context.registerReceiver(internetReceiver, filter);

        if (wifiAccessPointsScanEnabled) {
            filter = new IntentFilter(WifiManager.RSSI_CHANGED_ACTION);
            context.registerReceiver(wifiSignalStrengthReceiver, filter);
            // start WiFi scan in order to refresh access point list
            // if this won't be called WifiSignalStrengthChanged may never occur
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.startScan();
        }
    }

    @Override
    public void stop() {
        super.stop();

        context.unregisterReceiver(networkReceiver);
        context.unregisterReceiver(internetReceiver);
        if (wifiAccessPointsScanEnabled) {
            context.unregisterReceiver(wifiSignalStrengthReceiver);
        }
    }

    @Override
    protected boolean looping() throws InterruptedException {
        return false;
    }

    @Override
    public void onInternet(boolean internet) {
        for (NetworkCallback callback : callbacks) {
            callback.onInternet(internet);
        }
    }

    @Override
    public void onNetworkEvent(NetworkEvent event) {
        boolean hasInternet = false;
        switch (event.getNetworkStatus()) {
            case WIFI_CONNECTED_HAS_INTERNET:
            case MOBILE_CONNECTED:
                hasInternet = true;
                break;
        }

        for (NetworkCallback callback : callbacks) {
            callback.onNetworkEvent(event);
            callback.onInternet(hasInternet);
        }
    }

    public void register(NetworkCallback callback) {
        callbacks.add(callback);
    }

    public void unregister(NetworkCallback callback) {
        callbacks.remove(callback);
    }

    public NetworkManager enableWifiScan() {
        this.wifiAccessPointsScanEnabled = true;
        return this;
    }

    public NetworkManager enableInternetCheck() {
        networkReceiver.enableInternetCheck();
        return this;
    }

    public NetworkManager setPingParameters(String host, int port, int timeoutInMs) {
        internetManager.setPingParameters(host, port, timeoutInMs);
        return this;
    }

    public boolean hasInternet() {
        return internetManager.hasInternet();
    }

    public boolean hasConnection() {
        return networkReceiver.hasConnection();
    }


/*    public static boolean isConnectedToWiFiOrMobileNetwork(Context context) {
        final String service = Context.CONNECTIVITY_SERVICE;
        final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(service);
        final NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo == null) {
            return false;
        }

        final boolean isWifiNetwork = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        final boolean isMobileNetwork = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;

        if (isWifiNetwork || isMobileNetwork) {
            return true;
        }

        return false;
    }*/

    /*    public static boolean onInternet() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }*/

}
