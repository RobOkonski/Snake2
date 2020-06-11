package com.snake2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class that represents Menu
 */
public class Menu {
    /** Store start button*/
    private JButton startButton;
    /** Store menu panel */
    public JPanel panel1;
    /** Store players nick */
    private JTextField nick;
    /** Display high scores */
    private JTextArea scores;

    /**
     * Constructor of menu
     * @param frame frame object to get start method from there
     */
    public Menu(Frame frame) {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.start();
            }
        });
    }

    /**
     * Set scores in JtextArea
     * @param data list of scores read from file
     */
    public void SetScores(ArrayList<String> data)
    {
        for(String a : data)
        {
            scores.append(a + "\n");
        }
    }

    /**
     * Get players nick from JTextField
     * @return players nick
     */
    public String GetNick()
    {
        return nick.getText();
    }
}
