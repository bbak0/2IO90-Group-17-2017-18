package com.packing.algo;

import com.packing.models.*;
import com.packing.sorting.AreaComparator;
import com.packing.sorting.IndexComparator;
import com.packing.sorting.WidthComparator;

import java.util.ArrayList;
import java.util.Collections;

public class RecursiveHeuristic extends AbstractAlgorithm {

    ArrayList<Rectangle> rectanglesToPlace = input.getRectangles();
    Solution bestSolution;
    ArrayList<Rectangle> rectanglesPlaced = new ArrayList<>(input.getRectangles());


    public RecursiveHeuristic(Data in) {
        super(in);
    }

    @Override
    public Solution solve() {//HeuristicRecursion
        Collections.sort(rectanglesToPlace, new AreaComparator());

        return new Solution();
    }

    void RecursivePacking(BoundedSpace bs){
        if(true){
            return;
        }


    }

    void Packing(UnboundedSpace us){

    }

}
