package com.packing.models;
import java.util.ArrayList;
import java.util.Iterator;

public class BTP {

    private final ArrayList<Rectangle> root = new ArrayList();

    public BTP(int w, int h) {
        this.root.add(new Rectangle(0, 0, w, h));

    }


    public void fit(ArrayList<Rectangle> blocks) {
        Rectangle node;
        Rectangle block;
        Iterator<Rectangle> blockItr = blocks.iterator();
        int n=0;
        while (blockItr.hasNext()) {
            block = blockItr.next();
            if ((node = this.findNode(this.root.get(n), block.width, block.height))!=null) {
                block.fit = this.splitNode(node, block.width, block.height);
                if(node.isroot){
                    block.fit.isroot = true;
                }
            }else{
                n++;
            }
        }
    }

    public Rectangle findNode(Rectangle root, int w, int h) {
        if (root.used
                ) {
            Rectangle right = findNode(root.right, w, h);
            return (right != null ? right : findNode(root.down, w, h));
        } else if ((w <= root.width) && (h <= root.height)) {
            return root;
        } else {
            return null;
        }
    }

    public Rectangle splitNode(Rectangle node, int w, int h) {
        node.used = true;
        node.down = new Rectangle(node.x, node.y + h, node.width, node.height - h);
        node.right = new Rectangle(node.x + w, node.y, node.width - w, h);
        return node;
    }


}

