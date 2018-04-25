package com.packing.models;

public class Rectangle {

    int index;
    int width; //first length
    int height; //second length
    long area;
    boolean isPlaced = false;

    //coordinates
    int x = 0;
    int y = 0;


     public Rectangle(int index, int width, int height){
         this.width = width;
         this.height = height;
         this.index = index;
         area = (long) width * (long) height;
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

    public long getArea() {
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