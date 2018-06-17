package com.packing.sorting;

import com.packing.models.Rectangle;

import java.util.Comparator;

public class IncreaseWidthComparator implements Comparator<Rectangle> {
    @Override
    public int compare(Rectangle o1, Rectangle o2) {
        return o2.getWidth() - o1.getWidth();
    }
}
