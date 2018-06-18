package com.packing.algo;

import com.packing.models.BLnode;
import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.Solution;
import com.packing.sorting.IndexComparator;
import com.packing.sorting.WidthComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class FFDHAlgo extends AbstractAlgorithm {

    public FFDHAlgo(Data in) {
        super(in);
    }

    int containerHeight;
    int formerFirstRectangleWidth;
    int levelIndex = 0;
    int lastLevel = 0;
    boolean ceil = false;
    ArrayList<Integer> widthLevel = new ArrayList<Integer>(50);         //capacity is a potential issue
    ArrayList<Integer> heightLevel = new ArrayList<Integer>(50);
    ArrayList<Integer> ceilingLevel = new ArrayList<>(50);
    ArrayList<Rectangle>  placedRecs = new ArrayList<>(50);
    ArrayList<Integer>  wDefiningRecs = new ArrayList<>(50);
    ArrayList<Rectangle> rectangleCollection = input.getRectangles();


    @Override
    public Solution solve() {
        Collections.sort(rectangleCollection, new WidthComparator());
        widthLevel.add(0, 0);
        heightLevel.add(0, 0);
        containerHeight = input.getContainerHeight();
        ceilingLevel.add(0, containerHeight);
        wDefiningRecs.add(0, rectangleCollection.get(0).getWidth());
        formerFirstRectangleWidth = rectangleCollection.get(0).getWidth();               //initialising variable manually to largest width rectangle
        for (Rectangle rectangle : rectangleCollection) {
//            for(int cnt = 0, cnt > rectangleCollection.size()
            levelIndex = 0;

            while (levelIndex <= lastLevel) {                           //find a level it fits in
                ceil = false;
                if (floor_feasible(rectangle, levelIndex)) {   //fits in floor
                    break;     //break out of while loop because packing position is found
                } else {
                    if (ceiling_feasible(rectangle, levelIndex)){       //fits in ceil
                        ceil = true;
                        break;
                    }
                }
                levelIndex++;       //rectangle doesnt fit try on next level
            }

            if (levelIndex > lastLevel) {           //search didn't find any levels that the rectangle could fit in
                createNewLevel(rectangle);          // create a new level and set that as levelIndex
            }                                 //Else: search found levelIndex that rectangle can fit in



            placedRecs.add(rectangle);
            if(ceil == false){
                rectangle.placeRectangle(widthLevel.get(levelIndex), heightLevel.get(levelIndex));
                heightLevel.add(levelIndex, heightLevel.get(levelIndex) + rectangle.getHeight());        //update packed height (HAS to be after packing)

            }else{
                ceiling_pack(rectangle, levelIndex);
            }


        }

        Collections.sort(rectangleCollection, new IndexComparator());
        return new Solution(rectangleCollection);
    }


    boolean floor_feasible(Rectangle r, int levelX) {
        SmallCase sc = new SmallCase(input);
        boolean a = sc.rectangleFits(r, new BLnode(widthLevel.get(levelX), heightLevel.get(levelX)), placedRecs);
        boolean b = r.getHeight() <= containerHeight - heightLevel.get(levelX);

        if ( a&&b ) {
            return true;
        } else {
            return false;
        }


    }

    boolean ceiling_feasible(Rectangle r, int levelX) {
        SmallCase sc = new SmallCase(input);
        return sc.rectangleFits(r, new BLnode(widthLevel.get(levelX) + wDefiningRecs.get(levelX) - r.getWidth(),
                ceilingLevel.get(levelX) - r.getHeight()), placedRecs);


    }

    void ceiling_pack(Rectangle r, int levelX) {
        r.placeRectangle(widthLevel.get(levelX) +  wDefiningRecs.get(levelX) - r.getWidth(), ceilingLevel.get(levelX) - r.getHeight());
        ceilingLevel.add(levelX, ceilingLevel.get(levelX) - r.getHeight());
    }

    void createNewLevel(Rectangle r) {
        widthLevel.add(lastLevel + 1, widthLevel.get(lastLevel) + formerFirstRectangleWidth);
        wDefiningRecs.add(lastLevel + 1, r.getWidth());
        heightLevel.add(lastLevel + 1, 0);
        ceilingLevel.add(lastLevel+1, containerHeight);
        formerFirstRectangleWidth = r.getWidth();
        lastLevel++;
        levelIndex = lastLevel;
    }
}


