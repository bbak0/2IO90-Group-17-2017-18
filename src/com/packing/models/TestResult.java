package com.packing.models;

public class TestResult {
    double areaUsed;
    double totalArea;
    Data d;

    public TestResult(double areaUsed, double totalArea, Data d) {
        this.areaUsed = areaUsed;
        this.totalArea = totalArea;
        this.d = d;
    }

    public double getAreaUsed() {
        return areaUsed;
    }

    public void setAreaUsed(double areaUsed) {
        this.areaUsed = areaUsed;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public Data getD() {
        return d;
    }

    public void setD(Data d) {
        this.d = d;
    }
}
