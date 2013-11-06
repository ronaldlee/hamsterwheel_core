package com.hamsterwheel.core;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public abstract class AI {
	
	protected GameObject host;
	
	protected int check_time_counter;
	
	public void setGameObject(GameObject gameObj) {
		this.host = gameObj;
	}

	//main think function happen all the time
	public void think() {
		doPreThink();
		if (isDead()) {
			doDead();
			return;
		}
		
		//see if is time to think and do stuff?!
		check_time_counter++;
		if (check_time_counter <= getCheckTimeFrameRate()) { 
			return; 
		}
		else {check_time_counter=0;}
		
		Calendar calendar = new GregorianCalendar();
		Date cur_time = new Date();
		calendar.setTime(cur_time);

		doThink();
		
		
		idleAction();
	}
	
	protected int getCheckTimeFrameRate() {
		return 120;
	}
	
	abstract protected void idleAction();
			
	//external stimulation
	abstract public void touched();
	
	abstract public void hitTop();
	abstract public void hitBottom();
	abstract public void hitLeft();
	abstract public void hitRight();

	//ai for checking basic status
	abstract public boolean isDead();
	
	
	//certain actions
	protected void doPreThink() {}
	abstract protected void doThink();
	abstract protected void doDead();
	
	abstract protected void resetFlags();
	abstract protected void resetCounters();
	//----

	
}

