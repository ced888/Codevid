package com.example.code_vid_;

import android.graphics.Point;

//NOT USED IN MAIN CODE
//keep this class, I want to implement it later - cedric

public class MovePlayer extends Thread {

    private RectPlayer player;
    private boolean MoveUp;

    public boolean running;

    public  MovePlayer(RectPlayer player, boolean MoveUp)
    {
        this.player = player;
        this.MoveUp = MoveUp;
        this.running = true;
    }

    public void run()
    {
        while(running)
        {
            Point point = new Point(player.getRectangle().centerX(), player.getRectangle().centerY());

            while(MoveUp)
            {
                point.y += 10;

                if(point.y >= Constants.SCREEN_HEIGHT - 200) {
                    MoveUp = false;
                }
                player.update(point);
            }

            point.y -= 10;

            if(point.y <= 100) {
                point.y = 100;
            }
            player.update(point);

        }
    }

}
