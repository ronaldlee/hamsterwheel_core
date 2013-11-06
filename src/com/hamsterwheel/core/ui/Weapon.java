package com.hamsterwheel.core.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.hamsterwheel.core.ActionFrameCounters;
import com.hamsterwheel.core.BasicMovement;
import com.hamsterwheel.core.CollisionMgr;
import com.hamsterwheel.core.GameObject;
import com.hamsterwheel.core.HitSrc;
import com.hamsterwheel.core.SpriteData;

public class Weapon extends GameObject implements HitSrc {
	public static final byte STATE_STILL=0;
	public static final byte STATE_USE=1;
	
	private ActionFrameCounters stillFrameCounters;
	private ActionFrameCounters useFrameCounters;
	private int sprite_still_y_offset=0;
	private int sprite_use_y_offset=0;
	
	private CollisionMgr cm;
	
	public Weapon(Bitmap bitmap,
				  BasicMovement basicMovement,
				  SpriteData spriteData,
				  int sprite_still_y_offset,
				  int sprite_use_y_offset,
				  int still_frames, int still_duration,
				  int use_frames, int use_duration,
				  CollisionMgr cm) {
		super(bitmap, null,spriteData,basicMovement,null);
		
		this.cm = cm;
		
		stillFrameCounters = new ActionFrameCounters(height,sprite_still_y_offset,still_frames,still_duration,true);
		useFrameCounters = new ActionFrameCounters(height,sprite_use_y_offset,use_frames,use_frames,false);
		
		
		
		this.sprite_still_y_offset = sprite_still_y_offset;
		this.sprite_use_y_offset = sprite_use_y_offset;
	}

	//===
	
	public void use(int faceDirectin) {
		this.faceDirection = faceDirectin;
		cur_state = STATE_USE;
	}
	
	public Rect getSpriteRect() {
		switch (cur_state) {
			case STATE_STILL: {
		    	Rect dstRect = createDestRec(stillFrameCounters);
		    	boolean isDone = stillFrameCounters.play();
		    	
		    	return dstRect;
			}
			case STATE_USE: {
				Rect dstRect = createDestRec(useFrameCounters);
				boolean isDone = useFrameCounters.play();
				
		    	if (isDone) {
		    		cur_state=0;
		    	}
		    	return dstRect;
			}
		}
		
		return null;
	}
	
	@Override
	public void updateState() {
		// TODO Auto-generated method stub
		
		src = getSpriteRect();
	}
	
	@Override
	public void updateVideo(Canvas c,Context context) {
		try {
			isUpdateVideo = true;
			draw(c,src,dst);
		} finally {
			storePrevPos();
			isUpdateVideo = false;
		}
	}
	
	
	@Override
	public void resetCounters() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isVisible() {
		//see x/y/width/height is in the screen
		return true;
	}
	@Override
	public void destroy() {
		
	}

	public boolean isHitAll() {
		// TODO Auto-generated method stub
		return false;
	}
}
