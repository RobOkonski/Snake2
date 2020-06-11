package com.snake2;

import com.snake2.graphics.Screen;
import javax.swing.*;
import java.awt.*;

/**
 * Frame class extend swing JFrame
 * Set main window of the program
 */
public class Frame extends JFrame
{
    /**
     * Constructor of frame
     * Initialise window options, exit case and window title
     */
    public Frame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //turn off a program when you shout down the window
        setTitle("Snake");
        setResizable(false);
        init();
    }

    /**
     * Initialize window frame with menu screen
     */
    public void init()
    {
        setLayout(new GridLayout(1,1,0,0));
        Menu m = new Menu(this);
        setContentPane(m.panel1);

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Starts game after pressing start in menu
     */
    public void start()
    {
        getContentPane().removeAll();
        setLayout(new GridLayout(1,1,0,0));
        Screen s = new Screen();
        add(s);
        s.requestFocus(true);

        pack();

        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * Main method of a program. Consist of frame
     * @param args
     */
    public static void main(String[] args)
    {
        new Frame();
    }
}