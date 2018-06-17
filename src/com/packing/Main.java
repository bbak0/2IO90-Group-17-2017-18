package com.packing;

import com.packing.algo.*;
import com.packing.models.*;

public class Main {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        Data input = inputReader.read();
        //System.out.println(input);
        AbstractAlgorithm solver;

        solver = new BTRun(input);
        /*Random random = new Random();

        if (random.nextBoolean()){
            solver = new ExampleAlgo(input);
        } else {
            solver = new OtherAlgo(input);
        }*/

        Solution sol = solver.solve();
        print(input, sol);
    }

        //printing

    public static void print(Data input, Solution sol){
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
            if(!input.isRotationsAllowed()) {
                System.out.print(r.getWidth() + " ");
                System.out.println(r.getHeight());
            }
            else
            {
                if(r.isRotated)
                {
                    System.out.print(r.getHeight() + " ");
                    System.out.println(r.getWidth());
                }
                else
                {
                    System.out.print(r.getWidth() + " ");
                    System.out.println(r.getHeight());
                }
            }
        }
        System.out.println("placement of rectangles");
        if (input.isRotationsAllowed()) {
            for (Rectangle r : sol.rectangles) {
                String rot = "";
                if(r.isRotated)
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
        System.out.println("total : "+ (sol.getWastedArea() + sol.areaOfRectangles));

    }

}