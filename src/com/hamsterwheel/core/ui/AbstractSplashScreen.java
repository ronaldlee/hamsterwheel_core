package com.hamsterwheel.core.ui;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;

public abstract class AbstractSplashScreen extends Activity {
	protected boolean _active = true;
	protected int _splashTime = 5000;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        Thread splashTread = new SplashThread(this);
        splashTread.start();
    }
	
	//R.layout.splash
	protected abstract int getLayoutResID();
	//Main.class
	protected abstract Class getMainClass();
	
	class SplashThread extends Thread {
		private WeakReference<AbstractSplashScreen> contextRef;
		
		SplashThread(AbstractSplashScreen context) {
			this.contextRef = new WeakReference<AbstractSplashScreen>(context);
		}
		
		 @Override
         public void run() {
             try {
                 int waited = 0;
                 while(_active && (waited < _splashTime)) {
                     sleep(100);
                     if(_active) {
                         waited += 100;
                     }
                 }
             } catch(InterruptedException e) {
                 // do nothing
             } finally {
                 finish();
                 Intent data = new Intent(contextRef.get(),  getMainClass());
                 startActivity(data);
                 stop();
             }
         }
	}
}
