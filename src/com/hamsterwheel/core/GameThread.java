package com.hamsterwheel.core;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
	
	private boolean isRunning = false;
	private SurfaceHolder surfaceHolder;
	
//    public static final int fps = 20;  //default is 20 frames per second
//    public static final int delay_in_ms = 1000 / fps;
//    //public static final int delay_in_s = delay_in_ms/1000;
//    public static final float delay_in_s = ((float)GameThread.delay_in_ms)/(float)1000;
    	
	protected float cur_phone_x_accel;
	protected float cur_phone_y_accel;

	private GameFlowMgr gameFlowMgr;
	private WeakReference<Context> contextRef;
	
	private long gameLoopDelay;// = Constants.delay_in_ms;
	
	private int heartBeat;
    
	GameThread(SurfaceHolder surfaceHolder, GameFlowMgr gameFlowMgr, Context context, long gameLoopDelay) {
		this.surfaceHolder = surfaceHolder;
		this.gameFlowMgr = gameFlowMgr;
		
		this.contextRef = new WeakReference<Context>(context);
		
		this.gameLoopDelay = gameLoopDelay;
	}
	
	public void clear() {
		surfaceHolder = null;
		gameFlowMgr = null;
		if (contextRef != null) {
			contextRef.clear();
			contextRef = null;
		}
	}
	
	public void processFrame(Canvas c) {
		Context context = contextRef.get();
		updateState(context);
        updateInput();
        updateAI();
        updatePhysics();
        updateAnimations();
        updateSound();
        updateVideo(c,context);  
	}
	
    @Override
    public void run() {	
        while (isRunning) {
heartBeat++;
if (heartBeat % 50 == 0) {
	Log.e("", "OOOOOOOOOOOOOAAA HEART BEAT OOOOOOOOOOOOOO");
	heartBeat=0;
}
        	
            Canvas c = null;
            try {
                c = surfaceHolder.lockCanvas();               	
                synchronized (surfaceHolder) {
                	processFrame(c);           
                }    
    			Thread.sleep(getGameLoopDelay());
            } catch (InterruptedException e) {
            	break;
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null && surfaceHolder != null) {
                	surfaceHolder.unlockCanvasAndPost(c);
                }                
            }
        }    	
    }
    
    public void setGameLoopDelay(long gameLoopDelay) {
    	this.gameLoopDelay = gameLoopDelay;
    }
    public long getGameLoopDelay() {
    	return gameLoopDelay;
    }
  
    private void updateState(Context context) {
    	gameFlowMgr.updateSceneState(cur_phone_x_accel,cur_phone_y_accel,context);
    }
    
    private void updateInput() {
    	
    }
    
    private void updateAI() {  
    }
    
    private void updatePhysics() {    
    }
    
    private void updateAnimations() {    	
    }
    
    private void updateSound() {   
    }
    
    private void updateVideo(Canvas c, Context context) { 
//    	Paint paint = new Paint();
//    	paint.setColor(0xff46829);
//    	
//Log.e("#$#$#$","#$# screen width:" + ScreenInfo.width +"; height: " + ScreenInfo.height);    	
//		c.drawRect(0,0,ScreenInfo.width, ScreenInfo.height, paint);
    	gameFlowMgr.updateSceneVideo(c,context);
    	
    }
    
    
    public void setRunning(boolean b) {
        isRunning = b;
    }    
    public boolean isRunning() {
    	return isRunning;   	
    }
    
	public void setCurPhoneYAcceleration(float y_accel) {
		cur_phone_y_accel = y_accel;
	}
	public void setCurPhoneXAcceleration(float x_accel) {
		cur_phone_x_accel = x_accel;
	}	

}
