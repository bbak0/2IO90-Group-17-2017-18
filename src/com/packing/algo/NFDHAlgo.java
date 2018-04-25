package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.sorting.HeightComparator;
import com.packing.sorting.IndexComparator;

import java.util.ArrayList;
import java.util.Collections;

public class NFDHAlgo extends AbstractAlgorithm {

    public NFDHAlgo(Data in) {
        super(in);
    }

    int currentLevelWidth = 0;
    int currentFloorHeight = 0;

    @Override
    public void solve() {
        ArrayList<Rectangle> rectangleCollection = input.getRectangles();
        Collections.sort(rectangleCollection, new HeightComparator());
        for (Rectangle rectangle : rectangleCollection) {
            if (floor_feasible(rectangle) == true) {
                rectangle.placeRectangle(currentLevelWidth, currentFloorHeight);
            }
            else {
                createNewLevel(rectangle);
                rectangle.placeRectangle(currentLevelWidth, currentFloorHeight);
            }
        }
        Collections.sort(rectangleCollection, new IndexComparator());
        System.out.println("placement of rectangles");
        for (Rectangle r: rectangleCollection) {
            System.out.println(r);
        }

    }


    boolean floor_feasible(Rectangle r){
        if (r.getWidth() <= currentLevelWidth){
            return true;
        }
		else {
            return false;
        }
    }

    void createNewLevel(Rectangle r) {
        currentLevelWidth = 0;
        currentFloorHeight = currentFloorHeight + r.getHeight();
    }


}
