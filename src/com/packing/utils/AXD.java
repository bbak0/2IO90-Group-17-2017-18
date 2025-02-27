package com.packing.utils;

import com.packing.InputReader;
import com.packing.algo.*;
import com.packing.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AXD extends JFrame{
    JButton button;
    static JTextArea tf;
    String[] algoChoices = {"BinaryTree", "Simulated Annealing", "RandomSky", "SmallCase"};
    JComboBox<String> algopicker = new JComboBox<>(algoChoices);

    public void openNewCanvas(Solution sol, int mul){
        JFrame frame = new JFrame();
        JPanel panel = new RectangleCanvas(sol, mul);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel contentPane = new JPanel();
        //contentPane.setPreferredSize(new Dimension(500, 400));
        contentPane.add(scrollPane);
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);;
        frame.setVisible(true);
    }

    public void run(){
        JFrame frame = new JFrame("AXD");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        JTextField mul = new JTextField(2);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);
        tf = new JTextArea(5,20);
        //tf.setPreferredSize(new Dimension(640, 350));
        JScrollPane scrollPane = new JScrollPane( tf );
        panel.add(scrollPane);
        panel.add(algopicker);
        mul.setPreferredSize(new Dimension(100,100));
        panel.add(mul);
        JButton button = new JButton("Solve");
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                InputReader inputReader = new InputReader(tf.getText());
                Data input = inputReader.read();
                //System.out.println(input);
                AbstractAlgorithm solver;
                //solver = new SkylineStripFaster(input);
                switch ((String)algopicker.getSelectedItem()){
                    case ("Simulated Annealing"):
                        solver = new SimulatedAnnealing(input);
                        break;
                    case ("RandomSky"):
                        solver = new SkySolution(input);
                        break;
                    case("BinaryTree"):
                            solver = new BTRun(input);
                            break;
                    case ("SmallCase"):
                        solver = new SmallCase(input);
                        break;
                    default:
                        solver = new NFDHAlgo(input);

                }
                long startTime = System.nanoTime();
                Solution sol = solver.solve();
                long endTime   = System.nanoTime();
                long totalTime = endTime - startTime;
                System.out.println(totalTime);
                int m = Integer.valueOf(mul.getText());
                if (m == 0){
                    openNewCanvas(sol, 5);
                } else {
                    openNewCanvas(sol, m);
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
        });
        panel.add(button, BorderLayout.SOUTH);
        frame.setPreferredSize(new Dimension(640, 480));
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String... args) {
        new AXD().run();
    }

}
