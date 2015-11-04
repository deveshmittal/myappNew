package com.example.deveshmittal.myapplication.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.example.deveshmittal.myapplication.MyApplication;
import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.ConnectionQuality;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by deveshmittal on 04/11/15.
 */


public class NetworkManager implements ConnectionClassManager.ConnectionClassStateChangeListener {

    private static final String         LOG_TAG                   = "NETWORK_MANAGER";

    private boolean                     mConnected;

    private int                         mNetworkType;

    private int                         mNetworkSubtype;

    private int                         mNetworkTypeForApi;

    private int                         mNetworkQuality;

    private Set<ConnectivityListener> mListeners                = new HashSet<>();

    private static final NetworkManager INSTANCE                  = new NetworkManager();

    public static final int             NETWORK_QUALITY_UNKNOWN   = -1;

    public static final int             NETWORK_QUALITY_POOR      = 1;

    public static final int             NETWORK_QUALITY_MODERATE  = 2;

    public static final int             NETWORK_QUALITY_GOOD      = 3;

    public static final int             NETWORK_QUALITY_EXCELLENT = 4;

    public static NetworkManager getInstance() {
        return INSTANCE;
    }

    private NetworkManager() {
        MyApplication.getContext().registerReceiver(new ConnectivityBroadcastReceiver(),
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        ConnectionClassManager.getInstance().register(this);
        boolean connected = NetworkUtils.isConnected();
        NetworkInfo networkInfo = NetworkUtils.getActiveNetworkInfo();
        int networkType = -1;
        int networkSubtype = -1;
        if (networkInfo != null) {
            networkType = networkInfo.getType();
            networkSubtype = networkInfo.getSubtype();
        }
        int networkQuality = NETWORK_QUALITY_UNKNOWN;
        if (connected) {
            networkQuality = NetworkManager.getNetworkQualityInt(ConnectionClassManager.getInstance()
                    .getCurrentBandwidthQuality());
        }
        setNetworkParams(connected, networkType, networkSubtype, networkQuality);
    }

    @Override
    public void onBandwidthStateChange(ConnectionQuality bandwidthState) {
        setNetworkQuality(NetworkManager.getNetworkQualityInt(bandwidthState));
    }

    public interface ConnectivityListener {
        void onConnectivityChanged(boolean connected, int networkType, int networkSubtype);
    }

    private class ConnectivityBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean connected = NetworkUtils.isConnected();
            NetworkInfo networkInfo = NetworkUtils.getActiveNetworkInfo();
            int networkType = -1;
            int networkSubtype = -1;
            if (networkInfo != null) {
                networkType = networkInfo.getType();
                networkSubtype = networkInfo.getSubtype();
            }

            if (mConnected == connected && mNetworkType == networkType && mNetworkSubtype == networkSubtype) {
                LogUtils.debugLog(LOG_TAG, "No network change detected");
                return;
            }

            if (!connected) {
                ConnectionClassManager.getInstance().reset();
                setNetworkQuality(NETWORK_QUALITY_UNKNOWN);
            }

            setNetworkParams(connected, networkType, networkSubtype, mNetworkQuality);

            for (ConnectivityListener listener : mListeners) {
                listener.onConnectivityChanged(mConnected, mNetworkType, mNetworkSubtype);
            }
        }
    }

    private void setNetworkParams(boolean connected, int networkType, int networkSubtype, int networkQuality) {
        mConnected = connected;
        mNetworkType = networkType;
        mNetworkSubtype = networkSubtype == TelephonyManager.NETWORK_TYPE_UNKNOWN ? -1 : networkSubtype;
        mNetworkQuality = networkQuality;
        if (connected) {
            if (NetworkUtils.isConnectedToMI()) {
                mNetworkTypeForApi = ApiConstants.Network.NETWORK_TYPE_MOBILE;
            } else {
                mNetworkTypeForApi = ApiConstants.Network.NETWORK_TYPE_OTHER;
            }
        } else {
            mNetworkTypeForApi = ApiConstants.Network.NETWORK_NOT_CONNECTED;
        }

        LogUtils.debugLog(LOG_TAG, "connected:" + mConnected + ", networkType:" + mNetworkType + ", networkSubtype:"
                + mNetworkSubtype + ", apiNetworkType:" + mNetworkTypeForApi + ", networkQuality: " + networkQuality);
    }

    public void addListener(ConnectivityListener listener) {
        mListeners.add(listener);
    }

    public void removeListener(ConnectivityListener listener) {
        mListeners.remove(listener);
    }

    public int getNetworkTypeForApi() {
        return mNetworkTypeForApi;
    }

    public int getNetworkSubtypeForApi() {
        return mNetworkSubtype;
    }

    public int getNetworkQuality() {
        return mNetworkQuality;
    }

    private static int getNetworkQualityInt(ConnectionQuality quality) {
        switch (quality) {
            case POOR:
                return 1;
            case MODERATE:
                return 2;
            case GOOD:
                return 3;
            case EXCELLENT:
                return 4;
            case UNKNOWN:
            default:
                return -1;
        }
    }

    private void setNetworkQuality(int networkQuality) {
        mNetworkQuality = networkQuality;
        LogUtils.infoLog(LOG_TAG, "Network quality changed: " + networkQuality + ", Bandwidth: "
                + ConnectionClassManager.getInstance().getDownloadKBitsPerSecond());
    }
}