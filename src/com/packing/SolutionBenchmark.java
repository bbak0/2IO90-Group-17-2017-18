package com.packing;

import com.packing.algo.AbstractAlgorithm;
import com.packing.algo.SimulatedAnnealing;
import com.packing.algo.SkySolution;
import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.Solution;
import com.packing.models.TestResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class SolutionBenchmark {

    AbstractAlgorithm algo;
    Random rng = new Random();
    final int MAX_HEIGHT = 1300;
    int MAX_DIMENSION = 800;
    int MAX_DIMENSION_COPY = MAX_DIMENSION;
    final int MIN_HEIGHT = 10;
    boolean hFixed;
    int inputSize;

    ArrayList<TestResult> results = new ArrayList<>();

    public SolutionBenchmark(AbstractAlgorithm algo, boolean hF, int inputSize ){
        this.algo = algo;
        this.hFixed = hF;
        this.inputSize = inputSize;
    }


    //runs test n time
    void runTest(int n, int mode){
        ArrayList<Data> testCases = generateTestCases(n, mode);
        System.out.println("Generating testcases");
        int i = 1;
        for (Data d : testCases){
            System.out.println("Solving #" + i);
            try {
                Class[] cArg = new Class[1];
                cArg[0] = d.getClass();
                AbstractAlgorithm instance = algo.getClass().getDeclaredConstructor(cArg).newInstance(d);
                Solution sol = instance.solve();
                analyseSolution(sol, d);
                i++;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        printResults();
    }


    //mode:
    //std java random - 0
    //normal distribution - 1
    //std java random with bigger difference between dimensions
    ArrayList<Data> generateTestCases(int n, int mode){
        ArrayList<Data> cases = new ArrayList<>();
        for (int i = 0; i < n; i++){
            Data d = new Data();
            d.setContainerHeightFixed(hFixed);
            boolean rotations = rng.nextBoolean();
            d.setRotationsAllowed(rotations);
            if (hFixed){
                int cH = rng.nextInt(MAX_HEIGHT) + MIN_HEIGHT;
                d.setContainerHeight(cH);
                MAX_DIMENSION = cH ;
            }
            switch(mode){
                case 0:
                    for(int j = 0; j < inputSize; j++){
                        d.addRectangle(
                                new Rectangle(j,
                                        rng.nextInt(MAX_DIMENSION)+1,
                                        rng.nextInt(MAX_DIMENSION)+1
                                ));

                    }
                    break;
                case 1:
                    for(int j = 0; j < inputSize; j++){
                        d.addRectangle(
                                new Rectangle(j,
                                        rng.nextInt(MAX_DIMENSION)+1,
                                        rng.nextInt(MAX_DIMENSION)+1
                                ));

                    }
                    break;
                case 2:
                    for (int z = 0; z < inputSize; z++){
                        int dim = MAX_DIMENSION;
                        for (int j = 0; j < 5; j++){
                            dim = Math.min(dim, rng.nextInt(MAX_DIMENSION)+1);
                        }
                        int dim2 = rng.nextInt(MAX_DIMENSION)+1;
                        if (rng.nextBoolean()){
                            d.addRectangle(
                                    new Rectangle(z,
                                            dim,
                                            dim2
                                    ));
                        } else {
                            d.addRectangle(
                                    new Rectangle(z,
                                            dim2,
                                            dim
                                    ));
                        }

                    }
                    break;
            }
            cases.add(d);
        }
        return cases;

    }

    void analyseSolution(Solution sol, Data d){
        ArrayList<Rectangle> rectangles = sol.getRectangles();
        int containerMaxW = 0;
        int containerMaxH;
        int usedArea = 0;
        int totalArea = 0;
        if (d.isContainerHeightFixed()){
            containerMaxH = d.getContainerHeight();
        } else {
            containerMaxH = 0;
            for (Rectangle r : rectangles){
                containerMaxH = Math.max(containerMaxH, r.y + r.height);
            }
        }
        for (Rectangle r: rectangles){
            containerMaxW = Math.max(containerMaxW, r.x + r.width);
            usedArea = usedArea + r.getArea();
        }
        totalArea = containerMaxH * containerMaxW;
        results.add(new TestResult(usedArea, totalArea, d));
    }

    void clearResults(){
        results.clear();
    }

    void printResults(){
        System.out.println("% area utilized:");
        System.out.print("[");
        for (TestResult r : results){
            double aU = r.getAreaUsed();
            double aT = r.getTotalArea();
            System.out.print(round((aU/aT)*100.0, 4));
            System.out.print(", ");
        }
        System.out.print("]");
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
