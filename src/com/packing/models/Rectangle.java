package com.packing.models;

public class Rectangle {

    public int index;
    public int width; //first length
    public int height; //second length
    public int area;
    public boolean isPlaced = false;
    public boolean isRotated = false;
    public boolean used = false;
    public Rectangle right = null;
    public Rectangle down = null;
    public Rectangle fit = null;
    public boolean isroot = false;
    public boolean highlight = false;
    public int ratioR;
    public int ratio;
    public int heuristicRatio;


    //coordinates
    public int x = 0;
    public int y = 0;

    public Rectangle(int x, int y, int width, int height)
    {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        //ratio = height / width;
        //ratioR = Math.max(height, width) / Math.min(height, width);
        //heuristicRatio = ratioR * area;
    }



     public Rectangle(int index, int width, int height){
         this.width = width;
         this.height = height;
         this.index = index;
         area = width * height;
         //ratio = height / width;
         //ratioR = Math.max(height, width) / Math.min(height, width);
         //heuristicRatio = ratioR * area;
     }

     public boolean placeRectangle(int x, int y){
         this.x = x;
         this.y = y;
         return false;
     }
     public void rotate(){
         int temp = this.height;
         height = width;
         width = temp;
         isRotated = !isRotated;
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

    public Rectangle copyOf(){
        Rectangle copy = new Rectangle(this.x, this.y, this.width, this.height);
        copy.index = this.index;
        copy.isRotated = this.isRotated;
        return copy;
    }

    @Override
    public String toString(){
         return x + " " + y;
    }

}