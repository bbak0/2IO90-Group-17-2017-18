package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.sorting.IndexComparator;
import com.packing.sorting.WidthComparator;

import java.util.ArrayList;
import java.util.Collections;

public class FFDHAlgo extends AbstractAlgorithm {

    public FFDHAlgo(Data in) {
        super(in);
    }

    int containerHeight;
    int formerFirstRectangleWidth = 0;
    int levelIndex = 0;
    int lastLevel = 0;
    ArrayList<Integer> widthLevel = new ArrayList<Integer>(50);
    ArrayList<Integer> heightLevel = new ArrayList<Integer>(50);

    ArrayList<Rectangle> rectangleCollection = input.getRectangles();


    @Override
    public void solve() {
        Collections.sort(rectangleCollection, new WidthComparator());
        widthLevel.add(0,0);
        heightLevel.add(0,0);
        containerHeight = input.getContainerHeight();
        formerFirstRectangleWidth = rectangleCollection.get(0).getWidth();               //initialising variable manually to largest width rectangle
        for (Rectangle rectangle : rectangleCollection) {
            levelIndex = 0;
            while (levelIndex <= lastLevel) {                           //find a floor it fits in
                if (floor_feasible(rectangle, levelIndex) == false) {
                    levelIndex++;
                }
                else {
                    break;                      //SHOULD BREAK OUT OF LOOP WHILE ??
                }
            }
            if (levelIndex > lastLevel) {           //search didn't find any levels that the rectangle could fit in
                createNewLevel(rectangle);          // create a new level and set that as levelIndex
            }                                 //Else: search found levelIndex that rectangle can fit in

            rectangle.placeRectangle(widthLevel.get(levelIndex), heightLevel.get(levelIndex));
            heightLevel.add(levelIndex, heightLevel.get(levelIndex) + rectangle.getHeight());        //update packed height (HAS to be after packing)
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


}
