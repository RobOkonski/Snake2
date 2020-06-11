package com.snake2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that represents Menu
 */
public class Menu {
    /** Store start button*/
    private JButton startButton;
    /** Store menu panel */
    public JPanel panel1;

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
}
