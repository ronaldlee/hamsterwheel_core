package com.hamsterwheel.core.ui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.hamsterwheel.core.BasicMovement;
import com.hamsterwheel.core.GameObject;
import com.hamsterwheel.core.SpriteData;

public class Layer extends GameObject {
	private static final SpriteData LAYER = new SpriteData(0,0,0,0,1);
	
	protected ArrayList<GameObject> gos;
	
	private int scrollSpeed;
	public Layer(Bitmap bitmap, int scrollSpeed, BasicMovement basicMovement) {
		super(bitmap, null,LAYER,basicMovement,null);
		gos = new ArrayList<GameObject>();
		this.scrollSpeed = scrollSpeed;
	}
	
	public int getScrollSpeed() {
		return scrollSpeed;
	}
	
	public void tileGameObjects(GameObject go) {
		int length = gos.size();
		if (length > 0) {
			GameObject preObj = gos.get(length-1);
			go.setX(preObj.getX() + preObj.getWidth());	
		}
		gos.add(go);
		width += go.getWidth();
	}
	
	public ArrayList<GameObject> getGameObjects() {
		return gos;
	}
	
	public void updateState() {	
		for (GameObject go:gos) {
			go.updateState();
		}
	}
	
	public void updateVideo(Canvas c,Context context) {
		if (is_initializing) return;
	
		for (GameObject go:gos) {
			go.updateVideo(c,context);
		}
	}
	//====
	
	@Override
	public void resetCounters() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSound() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void destroy() {
		
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}

}
