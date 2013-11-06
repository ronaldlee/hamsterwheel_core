package com.hamsterwheel.core.ui;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.hamsterwheel.core.ActionFrameCounters;
import com.hamsterwheel.core.ScreenInfo;

public class SplashFrameProcessor implements FrameProcessor {
	
	private WeakReference<Activity> actRef;
	private WeakReference<Class> mainActClassRef;
	private WeakReference<Bitmap> logoBitmapRef;
	private Paint logo_paint;
	private Bitmap logo_bitmap;
	private Rect logo_bitmap_src,logo_bitmap_dst;
	
	private int wave_alpha;
	private boolean isDone;
	
	private boolean isWaveNumFading;
	private ActionFrameCounters waveNumDurationCounters =
		new ActionFrameCounters(0, 0, 60,2,false,true);
	
	public SplashFrameProcessor(Context context,Activity activity,Bitmap logo_bitmap,Class mainActivityClass) {
		actRef = new WeakReference<Activity>(activity);
		mainActClassRef =  new WeakReference<Class>(mainActivityClass);
		logoBitmapRef = new WeakReference<Bitmap>(logo_bitmap);
		
		logo_paint=new Paint();
		
		int width = logo_bitmap.getWidth();
		int height = logo_bitmap.getHeight();
		
		logo_bitmap_src = new Rect(0,0,width,height);
		
		logo_bitmap_dst = new Rect(0,0, 
				(int)(width*ScreenInfo.density),
				(int)(height*ScreenInfo.density));
		 
		logo_bitmap_dst.offset((int)( (ScreenInfo.width-width*ScreenInfo.density)/2 ),
							   (int)( (ScreenInfo.height-height*ScreenInfo.density)/2 ));
	}
	
	public void setIsWaveNumFading() {
		isWaveNumFading = true;
	}
	
	public void processFrame(Canvas c) {
		c.drawColor(Color.BLACK);
		if (isWaveNumFading) {
			wave_alpha-=5;
		}
		else {
			wave_alpha+=5;
		}
		if (wave_alpha >= 255) {
			wave_alpha = 255;		
			if (waveNumDurationCounters.play()) {
				isWaveNumFading = true;
			}
		}
		

//Log.e("MMMMMMMMMM", "MMMM yeyeye");
		if (wave_alpha <= 0) {
			wave_alpha = 0;
			
			if (!isDone) {
				Activity act = actRef.get();
				Class mainActClass = mainActClassRef.get();
				
//Log.e("MMMMMMMMMM", "main act class: " + mainActClass);
				Intent intent = new Intent(act, mainActClass);
				act.startActivity(intent);
				isDone = true;
			}
		}		
		
		logo_paint.setAlpha(wave_alpha);
		
		Bitmap logo_bitmap = logoBitmapRef.get();
		if (logo_bitmap != null) {
			c.drawBitmap(logo_bitmap, 
					logo_bitmap_src, logo_bitmap_dst, logo_paint);
		}
	}
	
	public boolean isDone() {
		return isDone;
	}

}
