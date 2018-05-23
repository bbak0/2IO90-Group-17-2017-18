package com.packing;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class GUI  {


    JFrame frame = new JFrame(); //creates frame
    JPanel panel_1;

    public JLabel[][] grid;

    Color myBG = new Color(255, 255, 255);
    Color myCell = new Color(0, 0, 0);
    Color myBorder = new Color(52, 3, 82);
    Color myPanel = new Color(255, 100, 95);
    Color red = new Color(255, 102, 102);   // colors
    Color blue = new Color(51, 153, 255);
    Color lightBlue = new Color(153, 255, 255);
    Color brown = new Color(160,82,45);

    Color[] colorSet = {Color.white, Color.red, Color.BLUE, Color.orange, Color.green, Color.pink, Color.cyan, Color.magenta, Color.yellow, Color.black, Color.white};


    static int xf, yf;

    public GUI(int length, int width, int bigBox[][]) {//constructor with 2

        panel_1 = new JPanel();
        panel_1.setLayout(new GridLayout(length, width)); //set layout of frame
        panel_1.setFont(new Font("Serif", Font.PLAIN, 12));
        frame.add(panel_1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        grid = new JLabel[length][width]; //allocate the size of grid

        for (int i = 0; i < length; i++) {
            for (int j = 0; j< width; j++) {


                grid[i][j] = new JLabel();
               // grid[i][j].setBorder(BorderFactory.createLineBorder(myBorder));
                grid[i][j].setOpaque(true);
              //  grid[i][j].setText((i+1) + " "+ (j+1));

            }
        }


        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {


              //  grid[i][j].setBorder(BorderFactory.createLineBorder(myBorder));
                grid[i][j].setOpaque(true);
                Color mycolor = new Color(255, 128, bigBox[i][j]);

                if (bigBox[i][j] > 0)
                    grid[i][j].setBackground(colorSet[bigBox[i][j] % 11]);
                else
                    grid[i][j].setBackground(Color.white);



                panel_1.add(grid[i][j]); //adds button to grid
            }
        }


        // frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {


        File file = new File("C:\\Users\\Zordon\\IdeaProjects\\2IO90-Group-17-2017-18\\src\\com\\packing\\testGUI.out");
        int[][] bigBox = new int[10000][10000];
        int i, j;

        try {
            Scanner sc = new Scanner(file);


            // READ X SIZE
            xf = sc.nextInt();
            // READ Y SIZE
            yf = sc.nextInt();



            // READ MATRIX

            for (i = 0; i < xf; i++)
            {
                for (j = 0; j < yf; j++)
                {
                    bigBox[i][j] = sc.nextInt();
                    System.out.print(bigBox[i][j] + " ");
                }

                System.out.println();
            }


        } catch (IOException e) {

            // do something
            e.printStackTrace();
        }

        System.out.println(xf + " "  + yf);

        new GUI(xf, yf, bigBox);//makes new ButtonGrid with 2 parameters
    }
}












