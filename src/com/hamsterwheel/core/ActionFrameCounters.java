package com.hamsterwheel.core;


public class ActionFrameCounters {
	private int actionFrame;
	private int actionDuration;
	private int maxActionFrame;
	private int maxFrameDuration;
	private boolean isLooping;
	
	private int spriteXOffset,spriteYOffset;
	private int spriteWidth,spriteHeight;
	
	private boolean isInterruptable;
	private boolean isSpritesHorizontal;
	
	private int initDelay;
	
	private int loopCount = -1;
	
	private boolean isBlackOut;
	
	public ActionFrameCounters(int spriteHeight, int spriteYOffset, 
							   int maxActionFrame, int maxFrameDuration,
							   boolean isLooping,boolean isInterruptable) {
		this.spriteYOffset = spriteYOffset;
		this.spriteHeight = spriteHeight;
		this.maxActionFrame = maxActionFrame;
		this.maxFrameDuration = maxFrameDuration;	
		this.isLooping = isLooping;
		this.isInterruptable = isInterruptable;
	}
	
	public ActionFrameCounters(int spriteHeight, int spriteYOffset, 
			   int maxActionFrame, int maxFrameDuration,
			   boolean isLooping,boolean isInterruptable,
			   int loopCount) {
		this(spriteHeight, spriteYOffset,maxActionFrame,maxFrameDuration,isLooping,isInterruptable);
		this.loopCount = loopCount;
	}
			
	public ActionFrameCounters(int spriteHeight, int spriteYOffset, 
			   int maxActionFrame, int maxFrameDuration,
			   boolean isLooping,boolean isInterruptable,
			   boolean isSpritesHorizontal,int spriteWidth, int spriteXOffset) {
		this(spriteHeight, spriteYOffset,maxActionFrame,maxFrameDuration,isLooping,isInterruptable);
		this.isSpritesHorizontal = isSpritesHorizontal;
		this.spriteWidth = spriteWidth;
		this.spriteXOffset = spriteXOffset;
	}	
	
	public ActionFrameCounters(int spriteHeight, int spriteYOffset, 
			   int maxActionFrame, int maxFrameDuration,
			   boolean isLooping) {
		this(spriteHeight,spriteYOffset,maxActionFrame,maxFrameDuration,isLooping,true);
	}
	
	public void resetCounters() {
		actionFrame = 0;
		actionDuration = 0;
		
		loopCount = -1;
	}
	public int getActionFrame() {
		return actionFrame;
	}
	public void setActionFrame(int frame) {
		actionFrame=frame;
	}
	public int getMaxActionFrame() {
		return maxActionFrame;
	}
	public void setMaxActionFrame(int maxActionFrame) {
		this.maxActionFrame = maxActionFrame;
	}
	public void setMaxFrameDuration(int maxFrameDuration) {
		this.maxFrameDuration = maxFrameDuration;
	}
	public int getMaxFrameDuration() {
		return this.maxFrameDuration;
	}
	
	public boolean isBlackOut() {
		return isBlackOut;
	}
	
	public int getLoopCount() {
		return this.loopCount;
	}
	
	public boolean play() {
		isBlackOut = true;
		actionDuration++;
    	if (actionDuration >= maxFrameDuration) {	    	
    		actionFrame++;
    		actionDuration=0;
    		isBlackOut = false;
    	}
    	if (actionFrame >= maxActionFrame) {
    		if (isLooping) {
    			actionFrame=0;
    		}
    		else {
    			loopCount--;
    			if (loopCount >= 0) {
    				actionFrame=0;
    			}
    			else {
    				loopCount = -1;
    				return true; //done
    			}
    		}
    	}
    	return false;
	}
	
	public int getYOffset(int faceDirection) {	 
		return spriteYOffset + spriteHeight*(maxActionFrame*faceDirection + actionFrame);
	}
	
	public int getXOffset(int faceDirection) {
		return spriteXOffset + spriteWidth*(maxActionFrame*faceDirection + actionFrame);
	}
	
	public int getYOffset() {	 
		return spriteYOffset + spriteHeight*actionFrame;
	}
	public int getXOffset() {
		return spriteXOffset + spriteWidth*actionFrame;
	}
	
	public boolean isInterruptable() {
		return this.isInterruptable;
	}
	public boolean isSpritesHorizontal() {
		return this.isSpritesHorizontal;
	}
	
	public void setInitDelay(int delay) {
		this.initDelay = delay;
	}
	public int getInitDelay() {
		return this.initDelay;
	}
	
	public boolean peekNextIsDone() {
		return actionFrame+1 == maxActionFrame;
	}
}
