package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.Solution;
import com.packing.sorting.*;

import java.util.ArrayList;

import com.packing.algo.BTP;

import java.util.Collections;
import java.util.Iterator;


public class BTRun extends AbstractAlgorithm {

    Data inputCopy, inputCopy2;

    public BTRun(Data in) {
        super(in);
        inputCopy = input;
        inputCopy2 = input;
    }
    boolean isFixedHeight = input.isContainerHeightFixed();


    private  ArrayList<Rectangle> procedure(ArrayList<Rectangle> collection, int sortingIndex)
    {
        boolean rotAllowed = input.isRotationsAllowed();


        int rWidth = 0; int rHeight = 0;
        if (rotAllowed)
            for (Rectangle rectangle : collection) {
                if (rectangle.height > rectangle.width) {
                    int x = rectangle.width;
                    rectangle.width = rectangle.height;
                    rectangle.height = x;
                    rectangle.isRotated = true;
                }
            }
        if(!isFixedHeight)
        {  if(sortingIndex==1)
             Collections.sort(collection, new HeightComparator());
        else if (sortingIndex==2)
            Collections.sort(collection, new WidthComparator());
        else
            Collections.sort(collection, new AreaComparator());
        }
            else
            Collections.sort(collection, new HeightComparator());


        for(Rectangle rectangle :collection) {
            rHeight+= rectangle.getHeight();
            rWidth += rectangle.getWidth();
        }
        rHeight /= collection.size();
        rWidth /= collection.size();
        int n = collection.size();
        int h = (int) (rHeight * Math.sqrt(n));
        int w = (int) (rWidth * Math.sqrt(n));
        if(isFixedHeight==true)
        {
            int fixedHeight = input.getContainerHeight();
            h = fixedHeight;

        }


            try {
                collection = method(collection, w, h);

            }
            catch (IndexOutOfBoundsException e) {

                try {
                    if(isFixedHeight)
                    {
                        w=w*3;
                    }
                    else
                    {
                        w = w*2 + w/2;
                        h = h*2 + h/2;
                    }
                    collection = method(collection, w, h);
                    return collection;
                }

                catch(IndexOutOfBoundsException f)
                {
                    try {
                        if(isFixedHeight)
                        {
                            w = w*6;
                        }
                        else
                        {
                            w = w*4 ;
                            h = h*4 ;
                        }
                        collection = method(collection, w, h);
                        return collection;
                    }
                    catch(IndexOutOfBoundsException g)
                    {
                        try {
                            if(isFixedHeight)
                            {
                                w=w*9;
                            }
                            else
                            {
                                w = w*6 + w/6;
                                h = h*6 + h/6;
                            }

                            collection = method(collection, w, h);
                            return collection;
                        }
                        catch(IndexOutOfBoundsException H)
                        {
                            if(isFixedHeight)
                            {
                                w=w*12;
                            }
                            else
                            {
                                w = w*8 + w/8;
                                h = h*8 + h/8;
                            }
                            collection = method(collection, w, h);
                            return collection;
                        }
                    }
                }
            }

            return collection;


    }


    public ArrayList<Rectangle> method(ArrayList<Rectangle> collection, int w, int h)
    {
        BTP packer = new BTP (w, h);
        packer.fit(collection);
        Iterator<Rectangle> rectangleIterator = collection.iterator();
        while (rectangleIterator.hasNext()) {
            Rectangle rectangle = rectangleIterator.next();
            if (rectangle.fit != null) {
                int fitX = rectangle.fit.x;
                int fitY = rectangle.fit.y;
                rectangle.placeRectangle(fitX, fitY);


            }
        }
        Collections.sort(collection, new IndexComparator());
        return collection;
    }

    @Override
    public Solution solve() {

        ArrayList<Rectangle> rectangleCollection = input.getRectangles();
        ArrayList<Rectangle> secondRectangleCollection = inputCopy.getRectangles();
        ArrayList<Rectangle> thirdRectangleCollection = inputCopy2.getRectangles();

        int config = 2;

        rectangleCollection = procedure(rectangleCollection, 1);
        Solution sol1 = new Solution(rectangleCollection);
        long heightWaste =  sol1.getWastedArea();

        secondRectangleCollection =  procedure(secondRectangleCollection, 2);
        sol1 = new Solution(secondRectangleCollection);
        long widthWaste =  sol1.getWastedArea();
        if(widthWaste > heightWaste) {
            config = 1;
            rectangleCollection = procedure(rectangleCollection, 1);
            sol1 = new Solution(rectangleCollection);
        }


        long hgwsWaste = sol1.getWastedArea();
        thirdRectangleCollection = procedure(thirdRectangleCollection, 3);
        sol1 = new Solution(thirdRectangleCollection);
        long maxSideWaste = sol1.getWastedArea();
        if(maxSideWaste > hgwsWaste)
            if(config==2)
            {
                secondRectangleCollection =  procedure(secondRectangleCollection, 2);
                sol1 = new Solution(secondRectangleCollection);
                return sol1;
            }
            else
            {
                rectangleCollection = procedure(rectangleCollection, 1);
                sol1 = new Solution(rectangleCollection);
                return sol1;

            }


            return sol1;

    }
}
