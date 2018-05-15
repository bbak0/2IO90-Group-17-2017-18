package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.Solution;
import com.packing.sorting.AreaComparator;

import java.util.Collections;

public class HrSaAlgo extends AbstractAlgorithm {

    private double temperature = 0;
    private double a = 0;
    private int L = 20000;
    int r1,r2;

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
        int nonAccepted=0;


        //while under 10 solutions are accepted
        while (nonAccepted < 10){
            for(int i=0;i<=L;i++){
                long ePre = sol.wastedArea;
                randomSwitch(sol);
                long ePost = sol.wastedArea;
                long eDiff = ePost - ePre;

                if(eDiff < 0){

                    //switch stays
                } else {                                               //unswitch, then switch with probability of -eDiff/T
                    //Switch stays with probability 1 - P(-eDiff/T)
                    if( Math.random() <= Math.exp(-eDiff/temperature)) {
                        //switch stays
                    } else {
                        Switch(r1, r2, sol);//unswitch
                        nonAccepted++;
                    }
                }
            }
            temperature *= a;

        }
    }

    public void randomSwitch(Solution sol){
        r1 = (int) Math.round(Math.random()*input.getRectangleAmount());
        do{r2 = (int) Math.round(Math.random()*input.getRectangleAmount());}
        while(r1==r2);
        Switch(r1,r2, sol);
    }

    public void Switch(int r1,int r2, Solution sol){
        int temp1,temp2;
        temp1 = sol.rectangles.get(r1).x;
        temp2 = sol.rectangles.get(r1).y;
        sol.rectangles.get(r1).x = sol.rectangles.get(r2).x;
        sol.rectangles.get(r1).y = sol.rectangles.get(r2).y;
        sol.rectangles.get(r2).x = temp1;
        sol.rectangles.get(r2).y = temp2;
        sol.update();
    }



}
