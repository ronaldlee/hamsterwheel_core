package com.hamsterwheel.core;

import java.util.ArrayList;

import android.graphics.Rect;

public class CollisionMgr {
		
	private ArrayList<HitTarget> hitTargets;
	private ArrayList<HitSrc> hitSrcs;
	
	public CollisionMgr() {
		hitTargets = new ArrayList<HitTarget>();
	}
	
	public void addHitTarget(HitTarget hitTarget) {
		hitTargets.add(hitTarget);
	}
	
	public void removeHitTarget(HitTarget hitTarget) {
		hitTargets.remove(hitTarget);
	}
	
	public void addHitSrc(HitSrc hitSrc) {
		hitSrcs.add(hitSrc);
	}
	
	public void removeHitSrc(HitSrc hitSrc) {
		hitSrcs.remove(hitSrc);
	}
	
	public void checkHit() {
		for (HitSrc hitSrc : hitSrcs) {
			for (HitTarget hitTarget : hitTargets) {
				if (isHitTargetTop(hitSrc,hitTarget)) {
					hitTarget.hitTop();
					if (!hitSrc.isHitAll()) {
						hitSrc.destroy();
						return;
					}
				}
				else if (isHitTargetBottom(hitSrc,hitTarget)) {
					hitTarget.hitBottom();
					if (!hitSrc.isHitAll()) {
						hitSrc.destroy();
						return;
					}
				}
				else if (isHitTargetLeft(hitSrc,hitTarget)) {
					hitTarget.hitLeft();
					if (!hitSrc.isHitAll()) {
						hitSrc.destroy();
						return;
					}
				}
				else if (isHitTargetRight(hitSrc,hitTarget)) {
					hitTarget.hitRight();
					if (!hitSrc.isHitAll()) {
						hitSrc.destroy();
						return;
					}
				}
			}
		}
	}
	
	//===
	
	public static boolean isHitTargetTop(HitSrc srcObj, HitTarget targetObj) {
		return isHitTargetTop(srcObj, targetObj,0);
	}
	public static boolean isHitTargetTop(HitSrc srcObj, HitTarget targetObj,int hitDepth) {	
		Rect src_rect = srcObj.getCollisionArea();
		Rect target_rect = targetObj.getCollisionArea();
		
		if (src_rect.bottom-hitDepth >= target_rect.top) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isHitTargetBottom(HitSrc srcObj, HitTarget targetObj) {	
		return isHitTargetBottom(srcObj,targetObj,0);
	}
	public static boolean isHitTargetBottom(HitSrc srcObj, HitTarget targetObj,int hitDepth) {		
		Rect src_rect = srcObj.getCollisionArea();
		Rect target_rect = targetObj.getCollisionArea();
		
		if (src_rect.top+hitDepth <= target_rect.bottom) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isHitTargetLeft(HitSrc srcObj, HitTarget targetObj) {
		return isHitTargetLeft(srcObj, targetObj, 0);
	}
	public static boolean isHitTargetLeft(HitSrc srcObj, HitTarget targetObj, int hitDepth) {
		Rect src_rect = srcObj.getCollisionArea();
		Rect target_rect = targetObj.getCollisionArea();
		
		if (src_rect.right-hitDepth>=target_rect.left) {
			return true;
		}
		
		return false;		
	}
	
	public static boolean isHitTargetRight(HitSrc srcObj, HitTarget targetObj) {
		return isHitTargetRight(srcObj, targetObj, 0);
	}
	public static boolean isHitTargetRight(HitSrc srcObj, HitTarget targetObj, int hitDepth) {
		Rect src_rect = srcObj.getCollisionArea();
		Rect target_rect = targetObj.getCollisionArea();
		
		if (src_rect.left+hitDepth <= target_rect.right) {
			return true;
		}
		
		return false;	
	}
	
}
