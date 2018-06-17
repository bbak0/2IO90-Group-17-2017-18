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

    int [][] bigBox = new int[10002][10002];
    int []xAxis = new int[10002];
    int []yAxis = new int[10002];

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





        for(i=0; i < numberOfRectangles; i++)
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
        for(i=0; i < numberOfRectangles; i++)
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


            for(int j=x; j< xAxis[i] + x; j++){
                for(int k=y; k< yAxis[i] + y; k++){
                    bigBox[j][k] = counter;
                    if(j+1 > xMax){
                        xMax = j+1;
                    }
                    if(k+1 > yMax){
                        yMax = k+1;
                    }

                }
            }

        }
    }

    public void run(){

        readGeneral();
       // new GUI(xMax, yMax, bigBox);
        System.out.println(xMax + " " + yMax);
        System.out.println((xMax) * (yMax));
        double used = 0;

        for(i=0; i<xMax;i++)
            for(j=0;j<yMax;j++)
                if(bigBox[i][j] > 0)
                    used++;
        double wasted = xMax * yMax - used;
        double total = wasted + used; double p = total/100; wasted = wasted/p; used = used/p;
        System.out.println("used%: " + used);
        System.out.println("wasted% "+ wasted);

    }


    public static void main(String[] args) {

        ( new Checker() ).run();


    }
}


