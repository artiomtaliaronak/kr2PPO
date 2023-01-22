package com.artiomtaliaronak.ipr1ppo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {

    Bitmap background, ground, platform, target;
    Rect rectBackground, rectGround;
    Context context;
    Handler handler;
    final long UPDATE_FREQ = 10;
    Runnable runnable;
    Paint scorePaint = new Paint();
    Paint healthPaint = new Paint();
    float TEXT_SIZE = 120;
    int points = 0;
    int life = 3;
    static int dWidth, dHeight;
    Random random;
    float platformX, platformY;
    float targetX, targetY;
    float oldPlatformX, oldX;
    Ball ball;
    Explosion explosion;
    boolean isExplosion = false;
    int cooldown = 5;
    int cooldownTarget = 10;
    int cooldownPlatform = 10;
    private Gyroscope gyroscope;

    public GameView(Context context) {
        super(context);
        this.context = context;
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        platform = BitmapFactory.decodeResource(getResources(), R.drawable.platform);
        target = BitmapFactory.decodeResource(getResources(), R.drawable.target);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rectBackground = new Rect(0,0, dWidth, dHeight);
        rectGround = new Rect(0, dHeight - ground.getHeight(), dWidth, dHeight);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        random = new Random();
        platformX = dWidth/2 - platform.getWidth()/2;
        platformY = dHeight - ground.getHeight() - platform.getHeight();
        targetX = dWidth/2 - target.getWidth()/2;
        targetY = 0;
        ball = new Ball(context);
        gyroscope = new Gyroscope(this.context);
        gyroscope.register();
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if (ry > 1.0f || ry < -1.0f){
                    float newPlatformX = dWidth/2 - platform.getWidth()/2 + (ry * 30);
                    if (newPlatformX <= 0)
                        platformX = 0;
                    else if (newPlatformX >= dWidth - platform.getWidth())
                        platformX = dWidth - platform.getWidth();
                    else
                        platformX = newPlatformX;
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rectBackground, null);
        canvas.drawBitmap(ground, null, rectGround, null);
        canvas.drawBitmap(platform, platformX, platformY, null);
        canvas.drawBitmap(target, targetX, targetY, null);
        canvas.drawBitmap(ball.getBall(ball.ballFrame), ball.ballX, ball.ballY, null);
        ball.ballFrame++;
        if ( ball.ballFrame > 3)
            ball.ballFrame = 0;
        ball.ballX += ball.ballVelocityX;
        ball.ballY += ball.ballVelocityY;
        //if ball falls on ground
        if (ball.ballY + ball.getBallHeight() >= dHeight - ground.getHeight()){
            isExplosion = true;
            explosion = new Explosion(context);
            explosion.explosionX = ball.ballX;
            explosion.explosionY = ball.ballY;
            ball.setPosition();
            life--;
            if (life <= 0){
                Intent intent = new Intent(context, GameOver.class);
                intent.putExtra("points", points);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        }
        //if ball hits top
        if (ball.ballY <= 0 && cooldown <=0){
            ball.ballVelocityY *= -1;
            cooldown = 5;
        }
        //if ball hits left
        if (ball.ballX <= 0 && cooldown <= 0){
            ball.ballVelocityX *= -1;
            cooldown = 5;
        }
        //if ball hits right
        if (ball.ballX + ball.getBallWidth() >= dWidth && cooldown <= 0){
            ball.ballVelocityX *= -1;
            cooldown = 5;
        }
        //if ball hits platform
        if (ball.ballX + ball.getBallWidth() >= platformX
        && ball.ballX <= platformX + platform.getWidth()
        && ball.ballY + ball.getBallHeight() >= platformY
        && cooldown <= 0){
            ball.ballVelocityY *= -1;
            cooldownPlatform = 10;
        }
        //if ball hits target
        if (ball.ballX + ball.getBallWidth() >= targetX
        && ball.ballX <= targetX + target.getWidth()
        && ball.ballY <= targetY + target.getHeight()
        && cooldown <= 0){
            ball.ballVelocityY *= -1;
            points++;
            cooldownTarget = 10;
        }
        cooldown--;
        cooldownTarget--;
        cooldownPlatform--;



        if(isExplosion){
            canvas.drawBitmap(explosion.getExplosion(explosion.explosionFrame), explosion.explosionX, explosion.explosionY, null);
            explosion.explosionFrame++;
            if ( explosion.explosionFrame > 2)
                isExplosion = false;
        }
        if (life == 3){
            healthPaint.setColor(Color.GREEN);
        } else if (life == 2) {
            healthPaint.setColor(Color.YELLOW);
        } else if (life == 1) {
            healthPaint.setColor(Color.RED);
        }
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawRect(dWidth-200, 30, dWidth-200+60*life, 80, healthPaint);
        canvas.drawText("" + points, 20, TEXT_SIZE, scorePaint);
        handler.postDelayed(runnable, UPDATE_FREQ);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= 0){
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN){
                oldX = event.getX();
                oldPlatformX = platformX;
            }
            if (action == MotionEvent.ACTION_MOVE){
                float shift = oldX - touchX;
                float newPlatformX = oldPlatformX - shift;
                if (newPlatformX <= 0)
                    platformX = 0;
                else if (newPlatformX >= dWidth - platform.getWidth())
                    platformX = dWidth - platform.getWidth();
                else
                    platformX = newPlatformX;
            }
        }
        return true;
    }
}
