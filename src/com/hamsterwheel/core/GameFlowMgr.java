package com.hamsterwheel.core;

import android.content.Context;
import android.graphics.Canvas;

public abstract class GameFlowMgr {
	protected static final int STATE_NOT_STARTED=-1;
	
	protected int cur_state=STATE_NOT_STARTED;
	
//	abstract protected void processState(int state);
	
	public void setState(int state) {				
		cur_state=state;
	}

	abstract public void updateSceneState(float cur_phone_x_accel, float cur_phone_y_accel, Context context);
	
	abstract public void updateSceneVideo(Canvas cv, Context context);
		
}
