package com.packing.models;

import java.util.ArrayList;

public class Data {

    private boolean containerHeightFixed;
    private int containerHeight = Integer.MAX_VALUE;
    private boolean rotationsAllowed;
    private int rectangleAmount;
    private ArrayList<Rectangle> rectangles = new ArrayList<>();

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

    public void setRotarionsAllowed(boolean rotarionsAllowed) {
        this.rotationsAllowed = rotarionsAllowed;
    }

    public int getRectangleAmount() {
        return rectangleAmount;
    }

    public void setRectangleAmount(int rectangleAmount) {
        this.rectangleAmount = rectangleAmount;
    }

    public void addRectangle(Rectangle r){
        rectangles.add(r);
    }

    @Override
    public String toString(){
        return "container height:" + this.containerHeight + "\n" +
                "rotations allowed: " + this.rotationsAllowed + "\n" +
                "number of rectangles: " + this.rectangleAmount;
    }
}
