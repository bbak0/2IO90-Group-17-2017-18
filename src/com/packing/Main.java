package com.packing;

import com.packing.algo.*;
import com.packing.models.*;

public class Main {

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
            Solution sol1 = new SimulatedAnnealing(input).solve();
            sol1.calcWidth();
            Solution sol2 = new BTRun(input).solve();
            sol2.calcWidth();
            Solution sol3 = new SkySolution(input).solve();
            sol3.calcWidth();
            if (sol1.maxWidth <= sol2.maxWidth && sol1.maxWidth <= sol3.maxWidth) {
                sol = sol1;
            } else if (sol2.maxWidth <= sol1.maxWidth && sol2.maxWidth <= sol3.maxWidth) {
                sol = sol2;
            } else {
                sol = sol3;
            }

        }

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
        System.out.println("wasted: " + sol.getWastedArea() + "\n" + "used :" + sol.areaOfRectangles);
        System.out.println("total : " + (sol.getWastedArea() + sol.areaOfRectangles));

    }

}