package com.packing.models;

import java.util.ArrayList;
public class Solution {

     ArrayList<Rectangle> rectangles;
     long area;
    int maxHeight = 0;
    int maxWidth = 0;

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
}
