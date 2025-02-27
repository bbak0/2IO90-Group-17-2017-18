package com.packing.sorting;

import com.packing.models.Rectangle;

import java.util.Comparator;

//when rotations are not allowed
public class DESCSS_NR implements Comparator<Rectangle> {
    @Override
    public int compare(Rectangle o1, Rectangle o2) {
        int shortO1 = Math.min(o1.width, o1.height);
        int shortO2 = Math.min(o2.width, o2.height);
        int longO1 = Math.max(o1.width, o1.height);
        int longO2 = Math.max(o2.width, o2.height);
        if (longO1 == longO2) {
            return shortO2 - shortO1;
        } else {
            return longO2 - longO1;
        }
    }
}
