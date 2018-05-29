package com.packing.models;

public class BTPnode {
    public boolean isroot = false;
    public String name;
    public double x;
    public double y;
    public double w;
    public double h;
    public boolean used = false;
    public BTPnode right = null;
    public BTPnode down = null;
    public BTPnode fit = null;

    public BTPnode(double w, double h) {
        this.name = name;
        this.w = w;
        this.h = h;
    }

    public BTPnode(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        if (x == 0 && y == 0) {
            this.isroot = true;//this is only necessary for me to print 'Pack Starts Here' in the example code
        }
    }
}
