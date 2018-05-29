package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.Solution;
import com.packing.sorting.IndexComparator;
import com.packing.sorting.WidthComparator;

import java.util.ArrayList;

import com.packing.models.BTP;

import java.util.Collections;
import java.util.Iterator;


public class BTRun extends AbstractAlgorithm {


    public BTRun(Data in) {
        super(in);
    }

    @Override
    public Solution solve() {

        boolean rotAllowed = input.isRotationsAllowed();
        boolean fixH = input.isContainerHeightFixed();
        int fixedHeight = 1000;
       /*if(fixH)
        {
             fixedHeight = input.getContainerHeight();

        } */

        BTP packer = new BTP (1000, fixedHeight);
        ArrayList<Rectangle> rectangleCollection = input.getRectangles();

        if (rotAllowed)
            for (Rectangle rectangle : rectangleCollection) {
                if (rectangle.height > rectangle.width) {
                    int x = rectangle.width;
                    rectangle.width = rectangle.height;
                    rectangle.height = x;
                    rectangle.isRotated = true;
                }
            }


        Collections.sort(rectangleCollection, new WidthComparator());

        packer.fit(rectangleCollection);
        Iterator<Rectangle> rectangleIterator = rectangleCollection.iterator();
        while (rectangleIterator.hasNext()) {
            Rectangle rectangle = rectangleIterator.next();
            if (rectangle.fit != null) {
                int fitX = rectangle.fit.x;
                int fitY = rectangle.fit.y;
                rectangle.placeRectangle(fitX, fitY);


            }
        }
        Collections.sort(rectangleCollection, new IndexComparator());
        return new Solution(rectangleCollection);
    }
}
