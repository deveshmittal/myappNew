package com.example.deveshmittal.myapplication.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StatFs;
import android.support.v4.content.ContextCompat;



public class DownloadUtils {

    private static final String IMAGES_FOLDER          = "images";

    public static final String  PATH_SEPARATOR         = "/";

    private static final float  MIN_SPACE_REQUIRED     = 15.0f;

    private static final String TEMP_FOLDER            = ".temp";

    private static final long   MIN_RENTED_FILE_LENGTH = 10 * 1024;

    private static String       LOG_TAG                = "DOWNLOAD_UTILS";

    public enum DownloadMode {
        RENT_MODE("RENT_MODE"), NONE("NONE");

        private final String                     id;

        private static Map<String, DownloadMode> idToStateMap = new HashMap<String, DownloadMode>();

        static {
            for (DownloadMode state : DownloadMode.values()) {
                idToStateMap.put(state.getId(), state);
            }
        }

        private DownloadMode(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public static DownloadMode getDownloadMode(String id) {
            return idToStateMap.get(id);
        }
    }


    public static String getSecureInternalDirPath(Context context) {
        if (context != null) {
            File path = new File(context.getFilesDir().getAbsolutePath() + PATH_SEPARATOR + TEMP_FOLDER);
            path.mkdirs();
            return path.getAbsolutePath();
        }
        return null;
    }

    private static boolean isRentedFileValid(String songId, File file) {
        if (file.exists()) {
            if (file.isFile()) {
                long size = file.length();
                if (size < MIN_RENTED_FILE_LENGTH) {
               //     FileUtils.deleteFileOrDirectory(file);
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static ArrayList<String> getRentDirPaths(Context context) {
        ArrayList<String> paths = new ArrayList<>();

        // We may have more than one storage devices
        File[] dirs = ContextCompat.getExternalFilesDirs(context, null);

        if (dirs != null) {
            for (File dir : dirs) {
                if (dir == null) {
                    LogUtils.infoLog(LOG_TAG, "dir unavailable");
                } else {
                    paths.add(dir.getAbsolutePath());
                }
            }
        }

        if (paths.size() == 0) {
            LogUtils.debugLog(LOG_TAG, "Couldn't find path for rentDirPaths");
        } else if (paths.size() > 1) {
            // Use secondary storage over built-in storage
            String first = paths.remove(0);
            paths.add(first);
        }

        return paths;
    }

    public static boolean isSpaceAvailable(String path) {
        return getFreeSpaceInMBs(path) >= MIN_SPACE_REQUIRED ? true : false;
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static double getFreeSpaceInMBs(String path) {
        try {
            StatFs stat = new StatFs(path);
            float availableMBs = 0f;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                availableMBs = (stat.getAvailableBlocksLong() / 1024f) * (stat.getBlockSizeLong() / 1024f);
            else
                availableMBs = (stat.getAvailableBlocks() / 1024f) * (stat.getBlockSize() / 1024f);

            // LogUtils.debugLog(LOG_TAG, "Available space: " + availableMBs);

            return availableMBs;
        } catch (IllegalArgumentException e) {
            LogUtils.errorLog(LOG_TAG, "The path doesn't exist", e);
            return -1;
        }
    }

    public static boolean isPathAvailable(String path) {
        boolean isWritable = isPathWritable(path);
        boolean isSpaceAvailable = isSpaceAvailable(path);

        boolean available = isSpaceAvailable && isWritable;


        return available;
    }

    public static boolean isPathWritable(String path) {
        return new File(path).canWrite();
    }

    public static byte[] getLocalImageBytes(Context context, String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);
            fis.close();
            return buffer;
        }
        return null;
    }

    public static byte[] getImageBytes(Context context, String url) throws IOException {
        String fileName = String.valueOf(url.hashCode());

        for (String path : getImagesDirPaths(context)) {
            String pathName = path + PATH_SEPARATOR + fileName;
            File file = new File(pathName);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[(int) file.length()];
                fis.read(buffer);
                fis.close();
                return buffer;
            }
        }
        return null;
    }

    public static byte[] getImageBytesOffline(Context context, String path) throws IOException {

        File file = new File(path);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);
            fis.close();
            return buffer;
        }
        return null;
    }

    public static ArrayList<String> getImagesDirPaths(Context context) {
        ArrayList<String> paths = getRentDirPaths(context);
        ArrayList<String> imageDirPaths = new ArrayList<>();

        for (String path : paths) {
            imageDirPaths.add(path + PATH_SEPARATOR + IMAGES_FOLDER);
        }

        return imageDirPaths;
    }



    /**
     * Downloads the image to the disk using the provided url. The method
     * performs file IO.
     *
     * @param context
     * @param url
     */

}
