package com.snake2.graphics;

import com.snake2.entities.BodyPart;
import com.snake2.entities.Fruit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class that represents screen actions extends JPanel
 */
public class Screen extends JPanel {

    /**
     * Store players nick
     */
    private String nick;

    /**
     * Serialization variable
     */
    private static final long serialVersionUID = 1L;

    /**
     * Store width of the grid
     */
    public static final int WIDTH = 800;

    /**
     * Store height of the grid
     */
    public static final int HEIGHT = 800;

    /**
     * Fruit generator thread
     */
    private FruitGenerator fruitGeneratorThread;

    /**
     * Snake thread
     */
    private Snake snakeThread;

    /**
     * SnakeAI thread
     */
    private SnakeAI snakeAIThread;

    /**
     * Store if fruit generator thread is running
     */
    private boolean fruitGeneratorRunning = false;

    /**
     * Store if snake thread is running
     */
    private boolean snakeRunning = false;

    /**
     * Store if snakeAI thread is running
     */
    private boolean snakeAIRunning = false;

    /**
     * Store body part before list addition
     */
    private BodyPart b;

    /**
     * Store snake body parts
     */
    private ArrayList<BodyPart> snake;

    /**
     * Store snakeAI body parts
     */
    private ArrayList<BodyPart> snakeAI;

    /**
     * Store fruit before list addition
     */
    private Fruit fruit;

    /**
     * Store fruits
     */
    private ArrayList<Fruit> fruits;

    /**
     * Random class object (used to generate fruits)
     */
    private Random r;

    /**
     * Store speed of snakes
     */
    private int SnakeSpeed = 75;

    /**
     * Store if the game should end
     */
    private boolean EndGame = false;

    /**
     * Start x coordinate of snake
     */
    private int x = 10;

    /**
     * Start x coordinate of snakeAI
     */
    private int xAI = 50;

    /**
     * Start y coordinate of snake
     */
    private int y = 10;

    /**
     * Start y coordinate of snakeAI
     */
    private int yAI = 50;

    /**
     * Start size of snake
     */
    private int size = 3;

    /**
     * Start size of snakeAI
     */
    private int sizeAI = size;

    /**
     * Store if right key was pressed (true on start)
     */
    private boolean right = true;

    /**
     * Store if right key was pressed (AI) (false on start)
     */
    private boolean rightAI = false;

    /**
     * Store if left key was pressed (false on start)
     */
    private boolean left = false;

    /**
     * Store if left key was pressed (AI) (true on start)
     */
    private boolean leftAI = true;

    /**
     * Store if up key was pressed (false on start)
     */
    private boolean up = false;

    /**
     * Store if up key was pressed (AI) (false on start)
     */
    private boolean upAI = false;

    /**
     * Store if down key was pressed (false on start)
     */
    private boolean down = false;

    /**
     * Store if down key was pressed (AI) (false on start)
     */
    private boolean downAI = false;

    /**
     * Key object (used to define keyboard keys)
     */
    private Key key;

    /**
     * Screen class constructor
     * Initialise screen view
     * @param nick_p players nick
     */
    public Screen(String nick_p) {
        nick = nick_p;
        setFocusable(true);
        key = new Key();
        addKeyListener(key);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        r = new Random();

        snake = new ArrayList<BodyPart>();
        snakeAI = new ArrayList<BodyPart>();
        fruits = new ArrayList<Fruit>();

        start();
    }

    /**
     * Add new score to file
     */
    public void WriteScore()
    {
        File file = new File("./Scores.txt");
        try {
            FileWriter fr = new FileWriter(file,true);
            fr.write("\n" + nick + " " + size);
            fr.close();
        }
        catch(Exception e){}

    }

    /**
     * Paint grid, snakes body parts and fruits
     *
     * @param g graphics object
     */
    public void paint(Graphics g) {
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);

        for (int i = 0; i < WIDTH / 10; i++) {
            g.drawLine(i * 10, 0, i * 10, HEIGHT);
        }

        for (int i = 0; i < HEIGHT / 10; i++) {
            g.drawLine(0, i * 10, WIDTH, i * 10);
        }

        for (int i = 0; i < snake.size(); i++) {
            snake.get(i).draw(g);
        }

        for (int i = 0; i < snakeAI.size(); i++) {
            snakeAI.get(i).draw(g);
        }

