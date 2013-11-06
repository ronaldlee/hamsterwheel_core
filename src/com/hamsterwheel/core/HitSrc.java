package com.hamsterwheel.core;

import android.graphics.Rect;

public interface HitSrc {
	
	boolean isHitAll();
	
	Rect getCollisionArea();
	
	void destroy();

}
