package com.packing.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class RectangleCanvas extends JPanel implements MouseListener {
    //public static final int CANVAS_WIDTH = 640;
    //public static final int CANVAS_HEIGHT = 480;
    public static final String TITLE = "...Title...";
    private Solution solution;
    int multiplier;
    private ArrayList<Rectangle> rect;
    // ......

    // private variables of GUI components
    // ......

    /** Constructor to setup the GUI components */
    public RectangleCanvas(Solution sol, int mul) {

        // "this" JPanel container sets layout
        // setLayout(new ....Layout());
        solution = sol;
        rect = sol.getRectangles();
        //setPreferredSize(new Dimension(sol.getMaxWidth(), sol.getMaxHeight()));
        setPreferredSize(new Dimension(10000,10000 ));
        multiplier = mul;
        // Allocate the UI components
        // .....
        // "this" JPanel adds components
        // add(....)

        // Source object adds listener
        // .....
    }

    /** Custom painting codes on this JPanel */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // paint background
        setBackground(Color.WHITE);
        for (Rectangle r : rect){
            g.setColor(Color.ORANGE);
            g.fillRect(multiplier * r.x, multiplier * r.y, multiplier * r.width, multiplier *  r.height);
            g.setColor(Color.black);
            g.drawRect(multiplier * r.x, multiplier * r.y, multiplier * r.width, multiplier *  r.height);
        }
        g.setColor(Color.magenta);
        g.fillRect(solution.customX, solution.customY,10,10);

        // Your custom painting codes
        // ......
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
