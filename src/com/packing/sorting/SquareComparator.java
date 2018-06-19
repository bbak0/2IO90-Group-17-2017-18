package com.packing.sorting;

import com.packing.models.Rectangle;

import java.util.Comparator;

public class SquareComparator implements Comparator<Rectangle> {
    @Override
    public int compare(Rectangle o1, Rectangle o2) {
        int a = o1.width + o1.height + (int) Math.sqrt(Math.pow(o1.width + o1.height, 2));
        int b = o2.width + o2.height + (int) Math.sqrt(Math.pow(o2.width + o2.height, 2));
        return b - a;
    }
}
