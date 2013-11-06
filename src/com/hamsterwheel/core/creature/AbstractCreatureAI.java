package com.hamsterwheel.core.creature;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import com.hamsterwheel.core.AI;

public abstract class AbstractCreatureAI extends AI {
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
		
		doGrow(cur_time.getTime());

		doThink();
		
		
		idleAction();
	}

	//grow to after living for certain amount of time since created
	abstract public void doGrow(long cur_time);
	abstract public void eat(int food_id);
	
	abstract public boolean isHungry();	
	abstract public boolean isSick();
	
	protected boolean isSleepingTime(int hour_of_day,int minute) {
		if (hour_of_day >= 21 || hour_of_day <= 7) { //9pm - next day 7am
			return true;
		}

		return false;
	}
	
	protected boolean isNapTime(int hour_of_day,int minute) {
		if ((hour_of_day == 13) || //1-1:59pm
			(hour_of_day == 16)) {	//4-4:59pm		
			Random rand = new Random();
			int sleep_chance = rand.nextInt(100);
			if (sleep_chance < 90) {
				return true;
			}
		}
		
		return false;
	}
	
	protected boolean isMealTime(int hour_of_day,int minute) {
		if (((hour_of_day == 8 && minute>=5) || (hour_of_day == 9 && minute <=5)) || //8:05am-9:05am
			((hour_of_day == 11 && minute>=10) || (hour_of_day == 12 && minute<=10)) ||//11:10pm-12:10pm
			((hour_of_day == 19 && minute>=40) || (hour_of_day == 20 && minute<=40))//7:40pm-8:40pm	
			) {	 	
			Random rand = new Random();
			int meal_chance = rand.nextInt(10);
			if (meal_chance < 5) {
				return true;
			}
		}
		
		return false;
	}
	
	protected boolean isPooTime(int hour_of_day,int minute) {
		if ((hour_of_day == 10 && (minute >=30 && minute<=45)) ||   //10:30am-10:45pm
			(hour_of_day == 14 && (minute >=30 && minute<=45))      //2:30pm-2:45pm
			) {   		
			Random rand = new Random();
			int meal_chance = rand.nextInt(10);
			if (meal_chance < 5) {
				return true;
			}
		}
		
		return false;
	}
}
