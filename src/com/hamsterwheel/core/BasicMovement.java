package com.hamsterwheel.core;


public class BasicMovement {

	protected GameObject gameObj;
	protected GamePhysics physics;
	protected Environment en;
	
	public BasicMovement(Environment en, GamePhysics physics) {
		this.physics = physics;
		this.en = en;
	}
	
	public void setGameObject(GameObject gameObj) {
		this.gameObj = gameObj;
	}
	
	protected GameObject getGameObject() {
		return gameObj;
	}
	
	
	public void moveY(float delta) {			
		gameObj.offsetY((int)delta);
	}

	
	public void moveX(float delta) {			
		gameObj.offsetX((int)delta);
	}
}

