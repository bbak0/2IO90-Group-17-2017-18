package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Solution;
import com.packing.sorting.AreaComparator;

import java.util.Collections;

public class SimulatedAnnealing extends AbstractAlgorithm {

    private double temperature = 20;
    private double a = 0.9;
    private int L = 2000;
    Solution previousSol;
    Data inputCopy;


    public SimulatedAnnealing(Data in) {
        super(in);
        inputCopy = input;
    }

    @Override
    public Solution solve() {
        Collections.sort(inputCopy.rectangles, new AreaComparator());
        Solution sol = new NFDHAlgo(input).solve();
        sol.update();
        sol.calcRectangleArea();
        int nonAccepted = 0;

        while (nonAccepted < 10 && temperature > 0) {                   //while under 10 solutions are accepted and the temperature isn't zero
            for (int i = 0; i <= L; i++) {
                previousSol = sol;
                randomSwitch(sol);
                long eDiff = sol.getWastedArea() - previousSol.getWastedArea();

                if (eDiff < 0) {

                    //switch stays
                } else {                                               //unswitch, then switch with probability of -eDiff/T
                    //Switch stays with probability 1 - P(-eDiff/T)
                    if (Math.random() <= Math.exp(-eDiff / temperature)) {
                        //switch stays
                    } else {
                        sol = previousSol;//unswitch
                        nonAccepted++;
                    }
                }
            }
            temperature *= a;

        }
        return sol;
    }

    public void randomSwitch(Solution sol) {
        int r1 = (int) Math.floor(Math.random() * input.getRectangleAmount());
        int r2;
        do {
            r2 = (int) Math.floor(Math.random() * input.getRectangleAmount());
        } while (r1 == r2);
        Switch(r1, r2, sol);
    }

    public void Switch(int r1, int r2, Solution sol) {
        int temp1, temp2;
        temp1 = sol.rectangles.get(r1).x;
        temp2 = sol.rectangles.get(r1).y;
        sol.rectangles.get(r1).x = sol.rectangles.get(r2).x;
        sol.rectangles.get(r1).y = sol.rectangles.get(r2).y;
        sol.rectangles.get(r2).x = temp1;
        sol.rectangles.get(r2).y = temp2;
        inputCopy.rectangles = sol.rectangles;
        sol.rectangles = new NFDHAlgo(inputCopy).solve().rectangles;
        sol.update();
    }


}