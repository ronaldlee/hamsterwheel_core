package com.hamsterwheel.core.ui;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SplashView extends SurfaceView 
implements SurfaceHolder.Callback , SensorEventListener{
	
	private WeakReference<Activity> actRef;
	private FrameThread st;
	private SplashFrameProcessor sfp;
	private boolean hasSent;
	
    public SplashView(Context context,Activity activity,Bitmap logo_bitmap,Class mainActivityClass) {
        super(context);

		actRef = new WeakReference<Activity>(activity);
		
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);        

        sfp = new SplashFrameProcessor(context,activity,logo_bitmap,mainActivityClass);
        int gameLoopDelay = 10;
        st = new FrameThread(holder, sfp, gameLoopDelay,"splashview",false);
        
        //setFocusable(true); // make sure we get key events        
    }
    
    public void destroy() {
    	this.postInvalidate();
    	
    	SurfaceHolder holder = getHolder();
    	if (holder != null) {
    		holder.removeCallback(this);   
    	}
    	 
    	if (st != null) {
    		st.setRunning(false);
    		st.clear();
    		st = null;
    	}
    }
    
    @Override
	public boolean onTouchEvent(MotionEvent e) {
    	
    	Activity act = actRef.get();
		
		if (act != null) {
			if (!hasSent) {
				sfp.setIsWaveNumFading();
				/*
				st.setRunning(false);
				
Log.e(">>>>>","SHITIIITITITIT");			
				Intent intent = new Intent(act, MainMenuActivity.class);
				act.startActivity(intent);
				*/
				hasSent = true;
			}
		}
		
    	return false;
    }
    

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
		st.setRunning(true);
	
		try {
			st.start();		
		} catch (Exception e) {
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
		st.setRunning(false);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}
    
}

