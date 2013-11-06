package com.hamsterwheel.core;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class LiveWallpaperGameView extends WallpaperService {
	protected GameFlowMgr gameFlowMgr;
	
	public LiveWallpaperGameView(GameFlowMgr gameFlowMgr) {		 
		  this.gameFlowMgr = gameFlowMgr;
	}
	
	@Override
	public Engine onCreateEngine() {
		return new GameViewEngine(gameFlowMgr);
	}
	
	protected void onWPTouchEvent(MotionEvent event) {
		
	}

	protected void engineOnCreate(SurfaceHolder surfaceHolder) {
		
	}
	
	class GameViewEngine extends Engine implements SensorEventListener{

		protected GameThread gameThread;
		protected GameFlowMgr gameFlowMgr;
		
		private float curXAcceleration = 0;
		private float curYAcceleration = 0;
		
		private final Handler mHandler = new Handler();
		private boolean mVisible;
		private SensorManager sensorMgr;
		
		public GameViewEngine(GameFlowMgr gameFlowMgr) {
			this.gameFlowMgr = gameFlowMgr;
		}
		
		private final Runnable mGameLoop = new Runnable() {
			public void run() {
				drawFrame();
			}
		};
		
		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			
			gameThread = new GameThread(surfaceHolder, gameFlowMgr, null,0);
			this.setTouchEventsEnabled(true);
	
			sensorMgr = (SensorManager)getSystemService(SENSOR_SERVICE);
	        
			List<Sensor> sensors = sensorMgr.getSensorList(Sensor.TYPE_ACCELEROMETER);

			if (sensors != null && sensors.size() == 1) {
				
		        boolean accelSupported = 
		        	sensorMgr.registerListener(this, 
		        			sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), //sensors.get(0),
		        		SensorManager.SENSOR_DELAY_GAME);

		        if (!accelSupported) {
		        	sensorMgr.unregisterListener(this,sensors.get(0));
		        }
			}
			
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager wm = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
			wm.getDefaultDisplay().getMetrics(dm);
			
	Log.e("**********","density: " + dm.density + "; d.dpi: " + dm.densityDpi + 
			"; heightPixels: " + dm.heightPixels + "; widthPixels: " + dm.widthPixels +
			"; xdpi: " + dm.xdpi + "; ydpi: " + dm.ydpi +"");

			
	        ScreenInfo.width = ScreenInfo.evenValue(dm.widthPixels);
	        ScreenInfo.height = ScreenInfo.evenValue(dm.heightPixels);
	        ScreenInfo.density = dm.density;
	        	
	        engineOnCreate(surfaceHolder);
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(mGameLoop);
		}
		
		@Override
		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			if (visible) {
				drawFrame();
			}
			else {
				mHandler.removeCallbacks(mGameLoop);
			}
		}
		
		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			drawFrame();
		}
		
		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
		}
		@Override 
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
		}
		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels) {
			drawFrame();
		}
		@Override
		public void onTouchEvent(MotionEvent event) {
			onWPTouchEvent(event);
		}
		
		void drawFrame() {
			final SurfaceHolder holder = getSurfaceHolder();
			
//            final Rect frame = holder.getSurfaceFrame();
//            ScreenInfo.width = ScreenInfo.evenValue(frame.width());
//			ScreenInfo.height = ScreenInfo.evenValue(frame.height());

            Canvas c = null;
            try {
                c = holder.lockCanvas();
                if (c != null) {
                	gameThread.processFrame(c);
                }
            } finally {
                if (c != null) holder.unlockCanvasAndPost(c);
            }
            
			mHandler.removeCallbacks(mGameLoop);
			if (mVisible) {
				mHandler.postDelayed(mGameLoop,gameThread.getGameLoopDelay());
			}
		}
		
		 public void onSensorChanged(SensorEvent event) {  //int sensor, float[] values) {
	    		
			double x = event.values[SensorManager.DATA_X];
			double y = event.values[SensorManager.DATA_Y];
			double z = event.values[SensorManager.DATA_Z];
	//Log.e("game_view"," is accel: ++ values: x:" + x + "; y: " + y + "; z: "+z);
			
			curYAcceleration = (float)y;
			curXAcceleration = (float)-x;
			
			gameThread.setCurPhoneYAcceleration(curYAcceleration);
			gameThread.setCurPhoneXAcceleration(curXAcceleration);
			
	    	return;  		
	    }
	    
	    public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    	
	    }  
	    
	}
}
