package com.example.deveshmittal.myapplication.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.example.deveshmittal.myapplication.MyApplication;

public class NetworkUtils {

    public static NetworkInfo getActiveNetworkInfo() {
        Context context = MyApplication.getContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        // TODO Do we really need isConnectedOrConnecting?
        return info != null && info.isConnectedOrConnecting();
    }

    public static boolean isConnectedToMI() {
        if (!isConnected()) {
            return false;
        }

        NetworkInfo info = getActiveNetworkInfo();
        return info != null && isNetworkMobileData(info.getType());
    }

    public static boolean isConnectedToWiFi() {
        if (!isConnected()) {
            return false;
        }

        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    private static boolean isNetworkMobileData(int networkType) {
        switch (networkType) {
            case ConnectivityManager.TYPE_MOBILE:
            case ConnectivityManager.TYPE_MOBILE_DUN:
            case ConnectivityManager.TYPE_MOBILE_HIPRI:
            case ConnectivityManager.TYPE_MOBILE_MMS:
            case ConnectivityManager.TYPE_MOBILE_SUPL:
                return true;
            default:
                return false;
        }
    }

    /**
     * Check if there is fast connectivity
     * 
     * @return
     */
    public static boolean isConnectedFast() {
        int networkQuality = NetworkManager.getInstance().getNetworkQuality();
        if (networkQuality != NetworkManager.NETWORK_QUALITY_UNKNOWN) {
            return (networkQuality > NetworkManager.NETWORK_QUALITY_MODERATE);
        } else {
            NetworkInfo info = getActiveNetworkInfo();
            return (info != null && info.isConnected() && NetworkUtils.isConnectionFast(info.getType(),
                    info.getSubtype()));
        }
    }

    /**
     * Check if the connection is fast
     * 
     * @param type
     * @param subType
     * @return
     */
    // Courtsey: https://gist.github.com/emil2k/5130324
    private static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                    /*
                     * Above API level 7, make sure to set
                     * android:targetSdkVersion to appropriate level to use
                     * these
                     */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return true; // ~ 10+ Mbps
                    // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isConnectedTo2G() {
        NetworkInfo info = getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        if (!info.isConnected()) {
            return false;
        }
        int type = info.getType();
        int subType = info.getSubtype();
        if (type == ConnectivityManager.TYPE_WIFI) {
            return false;
        }
        if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return true; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return true; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return true; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return false; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return false; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return true; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return false; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return false; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return false; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return false; // ~ 400-7000 kbps
                    /*
                     * Above API level 7, make sure to set
                     * android:targetSdkVersion to appropriate level to use
                     * these
                     */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return false; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return false; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return false; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return true; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return false; // ~ 10+ Mbps
                    // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        }
        return false;
    }
}
