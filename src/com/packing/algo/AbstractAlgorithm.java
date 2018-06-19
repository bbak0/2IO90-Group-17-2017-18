package com.packing.algo;

import com.packing.models.Data;
import com.packing.models.Solution;

public abstract class AbstractAlgorithm {

    protected Data input;

    public AbstractAlgorithm(Data in) {
        this.input = in.copyOf();
    }

    public abstract Solution solve();
}
