package com.packing;

import com.packing.models.Data;
import com.packing.models.Rectangle;

import java.util.Scanner;

public class InputReader {
    Scanner sc = new Scanner(System.in);

    public InputReader(){}

    public Data read(){
        Data result = new Data();
        String cHeight = sc.nextLine();
        String cRotations = sc.nextLine();
        String cRectangles = sc.nextLine();

        if (cHeight.contains("free")){
            result.setContainerHeightFixed(false);
        } else {
            result.setContainerHeightFixed(true);
            int i = cHeight.indexOf("fixed");
            int h = Integer.valueOf(cHeight.substring(i + "fixed ".length()));
            result.setContainerHeight(h);
        }

        if (cRotations.contains("yes")){
            result.setRotarionsAllowed(true);
        } else {
            result.setRotarionsAllowed(false);
        }
        int i = cRectangles.indexOf("rectangles:");
        int n = Integer.valueOf(cRectangles.substring(i + "rectangles: ".length()));
        result.setRectangleAmount(n);

        for (int j = 0; j < n; j++){
            result.addRectangle(new Rectangle(j, sc.nextInt(), sc.nextInt()));
        }
        return result;
    }
}
