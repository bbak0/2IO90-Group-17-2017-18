package com.packing.utils;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AXDForm implements MouseListener{
    private JButton button1;
    private JTextField textField1;
    private JComboBox comboBox1;



    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == button1){
            String item = comboBox1.getSelectedItem().toString();
        }
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
