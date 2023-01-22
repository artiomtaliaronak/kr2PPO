package com.artiomtaliaronak.ipr1ppo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Ball {
    Bitmap ball[] = new Bitmap[4];
    int ballFrame = 0;
    int ballX, ballY, ballVelocityX, ballVelocityY;
    Random random;

    public Ball(Context context){
        ball[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball0);
        ball[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball1);
        ball[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball2);
        ball[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball3);
        random = new Random();
        setPosition();
    }

    public Bitmap getBall(int ballFrame){
        return ball[ballFrame];
    }

    public int getBallWidth(){
        return ball[0].getWidth();
    }

    public int getBallHeight(){
        return ball[0].getHeight();
    }


    public void setPosition(){
        ballX = random.nextInt(GameView.dWidth - getBallWidth());
        ballY = 400;
        ballVelocityX = 20+random.nextInt(20);
        ballVelocityY = 30;
    }

}
