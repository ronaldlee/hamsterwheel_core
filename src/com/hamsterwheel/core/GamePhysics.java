package com.hamsterwheel.core;

import android.hardware.SensorManager;

public class GamePhysics {	
	
	public static final float ROTATE_ANGLE_DELTA = 1;
	
	public int getAirFiction() {
		return 1;
	}
	public int getWaterFiction() {
		return 2;
	}
	public float getGravity() {
		return SensorManager.STANDARD_GRAVITY;
	}
	
	public int getAccelFactor() {
		return 4;
	}
	public float getShakeXFactor() {
		return 256;
	}
	public float getShakeYFactor() {
		return 256;
	}
	
	public int getEnergyLoss() {
		return 40;
	}
	
	public int getUpboundTerminalVelocity() {
		return 400;
	}
	
	public int getLowboundTerminalVelocity() {
		return 200;
	}
}
