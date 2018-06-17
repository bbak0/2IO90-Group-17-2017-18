package com.packing.algo;

import com.packing.models.BLnode;
import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.Solution;

import java.util.ArrayList;

public class SmallCase extends AbstractAlgorithm{

    public SmallCase(Data in) {
        super(in);
    }

    Solution finalSolution = null;

    @Override
    public Solution solve() {
        ArrayList<BLnode> nodes = new ArrayList<BLnode>(); //initialize input
        nodes.add(new BLnode(0, 0));
        if (input.isRotationsAllowed() && input.isContainerHeightFixed()) {
            rectangleAssignerStripWithRotations(nodes, input.getRectangles(), new ArrayList<Rectangle>());
        } else if (input.isRotationsAllowed()){ // && !isContainerHeightFixed()
            rectangleAssignerWithRotations(nodes, input.getRectangles(), new ArrayList<Rectangle>());
        } else if (input.isContainerHeightFixed()) { //&& !isRotationsAllowed()
            rectangleAssignerStrip(nodes, input.getRectangles(), new ArrayList<Rectangle>());
        } else {
            rectangleAssigner(nodes, input.getRectangles(), new ArrayList<Rectangle>());
        }
        return finalSolution;
    }

    void rectangleAssigner(ArrayList<BLnode> nodes, ArrayList<Rectangle> rectangles, ArrayList<Rectangle> placedRectangles) {
        updateSolution(rectangles, placedRectangles);

        for(BLnode node : nodes) {
            for(Rectangle rect : rectangles) {
                putRectangle(rect, node, rectangles, placedRectangles, nodes);
            }
        }
    }
    void rectangleAssignerWithRotations(ArrayList<BLnode> nodes, ArrayList<Rectangle> rectangles, ArrayList<Rectangle> placedRectangles) {
        updateSolution(rectangles, placedRectangles);

        for(BLnode node : nodes) {
            for(Rectangle rect : rectangles) {
                putRectangle(rect, node, rectangles, placedRectangles, nodes);
                rect.rotate();  //MAYBE NEED A CLONE OVER HERE? IS THE ACTUAL RECTANGLE ROTATED IN PREVIOUS LINE AS WELL?
                putRectangleWithRotations(rect, node, rectangles, placedRectangles, nodes);
            }
        }
    }
    void rectangleAssignerStrip(ArrayList<BLnode> nodes, ArrayList<Rectangle> rectangles, ArrayList<Rectangle> placedRectangles) {
        updateSolution(rectangles, placedRectangles);

        for(BLnode node : nodes) {
            for(Rectangle rect : rectangles) {
                putRectangleFixedHeight(rect, node, rectangles, placedRectangles, nodes);
            }
        }
    }
    void rectangleAssignerStripWithRotations(ArrayList<BLnode> nodes, ArrayList<Rectangle> rectangles, ArrayList<Rectangle> placedRectangles) {
        updateSolution(rectangles, placedRectangles);

        for(BLnode node : nodes) {
            for(Rectangle rect : rectangles) {
                putRectangleFixedHeightWithRotations(rect, node, rectangles, placedRectangles, nodes);
                rect.rotate();
                putRectangleFixedHeightWithRotations(rect, node, rectangles, placedRectangles, nodes);
            }
        }
    }

    void updateSolution(ArrayList<Rectangle> rectangles, ArrayList<Rectangle> placedRectangles) {
        if(rectangles.isEmpty()) {
            ArrayList<Rectangle> placedRectanglesTemp = (ArrayList<Rectangle>) placedRectangles.clone();
            Solution finalSolutionTemp = new Solution(placedRectanglesTemp, true);
            System.out.println(finalSolutionTemp.area);
            if(finalSolution == null) {
                finalSolution = new Solution(placedRectanglesTemp, true);
                return;
            } else if(finalSolutionTemp.area < finalSolution.area) {
                finalSolution = finalSolutionTemp;
                System.out.println("Found it");
                return;
            }
        }
    }

