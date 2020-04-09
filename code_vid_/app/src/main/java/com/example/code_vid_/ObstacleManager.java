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
        this.playerGap = playerGap; //width of the gap for the player to fit through
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = System.currentTimeMillis();

        obstacles = new ArrayList<>(); //instantiate obstacles

        populateObstacles();// populates first set of obstacles

    }

    /**
     * we want obstacles to come onto the screen
     * we always want to be populating the screen and going off screen
     */
    private void populateObstacles(){
        /**int currY = -5 * Constants.SCREEN_HEIGHT/4;
        while(currY <  0){
            //while it hasn't gone onto the screen yet, we keep generating obstacles (currently rectangles)
            //we want there to be a random gap for the player to go through
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH - playerGap)); //subtract player gap so the gap is always on screen
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }**/

        int currX = (Constants.SCREEN_WIDTH + 5); //we start off screen to the right
        while(currX >  Constants.SCREEN_WIDTH/5){
            //while it hasn't gone onto the screen yet, we keep generating obstacles (currently rectangles)
            //we want there to be a random gap for the player to go through
            int left = Constants.SCREEN_WIDTH + 5;
            int top = (int)(Math.random()*(Constants.SCREEN_HEIGHT + playerGap));
            obstacles.add(new Obstacle(left, top, color, playerGap));
            currX += obstacleHeight - obstacleGap;
        }

    }

    public void update(){
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        //how far is the obstacles going to move per second?
        float speed = Constants.SCREEN_HEIGHT/10000.0f; //im milliseconds. increase speed to make is more challenging
        for (Obstacle ob: obstacles){
            ob.decrementX(speed * elapsedTime);
        }

        if(obstacles.get(obstacles.size() -1).getRectangle().right <= 0){
            int left = Constants.SCREEN_WIDTH + 5;
            int top = (int)(Math.random()*(Constants.SCREEN_HEIGHT));
            obstacles.add(0, new Obstacle(left,top,color,playerGap));
            obstacles.remove(obstacles.size()-1);
        }

    }

    public void draw(Canvas canvas){
        for (Obstacle ob : obstacles){
            ob.draw(canvas);
        }

    }
}
