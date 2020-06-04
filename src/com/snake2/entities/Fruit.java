package com.snake2.entities;

import java.awt.*;

/**
 * Class that represents fruit object
 */
public class Fruit
{
    /** Store x coordinate of fruit */
    private int x;
    /** Store y coordinate of fruit */
    private int y;
    /** Store width of fruit object */
    private int width;
    /** Store height of fruit object */
    private int height;

    /**
     * Fruit constructor
     * @param x x coordinate
     * @param y y coordinate
     * @param tileSize size of screen tile (describe width and height of fruit)
     */
    public Fruit(int x, int y, int tileSize)
    {
        this.x=x;
        this.y=y;
        this.width=tileSize;
        this.height=tileSize;
    }

    /**
     * Metod that describe how to paint fruit
     * @param g graphics object
     */
    public void draw(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillRect(x*width, y*height, width, height);
    }

    /**
     * Get x coordinate of fruit
     * @return x coordinate
     */
    public int getX()
    {
        return x;
    }

    /**
     * Set x coordinate of fruit
     * @param x x coordinate
     */
    public void setX(int x)
    {
        this.x = x;
    }

    /**
     * Get y coordinate of fruit
     * @return y coordinate
     */
    public int getY()
    {
        return y;
    }

    /**
     * Set y coordinate of fruit
     * @param y y coordinate
     */
    public void setY(int y)
    {
        this.y=y;
    }
}

