package com.packing;

import com.packing.algo.AbstractAlgorithm;
import com.packing.algo.BTRun;
import com.packing.algo.SkySolution;
import com.packing.algo.SmallCase;
import com.packing.models.Data;

public class BenchmarkLauncher {

    public static void main(String[] args){
        //replace with own class with empty data and run
        new SolutionBenchmark(new SkySolution(new Data()), true, 1000).runTest(10,2);
    }
}
