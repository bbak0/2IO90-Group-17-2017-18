package com.packing.models;

public class Rectangle {

    public int index;
    public int width; //first length
    public int height; //second length
    public int area;
    boolean isPlaced = false;

    //coordinates
    public int x = 0;
    public int y = 0;


     public Rectangle(int index, int width, int height){
         this.width = width;
         this.height = height;
         this.index = index;
         area = width * height;
     }

     public boolean placeRectangle(int x, int y){
         this.x = x;
         this.y = y;
         return false;
     }

    public int getIndex() {
        return index;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getArea() {
        return area;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString(){
         return x + " " + y;
    }
    
}