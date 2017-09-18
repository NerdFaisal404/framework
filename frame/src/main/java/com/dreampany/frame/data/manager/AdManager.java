package com.dreampany.frame.data.manager;

import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.dreampany.frame.data.callback.NetworkCallback;
import com.dreampany.frame.data.enums.Type;
import com.dreampany.frame.data.event.NetworkEvent;
import com.dreampany.frame.data.model.AdEvent;
import com.dreampany.frame.data.util.AndroidUtil;
import com.dreampany.frame.data.util.DataUtil;
import com.dreampany.frame.data.util.TimeUtil;

/**
 * Created by nuc on 10/26/2016.
 */

public final class AdManager extends Manager implements NetworkCallback {

    private static AdManager manager;
    private Context context;

    private enum State {NONE, LOADED, CLOSED, FAILED, LEFT, OPENED}

    private static final long defaultAdDelay = 1500L;
    private static final long interstitialAdPeriod = TimeUtil.minuteToMilli(5L);
    private long lastInterstitialAdTime;

    private AdView bannerAdView;
    private InterstitialAd interstitialAd;

    private State bannerState = State.NONE;
    private State interstitialState = State.NONE;

    private long bannerPoints = 50L;
    private long interstitialPoints = 100L;

    private Type pointType;

    private AdManager(Context context) {
        this.context = context.getApplicationContext();
        lastInterstitialAdTime = TimeUtil.currentTime();
    }

    synchronized public static AdManager onInstance(Context context) {
        if (manager == null) {
            manager = new AdManager(context);
        }
        return manager;
    }

    public void init(Type pointType) {
        this.pointType = pointType;
    }

    @Override
    public void start() {
        super.start();
        NetworkManager.onInstance(context).register(this);
    }

    @Override
    public void stop() {
        super.stop();
        NetworkManager.onInstance(context).unregister(this);
    }

    @Override
    protected boolean looping() throws InterruptedException {
        return false;
    }

    @Override
    public void onInternet(boolean internet) {
        if (internet) {
            if (!isBannerLoaded()) {
                AndroidUtil.post(new Runnable() {
                    @Override
                    public void run() {
                        loadBanner(bannerAdView);
                    }
                });
            }
        }
    }

    @Override
    public void onNetworkEvent(NetworkEvent event) {

    }

    public boolean isBannerLoaded() {
        return bannerState == State.LOADED;
    }

    public boolean isInterstitialLoaded() {
        return interstitialState == State.LOADED;
    }

    public void loadBanner(AdView adView) {
        if (adView == null) {
            return;
        }

        bannerAdView = adView;
        if (!AndroidUtil.isDebug()) {
            // NotifyUtil.shortToast(adView.getContext(), "loading");
            load(adView);
        }
    }

    public void loadInterstitial(final int adUnitId) {

        if (interstitialAd == null) {
            interstitialAd = new InterstitialAd(context);
            interstitialAd.setAdUnitId(context.getString(adUnitId));
        }

        if (!matchedTime()) {
            return;
        }

        if (!AndroidUtil.isDebug()) {
            load(interstitialAd);
        }
    }

    private void load(final AdView adView) {
        if (adView.getAdListener() == null) {
            adView.setAdListener(bannerListener);
        }
        if (NetworkManager.onInstance(context).hasInternet()) {
            adView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    View view = (View) adView.getParent();
                    view.setVisibility(View.VISIBLE);
                    adView.loadAd(new AdRequest.Builder().build());
                }
            }, defaultAdDelay);
        }
    }

    private void load(final InterstitialAd interstitialAd) {
        if (interstitialAd.getAdListener() == null) {
            interstitialAd.setAdListener(interstitialListener);
        }
        if (NetworkManager.onInstance(context).hasInternet()) {
            AndroidUtil.post(new Runnable() {
                @Override
                public void run() {
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                }
            }, defaultAdDelay);

        }
    }

    private boolean matchedTime() {
        long adPeriod = TimeUtil.currentTime() - lastInterstitialAdTime;
        return adPeriod > interstitialAdPeriod;
    }

