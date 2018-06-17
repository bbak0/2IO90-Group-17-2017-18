package com.packing.models;

public class BoundedSpace extends Space {

    public int width;
    public long area;

    public BoundedSpace(int height, int x, int y, int width) {
        super(height, x, y);
        this.width = width;
        this.area = this.area * this.width;
    }


    public long getFreeArea() {
        return area - areaOfRectangles;
    }
}
