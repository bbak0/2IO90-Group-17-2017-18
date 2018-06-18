package com.packing.sorting;

import com.packing.algo.AbstractAlgorithm;
import com.packing.models.*;
import com.packing.sorting.DESCSS;
import com.packing.utils.AXD;
import com.packing.utils.DisjointArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

public class SkylineStripFaster extends AbstractAlgorithm {

    ArrayList<Rectangle> rectangles = new ArrayList<>(input.getRectangles());
    ArrayList<SkylineNode> skyline = new ArrayList<SkylineNode>();
    int binHeight;
    int binWidth;
    ArrayList<Rectangle> solRect = new ArrayList();
    ArrayList<ArrayList<Rectangle>> wasteMap = new ArrayList<>();
    int areaUsed = 0;
    long inserted = 0;
    int maxWidth = 0;
    int maxSpread = Integer.MAX_VALUE;
    int minSkylineX = 0;



    public SkylineStripFaster(Data in) {
        super(in);
    }

    public void setMaxSpread(int maxSpread) {
        this.maxSpread = maxSpread;
    }

    @Override
    public Solution solve() {
        long startTime = System.currentTimeMillis();


        init();
        boolean successful = algoLoop();
        if (!successful){
            return null;
        }
        //Collections.sort(solRect, new IndexComparator());
        //System.out.println("Area used:" + areaUsed + "Total area:" + binHeight * maxWidth);
        binWidth = maxWidth;
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime/1000);
        SkylineSolution returnSol = new SkylineSolution(solRect, false);
        returnSol.setSkyline(skyline);
        returnSol.maxWidth = this.maxWidth;
        return returnSol;

    }

    void init(){
        if (input.getContainerHeight() != Integer.MAX_VALUE){
            binHeight = input.getContainerHeight();
            binWidth = Integer.MAX_VALUE;
        } else {
            throw new InputMismatchException("Algorithm meant to work with strip packing");
        }

        SkylineNode newNode = new SkylineNode(0,0, binHeight);
        skyline.add(newNode);
    }

    boolean algoLoop(){

            while(rectangles.size() > 0){
                BestValues globalBest = FindMWPosition(rectangles.get(0));
                Rectangle bestR = globalBest.getBestRectangle();
                if (bestR == null){
                    return false;
                }
                bestR.x = globalBest.bestX;
                bestR.y = skyline.get(globalBest.getBestSkylineIndex()).y;
                addSkylineLevel(globalBest.getBestSkylineIndex(), bestR);
                boolean debug = solRect.add(bestR);
                if(!debug){
                    SkylineSolution debugSol = new SkylineSolution(solRect, false);
                    debugSol.setSkyline(skyline);
                    new AXD().openNewCanvas(debugSol, 5);
                    System.out.println("aaaaa");
                    throw new RuntimeException();
                }
                rectangles.remove(bestR);

                areaUsed += bestR.area;
                maxWidth = Math.max(maxWidth, bestR.width + bestR.x);
                inserted++;

                //System.out.println("inserted:" + inserted);
            }
            return true;
    }
//done
    void addSkylineLevel(int skylineNodeIndex, Rectangle r){
        SkylineNode newNode = new SkylineNode(r.x + r.width, r.y, r.height);
        skyline.add(skylineNodeIndex, newNode);

        for (int i = skylineNodeIndex + 1; i < skyline.size(); i++){
            SkylineNode current = skyline.get(i);
            SkylineNode previous = skyline.get(i-1);
            if (current.y < previous.y + previous.length){
                int shrink = previous.y + previous.length - current.y;
                current.y += shrink;
                current.length -= shrink;

                if(current.length <= 0){
                    skyline.remove(current);
                    i--;
                } else {
                    break;
                }

            } else {
                break;
            }
        }
        mergeSkylines();
        updateMinNode();
    }
