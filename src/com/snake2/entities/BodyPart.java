package com.snake2.entities;

import java.awt.*;

/**
 * Class that represents snake body part
 */
public class BodyPart
{
    /** Store x coordinate of body part*/
    private int x;
    /** Store y coordinate of body part*/
    private int y;
    /** Store width of body part*/
    private int width;
    /** Store width of body part*/
    private int height;

    /**
     * Body part constructor
     * @param x x coordinate
     * @param y y coordinate
     * @param tileSize size of screen tile (describe width and height of body part)
     */
    public BodyPart(int x, int y, int tileSize)
    {
        this.x=x;
        this.y=y;
        this.width=tileSize;
        this.height=tileSize;
    }

    /**
     * Metod that describe how to paint body part
     * @param g graphics object
     */
    public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(x*width,y*height,width,height);
        g.setColor(Color.GREEN);
        g.fillRect(x*width+2,y*height+2,width-4,height-4);
    }

    /**
     * Get x coordinate of body part
     * @return x coordinate
     */
    public int getX()
    {
        return x;
    }

    /**
     * Set x coordinate of body part
     * @param x x coordinate
     */
    public void setX(int x)
    {
        this.x = x;
    }

    /**
     * Get y coordinate of body part
     * @return y coordinate
     */
    public int getY()
    {
        return y;
    }

    /**
     * Set y coordinate of body part
     * @param y y coordinate
     */
    public void setY(int y)
    {
        this.y=y;
    }
}
