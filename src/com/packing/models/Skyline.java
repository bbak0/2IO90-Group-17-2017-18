package com.packing.models;

import com.packing.algo.AbstractAlgorithm;
import com.packing.sorting.IndexComparator;

import java.util.ArrayList;
import java.util.Collections;

public class Skyline extends AbstractAlgorithm {

    ArrayList<Rectangle> rectangles = input.getRectangles();
    ArrayList<SkylineNode> skyline = new ArrayList<SkylineNode>();
    int binHeight;
    int binWidth;
    ArrayList<Rectangle> solRect = new ArrayList<Rectangle>();
    int areaUsed = 0;
    long inserted = 0;
    int maxHeight = 0;

    public Skyline(Data in) {
        super(in);
    }

    @Override
    public Solution solve() {
        init();
        algoLoop();
        Collections.sort(solRect, new IndexComparator());
        System.out.println("Area used:" + areaUsed + "Total area:" + maxHeight * binWidth);
        return new Solution(solRect, true);
    }

    void init(){
        if (input.isRotationsAllowed()){
            double a = (double)(input.getTotalWidth() + input.getTotalHeight()) / (2 * rectangles.size());
            a = a * Math.sqrt(2 * rectangles.size());
            binWidth = (int) a;
            binHeight = binWidth;
        } else {
            double a = (double)input.getTotalWidth() /  rectangles.size();
            a = a * Math.sqrt(rectangles.size());
            binWidth = (int) a;
            double b = (double)input.getTotalHeight() /  rectangles.size();
            b = b * Math.sqrt(rectangles.size());
            binHeight = (int) b;
        }
        SkylineNode newNode = new SkylineNode(0,0, binWidth);
        skyline.add(newNode);
    }

    void algoLoop(){
        while (rectangles.size() > 0){
            BestValues globalBest = new BestValues();

            for (int i = 0; i < rectangles.size(); i++){
                BestValues rectBest = FindBLPosition(rectangles.get(i));
                if (rectBest.getBestHeight() < globalBest.getBestHeight() ||
                        (rectBest.getBestHeight() == globalBest.getBestHeight() && rectBest.getBestWidth() < globalBest.getBestWidth())){
                    globalBest = rectBest;

                }
            }
            if (globalBest.getBestRectangle() == null){
                return;
            }

            //Add Skyline Level

            //Remove rectangle from the list
            Rectangle bestR = globalBest.getBestRectangle();
            bestR.x = skyline.get(globalBest.getBestSkylineIndex()).x;
            bestR.y = skyline.get(globalBest.getBestSkylineIndex()).y;
            addSkylineLevel(globalBest.getBestSkylineIndex(), bestR);
            solRect.add(bestR);
            areaUsed += bestR.area;
            maxHeight = Math.max(maxHeight, bestR.height + bestR.y);
            inserted++;
            System.out.println("inserted:" + inserted);
            rectangles.remove(globalBest.getBestRectangle());




        }
    }

    void addSkylineLevel(int skylineNodeIndex, Rectangle r){
        SkylineNode newNode = new SkylineNode(r.x, r.y + r.height, r.width);
        skyline.add(skylineNodeIndex, newNode);

        for (int i = skylineNodeIndex + 1; i < skyline.size(); i++){
            SkylineNode current = skyline.get(i);
            SkylineNode previous = skyline.get(i-1);
            if (current.x < previous.x + previous.length){
                int shrink = previous.x + previous.length - current.x;
                current.x += shrink;
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

    void mergeSkylines(){
        for (int i = 0; i < skyline.size() -  1; i++){
            SkylineNode current = skyline.get(i);
            SkylineNode next = skyline.get(i+1);
            if (current.y == next.y){
                current.length += next.length;
                skyline.remove(next);
                i--;
            }
        }
    }

    BestValues FindBLPosition(Rectangle r){
        BestValues b = new BestValues();

        for (int i = 0; i < skyline.size(); i++){
            int y = RectangleFits(i,r);
            if (y != -1){
                FindBLPositionHelper(i, y, r, b);
            }
            if (input.isRotationsAllowed()){
                r.rotate();
                y = RectangleFits(i,r);
                FindBLPositionHelper(i, y, r, b);
                r.rotate();
            }
        }

        return b;
    }

    void FindBLPositionHelper(int i, int y, Rectangle r, BestValues b){
        if (y + r.height < b.getBestHeight() ||
                (y + r.height == b.getBestHeight() && skyline.get(i).length < b.getBestWidth()) ){
            b.setBestHeight(y + r.height);
            b.setBestSkylineIndex(i);
            b.setBestWidth(skyline.get(i).length);
            b.setBestRectangle(r);
        }
    }

    int RectangleFits(int skyLineIndex, Rectangle r){
        int x = skyline.get(skyLineIndex).x;
        if (x + r.width > binWidth){
            return -1;
        }
        int remainingWidth = r.width;
        int i = skyLineIndex;
        int y = skyline.get(skyLineIndex).y;
        while (remainingWidth > 0){
            y = Math.max(y, skyline.get(i).y);
            //TODO: expanding the bin
            if (y + r.height > binHeight){
                return -1;
            }
            remainingWidth -= skyline.get(i).length;
            i++;
            if (i >= skyline.size() && remainingWidth > 0){
                return -1;
            }
        }


        return y;
    }



}