    void putRectangle(Rectangle rect, BLnode node, ArrayList<Rectangle> rectangles, ArrayList<Rectangle> placedRectangles, ArrayList<BLnode> nodes) {
        if(rectangleFits(rect, node, placedRectangles)) {
            Rectangle rectTemp = new Rectangle(rect.index, rect.width, rect.height);
            ArrayList<BLnode> nodesTemp = (ArrayList<BLnode>) nodes.clone();
            ArrayList<Rectangle> rectanglesTemp = (ArrayList<Rectangle>) rectangles.clone();
            rectTemp.x = node.x;
            rectTemp.y = node.y;
            rectTemp.isPlaced = true;
            ArrayList<Rectangle> placedRectanglesTemp = (ArrayList<Rectangle>) placedRectangles.clone();

            putRectangleGeneric(rect, node, rectangles, rectanglesTemp, placedRectangles, placedRectanglesTemp, rectTemp, nodes, nodesTemp);

            rectangleAssigner(nodesTemp, rectanglesTemp,placedRectanglesTemp);
        }
    }
    void putRectangleWithRotations(Rectangle rect, BLnode node, ArrayList<Rectangle> rectangles, ArrayList<Rectangle> placedRectangles, ArrayList<BLnode> nodes) {
        if(rectangleFits(rect, node, placedRectangles)) {
            Rectangle rectTemp = new Rectangle(rect.index, rect.width, rect.height);
            ArrayList<BLnode> nodesTemp = (ArrayList<BLnode>) nodes.clone();
            ArrayList<Rectangle> rectanglesTemp = (ArrayList<Rectangle>) rectangles.clone();
            rectTemp.x = node.x;
            rectTemp.y = node.y;
            rectTemp.isPlaced = true;
            ArrayList<Rectangle> placedRectanglesTemp = (ArrayList<Rectangle>) placedRectangles.clone();

            putRectangleGeneric(rect, node, rectangles, rectanglesTemp, placedRectangles, placedRectanglesTemp, rectTemp, nodes, nodesTemp);

            rectangleAssignerWithRotations(nodesTemp, rectanglesTemp,placedRectanglesTemp);
        }
    }

    void putRectangleFixedHeight(Rectangle rect, BLnode node, ArrayList<Rectangle> rectangles, ArrayList<Rectangle> placedRectangles, ArrayList<BLnode> nodes) {
        if(rectangleFits(rect, node, placedRectangles) && rectangleFitsContainer(rect, node)) {

            Rectangle rectTemp = new Rectangle(rect.index, rect.width, rect.height);
            ArrayList<BLnode> nodesTemp = (ArrayList<BLnode>) nodes.clone();
            ArrayList<Rectangle> rectanglesTemp = (ArrayList<Rectangle>) rectangles.clone();
            rectTemp.x = node.x;
            rectTemp.y = node.y;
            rectTemp.isPlaced = true;
            ArrayList<Rectangle> placedRectanglesTemp = (ArrayList<Rectangle>) placedRectangles.clone();

            putRectangleGeneric(rect, node, rectangles, rectanglesTemp, placedRectangles, placedRectanglesTemp, rectTemp, nodes, nodesTemp);

            //FIXED HEIGHT == STRIP
            rectangleAssignerStrip(nodesTemp, rectanglesTemp,placedRectanglesTemp);
        }
    }

