package com.hamsterwheel.core.ui;

import java.util.HashMap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapMgr {

	private HashMap<String,Bitmap> bitmapHashMap;
	
	public Bitmap getBitmap(Context c, int sprite_res_id) {
		if (bitmapHashMap == null) {
			bitmapHashMap = new HashMap<String,Bitmap>();
		}
		
		Bitmap objectBitmap = null;
	
		if (sprite_res_id >= 0) {
			objectBitmap = bitmapHashMap.get(""+sprite_res_id);
			if (objectBitmap == null || objectBitmap.isRecycled()) {
			    Resources res = c.getResources();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//				options.inScaled=false;
//				options.inDither=false;
//				options.inDither=false;
				
				objectBitmap = BitmapFactory.decodeResource(res,sprite_res_id);
		        		
				bitmapHashMap.put(""+sprite_res_id, objectBitmap);
			}	
		}
		
		return objectBitmap;
	}
	
	public Bitmap getBitmapClone(Context c, int sprite_res_id) {	
		Bitmap objectBitmap = null;
	
	    Resources res = c.getResources();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		
		objectBitmap = BitmapFactory.decodeResource(res,sprite_res_id);
		        			
		return objectBitmap;
	}
	
}
