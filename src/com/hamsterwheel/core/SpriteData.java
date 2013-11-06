package com.hamsterwheel.core;

import android.graphics.Rect;

public class SpriteData {

	private int originalWidth;
	private int originalHeight;
	private int initX;
	private int initY;
	private int width;
	private int height;
	
	public SpriteData(int initX, int initY, int width, int height, float density) {
		originalWidth = width;
		originalHeight = height;
		this.initX = (int)(initX*density);
		this.initY = (int)(initY*density);
		this.width = (int)(width*density);
		this.height = (int)(height*density);
	}
	
	public int getInitX() {
		return initX;
	}
	public int getInitY() {
		return initY;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getOriginalWidth() {
		return originalWidth;
	}
	public int getOriginalHeight() {
		return originalHeight;
	}
	
	public Rect getRect() {
		return new Rect(initX,initY,initX+width,initY+height);
	}
	
}
