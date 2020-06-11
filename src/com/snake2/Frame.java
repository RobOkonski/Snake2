package com.snake2;

import com.snake2.graphics.Screen;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Frame class extend swing JFrame
 * Set main window of the program
 */
public class Frame extends JFrame
{
    /** Store menu object */
    private Menu m = new Menu(this);
    private ArrayList<String> scores;
    private String nick;

    /**
     * Constructor of frame
     * Initialise window options, exit case and window title
     */
    public Frame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //turn off a program when you shout down the window
        setTitle("Snake");
        setResizable(false);
        ReadScores();
        m.SetScores(scores);
        init();
    }

    /**
     * Reed high scores from a txt file
     */
    public void ReadScores()
    {
        scores = new ArrayList<String>();
        File file = new File("./Scores.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                scores.add(line);
            }
        }
        catch(Exception e)
        {
        }
    }

    /**
     * Initialize window frame with menu screen
     */
    public void init()
    {
        setLayout(new GridLayout(1,1,0,0));

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
        nick = m.GetNick();
        getContentPane().removeAll();
        setLayout(new GridLayout(1,1,0,0));
        Screen s = new Screen(nick);
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