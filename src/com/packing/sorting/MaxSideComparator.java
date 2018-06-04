package com.packing.sorting;

import com.packing.models.Rectangle;

import java.util.Comparator;

public class MaxSideComparator implements Comparator<Rectangle> {

    @Override
    public int compare(Rectangle o1, Rectangle o2) {
       int h  =   o2.getHeight() -    o1.getHeight();
       int w  =   o2.getWidth()   -   o1.getWidth();
       if(h>w)
           return h;
       else
           return w;
    }
}
