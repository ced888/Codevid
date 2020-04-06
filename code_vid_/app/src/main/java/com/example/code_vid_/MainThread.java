package com.example.code_vid_;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * this class contains the loop for running the game
 */
public class MainThread extends Thread {

    public static final int MAX_FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder; //surface holder
    private GamePanel gamePanel; //the panel to draw on
    private boolean running; //whether or not thread is running
    public static Canvas canvas; //what we draw with



    /**
     * constructor
     * @param surfaceHolder the game surface holder
     * @param gamePanel the game gamePanel
     */
    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    /**
     * @param running true if game is running, false if not
     */
    public void setRunning(boolean running)
    {

        this.running = running;
    }

    /**
     * defines integers to monitor thread specs
     * contains the main game loop. while(running) runs the game
     */
    @Override
    public void run()
    {
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        //while loop that runs the game.
        while(running)
        {
            startTime = System.nanoTime();
            canvas = null;

            try
            {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }
            catch (Exception e) {e.printStackTrace();}
            finally
            {
                if(canvas != null)
                {
                    try
                    {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e) {e.printStackTrace();}
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try
            {
                if(waitTime > 0)
                {
                    this.sleep(waitTime);
                }
            }
            catch (Exception e) {e.printStackTrace();}
            totalTime += System.nanoTime() - startTime;

            frameCount++;
            if(frameCount == MAX_FPS)
            {
                averageFPS = 1000/((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }

    }


}
