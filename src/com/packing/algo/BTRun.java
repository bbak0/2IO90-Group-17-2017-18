package com.packing.algo;
import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.Solution;
import com.packing.sorting.WidthComparator;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
public class BTRun extends AbstractAlgorithm{


    public BTRun(Data in) {
        super(in);
    }
    @Override
    public Solution solve() {
        int avgH = 0, avgW = 0;
        int bigH, bigW;
        ArrayList<Rectangle> blocks = input.getRectangles();  // THIS IS SHIT.
        int n = blocks.size();
        avgH /= blocks.size();
        avgW /= blocks.size();
        for(Rectangle rectangle : blocks) {
            avgH += rectangle.getHeight();
            avgW += rectangle.getWidth();
        }
        avgH /= blocks.size();
        avgW /= blocks.size();
        bigH = (int) (avgH * Math.sqrt(n));
        bigW = (int) (avgW * Math.sqrt(n));
        BTP packer = new BTP(bigW, bigH);

        //////////


        Collections.sort(blocks, new WidthComparator());

        packer.fit(blocks);
        Iterator<Rectangle> blocksItr = blocks.iterator();
        while (blocksItr.hasNext()) {
            Rectangle block = blocksItr.next();
            if (block.fit != null) {
                int fitX = block.fit.x;
                int fitY = block.fit.y;
                block.placeRectangle(fitX, fitY);


            }
        }

        return new Solution(blocks);
    }
}

