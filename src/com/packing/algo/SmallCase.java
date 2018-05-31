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


        return null;
    }

    void rectangleAssigner(ArrayList<BLnode> nodes, ArrayList<Rectangle> rectangles, ArrayList<Rectangle> placedRectangles) {
        if(rectangles.isEmpty()) {
            if(finalSolution == null) {
                finalSolution = new Solution(placedRectangles, true);
            } else if(new Solution(placedRectangles, true).area < finalSolution.area) {
                finalSolution = new Solution(placedRectangles, true);
            }
        }
        for(BLnode node : nodes) {
            for(Rectangle rect : rectangles) {
                if(rectangleFits(rect, node, placedRectangles)) {
                    rect.x = node.x;
                    rect.y = node.y;
                    rect.isPlaced = true;

                    ArrayList<Rectangle> placedRectanglesTemp = placedRectangles;
                    placedRectanglesTemp.add(rect);

                    ArrayList<Rectangle> rectanglesTemp = rectangles;
                    rectanglesTemp.remove(rect);

                    ArrayList<BLnode> nodesTemp = nodes;

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

                    rectangleAssigner(nodesTemp, rectanglesTemp,placedRectanglesTemp);
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

}
