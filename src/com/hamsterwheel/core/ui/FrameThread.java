package com.hamsterwheel.core.ui;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class FrameThread extends Thread{
	
	private boolean isRunning;
	private SurfaceHolder surfaceHolder;
	private FrameProcessor fp;
	private long gameLoopDelay;
	private String name;
	private boolean is_debug;
	
	public FrameThread(SurfaceHolder surfaceHolder,FrameProcessor fp,long gameLoopDelay,String name,boolean is_debug) {
		this.surfaceHolder = surfaceHolder;
		this.fp = fp;
		this.gameLoopDelay = gameLoopDelay;
		this.name = name;
		this.is_debug = is_debug;
	}
	
	public FrameThread(SurfaceHolder surfaceHolder,FrameProcessor fp,long gameLoopDelay,String name) {
		this(surfaceHolder,fp,gameLoopDelay,name,false);
		
	}
	
	public void clear() {		
		setRunning(false);
		this.interrupt();
//		surfaceHolder = null;
//		fp = null;
	}
	
	@Override
    public void run() {	
        while (isRunning) {
        	if (is_debug) {
    Log.e("FFFF","framethread: " + name); 
        	}
//        	if (fp != null && fp.isDone()) {
        	if (fp.isDone()) {
        		isRunning = false;
        		return;
        	}
        	
            Canvas c = null;
            try {
//            	if (surfaceHolder==null) {
//            		isRunning = false;
//            		break;
//            	}
                c = surfaceHolder.lockCanvas();               	
                synchronized (surfaceHolder) {
                	if (c == null) {
                		isRunning = false;
                		break;
                	}
                	fp.processFrame(c);           
                }    
    			Thread.sleep(getGameLoopDelay());
            } catch (InterruptedException e) {
            	break;
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) { // && surfaceHolder != null) {
                	surfaceHolder.unlockCanvasAndPost(c);
                }                
            }
        }    
        isRunning = false;
        surfaceHolder=null;
        fp=null;
    }
	
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public void setGameLoopDelay(long gameLoopDelay) {
    	this.gameLoopDelay = gameLoopDelay;
    }
    public long getGameLoopDelay() {
    	return gameLoopDelay;
    }
    
}
