package com.packing;

import java.util.Random;


public class TextCaseGenerator {

    //log("Generating 10 random integers in range 0..99.");

    //note a single Random object is reused here


    public static void main(String[] args) {
        boolean cHeight, rotation;
        String ch;

        Random randomGenerator = new Random();


        for (int idx = 1; idx <= 10; ++idx) {
            int randomInt = randomGenerator.nextInt(100);
        }

        int random = randomGenerator.nextInt(2);
        int randomRot = randomGenerator.nextInt(2);
        int height = randomGenerator.nextInt(10000);
        int n = randomGenerator.nextInt(100);
        if (random % 2 == 0)
            cHeight = true;
        else
            cHeight = false;

        if (randomRot % 2 == 0)
            rotation = true;
        else
            rotation = false;

            n=5000;

        //if (cHeight == true)
            ch = " free";
      //  else
       //     ch = " fixed " + height;

        System.out.println("container height:" + ch);

        if(rotation == true)
            ch = "rotations allowed: yes";
        else
            ch = "rotations allowed: no";
        System.out.println(ch);
        System.out.println("number of rectangles: " + n);


        for(int i=1; i<=n; i++)
        {
            int random1 = randomGenerator.nextInt(100);
            int random2 = randomGenerator.nextInt(100);

            System.out.println(random1 + " " + random2);

        }





    }
}