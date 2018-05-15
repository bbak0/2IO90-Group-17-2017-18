package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.sorting.IndexComparator;
import com.packing.sorting.WidthComparator;

import java.util.ArrayList;
import java.util.Collections;

public class RecursiveHeuristic extends AbstractAlgorithm {

    public RecursiveHeuristic(Data in) {
        super(in);
    }

    int currentLevelWidth = 0;
    int currentFloorHeight = 0;
    int containerHeight = input.getContainerHeight();
    int formerFirstRectangleWidth = 0;
    ArrayList<Rectangle> rectangleCollection = input.getRectangles();

    @Override
    public void solve() {
        Collections.sort(rectangleCollection, new WidthComparator());
        for (Rectangle rectangle : rectangleCollection) {
            if (floor_feasible(rectangle) == false) {
                createNewLevel(rectangle);
            }
            rectangle.placeRectangle(currentLevelWidth, currentFloorHeight);
            currentFloorHeight = currentFloorHeight + rectangle.getHeight();  //(HAS to be after packing)
        }
        Collections.sort(rectangleCollection, new IndexComparator());
        print();
    }

    public void print() {
        System.out.print("container height: ");
        if(input.isContainerHeightFixed()) {
            System.out.println("fixed " + input.getContainerHeight());
        } else {
            System.out.println("free");
        }
        System.out.print("rotations allowed: ");
        if(input.isRotationsAllowed()) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
        System.out.println("number of rectangles: " + input.getRectangleAmount());
        for (Rectangle r: rectangleCollection) {
            System.out.print(r.getWidth() + " ");
            System.out.println(r.getHeight());
        }
        System.out.println("placement of rectangles");
        if (input.isRotationsAllowed()){
            for (Rectangle r: rectangleCollection) {
                //       System.out.print(r.getWidth() + ": ");
                System.out.print("no ");                           // if rotations are allowed UPDATE to whether is rotated or not
                System.out.println(r);
            }
        } else {
            for (Rectangle r: rectangleCollection) {
                //       System.out.print(r.getWidth() + ": ");

                System.out.println(r);
            }
        }
    }



    boolean floor_feasible(Rectangle r){
        if(currentFloorHeight == 0 && currentLevelWidth == 0){
            formerFirstRectangleWidth = r.getWidth();               //initialising variable manually to largest width rectangle
        }
        if (r.getHeight() <= containerHeight - currentFloorHeight ){
            return true;
        }
        else {
            return false;
        }
    }

    void createNewLevel(Rectangle r) {
        currentLevelWidth = currentLevelWidth + formerFirstRectangleWidth;
        currentFloorHeight = 0;
        formerFirstRectangleWidth = r.getWidth();
    }


}
