package com.example.deveshmittal.myapplication.volley;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import android.content.Context;

import com.example.deveshmittal.myapplication.common.DownloadUtils;
import com.example.deveshmittal.myapplication.common.LogUtils;


public class DiskCache extends DiskBasedCache {
    private static final String          LOG_TAG = "DISK_CACHE";

    private final WeakReference<Context> mContext;

    public DiskCache(Context context, File rootDirectory, int maxCacheSizeInBytes) {
        super(rootDirectory, maxCacheSizeInBytes);

        mContext = new WeakReference<Context>(context);
    }

    public boolean contains(String key) {
        return mEntries.containsKey(key);
    }

    @Override
    public synchronized Entry get(String key) {


        Entry entry = super.get(key);
        Context context = mContext.get();

        if (entry == null && context != null) {
            // The bitmap wasn't found in the Disk Cache. Check the file storage
            // area for image of offline songs.

            // Note: Volley uses the url as the key.
            try {
                LogUtils.infoLog(LOG_TAG, "in try of get of DiskCache");
                byte[] bytes = DownloadUtils.getImageBytesOffline(context, key);

                if (bytes == null) {
                    LogUtils.infoLog(LOG_TAG, "in try of get of DiskCache, bytes = null");
                    return null;
                }

              //  if (LogUtils.isDebugLogEnabled())
              //      LogUtils.debugLog(LOG_TAG, "Serving bitmap from file");

                return createFakeHeaderEntry(bytes);
            } catch (IOException e) {
                LogUtils.errorLog(LOG_TAG, "Error", e);
            }
        }

        return entry;
    }

    public static Entry createFakeHeaderEntry(byte[] data) throws IOException {
        LogUtils.infoLog(LOG_TAG, "in createFakeHeaderEntry of DiskCache");
        Entry e = new Entry();
        e.data = data;
        e.ttl = Long.MAX_VALUE;
        e.softTtl = Long.MAX_VALUE;
        return e;

        // e.etag = etag;
        // e.serverDate = serverDate;
        // e.responseHeaders = responseHeaders;
    }
}
