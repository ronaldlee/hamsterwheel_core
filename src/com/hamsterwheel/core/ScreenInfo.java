package com.hamsterwheel.core;

public class ScreenInfo {

	public static boolean isNaturalLandscape = false;
	
	public static int width;
	public static int height;
	public static float density;
	
	public static int ground_y;

	public static int x_padding;
	public static int y_padding;
	
	public static final int evenValue(int value) {
		return (value%2 == 0)?value:value-1;
	}
	
	public static final int getMinX(int intendedX) {
		if (intendedX < 0)
			return 0;
		
		if (intendedX > width)
			return width;
	
		return intendedX;
	}
	
	public static final int getMinY(int intendedY) {
		if (intendedY < 0)
			return 0;
		
		if (intendedY > width)
			return width;
	
		return intendedY;
	}
	
}

