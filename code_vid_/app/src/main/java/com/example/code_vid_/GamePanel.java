package com.example.code_vid_;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.provider.SyncStateContract;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.graphics.Canvas;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private RectPlayer player;
    private Point playerPoint;

    public  GamePanel(Context context)
    {
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        player = new RectPlayer(new Rect(100,100,200,200), Color.RED);
        playerPoint = new Point(200, 200);
        setFocusable(true);


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();

        //playerThread = new MovePlayer(player, false);
        //new Thread(playerThread).start();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;

        while (true)
        {
            try {
                thread.setRunning(false);
                thread.join();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            retry = false;
        }
    }

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
        }


    }

    @Override
    public void draw (Canvas canvas)
    {
        super.draw(canvas);
        //canvas will be white
        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
    }
}
