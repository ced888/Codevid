package com.example.code_vid_;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;


/**
 * this class...
 * SURFACE HOLDER: "Abstract interface to someone holding a display surface.
 * Allows you to control the surface size and format, edit the pixels in the surface,
 * and monitor changes to the surface"
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    /**
     * constructor
     * @param  context current context
     */
    public  GamePanel(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        thread = new MainThread(getHolder(), this); //instantiate new thread
        player = new RectPlayer(new Rect(100,100,200,200), Color.RED); //our main player
        playerPoint = new Point(200, 200); //starting point
        obstacleManager = new ObstacleManager(300, 300, 75,Color.BLUE); //obstacles

    }

    /**
     * TODO
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    /**
     * starting the thread
     * @param holder surface holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true); //loops
        thread.start(); //starts

        //playerThread = new MovePlayer(player, false);
        //new Thread(playerThread).start();
    }


    /**
     * stop the current thread
     * @param holder surface holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;

        while (retry)
        {
            try {
                thread.setRunning(false); //stops the game loop
                thread.join(); //finish running thread and then terminates
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            retry = false; //takes a few tries to destroy thread. loops until successful destroy
        }
    }

    /**
     * instructions for touch event
     * @param the touch event
     */
    boolean MoveUp = false;
    @Override
    public boolean onTouchEvent (MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_BUTTON_PRESS:
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                MoveUp = true;
                break;
            case MotionEvent.ACTION_UP:
                MoveUp = false;
                break;
        }

        //detect all touch
        return true;
    }

    /**
     * redraws the main player upon touch event
     */
    public void update()
    {
        if(!MoveUp)
        {
            Point currentPoint = new Point(playerPoint.x, playerPoint.y += 10);
            if(currentPoint.y >= Constants.SCREEN_HEIGHT - 200)
            {
                currentPoint.y = Constants.SCREEN_HEIGHT - 200;
            }
            player.update(currentPoint);
        }
        else{
            Point currentPoint = new Point(playerPoint.x, playerPoint.y -= 10);
            if(currentPoint.y <= 200)
            {
                MoveUp = false;
            }
            player.update(currentPoint);
            obstacleManager.update();
        }


    }

    /**
     * @param  canvas the canvas to draw on
     */
    @Override
    public void draw (Canvas canvas)
    {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);   //canvas will be white

        player.draw(canvas);
        obstacleManager.draw(canvas);
    }
}
