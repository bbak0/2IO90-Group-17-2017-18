package com.packing.models;

public class Rectangle {

    int a; //first length
    int b; //second length
    int area;
    boolean isPlaced = false;

    //coordinates
    int x = 0;
    int y = 0;


     public Rectangle(int a, int b){
         this.a = a;
         this.b = b;
         area = a * b;
     }

     public boolean placeRectangle(int x, int y){return false;}


}