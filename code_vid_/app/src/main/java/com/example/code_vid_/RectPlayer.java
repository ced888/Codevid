package com.example.code_vid_;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * this class represents our main player
 */
public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;

    /**
     * constructor
     * @param rectangle the rectangle to draw
     * @param color his color
     */
    public  RectPlayer(Rect rectangle, int color)
    {
        this.rectangle = rectangle;
        this.color = color;
    }

    /**
     * draws the main player
     * @param canvas the canvas to draw on
     */
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(this.rectangle, paint);
    }

    /**
     * TODO
     */
    @Override
    public void update() {

    }

    /**
     * sets our main player on the screen
     * @param point center point of the rectangle on the screen
     */
    public void update(Point point)
    {
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);
    }

    /**
     * @return main player
     */
    public Rect getRectangle()
    {
        return rectangle;
    }

}
