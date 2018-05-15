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
        for (Rectangle rectangle : rectangleCollection) {
            if (floor_feasible(rectangle) == false) {
                createNewLevel(rectangle);
            }
            rectangle.placeRectangle(currentLevelWidth, currentFloorHeight);
            currentFloorHeight = currentFloorHeight + rectangle.getHeight();  //(HAS to be after packing)
        }
        Collections.sort(rectangleCollection, new IndexComparator());

    }


    boolean floor_feasible(Rectangle r){
        if(currentFloorHeight == 0 && currentLevelWidth == 0){
            formerFirstRectangleWidth = r.getWidth();               //initialising variable manually to largest width rectangle
        }
        if (r.getHeight() <= containerHeight - currentFloorHeight && r.getWidth() <= formerFirstRectangleWidth){
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
