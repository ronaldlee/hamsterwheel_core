package com.hamsterwheel.core;

import android.graphics.Rect;
import android.util.Log;

public class Environment {
	public static final int MEDIUM_WATER=0;
	public static final int MEDIUM_AIR=1;
	
	protected int curMedium;
	
	protected int topBorder;
	protected int leftBorder;
	protected int bottomBorder;
	protected int rightBorder;
	
	public Environment() {
		topBorder = 0;
		bottomBorder = ScreenInfo.height;
		leftBorder = 0;
		rightBorder = ScreenInfo.width;
	}
	
	public void updateRightBottomBorder() {
		bottomBorder = ScreenInfo.height;
		rightBorder = ScreenInfo.width;
	}
	
	public void setCurrentMedium(int medium) {
		this.curMedium = medium;
	}
	public boolean isInWater() {
		return curMedium == MEDIUM_WATER;
	}
	public boolean isInAir() {
		return curMedium == MEDIUM_AIR;
	}
	
	public int getTopBorder() {
		return topBorder;
	}
	public int getBottomBorder() {
		return bottomBorder;
	}
	public int getLeftBorder() {
		return leftBorder;
	}
	public int getRightBorder() {
		return rightBorder;
	}
	
	public boolean isHitTop(GameObject srcObj, float delta) {
		
		Rect src_rect = srcObj.getCollisionArea();
		
		if (src_rect.top+delta <= topBorder) {
			return true;
		}
		
		return false;
	}
	
	public boolean isHitBottom(GameObject srcObj, float delta) {	
		Rect src_rect = srcObj.getCollisionArea();
		
		if (src_rect.bottom+delta >= bottomBorder) {
			return true;
		}
		
		return false;
	}
	
	public boolean isOutLeft(GameObject srcObj, float delta) {
		Rect src_rect = srcObj.getCollisionArea();

		if (src_rect.right+delta <= leftBorder) {
			return true;
		}
		
		return false;		
	}
	public boolean isOutRight(GameObject srcObj, float delta) {
		Rect src_rect = srcObj.getCollisionArea();
		if (src_rect.left+delta >= rightBorder) {
			return true;
		}
		
		return false;		
	}
	
	public boolean isHitLeft(GameObject srcObj, float delta) {
		Rect src_rect = srcObj.getCollisionArea();

		if (src_rect.left+delta <= leftBorder) {
			return true;
		}
		
		return false;		
	}
	
	public boolean isHitRight(GameObject srcObj, float delta) {
		Rect src_rect = srcObj.getCollisionArea();
//Log.e("@@@@@1","right: " + src_rect.right + "; delta: " + delta + "; rightBorder: " + rightBorder +"; src_rect: " + src_rect);
		if (src_rect.right+delta >= rightBorder) {
			return true;
		}
		
		return false;		
	}
	
	public void offsetXY(int deltaX, int deltaY) {
		topBorder += deltaY;
		bottomBorder += deltaY;
		leftBorder += deltaX;
		rightBorder += deltaX;
	}
}
