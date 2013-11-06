package com.hamsterwheel.core;

import android.util.Log;


public class InteractiveMovement extends BasicMovement {
	
	protected int cur_y_velocity=0;
	protected int cur_x_velocity=0;
	private int prev_y_velocity=0;
	private int prev_x_velocity=0;

	public InteractiveMovement(Environment en, GamePhysics physics) {
		super(en,physics);
	}
	
	public Environment getEnvironment() {
		return en;
	}
	
	protected int getVelocityCausedByGravity(int velocity, float timeElapsed) {	
		return velocity;
	}
	
	public int getFriction(int velocity) {   
		int sign = velocity < 0?-1:1;
	
		if (en.isInAir()) {
			velocity += -1*sign*physics.getAirFiction();
		}
		else if (en.isInWater()) {
			velocity += -1*sign*physics.getWaterFiction();
		}	
			
		//new sign
		int new_sign = velocity < 0?-1:1;
		
//Log.e("@@@@@@@","new sign: " + new_sign);		
		//cannot make it go the other way by friction!
		if (new_sign != sign) return 0; 
		
		return velocity;
	}
	
	public int getTerminalVelocity(int y_velocity) {
		if (y_velocity == 0) return y_velocity;
		
		int upbound = physics.getUpboundTerminalVelocity();
		int lowbound = physics.getLowboundTerminalVelocity();

		
		if (y_velocity >= upbound) {
			return upbound;
		}
		if (y_velocity > lowbound && y_velocity < upbound) {
			return y_velocity ;
		}
		
		return y_velocity;
	}

	
//	private float getYAccelByExternalForce(float time_elasped_in_sec) {
//		float y_accel = cur_phone_y_accel + 
//			(cur_phone_y_accel-prev_phone_y_accel)*Physics.SHAKE_Y_FACTOR ;
//    	
//		prev_y_accel = cur_phone_y_accel;
//		return y_accel * physics.getAccelFactor() * time_elasped_in_sec;
//	}
	
	protected int getYAccelByExternalForce(float time_elasped_in_sec) {
		return 0;
	}
	public void moveVectorY(float time_elasped_in_sec) {
//		GameObject gameObj = gameObjRef.get();
//		if (gameObj == null) return;
 	
    	int y_accel_by_external_force = getYAccelByExternalForce(time_elasped_in_sec);
    	cur_y_velocity = prev_y_velocity + y_accel_by_external_force;
    	//cap it with terminal velocity
//Log.e("@@@@@","y vel: " + cur_y_velocity + "; time: " + time_elasped_in_sec + "; gravity: " + physics.getGravity()); 
    	
    	cur_y_velocity = getVelocityCausedByGravity(cur_y_velocity,time_elasped_in_sec);
//Log.e("11111", " y: v: " + cur_y_velocity) ;   	
    	cur_y_velocity = getFriction(cur_y_velocity);
//Log.e("22222", " y: v: " + cur_y_velocity) ;    	
    	cur_y_velocity = getTerminalVelocity(cur_y_velocity);
//Log.e("33333", " y: v: " + cur_y_velocity) ;
    	
    	float y_distance = cur_y_velocity * time_elasped_in_sec;
    	
//Log.e("@@@@@","dist: " + y_distance);    	
		moveY(y_distance);		
		
		if (gameObj.prev_ypos < gameObj.getY()) {
			notifyIsFalling();
		}
		else if (gameObj.prev_ypos > gameObj.getY()) {
			notifyIsRising();
		}
		else {
			notifyIsSameHeight();
		}
		
		prev_y_velocity = cur_y_velocity;
	}
	
	
//	private float getXAccelByTimeElasped(float time_elasped_in_sec) {
//		float x_accel = cur_phone_x_accel + 
//		(cur_phone_x_accel-prev_phone_x_accel)*Physics.SHAKE_X_FACTOR ; 
//		
//		prev_phone_x_accel = cur_phone_x_accel;
//		return x_accel * Physics.ACCEL_FACTOR * time_elasped_in_sec;
//	}	
	protected int getXAccelByExternalForce(float time_elasped_in_sec) {
		return 0;
	}
	public void moveVectorX(float time_elasped_in_sec) {		
		int x_accel_by_external_force = getXAccelByExternalForce(time_elasped_in_sec);
    	cur_x_velocity = prev_x_velocity + x_accel_by_external_force;
//		
//    	if (prev_x_velocity != 0) {
//Log.e("!!!!!!", "before prev x velocity: " + prev_x_velocity);
//    	}
    	
    	cur_x_velocity = getFriction(cur_x_velocity);
    	cur_x_velocity = getTerminalVelocity(cur_x_velocity);	    	
    	    	
    	float x_distance = cur_x_velocity * time_elasped_in_sec;
    	
//    	if (x_distance != 0) {
//Log.e("!!!!!!","x_distance: " + x_distance + 
//		"; time_elasped_in_sec: " + time_elasped_in_sec +
//		"; after cur_x_velocity: " + cur_x_velocity); 
//    	}
    	
//    	float abs = Math.abs(x_distance);
//    	if (abs == 0) {
//    		moveX(0);
//    	}
//    	else {
//	    	int rounded = (int)abs;
//	    	
//	    	float sign = x_distance/abs;
//	    	
//			moveX(abs*sign); //x_distance);	
//    	}
    	
    	moveX(x_distance);	
    	
		prev_x_velocity = cur_x_velocity;
	}
	
