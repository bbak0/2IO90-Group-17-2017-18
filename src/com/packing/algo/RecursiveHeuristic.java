package com.packing.algo;

import com.packing.models.*;
import com.packing.sorting.AreaComparator;
import com.packing.sorting.IndexComparator;
import com.packing.sorting.WidthComparator;

import java.util.ArrayList;
import java.util.Collections;

public class RecursiveHeuristic extends AbstractAlgorithm {

    ArrayList<Rectangle> rectangleCollection = input.getRectangles();

    public RecursiveHeuristic(Data in) {
        super(in);
    }

    @Override
    public Solution solve() {

        return new Solution();
    }

    void RecursivePacking(BoundedSpace bs){
        if(true){
            return;
        }


    }

    void Packing(UnboundedSpace us){

    }

    void HeuristicRecursion(){
        Collections.sort(rectangleCollection, new AreaComparator());

    }

}
