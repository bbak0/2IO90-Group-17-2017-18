package com.packing.models;

import com.packing.algo.AbstractAlgorithm;
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
        if (input.isRotationsAllowed()){
            maxH = Math.max(input.getMaxHeight(), input.getMaxWidth());
        } else {
            maxH = input.getMaxWidth();
        }

        int bestWidth = Integer.MAX_VALUE;
        Solution bestSol = null;
        for (int i = 0; i < 20000 / input.getRectangles().size(); i++){
            Collections.shuffle(input.getRectangles());
            bestSol = executeAlgo(input, bestSol, maxH);
        }
        while (bestSol == null){
            Collections.shuffle(input.getRectangles());
            bestSol = executeAlgo(input, bestSol, maxH);
            maxH = maxH + (maxH/10);
        }

        Collections.sort(input.getRectangles(), new RatioComparator());
        bestSol = executeAlgo(input, bestSol, maxH);

        Collections.reverse(input.getRectangles());
        bestSol = executeAlgo(input, bestSol, maxH);

        Collections.sort(input.getRectangles(), new DESCSS());
        bestSol = executeAlgo(input, bestSol, maxH);
        Collections.sort(input.getRectangles(), new AreaComparator());
        bestSol = executeAlgo(input, bestSol, maxH);

        Collections.sort(input.getRectangles(), new HeightComparator());
        bestSol = executeAlgo(input, bestSol, maxH);

        Collections.sort(input.getRectangles(), new DESCSS_NR());
        bestSol = executeAlgo(input, bestSol, maxH);

        Collections.sort(input.getRectangles(), new WidthComparator());
        bestSol = executeAlgo(input, bestSol, maxH);

        Collections.sort(input.getRectangles(), new SquareComparator());
        bestSol = executeAlgo(input, bestSol, maxH);



        return bestSol;
}

    Solution executeAlgo(Data input, Solution bestSol, int maxSpread){
        SkylineStripFaster inst = new SkylineStripFaster(input);
        if (maxSpread != 0){
            inst.setMaxSpread(maxSpread);
        }
        Solution sol = inst.solve();
        if (sol == null){
            return bestSol;
        }
        int solWidth = sol.getMaxWidth();
        if (solWidth < bestWidth){
            bestWidth = solWidth;
            ArrayList<Rectangle> check = new DisjointArrayList();
            for (Rectangle r : sol.rectangles){
                boolean c = check.add(r);
                if (!c){
                    throw new IllegalStateException("rectangles not disjoint");
                }
            }
            return sol;
        } else {
            return bestSol;
        }
    }
}
