package com.example.deveshmittal.myapplication.volley;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;

public class VolleyLib {

    public static final String     LOG_TAG             = "VOLLEY_LIB";

    private static final String    DEFAULT_CACHE_DIR   = "volley";

    private static final int       MAX_MEM_CACHE       = (int) (Runtime.getRuntime().maxMemory() / 1024 / 8); // kilobytes

    private static final int       DISK_CACHE_MAX_SIZE = 20 * 1024 * 1024;                                   // bytes

    // This queue is used for making unisearch API calls only
    private static RequestQueue    sSearchRequestQueue;

    // This queue is used for downloading images and making general API calls
    private static RequestQueue    sGeneralRequestQueue;

    private static RequestQueue    sJobRequestQueue;

    private static RequestQueue    sImageRequestQueue;

    // This queue is used for getting authenticated urls only
    private static RequestQueue    sPlayerRequestQueue;

    private static MemoryCache     sMemoryCache;

    private static DiskCache       sDiskCache;


    public static void init(Context context) {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        // TODO: new DiskCache(context, cacheDir, DISK_CACHE_MAX_SIZE);
        sDiskCache = new DiskCache(context, cacheDir, DISK_CACHE_MAX_SIZE);

        sMemoryCache = new MemoryCache(MAX_MEM_CACHE);
        sGeneralRequestQueue = newGeneralRequestQueue();
        sJobRequestQueue = newJobRequestQueue();
        sImageRequestQueue = newImageRequestQueue();
        sSearchRequestQueue = newSearchRequestQueue();
        sPlayerRequestQueue = newPlayerRequestQueue();

    }

    private static void setDefaultCookieManager() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        CookieHandler.setDefault(cookieManager);
    }

    private static RequestQueue newGeneralRequestQueue() {
        RequestQueue queue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()), 4);
        queue.start();
        return queue;
    }

    private static RequestQueue newJobRequestQueue() {
        RequestQueue queue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()), 4);
        queue.start();
        return queue;
    }

    private static RequestQueue newImageRequestQueue() {
        RequestQueue queue = new RequestQueue(sDiskCache, new BasicNetwork(new HurlStack()), 10);
        queue.start();
        return queue;
    }

    private static RequestQueue newSearchRequestQueue() {
        RequestQueue queue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()), 1);
        queue.start();
        return queue;
    }

    private static RequestQueue newPlayerRequestQueue() {
        RequestQueue queue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()), 1);
        queue.start();
        return queue;
    }

    public static RequestQueue getSearchRequestQueue() {
        return sSearchRequestQueue;
    }

    public static RequestQueue getRequestQueue() {
        return sGeneralRequestQueue;
    }

    public static RequestQueue getJobRequestQueue() {
        return sJobRequestQueue;
    }

    public static RequestQueue getImageRequestQueue() {
        return sImageRequestQueue;
    }

    public static RequestQueue getPlayerRequestQueue() {
        return sPlayerRequestQueue;
    }


    public static boolean diskCacheContains(String key) {
        return sDiskCache.mEntries.containsKey(key);
    }

    public static void trimMemory(float ratio) {
        if (ratio == 1.0f) {
            sMemoryCache.evictAll();
        } else {
            int size = sMemoryCache.size();
            sMemoryCache.trimToSize((int) (size * ratio));
        }
    }

    public enum VolleyRequestQueue {
        GENERAL_QUEUE {
            @Override
            public RequestQueue getQueue() {
                return VolleyLib.getRequestQueue();
            }
        },
        SEARCH_QUEUE {
            @Override
            public RequestQueue getQueue() {
                return VolleyLib.getSearchRequestQueue();
            }
        },
        JOB_QUEUE {
            @Override
            public RequestQueue getQueue() {
                return VolleyLib.getJobRequestQueue();
            }
        },
        IMAGE_QUEUE {
            @Override
            public RequestQueue getQueue() {
                return VolleyLib.getImageRequestQueue();
            }
        },
        PLAYER_QUEUE {
            @Override
            public RequestQueue getQueue() {
                return VolleyLib.getPlayerRequestQueue();
            }
        };

        public abstract RequestQueue getQueue();
    }
}
