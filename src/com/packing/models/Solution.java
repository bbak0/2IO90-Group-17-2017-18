package com.packing.models;

import java.util.ArrayList;
public class Solution {

     public ArrayList<Rectangle> rectangles;
    public long area;
    public int maxHeight ;
    public int maxWidth;
    public long areaOfRectangles;


    public void update(){
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
    public long getWastedArea(){
        return area-areaOfRectangles;
    }
}
