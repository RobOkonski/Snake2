package com.snake2;

import com.snake2.graphics.Screen;
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame
{

    public Frame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //turn off a program when you shout down the window
        setTitle("Snake");
        setResizable(false);

        init();
    }

    public void init()
    {
        setLayout(new GridLayout(1,1,0,0));

        Screen s = new Screen();
        add(s);

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new Frame();
    }
}