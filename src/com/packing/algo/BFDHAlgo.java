package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.Solution;
import com.packing.sorting.IndexComparator;
import com.packing.sorting.WidthComparator;

import java.util.ArrayList;
import java.util.Collections;

public class BFDHAlgo extends AbstractAlgorithm {

    public BFDHAlgo(Data in) {
        super(in);
    }

    int containerHeight;
    int formerFirstRectangleWidth;
    int levelIndex = 0;
    int lastLevel = 0;
    ArrayList<Integer> widthLevel = new ArrayList<Integer>(50);         //capacity is a potential issue
    ArrayList<Integer> heightLevel = new ArrayList<Integer>(50);

    ArrayList<Rectangle> rectangleCollection = input.getRectangles();


    @Override
    public Solution solve() {
        Collections.sort(rectangleCollection, new WidthComparator());
        widthLevel.add(0,0);
        heightLevel.add(0,0);
        containerHeight = input.getContainerHeight();
        formerFirstRectangleWidth = rectangleCollection.get(0).getWidth();               //initialising variable manually to largest length rectangle
        for (Rectangle rectangle : rectangleCollection) {
            levelIndex = levelWithLowestHeight();                            //Pick level with most height left so the heightLevel should be minimal;

            if (floor_feasible(rectangle, levelIndex) == false){
                createNewLevel(rectangle);          // create a new level and set that as levelIndex
            }                                 //Else: search found levelIndex that rectangle can fit in

            rectangle.placeRectangle(widthLevel.get(levelIndex), heightLevel.get(levelIndex));
            heightLevel.add(levelIndex, heightLevel.get(levelIndex) + rectangle.getHeight());        //update packed height (HAS to be after packing)
        }

        Collections.sort(rectangleCollection, new IndexComparator());
        return new Solution(rectangleCollection);
    }

    boolean floor_feasible(Rectangle r, int levelX){
        if (r.getHeight() <= containerHeight - heightLevel.get(levelX) ){
            return true;
        }
        else {
            return false;
        }
    }

    void createNewLevel(Rectangle r) {
        widthLevel.add(lastLevel + 1,widthLevel.get(lastLevel) + formerFirstRectangleWidth);
        heightLevel.add(lastLevel + 1, 0) ;
        formerFirstRectangleWidth = r.getWidth();
        lastLevel++;
        levelIndex = lastLevel;
    }

    int levelWithLowestHeight() {
        int max = 0;
        for (int i=1; i>heightLevel.size(); i++) {
            if (heightLevel.get(i) > heightLevel.get(max)) {
                max = i;
            }
        }
        return max;
    }


}
