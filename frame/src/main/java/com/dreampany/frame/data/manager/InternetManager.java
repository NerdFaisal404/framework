package com.dreampany.frame.data.manager;

import android.content.Context;
import android.content.Intent;

import com.dreampany.frame.data.receiver.InternetReceiver;
import com.dreampany.frame.data.util.NetworkUtil;

import java.io.IOException;

public class InternetManager {

    private static final String DEFAULT_PING_HOST = "www.google.com";
    private static final int DEFAULT_PING_PORT = 80;
    private static final int DEFAULT_PING_TIMEOUT_IN_MS = 2000;

    private final Context context;
    private String pingHost;
    private int pingPort;
    private int pingTimeout;
    private boolean hasInternet;

    public InternetManager(Context context) {
        this.context = context;
        this.pingHost = DEFAULT_PING_HOST;
        this.pingPort = DEFAULT_PING_PORT;
        this.pingTimeout = DEFAULT_PING_TIMEOUT_IN_MS;
    }

    public void check() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    hasInternet = NetworkUtil.hasInternet(pingHost, pingPort, pingTimeout);
                } catch (IOException e) {
                    hasInternet = false;
                } finally {
                    sendBroadcast(hasInternet);
                }
            }
        }).start();
    }

    public void setPingParameters(String host, int port, int timeoutInMs) {
        this.pingHost = host;
        this.pingPort = port;
        this.pingTimeout = timeoutInMs;
    }

    public boolean hasInternet() {
        return hasInternet;
    }

    private void sendBroadcast(boolean hasInternet) {
        Intent intent = new Intent(InternetReceiver.INTENT);
        intent.putExtra(InternetReceiver.INTENT_EXTRA, hasInternet);
        context.sendBroadcast(intent);
    }

}