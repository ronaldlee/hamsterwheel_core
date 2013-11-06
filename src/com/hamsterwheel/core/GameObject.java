package com.hamsterwheel.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.hamsterwheel.core.ui.Scene;

public abstract class GameObject {

	//these int values affect how the action sprite postion for each action spirte series.
	public static final int FACE_LEFT=0;
	public static final int FACE_RIGHT=1;
	public static final int FACE_TOP=2;
	public static final int FACE_DOWN=3;
	
	protected String id;
	
	protected Bitmap objectBitmap;	
	
	protected Rect src;
	protected Rect dst;	
	
	protected int prev_xpos;
	protected int prev_ypos;
	private int xPos;
	private int yPos;
	
	protected int width;
	protected int height;
	
	private BasicMovement defaultMovement;
	
	protected int prev_state;
	protected int cur_state;
	protected boolean isUpdateVideo;
	
	protected boolean isDebug=true;
	protected boolean is_initializing;
	
	protected AI ai;
//	
//	private Scene scene;
	protected int faceDirection;
	protected int sprite_init_y_pos=0;
	protected int sprite_init_x_pos=0;
	
	private Scene scene;
	
	
	public GameObject(Bitmap objectBitmap, String id, 
					  SpriteData sd,
					  BasicMovement basicMovement, AI ai) {
		this.id = id;
		
		this.objectBitmap = objectBitmap;
		
		if (sd != null) {
			this.sprite_init_x_pos = sd.getInitX();
			this.sprite_init_y_pos = sd.getInitY();
			this.width = sd.getWidth();
			this.height = sd.getHeight();
		}
		
		setDefaultMovement(basicMovement);
		setAI(ai);
	}
	
	public Bitmap getBitmap() {
		return objectBitmap;
	}
	
	public String getId() {
		return id;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void setInitXY(int x,int y) {
		xPos = x;
		yPos = y;
	}
	
	public void setX(int x) {
		xPos = x;
	}
	public void setY(int y) {
		yPos = y;
	}
	public void offsetX(int delta) {
		xPos += delta;
	}
	public void offsetY(int delta) {
		yPos += delta;
	}
	
	public int getX() {
		return xPos;
	}
	public int getY() {
		return yPos;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	protected void setAI(AI ai) {
		if (ai == null) return;
		
		this.ai = ai;
		ai.setGameObject(this);
	}
	public AI getAI() {
		return ai;
	}
	protected void setDefaultMovement(BasicMovement bm) {
		if (bm == null) return;
		
		this.defaultMovement = bm;
		defaultMovement.setGameObject(this);
	}
	public BasicMovement getDefaultMovement() {
		return this.defaultMovement;
	}

	public void draw(Canvas c, Rect src, Rect dst) {
//Log.e("####","dst: " + dst.left);
		if (objectBitmap != null && src != null && dst != null) {			
			c.drawBitmap(objectBitmap, src, dst, null);	
		}
	}
	
	public void drawUseMatrix(Canvas c, Matrix m) {	
		c.save();	

		//c.rotate(degrees, px, py);
		if (isDebug) {
		Paint p = new Paint();
		p.setColor(Color.WHITE);
   		c.drawRect(dst, p);
		}		
		
//		m.preTranslate(dst.left, dst.top);
//		m.preRotate(degrees,px,py);
		if (objectBitmap != null) {
			c.drawBitmap(objectBitmap, m, null);
		}
		
   		c.restore();

	}
	
	protected void storePrevPos() {
		prev_xpos = xPos;
		prev_ypos = yPos;		
	}
	
	public int getPrevX() {
		return prev_xpos;
	}
	public int getPrevY() {
		return prev_ypos;
	}
	
	public Rect getCollisionArea() {
//Log.e("----","width: " + width + "; height: " + height);		
		Rect ca = new Rect(xPos,yPos,xPos+width,yPos+height);
		return ca;
	}
	
	protected void storePrevState() {
		prev_state = cur_state;
	}
	
	public void setCurState(int cur_state) {
		this.cur_state = cur_state;
	}

	
	//Dimension info
//	public Rect getCollisionRect() {
//		return new Rect(0,0,width,height);
//	}
	
	//Game state info
	public abstract void updateState();
	public abstract void updateVideo(Canvas c,Context context);
	//public void updateVideo(Canvas c,Context context){};
	public abstract void updateSound();
	public abstract void resetCounters();
	
	public void destroy() {
		//who ever pass the bitmap is responsible for destroying it.
//		if (objectBitmap != null) {
//			objectBitmap.recycle();		
//			objectBitmap = null;
//		}
		
		this.defaultMovement = null;
	};
	
	public boolean isVisible() {return true;};
	
	
	protected int getInitSpriteYPos() {
		return sprite_init_y_pos;
	}

	protected int getInitSpriteXPos() {
		return sprite_init_x_pos;
	}
	
	protected Rect createDestRec(ActionFrameCounters actionFrameCounters) {
		int src_left = getInitSpriteXPos();
    	int src_right = src_left + getWidth();
  
    	int src_top = 
    		getInitSpriteYPos()+
    		actionFrameCounters.getYOffset(faceDirection);
    	
    	int src_bottom = src_top+getHeight();
    	return new Rect(src_left,src_top,src_right,src_bottom);
	}
	
	protected Rect createDestRecAnimatedHorizontally(ActionFrameCounters actionFrameCounters,int sprite_y_offset) {
		int src_left = getInitSpriteXPos()+
			actionFrameCounters.getXOffset();
		
    	int src_right = src_left + getWidth();
  
    	int src_top = sprite_y_offset;
		
    	int src_bottom = src_top+getHeight();
    	
    	return new Rect(src_left,src_top,src_right,src_bottom);
	}
	
	public boolean isFaceLeft() {
		return faceDirection == FACE_LEFT;
	}
	public boolean isFaceRight() {
		return faceDirection == FACE_RIGHT;
	}
	public boolean isFaceTop() {
		return faceDirection == FACE_TOP;
	}
	public boolean isFaceDown() {
		return faceDirection == FACE_DOWN;
	}
	public void setFaceLeft(){
		faceDirection = FACE_LEFT;
	}
	public void setFaceRight(){
		faceDirection = FACE_RIGHT;
	}
	public void setFaceTop(){
		faceDirection = FACE_TOP;
	}
	public void setFaceDown(){
		faceDirection = FACE_DOWN;
	}
	
	public void destroyBitmap() {
		objectBitmap = null;
	}
	
	public int getXDelta() {
		return xPos - prev_xpos;
	}
	public int getYDelta() {
		return yPos - prev_ypos;
	}
}
