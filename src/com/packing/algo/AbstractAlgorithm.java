package com.packing.algo;

import com.packing.models.Data;

public abstract class AbstractAlgorithm {

    protected Data input;

    public AbstractAlgorithm(Data in){
        this.input = in;
    }

    public abstract void solve();
}