/*    public void loadTestBannerAd(AdView adView) {
        View view = (View) adView.getParent();
        view.setVisibility(View.VISIBLE);
        adView.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4")
                .addTestDevice("BE5D7D1E701EF21AB93369A353CAA3ED")
                .addTestDevice("921DF5A672991967B9FFE364D0FF8498")
                .addTestDevice("A642C45F5DD4C0E09AA896DDABD36789")
                .addTestDevice("5270E2092AA1F46AC51964363699AB9E")
                .build());
    }*/

/*    public static void loadDelayBannerAd(final AdView adView) {
        if (adView == null) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (adView == null) return;
                adView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adView != null) {
                            View view = (View) adView.getParent();
                            if (NetworkManager.onInternet()) {
                                view.setVisibility(View.VISIBLE);
                                adView.loadAd(new AdRequest.Builder().build());
                            }
                        }
                    }
                }, defaultAdDelay);
            }
        }).start();
    }*/

/*    public static void loadBannerAd(final AdView adView) {
        if (adView == null) return;
        if (NetworkManager.hasNetwork(adView.getContext())) {
            adView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (adView != null) {
                        View view = (View) adView.getParent();
                        view.setVisibility(View.VISIBLE);
                        adView.loadAd(new AdRequest.Builder().build());
                    }
                }
            }, defaultAdDelay);
        }
    }*/

/*    private static InterstitialAd interstitial = null;
    private static final Object lock = new Object();

    private static InterstitialAd getInstance(final Context context, int stringRef) {
        synchronized (lock) {
            if (interstitial != null)
                return interstitial;

            if (context != null) {
                interstitial = new InterstitialAd(context);
                interstitial.setAdUnitId(context.getString(stringRef));
            }

            return interstitial;
        }
    }*/

    /*public static void toInterstitialAd(final Context context) {
        final InterstitialAd interstitialAd = getInstance(context);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (isToInterstitialAd(context)) {
                    interstitialAd.show();
                }
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                toInterstitialAdTime(context);
            }
        });
    }


    public static boolean isToInterstitialAd(Context context) {
        long period = Constant.INTERSTITIAL_AD_TIME_PERIOD;
        long currentTime = TimeUtil.currentTime();
        long interstitialAdTime = PrefManager.onInstance(context).getInterstitialAdTime();

        long adPeriod = currentTime - interstitialAdTime;

        return adPeriod > period;
    }

    public static void toInterstitialAdTime(Context context) {
        long currentTime = TimeUtil.currentTime();
        PrefManager.onInstance(context).setInterstitialAdTime(currentTime);
    }*/

    private final AdListener bannerListener = new AdListener() {
        @Override
        public void onAdClosed() {
            super.onAdClosed();
            bannerState = State.CLOSED;
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
            bannerState = State.LOADED;
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
            bannerState = State.LEFT;
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
            bannerState = State.OPENED;
            PointManager.onInstance().trackPoints(context, DataUtil.getSha256(), bannerPoints, pointType);

            AdEvent event = new AdEvent();
            event.points = bannerPoints;
            event.type = pointType;

            EventManager.post(event);
        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            bannerState = State.LOADED;
        }
    };

    private final AdListener interstitialListener = new AdListener() {
        @Override
        public void onAdClosed() {
            super.onAdClosed();
            interstitialState = State.CLOSED;
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
            interstitialState = State.LOADED;
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
            interstitialState = State.LEFT;
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
            interstitialState = State.OPENED;
            PointManager.onInstance().trackPoints(context, DataUtil.getSha256(), interstitialPoints, pointType);
        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            interstitialState = State.LOADED;

            interstitialAd.show();
            lastInterstitialAdTime = TimeUtil.currentTime();
        }
    };
}
