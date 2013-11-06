package com.hamsterwheel.core.ui;

import android.graphics.Bitmap;

import com.hamsterwheel.core.BasicMovement;
import com.hamsterwheel.core.CollisionMgr;
import com.hamsterwheel.core.SpriteData;

public class ProjectileWeapon extends Weapon {

	private int xInitVelocity;
	private int yInitVelcoity;
	
	public ProjectileWeapon(Bitmap bitmap,
			BasicMovement basicMovement, SpriteData spriteData,
			int spriteStillYOffset, int spriteUseYOffset, int stillFrames,
			int stillDuration, int useFrames, int useDuration,
			int xInitVelocity, int yInitVelcoity,
			CollisionMgr cm			
	) {
		super(bitmap, basicMovement, spriteData, spriteStillYOffset,
				spriteUseYOffset, stillFrames, stillDuration, useFrames, useDuration,cm);
		// TODO Auto-generated constructor stub
		
		this.xInitVelocity = xInitVelocity;
		this.yInitVelcoity = yInitVelcoity;
	}

}
