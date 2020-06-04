package com.snake2.graphics;

import com.snake2.entities.BodyPart;
import com.snake2.entities.Fruit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class that represents screen actions extends JPanel
 */
public class Screen extends JPanel
{

    /** Serialization variable */
    private static final long  serialVersionUID = 1L;

    /** Store width of the grid */
    public static final int WIDTH=800;
    /** Store height of the grid */
    public static final int HEIGHT=800;
    /** Fruit generator thread */
    private FruitGenerator fruitGeneratorThread;
    /** Snake thread */
    private Snake snakeThread;
    /** Store if fruit generator thread is running */
    private boolean fruitGeneratorRunning = false;
    /** Store if snake thread is running */
    private boolean snakeRunning = false;

    /** Store body part before list addition */
    private BodyPart b;
    /** Store snake body parts */
    private ArrayList<BodyPart> snake;

    /** Store fruit before list addition */
    private Fruit fruit;
    /** Store fruits */
    private ArrayList<Fruit> fruits;

    /** Random class object (used to generate fruits)*/
    private Random r;

    /** Start x coordinate of snake */
    private int x=10;
    /** Start y coordinate of snake */
    private int y=10;
    /** Start size of snake */
    private int size =5;

    /** Store if right key was pressed (true on start) */
    private boolean right=true;
    /** Store if left key was pressed (false on start) */
    private boolean left=false;
    /** Store if up key was pressed (false on start) */
    private boolean up=false;
    /** Store if down key was pressed (false on start) */
    private boolean down=false;

    /** Clock ticks counter */
    private int ticks =0;

    /** Key object (used to define keyboard keys) */
    private Key key;

    /**
     * Screen class constructor
     * Initialise screen view
     */
    public Screen()
    {
        setFocusable(true);
        key = new Key();
        addKeyListener(key);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        r= new Random();

        snake = new ArrayList<BodyPart>();
        fruits = new ArrayList<Fruit>();

        start();
    }


    /**
     * Paint grid, snakes body parts and fruits
     * @param g graphics object
     */
    public void paint(Graphics g)
    {
        g.clearRect(0,0,WIDTH,HEIGHT);

        g.setColor(Color.BLACK);

        for(int i=0;i<WIDTH/10;i++)
        {
            g.drawLine(i*10,0,i*10,HEIGHT);
        }

        for(int i=0;i<HEIGHT/10;i++)
        {
            g.drawLine(0,i*10,WIDTH,i*10);
        }

        for(int i=0; i<snake.size();i++)
        {
            snake.get(i).draw(g);
        }

        for(int i=0; i<fruits.size(); i++)
        {
            fruits.get(i).draw(g);
        }
    }

    /**
     * Start threads in single play
     */
    public void start()
    {
        fruitGeneratorRunning = true;
        fruitGeneratorThread = new FruitGenerator();
        fruitGeneratorThread.start();
        snakeRunning = true;
        snakeThread = new Snake();
        snakeThread.start();
    }

    /**
     * Private class that implements key listener to take info from keyboard
     */
    private class Key implements KeyListener
    {
        /**
         * Check if key was pressed and define action in game
         * @param e key event
         */
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_RIGHT && !left)
            {
                up = false;
                down = false;
                right = true;
            }

            if(key == KeyEvent.VK_LEFT && !right)
            {
                up = false;
                down = false;
                left = true;
            }

            if(key == KeyEvent.VK_UP && !down)
            {
                right = false;
                left = false;
                up = true;
            }

            if(key == KeyEvent.VK_DOWN && !up)
            {
                right = false;
                left = false;
                down = true;
            }
        }

        /**
         * Overide interface keyTyped method
         * @param e key event
         */
        @Override
        public void keyTyped(KeyEvent e)
        {

        }

        /**
         * Overide interface keyReleased method
         * @param e key event
         */
        @Override
        public void keyReleased(KeyEvent e)
        {

        }
    }

    /**
     * Private class that create Snake thread
     * Extends thread class
     * Implements Runnable interface
     */
    private class Snake extends Thread implements Runnable
    {
        /**
         * Represents snake action and logic
         * Check collisions and set moves
         */
        public void tick()
        {
            if(snake.size()==0)
            {
                b= new BodyPart(x,y,10);
                snake.add(b);
            }

            for(int i=0; i<snake.size(); i++)
            {
                if(x==snake.get(i).getX() && y==snake.get(i).getY())
                {
                    if(i != snake.size()-1)
                    {
                        stopSnake();
                    }
                }
            }

            if(x<0 || x>79 || y<0 || y>79)
            {
                stopSnake();
            }

            ticks++;

            if(ticks>250000)
            {
                if(right) x++;
                if(left) x--;
                if(up) y--;
                if(down) y++;

                ticks =0;

                b= new BodyPart(x,y,10);
                snake.add(b);

                if(snake.size()>size)
                {
                    snake.remove(0);
                }
            }
        }

        /**
         * Start thread run
         */
        public void run()
        {
            while (snakeRunning)
            {
                tick();
                repaint();
            }
        }

        /**
         * End thread after collision
         */
        public void stopSnake()
        {
            try
            {
                snakeThread.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Private class that create Fruit generator
     * Extends thread class
     * Implements runnable interface
     */
    private class FruitGenerator extends Thread implements Runnable
    {
        /**
         * Represents fruit generator actions and logic
         * Set and remove fruits after snakes consumption
         */
        public void tick()
        {
            if(fruits.size()==0)
            {
                int x = r.nextInt(79);
                int y = r.nextInt(79);

                fruit = new Fruit(x,y,10);
                fruits.add(fruit);
            }

            for(int i=0; i<fruits.size(); i++) {
                if (x == fruits.get(i).getX() && y == fruits.get(i).getY()) {
                    size++;
                    fruits.remove(i);
                    i--;
                }
            }
        }

        /**
         * Start fruit generator thread run
         */
        public void run()
        {
            while (fruitGeneratorRunning)
            {
                tick();
                repaint();
            }
        }

        /**
         * End fruit generator thread after collision
         */
        public void stopFruit()
        {
            fruitGeneratorRunning = false;
            try
            {
                fruitGeneratorThread.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}