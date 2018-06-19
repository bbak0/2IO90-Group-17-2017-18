package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.Solution;
import com.packing.sorting.*;
import com.packing.utils.DisjointArrayList;

import java.util.ArrayList;
import java.util.Collections;

public class SkySolution extends AbstractAlgorithm {

    public SkySolution(Data in) {
        super(in);
    }

    int bestWidth = Integer.MAX_VALUE;

    @Override
    public Solution solve() {
        int maxH;
        if (input.isRotationsAllowed()) {
            maxH = Math.max(input.getMaxHeight(), input.getMaxWidth());
        } else {
            maxH = input.getMaxWidth();
        }

        int bestWidth = Integer.MAX_VALUE;
        Solution bestSol = null;

        while (bestSol == null) {


            Collections.sort(input.getRectangles(), new RatioComparator());
            bestSol = executeAlgo(input, bestSol, maxH);

            Collections.reverse(input.getRectangles());
            bestSol = executeAlgo(input, bestSol, maxH);

            Collections.sort(input.getRectangles(), new DESCSS());
            bestSol = executeAlgo(input, bestSol, maxH);
            Collections.sort(input.getRectangles(), new AreaComparator());
            bestSol = executeAlgo(input, bestSol, maxH);
//
            Collections.sort(input.getRectangles(), new HeightComparator());
            bestSol = executeAlgo(input, bestSol, maxH);
////
            Collections.sort(input.getRectangles(), new DESCSS_NR());
            bestSol = executeAlgo(input, bestSol, maxH);
//
            Collections.sort(input.getRectangles(), new WidthComparator());
            bestSol = executeAlgo(input, bestSol, maxH);
//
            Collections.sort(input.getRectangles(), new SquareComparator());
            bestSol = executeAlgo(input, bestSol, maxH);
            maxH += maxH + (maxH / 8);
        }


        return bestSol;
    }

    Solution executeAlgo(Data input, Solution bestSol, int maxSpread) {
        SkylineStripFaster inst = new SkylineStripFaster(input);
        if (maxSpread != 0) {
            inst.setMaxSpread(maxSpread);
        }
        Solution sol = inst.solve();
        if (sol == null) {
            return bestSol;
        }
        int solWidth = sol.getMaxWidth();
        if (solWidth < bestWidth) {
            bestWidth = solWidth;
            ArrayList<Rectangle> check = new DisjointArrayList();
            for (Rectangle r : sol.rectangles) {
                boolean c = check.add(r);
                if (!c) {
                    throw new IllegalStateException("Solution is wrong");
                }
            }
            return sol;
        } else {
            return bestSol;
        }
    }
}
