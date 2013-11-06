package com.hamsterwheel.core;

import java.util.ArrayList;

import com.hamsterwheel.core.ui.Layer;
import com.hamsterwheel.core.ui.Scene;

public class RepeatingSceneMovement extends BasicMovement {

	private Environment en;
	public RepeatingSceneMovement(Scene scene, Environment en) {
		super(en,null);

		this.en = en;
	}

	@Override
	public void moveX(float delta) {
		Scene scene = (Scene)getGameObject();
		
		ArrayList<Layer> layers = scene.getLayers();
		for (Layer layer:layers) {
			ArrayList<GameObject> bgObjs = layer.getGameObjects();
			float layerMoveSpeed = layer.getScrollSpeed();
			float actualDelta = delta*layerMoveSpeed;
			int layerWidth = layer.getWidth();
			for (GameObject bg:bgObjs) {
				if (en.isOutLeft(gameObj,actualDelta)) { //hit left, move the obj to right side 
					gameObj.offsetX(layerWidth);
				}
				else if (en.isOutRight(gameObj,actualDelta)) {
					gameObj.offsetX(-layerWidth);
				}
				else {			
					bg.getDefaultMovement().moveX(actualDelta);
				}	
			}
		}
	}
	
}
