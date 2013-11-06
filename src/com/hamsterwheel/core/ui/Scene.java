package com.hamsterwheel.core.ui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.hamsterwheel.core.BasicMovement;
import com.hamsterwheel.core.GameObject;
import com.hamsterwheel.core.SpriteData;

/*
 * Scene object: manage background and foreground game objects.
 */
public abstract class Scene extends GameObject implements OnTouchListener {
	private static final SpriteData LAYER =  new SpriteData(0,0,0,0,1);
	
	protected ArrayList<Layer> layers = new ArrayList<Layer>();
	protected ArrayList<GameObject> game_objects = new ArrayList<GameObject>();
	
	public Scene(Bitmap bitmap, BasicMovement basicMovement) {
		super(bitmap, null,LAYER,basicMovement,null);
	}
	public void scrollLeft() {
		//move objects to the right
		BasicMovement sceneMovement = getDefaultMovement();
		sceneMovement.moveX(1);
	}
	
	public void scrollRight() {
		//move objects to the left
		BasicMovement sceneMovement = getDefaultMovement();
		sceneMovement.moveX(-1);
	}
	
	protected void addGameObject(GameObject go) {
		go.setScene(this);
		game_objects.add(go);
	}
	
	protected void removeGameObject(GameObject go) {
		game_objects.remove(go);
	}
	
	protected void clearup() {
		game_objects.clear();
	}
	
	public void addLayer(Layer layer) {
		layers.add(layer);
	}
	
	public ArrayList<Layer> getLayers() {
		return layers;
	}
	
	public void updateState() {	
		for (GameObject layer:layers) {
			layer.updateState();
		}
		for (GameObject go:game_objects) {
			go.updateState();
		}				
	}
	
	@Override
	public void updateVideo(Canvas c,Context context) {
		if (is_initializing) return;

		for (GameObject layer:layers) {
			layer.updateVideo(c,context);
		}
		for (GameObject go:game_objects) {
			go.updateVideo(c,context);	
		}
	}
	
	//===
	

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public void resetCounters() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSound() {
		// TODO Auto-generated method stub
		
	}
}
