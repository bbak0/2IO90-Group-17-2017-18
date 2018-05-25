/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packing;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Checker {

    Scanner sc = new Scanner(System.in);

    int [][] bigBox = new int[10000][10000];
    int []xAxis = new int[10000];
    int []yAxis = new int[10000];

    int xMax, yMax, i, j, x, y, k;
    int counter = 0;

    public boolean fixedHeight, rotationsAllowed;
    public int numberOfRectangles, fixedHeightValue, swap;
    String bool;


    public void readGeneral(){




        String cHeight = sc.nextLine();
        String cRotations = sc.nextLine();
        String cRectangles = sc.nextLine();



        if (cHeight.contains("free")){
            fixedHeight = false;
        } else {
            fixedHeight = true;
            int i = cHeight.indexOf(" fixed");
            int h = Integer.valueOf(cHeight.substring(i + 7));
            fixedHeightValue = h;
        }

        rotationsAllowed = cRotations.contains("yes");
        int i = cRectangles.indexOf("rectangles:");
        numberOfRectangles = Integer.valueOf(cRectangles.substring(i + "rectangles: ".length()));





        for(i=1; i <= numberOfRectangles; i++)
        {

            j = sc.nextInt();
            k = sc.nextInt();
            xAxis[i] = j;
            yAxis[i] = k;
        }

        cHeight = sc.nextLine();
        cHeight = sc.next();
        cHeight = sc.next();
        cHeight = sc.next();


        doCalculations(numberOfRectangles);
    }
    public void doCalculations(int numberOfRectangles)
    {
        for(i=1; i <= numberOfRectangles; i++)
        {
            counter++;
            if(rotationsAllowed)
            {

                bool = sc.next();

                if(bool.contains("yes"))
                {
                    y = sc.nextInt();   // READ NUMBERS IN SWAPPED ORDER
                    x = sc.nextInt();


                }
                else
                {

                    x = sc.nextInt();
                    y = sc.nextInt();
                }
            }

            else

            {

                x = sc.nextInt();
                y = sc.nextInt();
            }


            for(int j=x+1; j<=xAxis[i] + x; j++){
                for(int k=y+1; k<=yAxis[i] + y; k++){
                    bigBox[j][k] = counter;
                    if(j > xMax){
                        xMax = j;
                    }
                    if(k > yMax){
                        yMax = k;
                    }

                }
            }
            //System.out.println(xAxis[i] + " " + yAxis[i]);
           // System.out.println(x + " " + y);

        }
    }

    public void run(){

        readGeneral();
        System.out.println((xMax) * (yMax));
        new GUI(xMax, yMax, bigBox);
    }


    public static void main(String[] args) {

        ( new Checker() ).run();


    }
}