//done
    void mergeSkylines(){
        for (int i = 0; i < skyline.size() -  1; i++){
            SkylineNode current = skyline.get(i);
            SkylineNode next = skyline.get(i+1);
            if (current.x == next.x){
                current.length += next.length;
                skyline.remove(next);
                i--;
            }
        }
    }

    BestValues FindMWPosition(Rectangle r){
        BestValues b = new BestValues();
        for (int i = 0; i < skyline.size(); i++){
            int x = RectangleFits(i,r);
            if (x - minSkylineX > maxSpread){
                continue;
            }
            if (x >= 0){
                FindMWPositionHelper(i, x, r, b);
            }
            boolean check = false;
            if (input.isRotationsAllowed()){
                r.rotate();
                x = RectangleFits(i, r);
                if (x >= 0) {
                    check = FindMWPositionHelper(i, x, r, b);
                }
                if (!check){
                    r.rotate();
                }

            }
        }

        return b;
    }

    boolean FindMWPositionHelper(int i, int x, Rectangle r, BestValues b){
        int wasted = computeWastedArea(i, r, x);
        if (wasted < b.getBestWastedArea() ||
                (wasted == b.getBestWastedArea()) && (x + r.width < b.getBestWidth())){
            b.setBestWidth(x + r.width);
            b.setBestSkylineIndex(i);
            b.setBestWastedArea(wasted);
            b.setBestRectangle(r);
            b.bestX = x;
            return true;
        }
        return false;
    }

    boolean FindMWPositionHelper2(int i, int x, Rectangle r, BestValues b){
        int wasted = computeWastedArea(i, r, x);
        if (x + r.width < b.getBestWidth() || //change
                (x + r.width == b.getBestWidth()) && (wasted == b.getBestWastedArea())){
            b.setBestWidth(x + r.width);
            b.setBestSkylineIndex(i);
            b.setBestWastedArea(wasted);
            b.setBestRectangle(r);
            return true;
        }
        return false;
    }
//conversion done
    int RectangleFits(int skyLineIndex, Rectangle r){
        int y = skyline.get(skyLineIndex).y;
        if (y + r.height > binHeight){
            return -1;
        }
        int remainingHeight = r.height;
        int i = skyLineIndex;
        int x = skyline.get(skyLineIndex).x;
        while (remainingHeight > 0){
            x = Math.max(x, skyline.get(i).x);
            //TODO: expanding the bin
            if (x + r.width > binWidth){
                return -1;
            }
            remainingHeight -= skyline.get(i).length;
            i++;
            if (i >= skyline.size() && remainingHeight > 0){
                return -1;
            }
        }


        return x;
    }

    int computeWastedArea(int skyLineIndex, Rectangle r, int x){
        int wasted = 0;
        SkylineNode current = skyline.get(skyLineIndex);
        int rectLeft = current.y;
        int rectRight = rectLeft + r.height;
        int leftSide = 0;
        int rightSide = 0;
        //ArrayList<WasteMapArea> wma = new ArrayList<>();

        for (;skyLineIndex < skyline.size() && skyline.get(skyLineIndex).y  < rectRight; skyLineIndex++){
            current = skyline.get(skyLineIndex);
            if (current.y >= rectRight || current.y + current.length <=rectLeft ){
                break;
            }
            leftSide = current.y;
            rightSide = Math.min(rectRight, leftSide + current.length);
            if ((rightSide - leftSide) * (x - current.x) < 0){
                //System.out.println("wrong");
            }
            wasted += (rightSide - leftSide) * (x - current.x);
           // wma.add(new WasteMapArea(x - current.x, x, leftSide, rightSide));
        }
//        ArrayList<Rectangle> possiblePlacementa = new ArrayList<>();
//        for (int i = 0; i < wma.size(); i++){
//
//        }
//
//        Rectangle wastedRectangle = new Rectangle(-1, x - current.x, rightSide - leftSide);
//        wastedRectangle.x = x;
//        wastedRectangle.y = ;
        if (wasted < 0){
            //System.out.println("wrong");
        }

        return wasted;
    }


    void updateMinNode(){
        int x = Integer.MAX_VALUE;
        for(SkylineNode s: skyline) {
            if (s.x < x) {
                x = s.x;
            }
        }
        minSkylineX = x;
    }



}
