package com.packing;

import com.packing.algo.*;
import com.packing.models.*;
import com.packing.sorting.IndexComparator;

import java.util.Collections;

public class PackingSolver {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        Data input = inputReader.read();
        Solution sol = new Solution();

        if (input.getRectangleAmount() <= 10) {
            AbstractAlgorithm solver = new SmallCase(input);
            sol = solver.solve();
        }
        if (input.getRectangleAmount() > 10 && !input.isContainerHeightFixed()) {
            AbstractAlgorithm solver = new BTRun(input);
            sol = solver.solve();
        }
        if (input.getRectangleAmount() > 10 && input.isContainerHeightFixed()) {
            Solution sol1 = new SkySolution(input).solve();
            sol1.calcWidth();
            Solution sol2 = new SimulatedAnnealing(input).solve();
            sol2.calcWidth();
            if (sol2.maxWidth < sol1.maxWidth) {
                sol = sol2;
            } else {
                sol = sol1;
            }

        }
        Collections.sort(sol.rectangles, new IndexComparator());
        print(input, sol);
    }

    //printing

    public static void print(Data input, Solution sol) {
        System.out.print("container height: ");
        if (input.isContainerHeightFixed()) {
            System.out.println("fixed " + input.getContainerHeight());
        } else {
            System.out.println("free");
        }
        System.out.print("rotations allowed: ");
        if (input.isRotationsAllowed()) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
        System.out.println("number of rectangles: " + input.getRectangleAmount());
        for (Rectangle r : sol.rectangles) {
            if (!input.isRotationsAllowed()) {
                System.out.print(r.getWidth() + " ");
                System.out.println(r.getHeight());
            } else {
                if (r.isRotated) {
                    System.out.print(r.getHeight() + " ");
                    System.out.println(r.getWidth());
                } else {
                    System.out.print(r.getWidth() + " ");
                    System.out.println(r.getHeight());
                }
            }
        }
        System.out.println("placement of rectangles");
        if (input.isRotationsAllowed()) {
            for (Rectangle r : sol.rectangles) {
                String rot = "";
                if (r.isRotated)
                    rot = "yes ";
                else
                    rot = "no ";
                System.out.print(rot);                           // if rotations are allowed UPDATE to whether is rotated or not
                System.out.println(r);
            }
        } else {
            for (Rectangle r : sol.rectangles) {
                //       System.out.print(r.getWidth() + ": ");

                System.out.println(r);
            }
        }

    }

}