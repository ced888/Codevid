package com.example.code_vid_;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;



public class Obstacle implements GameObject {

    //the following two rectangles are the rectangles that are generated to have a gap in between them
    private Rect rectangle;
    private int color;


    /**
     *the specs of the obstacle to draw
     * currentley two rectangles entering the screen vertically
     * @param rectHeight ....rectangle height....
     * @param color
     * @param startX
     * @param startY
     * @param playerGap the gap left in between the obstacles and the player
     **/
    public Obstacle(int left, int top, int color,  int playerGap)
     {
         rectangle = new Rect(left, top, left + 50, top + 50); //left, top, right ,bottom
         this.color = color;
     }

    /**
     * TODO
     * @param player
     * @return
     */
    public boolean playerCollide(RectPlayer player)
    {
        if(rectangle.contains(player.getRectangle().left, player.getRectangle().top)
        ||rectangle.contains(player.getRectangle().right, player.getRectangle().top)
        ||rectangle.contains(player.getRectangle().left, player.getRectangle().bottom)
        ||rectangle.contains(player.getRectangle().right, player.getRectangle().bottom))
        {
            return true;
        }
        return false;
    }


    /**
     * draws objects on canvas
     * @param canvas the canvas to draw on
     */
    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        //canvas.drawRect(rectangle2, paint);
    }

    /**
     * TODO
     */
    @Override
    public void update()
    {

    }

    /**
     * @return rectangle
     */
    public Rect getRectangle() {
        return rectangle;
    }


    /**
     * @param y integer to change the coordinates of the obstacle by
     */
    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom += y;

    }

    public void decrementX(float x){
        rectangle.left -= x;
        rectangle.right -= x;

    }



}
