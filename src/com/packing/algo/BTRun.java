package com.packing.algo;
import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.Solution;
import com.packing.sorting.IndexComparator;
import com.packing.sorting.WidthComparator;
import java.util.ArrayList;
import com.packing.models.BTP;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
public class BTRun extends AbstractAlgorithm{


    public BTRun(Data in) {
        super(in);
    }
    @Override
    public Solution solve() {

        ArrayList<Rectangle> rectangleCollection = input.getRectangles();  // THIS IS SHIT.
        BTP packer = new BTP(1000, 1000);




        Collections.sort(rectangleCollection, new WidthComparator());

        packer.fit(rectangleCollection);
        Iterator<Rectangle> blocksItr = rectangleCollection.iterator();
        while (blocksItr.hasNext()) {
            Rectangle rectangle = blocksItr.next();
            if (rectangle.fit != null) {
                int fitX = rectangle.fit.x;
                int fitY = rectangle.fit.y;
                rectangle.placeRectangle(fitX, fitY);


            }
        }
        Collections.sort(rectangleCollection, new IndexComparator());
        return new Solution(rectangleCollection);
    }
}
