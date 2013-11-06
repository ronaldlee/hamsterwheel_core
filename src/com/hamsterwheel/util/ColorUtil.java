package com.hamsterwheel.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class ColorUtil {

	public static Bitmap toGrayscale(Bitmap orig) {
		int width,height;
		height = orig.getHeight();
		width = orig.getWidth();
		
		Bitmap gray = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(gray);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(orig, 0, 0, paint);
		return gray;		
	}
}
