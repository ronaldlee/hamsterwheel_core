package com.hamsterwheel.core;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView 
implements SurfaceHolder.Callback, SensorEventListener {
	
	protected GameThread gameThread;
	protected GameFlowMgr gameFlowMgr;

	private float curXAcceleration = 0;
	private float curYAcceleration = 0;
	
	private long gameLoopDelay;
	
    public GameView(Context context, GameFlowMgr gameFlowMgr, long gameLoopDelay) {
        super(context);
    
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);        

        //===
        
        this.gameFlowMgr = gameFlowMgr;
        this.gameLoopDelay = gameLoopDelay;
        gameThread = new GameThread(holder, gameFlowMgr, getContext(), gameLoopDelay);
            
        //===
        
        setFocusable(true); // make sure we get key events   
        
    }
    
    public void destroy() {
    	this.postInvalidate();
    	
    	SurfaceHolder holder = getHolder();
    	if (holder != null) {
    		holder.removeCallback(this);   
    	}
    	 
    	if (gameThread != null) {
    		gameThread.clear();
    		gameThread = null;
    	}
    	gameFlowMgr = null;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	switch (keyCode) {
    		case KeyEvent.KEYCODE_DPAD_UP:
    			
    			return true;
    		case KeyEvent.KEYCODE_DPAD_DOWN:
    			
    			return true;
    		case KeyEvent.KEYCODE_DPAD_LEFT:
    			
    			return true;
    		case KeyEvent.KEYCODE_DPAD_RIGHT:
    			
    			return true;
  			
    	}
    	return false;
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
    	    	
    	return true;
    }
    

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {	
		
Log.e("gameview","w: " + this.getWidth() + "; h: " + this.getHeight());	
ScreenInfo.width = ScreenInfo.evenValue(this.getWidth());
ScreenInfo.height = ScreenInfo.evenValue(this.getHeight());

		gameThread.setRunning(true);
		try {
			gameThread.start();		
		} catch (Exception e) {
			try {   
				gameThread = new GameThread(holder, gameFlowMgr, getContext(), gameLoopDelay);
				gameThread.setRunning(true);
				gameThread.start();
			} catch (Exception ep) {
				Log.e("game view","===== create new thread excep: ",ep);
			}
		}
	}


	public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
            	gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }		
	}
    
	
    public void onSensorChanged(SensorEvent event) {  //int sensor, float[] values) {
    		
		double x = event.values[SensorManager.DATA_X];
		double y = event.values[SensorManager.DATA_Y];
		double z = event.values[SensorManager.DATA_Z];
//Log.e("game_view"," is accel: ++ values: x:" + x + "; y: " + y + "; z: "+z);
		
		curYAcceleration = (float)y;
		curXAcceleration = (float)-x;
		
		if (gameThread != null) {
			gameThread.setCurPhoneYAcceleration(curYAcceleration);
			gameThread.setCurPhoneXAcceleration(curXAcceleration);
		}
		
    	return;  		
	
    }
    
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    	
    }    
    
}
