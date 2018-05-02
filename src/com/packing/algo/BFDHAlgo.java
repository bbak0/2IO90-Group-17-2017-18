package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Rectangle;
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
    public void solve() {
        Collections.sort(rectangleCollection, new WidthComparator());
        widthLevel.add(0,0);
        heightLevel.add(0,0);
        containerHeight = input.getContainerHeight();
        formerFirstRectangleWidth = rectangleCollection.get(0).getWidth();               //initialising variable manually to largest width rectangle
        for (Rectangle rectangle : rectangleCollection) {
            levelIndex = levelWithLowestHeight();                            //Pick level with most height left so the heightLevel should be minimal;

            if (floor_feasible(rectangle, levelIndex) == false){
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