    void putRectangleFixedHeightWithRotations(Rectangle rect, BLnode node, ArrayList<Rectangle> rectangles, ArrayList<Rectangle> placedRectangles, ArrayList<BLnode> nodes) {
        if(rectangleFits(rect, node, placedRectangles) && rectangleFitsContainer(rect, node)) {

            Rectangle rectTemp = new Rectangle(rect.index, rect.width, rect.height);
            ArrayList<BLnode> nodesTemp = (ArrayList<BLnode>) nodes.clone();
            ArrayList<Rectangle> rectanglesTemp = (ArrayList<Rectangle>) rectangles.clone();
            rectTemp.x = node.x;
            rectTemp.y = node.y;
            rectTemp.isPlaced = true;
            ArrayList<Rectangle> placedRectanglesTemp = (ArrayList<Rectangle>) placedRectangles.clone();

            putRectangleGeneric(rect, node, rectangles, rectanglesTemp, placedRectangles, placedRectanglesTemp, rectTemp, nodes, nodesTemp);

            //FIXED HEIGHT == STRIP
            rectangleAssignerStripWithRotations(nodesTemp, rectanglesTemp,placedRectanglesTemp);
        }
    }
    void putRectangleGeneric(Rectangle rect, BLnode node, ArrayList<Rectangle> rectangles, ArrayList<Rectangle> rectanglesTemp, ArrayList<Rectangle> placedRectangles, ArrayList<Rectangle> placedRectanglesTemp, Rectangle rectTemp, ArrayList<BLnode> nodes, ArrayList<BLnode> nodesTemp){

        placedRectanglesTemp.add(rectTemp);
        rectanglesTemp.remove(rect);

        nodesTemp.add(addVerticalNode(nodesTemp, node, rect.height, placedRectanglesTemp));
        nodesTemp.add(addHorizontalNode(nodesTemp, node, rect.width, placedRectanglesTemp));

        nodesTemp.remove(node);
        //
        //                    //add the other possiblity

        for(BLnode nodeno : nodes) {
            if(!isNodeFree( placedRectangles,nodeno)) {
                if(node.x == nodeno.x) {
                    nodeno.x = nodeno.x + rect.width;
                } else if (node.y == nodeno.y) {
                    nodeno.y = nodeno.y + rect.height;
                }
            }
        }
    }
    BLnode addVerticalNode(ArrayList<BLnode> nodes, BLnode node, int height, ArrayList<Rectangle> placedRectangles) {

        int x = node.x; //initialize for comparison

        for(BLnode currentNode : nodes) {
            if(currentNode.x < x) { //for every node on the left
                if(currentNode.y < node.y + height) { // check if the node is lower than current node
                    x = currentNode.x; //then update leftest possible node
                    if(!isNodeFree(placedRectangles, new BLnode(node.x - 1, node.y))) {
                        break;
                    }
                }
            }
        }

        return new BLnode(node.x, node.y + height); //thus add the leftest possible node
    }

    BLnode addHorizontalNode(ArrayList<BLnode> nodes, BLnode node, int width, ArrayList<Rectangle> placedRectangles) {

        int y = node.y; //initialize for comparison

        for(BLnode currentNode : nodes) {
            if(currentNode.y < y) { //for every node under
                if(currentNode.x < node.x + width) { // check if the node is lefter than the node considered
                    y = currentNode.y; //then update lowest possible node
                    if(!isNodeFree(placedRectangles, new BLnode(node.x, node.y - 1))) {
                        break;
                    }
                }
            }
        }

        return new BLnode(node.x + width, y); //thus add the leftest possible node
    }

    boolean isNodeFree(ArrayList<Rectangle> placedRectangles, BLnode node) {
        return rectangleFits(new Rectangle(0,0, 1, 1), node, placedRectangles); //create dummy rectangle 1 by 1 and check if it fits on the node.
    }

    boolean rectangleFits(Rectangle rectangle, BLnode node, ArrayList<Rectangle> placedRectangles) {
        for(Rectangle rect : placedRectangles) {
            if(!(node.x + rectangle.width <= rect.x || rect.x + rect.width <= node.x)) { //compare if rectangles overlap horizontally
                if (!(node.y + rectangle.height <= rect.y || rect.y + rect.height <= node.y)) { //compare if rectangles overlap vertically
                    return false;
                }
            }
        }
        return true;
    }

    boolean rectangleFitsContainer(Rectangle rectangle, BLnode node) {
        return node.y + rectangle.height <= input.getContainerHeight();
    }


}
