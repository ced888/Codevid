package com.example.code_vid_;

import android.graphics.Canvas;
import java.util.ArrayList;


public class ObstacleManager {
    //the higher the index, the further down on the screen the obstacle is
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;// gives player room to move around
    private int obstacleHeight;
    private int color;

    private long startTime;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color){
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();// populates first set of obstacles

    }

    private void populateObstacles(){
        int currY = -5 * Constants.SCREEN_HEIGHT/4;
        while(currY <  0){
            //while it hasn't gone onto the screen yet, we keep generating obstacles (currently rectangles)
            //we want there to be a random gap for the player to go through
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH - playerGap)); //subtract player gap so the gap is always on screen
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update(){
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        //how far is the obstacles going to move per second?
        float speed = Constants.SCREEN_HEIGHT/10000.0f; //im milliseconds. increase speed to make is more challenging
        for (Obstacle ob: obstacles){
            ob.incrementY(speed * elapsedTime);
        }
        if(obstacles.get(obstacles.size() -1).getRectangle().top >= Constants.SCREEN_HEIGHT){
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            int yStart = obstacles.get(0).getRectangle().top + obstacleHeight - obstacleGap; //leave gap between obstacles when we generate a new one + adding it to top because we want lowest index to be the highest on screem
            obstacles.add(0, new Obstacle(obstacleHeight,color,xStart, yStart, playerGap));
            obstacles.remove(obstacles.size()-1);
        }

    }

    public void draw(Canvas canvas){
        for (Obstacle ob : obstacles){
            ob.draw(canvas);
        }

    }
}
