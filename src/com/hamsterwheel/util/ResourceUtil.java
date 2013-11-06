package com.hamsterwheel.util;

import java.lang.reflect.Field;


public class ResourceUtil {

	//resource_class => R.raw.class
	public static final int getRID(Class resource_class, String id) {
		try {
			Field field = resource_class.getField(id);
			return field.getInt(resource_class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
}
