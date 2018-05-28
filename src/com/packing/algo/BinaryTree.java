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
    ArrayList<Integer> freepointX = new ArrayList<Integer>(5005);
    ArrayList<Integer> freepointY = new ArrayList<Integer>(5005);
    ArrayList<Integer> Hlimit = new ArrayList<Integer>(5005);
    int bigH = 0, bigW=0, k = 2;
    double avgH = 0, avgW = 0;
    public BinaryTree(Data in) {
        super(in);
        inputCopy = input;
    }

    @Override
    public  Solution solve() {
        Collections.sort(inputCopy.rectangles, new AreaComparator());
        int n = rectangleCollection.size();
        rectangleCollection.get(0).placeRectangle(0, 0);
        freepointX.add(0, rectangleCollection.get(0).getWidth());
        freepointY.add(0, 0);
        freepointX.add(1, 0);
        freepointY.add(1, rectangleCollection.get(0).getHeight());
        Hlimit.add(0, rectangleCollection.get(0).getHeight());
        Hlimit.add(1, bigH);

        for(Rectangle rectangle : rectangleCollection) {
            avgH += rectangle.getHeight();
            avgW += rectangle.getWidth();
        }
        avgH /= rectangleCollection.size();
        avgW /= rectangleCollection.size();
        bigH = (int) (avgH * Math.sqrt(n));
        bigW = (int) (avgW * Math.sqrt(n));
        System.out.println("\n**" + bigH + " " + bigW + "\n");

        for(Rectangle rectangle : rectangleCollection) {
            for(int i=0; i<k; i++) {
                if (isFit(rectangle, freepointX.get(i), freepointY.get(i), Hlimit.get(i))) {
                    rectangle.placeRectangle(freepointX.get(i), freepointY.get(i));
                    freepointX.add(i, freepointX.get(i)+rectangle.getWidth());
                    break;
                }
            }
        }

        Collections.sort(rectangleCollection, new IndexComparator());
        return new Solution(rectangleCollection);

    }

    boolean isFit(Rectangle rectangle, int wtemp, int htemp, int hlimit) {
        return rectangle.getWidth() + wtemp <= bigW || rectangle.getHeight() + htemp <= hlimit;
    }
}
