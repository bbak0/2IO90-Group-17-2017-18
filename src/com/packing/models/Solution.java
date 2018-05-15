package com.packing.models;

import java.util.ArrayList;
public class Solution {

     public ArrayList<Rectangle> rectangles;
    public long area;
    public long wastedArea;
    public int maxHeight = 0;
    public int maxWidth = 0;


    public void update(){
        calcDimensions();
        calcWastedArea();
    }

    public void calcDimensions(){
        for(Rectangle r: rectangles){
            if(r.y+r.height>maxHeight){
                maxHeight = r.y+r.height;
            }
            if(r.x+r.width>maxWidth){
                maxWidth = r.x+r.width;
            }
        }
        area = maxHeight*maxHeight;
    }
    public void calcWastedArea(){
        long rectangleArea = 0;
        for(Rectangle r: rectangles){
            rectangleArea += r.area;
        }
        wastedArea = area - rectangleArea;

    }
}
