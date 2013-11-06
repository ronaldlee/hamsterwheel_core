package com.hamsterwheel.core;

import android.graphics.Rect;

public interface HitTarget {

	void hitTop();
	void hitBottom();
	void hitLeft();
	void hitRight();
	
	Rect getCollisionArea();
}
