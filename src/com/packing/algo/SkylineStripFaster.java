package com.packing.algo;

import com.packing.algo.AbstractAlgorithm;
import com.packing.models.BestValues;
import com.packing.models.Data;
import com.packing.models.Rectangle;
import com.packing.models.SkylineNode;
import com.packing.models.SkylineSolution;
import com.packing.models.Solution;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class SkylineStripFaster extends AbstractAlgorithm {

    ArrayList<Rectangle> rectangles = new ArrayList<>(input.getRectangles());
    ArrayList<Rectangle> freeRectangles = new ArrayList<>(input.getRectangles());
    ArrayList<SkylineNode> skyline = new ArrayList<SkylineNode>();
    ArrayList<Rectangle> solRect = new ArrayList();
    int binHeight;
    int binWidth;
    int areaUsed = 0;
    long inserted = 0;
    int maxWidth = 0;
    int maxSpread = Integer.MAX_VALUE;
    int minSkylineX = 0;


    public SkylineStripFaster(Data in) {
        super(in);
    }

    public void setMaxSpread(int maxSpread) {
        this.maxSpread = maxSpread;
    }

    @Override
    public Solution solve() {
        long startTime = System.currentTimeMillis();


        init();
        boolean successful = algoLoop();
        if (!successful) {
            return null;
        }
        binWidth = maxWidth;
        SkylineSolution returnSol = new SkylineSolution(solRect, false);
        returnSol.setSkyline(skyline);
        returnSol.maxWidth = this.maxWidth;
        returnSol.maxHeight = input.getMaxHeight();
        return returnSol;

    }

    void init() {
        if (input.getContainerHeight() != Integer.MAX_VALUE) {
            binHeight = input.getContainerHeight();
            binWidth = Integer.MAX_VALUE;
        } else {
            throw new InputMismatchException("Algorithm meant to work with strip packing");
        }
        //freeRectangles.sort(new HeightComparator());
        SkylineNode newNode = new SkylineNode(0, 0, binHeight);
        skyline.add(newNode);

    }

    boolean algoLoop() {

        while (rectangles.size() > 0) {
            BestValues globalBest = FindMWPosition(rectangles.get(0));
            Rectangle bestR = globalBest.getBestRectangle();
            if (bestR == null) {
                return false;
            }
            bestR.x = globalBest.bestX;
            bestR.y = skyline.get(globalBest.getBestSkylineIndex()).y;
            rectangles.remove(bestR);
            freeRectangles.remove(bestR);
            //with true param it tries to add a rectangle to wasted area
            computeWastedArea(globalBest.getBestSkylineIndex(), bestR, bestR.x, true);
            addSkylineLevel(globalBest.getBestSkylineIndex(), bestR);
            boolean debug = solRect.add(bestR.copyOf());
            //following code used in debugging
//                if(!debug){
//                    SkylineSolution debugSol = new SkylineSolution(solRect, false);
//                    debugSol.setSkyline(skyline);
//                    new AXD().openNewCanvas(debugSol, 5);
//                    System.out.println("aaaaa");
//                    throw new RuntimeException();
//                }

            areaUsed += bestR.area;
            maxWidth = Math.max(maxWidth, bestR.width + bestR.x);
            inserted++;
        }
        return true;
    }

    //done
    void addSkylineLevel(int skylineNodeIndex, Rectangle r) {
        SkylineNode newNode = new SkylineNode(r.x + r.width, r.y, r.height);
        skyline.add(skylineNodeIndex, newNode);

        for (int i = skylineNodeIndex + 1; i < skyline.size(); i++) {
            SkylineNode current = skyline.get(i);
            SkylineNode previous = skyline.get(i - 1);
            if (current.y < previous.y + previous.length) {
                int shrink = previous.y + previous.length - current.y;
                current.y += shrink;
                current.length -= shrink;

                if (current.length <= 0) {
                    skyline.remove(current);
                    i--;
                } else {
                    break;
                }

            } else {
                break;
            }
        }
        mergeSkylines();
        updateMinNode();
    }

    void mergeSkylines() {
        for (int i = 0; i < skyline.size() - 1; i++) {
            SkylineNode current = skyline.get(i);
            SkylineNode next = skyline.get(i + 1);
            if (current.x == next.x) {
                current.length += next.length;
                skyline.remove(next);
                i--;
            }
        }
    }

    BestValues FindMWPosition(Rectangle r) {
        BestValues b = new BestValues();
        for (int i = 0; i < skyline.size(); i++) {
            int x = RectangleFits(i, r);
            if (x - minSkylineX > maxSpread) {
                continue;
            }
            if (x >= 0) {
                FindMWPositionHelper2(i, x, r, b);
            }
            boolean check = false;
            if (input.isRotationsAllowed()) {
                r.rotate();
                x = RectangleFits(i, r);
                if (x >= 0) {
                    check = FindMWPositionHelper2(i, x, r, b);
                }
                if (!check) {
                    r.rotate();
                }

            }
        }

        return b;
    }

    //not used in current version
    boolean FindMWPositionHelper(int i, int x, Rectangle r, BestValues b) {
        int wasted = computeWastedArea(i, r, x, false);
        if (wasted < b.getBestWastedArea() ||
                (wasted == b.getBestWastedArea()) && (x + r.width < b.getBestWidth())) {
            b.setBestWidth(x + r.width);
            b.setBestSkylineIndex(i);
            b.setBestWastedArea(wasted);
            b.setBestRectangle(r);
            b.bestX = x;
            return true;
        }
        return false;
    }

    boolean FindMWPositionHelper2(int i, int x, Rectangle r, BestValues b) {
        int wasted = computeWastedArea(i, r, x, false);
        if (x + r.width + Math.sqrt(wasted) < b.getBestWidth() + Math.sqrt(b.getBestWastedArea()) ||
                (x + r.width + Math.sqrt(wasted) == b.getBestWidth() + Math.sqrt(b.getBestWastedArea())) && (x + r.width < b.getBestWidth())) {
            b.setBestWidth(x + r.width);
            b.setBestSkylineIndex(i);
            b.setBestWastedArea(wasted);
            b.setBestRectangle(r);
            b.bestX = x;
            return true;
        }
        return false;
    }

    //conversion done
    int RectangleFits(int skyLineIndex, Rectangle r) {
        int y = skyline.get(skyLineIndex).y;
        if (y + r.height > binHeight) {
            return -1;
        }
        int remainingHeight = r.height;
        int i = skyLineIndex;
        int x = skyline.get(skyLineIndex).x;
        while (remainingHeight > 0) {
            x = Math.max(x, skyline.get(i).x);
            if (x + r.width > binWidth) {
                return -1;
            }
            remainingHeight -= skyline.get(i).length;
            i++;
            if (i >= skyline.size() && remainingHeight > 0) {
                return -1;
            }
        }


        return x;
    }

    int computeWastedArea(int skyLineIndex, Rectangle r, int x, boolean addWasteMap) {
        int wasted = 0;
        SkylineNode current = skyline.get(skyLineIndex);
        int rectLeft = current.y;
        int rectRight = rectLeft + r.height;
        int leftSide = 0;
        int rightSide = 0;
        for (; skyLineIndex < skyline.size() && skyline.get(skyLineIndex).y < rectRight; skyLineIndex++) {
            current = skyline.get(skyLineIndex);
            if (current.y >= rectRight || current.y + current.length <= rectLeft) {
                break;
            }
            leftSide = current.y;
            rightSide = Math.min(rectRight, leftSide + current.length);
            wasted += (rightSide - leftSide) * (x - current.x);
            if (addWasteMap) {
                int width = x - current.x;
                int height = rightSide - leftSide;
                placeWasteRectangle(width, height, current.x, current.y, true);
            }

        }


        return wasted;
    }


    void updateMinNode() {
        int x = Integer.MAX_VALUE;
        for (SkylineNode s : skyline) {
            if (s.x < x) {
                x = s.x;
            }
        }
        minSkylineX = x;
    }

    void placeWasteRectangle(int width, int height, int x, int y, boolean recursive) {
        Rectangle bestRectangle = null;
        int placementScore = 0;
        for (Rectangle z : freeRectangles) {
            int score = z.width * z.height;
            if (z.width <= width && z.height <= height) {

                if (score > placementScore) {
                    bestRectangle = z;
                    placementScore = score;
                }
            } else if (input.isRotationsAllowed() && z.width <= height && z.height <= width) {
                z.rotate();
                if (score > placementScore) {
                    bestRectangle = z;
                    placementScore = score;
                }
            }
        }
        if (bestRectangle != null) {
            bestRectangle.x = x;
            bestRectangle.y = y;
            solRect.add(bestRectangle.copyOf());
            freeRectangles.remove(bestRectangle);
            rectangles.remove(bestRectangle);
            if (recursive == true) {
                if (width - bestRectangle.width > height - bestRectangle.width) {
                    placeWasteRectangle(width - bestRectangle.width, height, x + bestRectangle.width, y, false);
                    placeWasteRectangle(bestRectangle.width, height - bestRectangle.height, x, y + bestRectangle.height, false);
                } else {
                    placeWasteRectangle(width, height - bestRectangle.height, x, y + bestRectangle.height, false);
                    placeWasteRectangle(width - bestRectangle.width, bestRectangle.height, x + bestRectangle.width, y, false);
                }
            }
        }
    }

}
