package com.packing.sorting;

import com.packing.models.Rectangle;

import java.util.Comparator;

public class DecreaseWidthComparator implements Comparator<Rectangle> {


    @Override
    public int compare(Rectangle o1, Rectangle o2) {
        return o1.getWidth() - o2.getWidth();
    }
}
