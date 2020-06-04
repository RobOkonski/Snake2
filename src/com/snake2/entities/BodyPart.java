package com.snake2.entities;

        import java.awt.*;

public class BodyPart
{
    private int x, y, width, height;

    public BodyPart(int x, int y, int tileSize)
    {
        this.x=x;
        this.y=y;
        this.width=tileSize;
        this.height=tileSize;
    }

    public void tick()
    {

    }

    public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(x*width,y*height,width,height);
        g.setColor(Color.GREEN);
        g.fillRect(x*width+2,y*height+2,width-4,height-4);
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y=y;
    }
}
