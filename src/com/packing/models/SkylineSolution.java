package com.packing.models;

import java.util.ArrayList;

public class SkylineSolution extends Solution {
    ArrayList<SkylineNode> skyline;

    public SkylineSolution(ArrayList<Rectangle> recs, boolean calc) {
        super(recs, calc);
    }

    public ArrayList<SkylineNode> getSkyline() {
        return skyline;
    }

    public void setSkyline(ArrayList<SkylineNode> skyline) {
        this.skyline = skyline;
    }
}
