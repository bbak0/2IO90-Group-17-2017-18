package com.packing.models;

public class BestValues {
    int bestWidth;
    int bestHeight;
    int bestSkylineIndex;
    int bestRectIndex;
    Rectangle bestRectangle;
    Rectangle oldRectangle;
    int bestWastedArea = Integer.MAX_VALUE;


    public BestValues() {
        bestWidth = Integer.MAX_VALUE;
        bestHeight = Integer.MAX_VALUE;
        bestSkylineIndex = -1;
        bestRectIndex = -1;
    }

    public Rectangle getOldRectangle() {
        return oldRectangle;
    }

    public void setOldRectangle(Rectangle oldRectangle) {
        this.oldRectangle = oldRectangle;
    }

    public int getBestWastedArea() {
        return bestWastedArea;
    }

    public void setBestWastedArea(int bestWastedArea) {
        this.bestWastedArea = bestWastedArea;
    }

    public int getBestWidth() {
        return bestWidth;
    }

    public void setBestWidth(int bestWidth) {
        this.bestWidth = bestWidth;
    }

    public int getBestHeight() {
        return bestHeight;
    }

    public void setBestHeight(int bestHeight) {
        this.bestHeight = bestHeight;
    }

    public int getBestSkylineIndex() {
        return bestSkylineIndex;
    }

    public void setBestSkylineIndex(int bestSkylineIndex) {
        this.bestSkylineIndex = bestSkylineIndex;
    }

    public int getBestRectIndex() {
        return bestRectIndex;
    }

    public void setBestRectIndex(int bestRectIndex) {
        this.bestRectIndex = bestRectIndex;
    }

    public Rectangle getBestRectangle() {
        return bestRectangle;
    }

    public void setBestRectangle(Rectangle bestRectangle) {
        this.bestRectangle = bestRectangle;
    }
}
