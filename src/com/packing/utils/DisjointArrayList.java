package com.packing.utils;

import com.packing.models.Rectangle;

import java.util.ArrayList;

public class DisjointArrayList extends ArrayList<Rectangle> {
    @Override
    public boolean add(Rectangle a){
        for (Rectangle b : this){
            if (!isDisjoint(a, b)){
                throw new IllegalStateException("Overlap Detected");
            }
            System.out.println("insert a: " + a + " checking disjoint with " + b);

        }
        super.add(a);


        return true;
    }

    public static boolean isDisjoint(Rectangle a, Rectangle b){
        if (a.x + a.width <= b.x ||
                b.x + b.width <= a.x ||
                a.y + a.height <= b.y ||
                b.y + b.height <= a.y){
            return true;
        }
        return false;
    }
}
