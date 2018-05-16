package com.packing.sorting;

import com.packing.models.Rectangle;

import java.util.Comparator;

public class AreaComparator implements Comparator<Rectangle> {

    @Override
    public int compare(Rectangle o1, Rectangle o2) {
        return (o2.getHeight()*o2.getWidth()) - (o1.getHeight()*o1.getWidth());
    }
}
