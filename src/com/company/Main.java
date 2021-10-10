package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel{

    public static void main(String[] args) {
        String input="a guy walked to a park and jazzed all over the place";

        JFrame frame=new JFrame("..>*<..");
        frame.setSize(625,450);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setFocusable(true);

        JLabel label=new JLabel();
        label.setBounds(5,275,300,30);
        label.setFont(new Font("Sans-serif",Font.PLAIN,28));
        frame.add(label);

        MyPanel myPanel=new MyPanel(input, frame);
        frame.setContentPane(myPanel);

        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar()>='a'&& e.getKeyChar()<='z') {
                    myPanel.update(myPanel.getGraphics());
                    myPanel.setCharTyped(e.getKeyChar());
                }
            }
            public void keyReleased(KeyEvent e) {

            }
        });
        frame.setVisible(true);
    }
}
