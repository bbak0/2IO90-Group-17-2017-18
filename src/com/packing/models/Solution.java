package com.packing.models;

import java.util.ArrayList;
public class Solution {

    public ArrayList<Rectangle> rectangles;
    public long area;
    public int maxHeight ;
    public int maxWidth;
    public long areaOfRectangles;

    public Solution(){ }
    public Solution(ArrayList<Rectangle> recs){
        this(recs,false);
    }
    public Solution(ArrayList<Rectangle> recs, boolean calc){
        rectangles = recs;
        if(calc) {
            calcRectangleArea();
            update();
        }
    }

    public void update(){
        for(Rectangle r: rectangles){
            if(r.y+r.height>maxHeight){
                maxHeight = r.y+r.height;
            }
            if(r.x+r.width>maxWidth){
                maxWidth = r.x+r.width;
            }
        }
        area = maxHeight*maxWidth;
    }
    public void calcRectangleArea(){
        for (Rectangle r: rectangles){
            areaOfRectangles += r.area;
        }
    }
    public long getWastedArea(){
        update();
        calcRectangleArea();
        return area-areaOfRectangles;
    }

    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }
}
