package com.packing.models;

import java.util.ArrayList;

public class Data {

    boolean containerHeightFixed;
    int containerHeight = Integer.MAX_VALUE;
    boolean rotationsAllowed;
    int rectangleAmount;
    int totalWidth = 0;
    int totalHeight = 0;
    int maxHeight = 0;
    int maxWidth = 0;

    public ArrayList<Rectangle> rectangles = new ArrayList<>();

    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public boolean isContainerHeightFixed() {
        return containerHeightFixed;
    }

    public void setContainerHeightFixed(boolean containerHeightFixed) {
        this.containerHeightFixed = containerHeightFixed;
    }

    public int getContainerHeight() {
        return containerHeight;
    }

    public void setContainerHeight(int containerHeight) {
        this.containerHeight = containerHeight;
    }

    public boolean isRotationsAllowed() {
        return rotationsAllowed;
    }

    public void setRotationsAllowed(boolean rotationsAllowed) {
        this.rotationsAllowed = rotationsAllowed;
    }

    public int getRectangleAmount() {
        return rectangleAmount;
    }

    public void setRectangleAmount(int rectangleAmount) {
        this.rectangleAmount = rectangleAmount;
    }

    public void addRectangle(Rectangle r) {
        totalWidth += r.width;
        totalHeight += r.height;
        maxWidth = Math.max(maxWidth, r.width);
        maxHeight = Math.max(maxHeight, r.height);
        rectangles.add(r);
    }

    public int getTotalWidth() {
        return totalWidth;
    }

    public int getTotalHeight() {
        return totalHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public Data copyOf(){
        Data out = new Data();
        ArrayList<Rectangle> temparray = new ArrayList<>();
        for(Rectangle rect: rectangles) {
            temparray.add(rect.copyOf());
        }
        out.rectangles = temparray;
        out.containerHeight = this.containerHeight;
        out.containerHeightFixed = this.containerHeightFixed;
        out.maxHeight = this.maxHeight;
        out.maxWidth = this.maxWidth;
        out.rectangleAmount = this.rectangleAmount;
        out.rotationsAllowed = this.rotationsAllowed;
        out.totalWidth = this.totalWidth;
        out.totalHeight = this.totalHeight;
        return out;
    }

    @Override
    public String toString() {
        return "container height:" + this.containerHeight + "\n" +
                "rotations allowed: " + this.rotationsAllowed + "\n" +
                "number of rectangles: " + this.rectangleAmount;
    }
}