package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.Solution;
import com.packing.sorting.AreaComparator;

import java.util.Collections;

public class HrSaAlgo extends AbstractAlgorithm {

    private double initialTemp = 0;
    private double a = 0;
    private int L = 20000;

    public HrSaAlgo(Data in) {
        super(in);
    }

    @Override
    public void solve() {
        Solution sol = new Solution();
        for(Rectangle r: input.rectangles){
            sol.rectangles.add(r);
        }
        Collections.sort(sol.rectangles, new AreaComparator());
        sol.update();

        for(int i=0;i<=9;i++){

        }
    }

    public void randomSwitch(Solution sol){
        int r1 = (int) Math.round(Math.random()*input.getRectangleAmount());
        int r2;
        do{r2 = (int) Math.round(Math.random()*input.getRectangleAmount());}
        while(r1==r2);
        int temp1,temp2;
        temp1 = sol.rectangles.get(r1).x;
        temp2 = sol.rectangles.get(r1).y;
        sol.rectangles.get(r1).x = sol.rectangles.get(r2).x;
        sol.rectangles.get(r1).y = sol.rectangles.get(r2).y;
        sol.rectangles.get(r2).x = temp1;
        sol.rectangles.get(r2).y = temp2;

    }


}
