package com.packing.sorting;

import com.packing.algo.AbstractAlgorithm;
import com.packing.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

public class SkylineStripFaster2 extends AbstractAlgorithm {

    ArrayList<Rectangle> rectangles = input.getRectangles();
    ArrayList<SkylineNode> skyline = new ArrayList<SkylineNode>();
    int binHeight;
    int binWidth;
    ArrayList<Rectangle> solRect = new ArrayList<>();
    ArrayList<ArrayList<Rectangle>> wasteMap = new ArrayList<>();
    int areaUsed = 0;
    long inserted = 0;
    int maxWidth = 0;
    int MAX_SPREAD = 50;
    int minX = 0;

    public SkylineStripFaster2(Data in) {
        super(in);
    }

    @Override
    public Solution solve() {
        long startTime = System.currentTimeMillis();
        Collections.sort(rectangles, new IncreaseWidthComparator());
        for(Rectangle r : rectangles){
            if (r.height < r.width){
                r.rotate();
            }
        }
        input.setRotationsAllowed(false);

        init();
        algoLoop();
        //Collections.sort(solRect, new IndexComparator());
        System.out.println("Area used:" + areaUsed + "Total area:" + binHeight * maxWidth);
        binWidth = maxWidth;
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime/1000);
        return new Solution(solRect, true);

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

    void algoLoop(){



            for (int i = 0; i < rectangles.size(); i++){
                if (inserted == 40){
                     System.out.println("debug");
                }
                if (i == 9950){
                    System.out.println("debug");
                }
                BestValues globalBest = FindMWPosition(rectangles.get(i));
                Rectangle bestR = globalBest.getBestRectangle();
                if (inserted == 2105){
                    System.out.println("dd");
                }
                bestR.x = skyline.get(globalBest.getBestSkylineIndex()).x;
                bestR.y = skyline.get(globalBest.getBestSkylineIndex()).y;
                addSkylineLevel(globalBest.getBestSkylineIndex(), bestR);

                solRect.add(bestR);
                areaUsed += bestR.area;
                maxWidth = Math.max(maxWidth, bestR.width + bestR.x);
                inserted++;
                System.out.println("inserted:" + inserted);
            }






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
                (wasted == b.getBestWastedArea()) && x + r.width < b.getBestWidth()){
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
                System.out.println("cos sie zjebalo");
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
            System.out.println("cos sie zjebalo");
        }

        return wasted;
    }



}
