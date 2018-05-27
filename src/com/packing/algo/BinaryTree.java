package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.Solution;
import com.packing.sorting.AreaComparator;
import com.packing.sorting.IndexComparator;

import java.util.ArrayList;
import java.util.Collections;

public class BinaryTree extends AbstractAlgorithm {

    Data inputCopy;
    ArrayList<Rectangle> rectangleCollection = input.getRectangles();
    ArrayList<Integer> widthLevel = new ArrayList<Integer>(5005);
    ArrayList<Integer> heightLevel = new ArrayList<Integer>(5005);

    ArrayList<Integer> freepointX = new ArrayList<Integer>(5005);
    ArrayList<Integer> freepointY = new ArrayList<Integer>(5005);
    int bigH = 0, bigW=0;
    public BinaryTree(Data in) {
        super(in);
        inputCopy = input;
    }

    @Override
    public  Solution solve() {
        Collections.sort(inputCopy.rectangles, new AreaComparator());
        widthLevel.add(0,0);
        heightLevel.add(0,0);

        freepointX.add(0, widthLevel.get(0));
        freepointY.add(0, heightLevel.get(0));
        for(Rectangle rectangle : rectangleCollection) {

        }

        Collections.sort(rectangleCollection, new IndexComparator());
        return new Solution(rectangleCollection);

    }



}
