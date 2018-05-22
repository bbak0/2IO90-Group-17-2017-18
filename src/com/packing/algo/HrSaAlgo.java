//package com.packing.algo;
//
//import com.packing.models.Data;
//import com.packing.models.Rectangle;
//import com.packing.models.Solution;
//import com.packing.sorting.AreaComparator;
//
//import java.util.Collections;
//
//public class HrSaAlgo extends AbstractAlgorithm {
//
//    private double temperature = 0;
//    private double a = 0;
//    private int L = 20000;
//    int r1,r2;
//    Solution sol = new Solution();
//
//    public HrSaAlgo(Data in) {
//        super(in);
//    }
//
//    @Override
//    public void solve() {
//        System.out.print("1");
//        for(Rectangle r: input.rectangles){
//            sol.rectangles.add(r);
//        }
//        Collections.sort(sol.rectangles, new AreaComparator());
//        input.rectangles = sol.rectangles;
//        AbstractAlgorithm RH = new RecursiveHeuristic(input);
//        RH.solve();
//        sol.rectangles=((RecursiveHeuristic) RH).rectangleCollection;
//        sol.update();
//        int nonAccepted=0;
//
//
//
//        while (nonAccepted < 10){                   //while under 10 solutions are accepted
//            for(int i=0;i<=L;i++){
//                long ePre = sol.wastedArea;
//                randomSwitch(sol);
//                long ePost = sol.wastedArea;
//                long eDiff = ePost - ePre;
//
//                if(eDiff < 0){
//
//                    //switch stays
//                } else {                                               //unswitch, then switch with probability of -eDiff/T
//                    //Switch stays with probability 1 - P(-eDiff/T)
//                    if( Math.random() <= Math.exp(-eDiff/temperature)) {
//                        //switch stays
//                    } else {
//                        Switch(r1, r2, sol);//unswitch
//                        nonAccepted++;
//                    }
//                }
//            }
//            temperature *= a;
//
//        }
//        print();
//    }
//
//    public void randomSwitch(Solution sol){
//        r1 = (int) Math.round(Math.random()*input.getRectangleAmount());
//        do{r2 = (int) Math.round(Math.random()*input.getRectangleAmount());}
//        while(r1==r2);
//        Switch(r1,r2, sol);
//    }
//
//    public void Switch(int r1,int r2, Solution sol){
//        int temp1,temp2;
//        temp1 = sol.rectangles.get(r1).x;
//        temp2 = sol.rectangles.get(r1).y;
//        sol.rectangles.get(r1).x = sol.rectangles.get(r2).x;
//        sol.rectangles.get(r1).y = sol.rectangles.get(r2).y;
//        sol.rectangles.get(r2).x = temp1;
//        sol.rectangles.get(r2).y = temp2;
//        AbstractAlgorithm RH = new RecursiveHeuristic(input);
//        RH.solve();
//        sol.rectangles=((RecursiveHeuristic) RH).rectangleCollection;
//        sol.update();
//    }
//
//
//    public void print() {
//        System.out.print("container height: ");
//        if(input.isContainerHeightFixed()) {
//            System.out.println("fixed " + input.getContainerHeight());
//        } else {
//            System.out.println("free");
//        }
//        System.out.print("rotations allowed: ");
//        if(input.isRotationsAllowed()) {
//            System.out.println("yes");
//        } else {
//            System.out.println("no");
//        }
//        System.out.println("number of rectangles: " + input.getRectangleAmount());
//        for (Rectangle r: sol.rectangles) {
//            System.out.print(r.getWidth() + " ");
//            System.out.println(r.getHeight());
//        }
//        System.out.println("placement of rectangles");
//        if (input.isRotationsAllowed()){
//            for (Rectangle r: sol.rectangles) {
//                //       System.out.print(r.getWidth() + ": ");
//                System.out.print("no ");                           // if rotations are allowed UPDATE to whether is rotated or not
//                System.out.println(r);
//            }
//        } else {
//            for (Rectangle r: sol.rectangles) {
//                //       System.out.print(r.getWidth() + ": ");
//
//                System.out.println(r);
//            }
//        }
//    }
//}
