package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.sorting.HeightComparator;

import java.util.ArrayList;
import java.util.Collections;

public class NFDHAlgo extends AbstractAlgorithm {

    public NFDHAlgo(Data in) {
        super(in);
    }

    @Override
    public void solve() {
        ArrayList<Rectangle> rectangleCollection = input.getRectangles();
        Collections.sort(rectangleCollection, new HeightComparator());
        System.out.print("xd");
    }
}
