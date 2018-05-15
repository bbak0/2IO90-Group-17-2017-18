package com.packing;

import com.packing.algo.*;
import com.packing.models.Data;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        Data input = inputReader.read();
        //System.out.println(input);
        AbstractAlgorithm solver;
        solver = new RecursiveHeuristic(input);

        /*Random random = new Random();

        if (random.nextBoolean()){
            solver = new ExampleAlgo(input);
        } else {
            solver = new OtherAlgo(input);
        }*/

        solver.solve();
    }
}