	//---
	
	protected void reboundVertically() {		
//		abs_velocity -= physics.getEnergyLoss();
//		if (abs_velocity < 0)
//			abs_velocity = 0;
		
		cur_y_velocity = -1 * cur_y_velocity;	
	}
	
	protected void reboundHorizontally() {
//		
//		abs_velocity -= physics.getEnergyLoss();
//		if (abs_velocity < 0)
//			abs_velocity = 0;
		
		cur_x_velocity = -1 * cur_x_velocity;
	}
	
	//---
	@Override
	public void moveY(float delta) {
//		GameObject gameObj = gameObjRef.get();
//		if (gameObj == null) return;
		
		//Log.e("@@@@@", "y pos: " + gameObj.yPos);
		
		if (en.isHitTop(gameObj,delta)) {
//Log.e("@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@");			
//Log.e("@@@@@", "hit top");
			reboundVertically();
			
			gameObj.setY(en.getTopBorder()+1);
			
			notifyHitTopBorder();
		}
		else if (en.isHitBottom(gameObj,delta)) {
//Log.e("@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@");			
//Log.e("@@@@@", "hit bottom");			
			reboundVertically();
			
			gameObj.setY(en.getBottomBorder()-gameObj.getCollisionArea().height()-1);
			
			notifyHitBottomBorder();
		}
		else {
//Log.e(">>>>>>>","+yPos: delta: " + delta);		
			float abs_delta = Math.abs(delta);
			int delta_int = (int)abs_delta;
			int sign = (int)(delta/abs_delta);
			if (sign < 0) {
				gameObj.offsetY(-delta_int);
			}
			else {
				gameObj.offsetY(delta_int);
			}
		}
	}

	@Override
	public void moveX(float delta) {
//		GameObject gameObj = gameObjRef.get();
//		if (gameObj == null) return;
		
		if (en.isHitLeft(gameObj,delta)) {
//Log.e("@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@");				
//Log.e("@@@@@", "hit left");	
			
Log.e("SEEEE", "en: " + en);
			reboundHorizontally();

			gameObj.setX(en.getLeftBorder()+1);
			
			notifyHitLeftBorder();
		}
		else if (en.isHitRight(gameObj,delta)) {
//Log.e("@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@");			
//Log.e("@@@@@", "hit right");			
			reboundHorizontally();

			gameObj.setX(en.getRightBorder()-gameObj.getCollisionArea().width()-1);
			
			notifyHitRightBorder();
		}
		else {		
			float abs_delta = Math.abs(delta);
			int delta_int = (int)abs_delta;
			int sign = (int)(delta/abs_delta);
			//int oldX = gameObj.getX();
			if (sign < 0) {
				gameObj.offsetX(-delta_int);
			}
			else {
				gameObj.offsetX(delta_int);
			}
			
//Log.e(">>>>>>>","+xPos: oldX: " + oldX +"; delta: " + delta_int + "; gameObj.xPos: " + gameObj.xPos);
		}
	}

	public void moveTo(int x_velocity, int y_velocity, float time_elasped_in_sec) {
//Log.e("@@@@@@@@","moveTo: cur_x_velocity: " + cur_x_velocity + 
//		";cur_y_velocity: " + cur_y_velocity);
		this.prev_x_velocity = x_velocity;
		this.prev_y_velocity = y_velocity;
		moveVectorX(time_elasped_in_sec);
		moveVectorY(time_elasped_in_sec);
	}
	
	public void initVelocity(int x_velocity, int y_velocity) {
		this.prev_x_velocity = x_velocity;
		this.prev_y_velocity = y_velocity;
	}
	
	public void stopMovement() {
		prev_x_velocity = cur_x_velocity = 0;
		prev_y_velocity = cur_y_velocity = 0;
	}
	//----
	
	public void storePrevVelocity() {
		prev_x_velocity = cur_x_velocity; 
		prev_y_velocity = cur_y_velocity;		
	}
	
	public boolean isStopped() {
		return cur_x_velocity == 0 && cur_y_velocity == 0;
	}

	protected void notifyHitTopBorder() {
		GameObject gameObj = getGameObject();
		if (gameObj == null) return;
		
		AI ai = gameObj.getAI();
		if (ai != null) {
			ai.hitTop();
		}
	}
	protected void notifyHitBottomBorder() {
		GameObject gameObj = getGameObject();
		if (gameObj == null) return;
		
		AI ai = gameObj.getAI();
		if (ai != null) {
			ai.hitBottom();
		}
	}
	protected void notifyHitLeftBorder() {
		GameObject gameObj = getGameObject();
		if (gameObj == null) return;
		
		AI ai = gameObj.getAI();
		if (ai != null) {
			ai.hitLeft();
		}
	}
	protected void notifyHitRightBorder() {
		GameObject gameObj = getGameObject();
		if (gameObj == null) return;
		
		AI ai = gameObj.getAI();
		if (ai != null) {
			ai.hitRight();
		}
	}
	
	protected void notifyIsFalling() {}
	protected void notifyIsRising() {}
	protected void notifyIsSameHeight() {}	
}

