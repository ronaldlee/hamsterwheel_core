package com.hamsterwheel.core.ui;

import android.graphics.Canvas;

public interface FrameProcessor {

	void processFrame(Canvas c);
	
	boolean isDone();
}