        for (int i = 0; i < fruits.size(); i++) {
            fruits.get(i).draw(g);
        }
    }

    /**
     * Start threads in single play
     */
    public void start() {
        fruitGeneratorRunning = true;
        fruitGeneratorThread = new FruitGenerator();
        fruitGeneratorThread.start();
        snakeRunning = true;
        snakeAIRunning = true;
        snakeThread = new Snake();
        snakeThread.start();
        snakeAIThread = new SnakeAI();
        snakeAIThread.start();
    }

    /**
     * Private class that implements key listener to take info from keyboard
     */
    private class Key implements KeyListener {
        /**
         * Check if key was pressed and define action in game
         *
         * @param e key event
         */
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_RIGHT && !left) {
                up = false;
                down = false;
                right = true;
            }

            else if (key == KeyEvent.VK_LEFT && !right) {
                up = false;
                down = false;
                left = true;
            }

            else if (key == KeyEvent.VK_UP && !down) {
                right = false;
                left = false;
                up = true;
            }

            else if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                left = false;
                down = true;
            }
        }

        /**
         * Overide interface keyTyped method
         *
         * @param e key event
         */
        @Override
        public void keyTyped(KeyEvent e) {

        }

        /**
         * Overide interface keyReleased method
         *
         * @param e key event
         */
        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    /**
     * Private class that create Snake thread
     * Extends thread class
     * Implements Runnable interface
     */
    private class Snake extends Thread implements Runnable {

        /**
         * Represents snake action and logic
         * Check collisions and set moves
         */
        public void tick() {
            if(EndGame)
            {
                stopSnake();
            }

            if (snake.size() == 0) {
                b = new BodyPart(x, y, 10);
                snake.add(b);
            }

            for (int i = 0; i < snake.size(); i++) {
                if (x == snake.get(i).getX() && y == snake.get(i).getY()) {
                    if (i != snake.size() - 1) {
                        EndGame = true;
                        stopSnake();
                    }
                }
            }

            if (x < 0 || x > 79 || y < 0 || y > 79) {
                EndGame = true;
                stopSnake();
            }

            if (right) x++;
            if (left) x--;
            if (up) y--;
            if (down) y++;


            b = new BodyPart(x, y, 10);
            snake.add(b);

            if (snake.size() > size) {
                snake.remove(0);
            }
        }

        /**
         * Start thread run
         */
        public void run() {
            while (snakeRunning) {
                tick();
                try {
                    Thread.sleep(SnakeSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }

        /**
         * End thread after collision
         */
        public void stopSnake() {
            WriteScore();
            try {
                snakeThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Private class that create SnakeAI thread
     * Extends thread class
     * Implements Runnable interface
     */
    private class SnakeAI extends Thread implements Runnable {
        /**
         * Represents snake action and logic
         * Check collisions and set moves
         * Provides simple AI
         */
        public void tick() {
            if(!fruits.isEmpty())
            {
                int xDistanceFruit = fruits.get(0).getX() - xAI;
                int yDistanceFruit = fruits.get(0).getY() - yAI;

                if(xDistanceFruit<0 && rightAI==false) {
                    leftAI = true;
                    upAI= false;
                    downAI = false;
                }
                else if(xDistanceFruit>0 && leftAI==false)
                {
                    rightAI = true;
                    upAI= false;
                    downAI = false;
                }
                else if(yDistanceFruit>0 && upAI==false)
                {
                    rightAI = false;
                    leftAI = false;
                    downAI = true;
                }
                else if(yDistanceFruit<0 && downAI==false)
                {
                    rightAI = false;
                    leftAI = false;
                    upAI = true;
                }
            }

            if(EndGame)
            {
                stopSnakeAI();
            }

            if (snakeAI.size() == 0) {
                b = new BodyPart(xAI, yAI, 10);
                snakeAI.add(b);
            }

            for (int i = 0; i < snakeAI.size(); i++) {
                if (xAI == snakeAI.get(i).getX() && yAI == snakeAI.get(i).getY()) {
                    if (i != snakeAI.size() - 1) {
                        EndGame = true;
                        stopSnakeAI();
                    }
                }
            }

            if (xAI < 0 || xAI > 79 || yAI < 0 || yAI > 79) {
                EndGame = true;
                stopSnakeAI();
            }




            if (rightAI) xAI++;
            if (leftAI) xAI--;
            if (upAI) yAI--;
            if (downAI) yAI++;

            b = new BodyPart(xAI, yAI, 10);
            snakeAI.add(b);

            if (snakeAI.size() > sizeAI) {
                snakeAI.remove(0);
            }
        }

        /**
         * Start thread run
         */
        public void run() {
            while (snakeAIRunning) {
                tick();
                try {
                    Thread.sleep(SnakeSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }

        /**
         * End thread after collision
         */
        public void stopSnakeAI() {
            try {
                snakeAIThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Private class that create Fruit generator
     * Extends thread class
     * Implements runnable interface
     */
    private class FruitGenerator extends Thread implements Runnable {
        /**
         * Represents fruit generator actions and logic
         * Set and remove fruits after snakes consumption
         */
        public void tick() {
            if (fruits.size() < 10) {
                int x = r.nextInt(79);
                int y = r.nextInt(79);

                fruit = new Fruit(x, y, 10);
                fruits.add(fruit);
            }

            for (int i = 0; i < fruits.size(); i++) {
                if (x == fruits.get(i).getX() && y == fruits.get(i).getY()) {
                    size++;
                    fruits.remove(i);
                    i--;
                }
                else if (xAI == fruits.get(i).getX() && yAI == fruits.get(i).getY())
                {
                    sizeAI++;
                    fruits.remove(i);
                    i--;
                }
            }
        }

        /**
         * Start fruit generator thread run
         */
        public void run() {
            while (fruitGeneratorRunning) {
                tick();
                try {
                    Thread.sleep((SnakeSpeed/2));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }

//        /**
//         * End fruit generator thread after collision
//         */
//        public void stopFruit()
//        {
//            fruitGeneratorRunning = false;
//            try
//            {
//                fruitGeneratorThread.join();
//            }
//            catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//        }
    }
}