package com.example.deveshmittal.myapplication.volley;

import java.util.Comparator;

public class ImageDimen {
    private final int width;

    private final int height;

    public ImageDimen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static class ImageDimenComparator implements Comparator<ImageDimen> {
        @Override
        public int compare(ImageDimen lhs, ImageDimen rhs) {
            return lhs.getWidth() - rhs.getWidth();
        }
    }
}
