package com.snake2.graphics;

import com.snake2.entities.BodyPart;
import com.snake2.entities.Fruit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Screen extends JPanel
{
    private static final long  serialVersionUID = 1L;

    public static final int WIDTH=800, HEIGHT=800;
    private FruitGenerator fruitGeneratorThread;
    private Snake snakeThread;
    private boolean fruitGeneratorRunning = false;
    private boolean snakeRunning = false;

    private BodyPart b;
    private ArrayList<BodyPart> snake;

    private Fruit fruit;
    private ArrayList<Fruit> fruits;

    private Random r;

    private int x=10, y=10;
    private int size =5;

    private boolean right=true, left=false, up=false,down=false;

    private int ticks =0;

    private Key key;

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

    public void start()
    {
        fruitGeneratorRunning = true;
        fruitGeneratorThread = new FruitGenerator();
        fruitGeneratorThread.start();
        snakeRunning = true;
        snakeThread = new Snake();
        snakeThread.start();
    }

    private class Key implements KeyListener
    {
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

        @Override
        public void keyTyped(KeyEvent e)
        {

        }

        @Override
        public void keyReleased(KeyEvent e)
        {

        }
    }

    private class Snake extends Thread implements Runnable
    {
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

        public void run()
        {
            while (snakeRunning)
            {
                tick();
                repaint();
            }
        }


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

    private class FruitGenerator extends Thread implements Runnable
    {
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

        public void run()
        {
            while (fruitGeneratorRunning)
            {
                tick();
                repaint();
            }
        }

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