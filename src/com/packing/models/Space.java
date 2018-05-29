package com.packing.models;

import java.util.ArrayList;

public abstract class Space {

    protected ArrayList<Rectangle> rectangles;
    protected int height;
    protected boolean isPacked;
    protected long areaOfRectangles;
    //coordinates
    public int x = 0;
    public int y = 0;

    public Space(int height, int x, int y) {
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public void calcRectangleArea() {
        for (Rectangle r : rectangles) {
            areaOfRectangles += r.area;
        }
    }
}
