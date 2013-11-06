package com.hamsterwheel.core.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.hamsterwheel.core.BasicMovement;
import com.hamsterwheel.core.Environment;
import com.hamsterwheel.core.RepeatingSceneMovement;

/*
 * Scene that handles background images that repeats itself 
 */
public class RepeatingScene extends Scene {

	public RepeatingScene(Bitmap bitmap, BasicMovement basicMovement, Environment en) {
		super(bitmap, basicMovement);
	}

	public void scrollLeft() {
		//move objects to the right
		RepeatingSceneMovement sceneMovement = (RepeatingSceneMovement)getDefaultMovement();
		sceneMovement.moveX(1);
	}
	
	public void scrollRight() {
		//move objects to the left
		RepeatingSceneMovement sceneMovement = (RepeatingSceneMovement)getDefaultMovement();
		sceneMovement.moveX(-1);
	}
	@Override
	public boolean isVisible() {
		return true;
	}
	@Override
	public void destroy() {
		
	}

	@Override
	public void updateVideo(Canvas c, Context context) {
		// TODO Auto-generated method stub
		
	}
}
