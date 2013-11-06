package com.hamsterwheel.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class DataStore {
	
	public static final Properties loadResources(Context c, int resourceId) {
		InputStream rawResource = null;
		try {
			Resources r = c.getResources();
			rawResource = r.openRawResource(resourceId);
			Properties properties = new Properties();
			properties.load(rawResource);
			
			return properties;
		} catch (Exception e) {
			Log.e("Datastore","error reading prop file: " + e,e);
		} finally {
			if (rawResource != null) {
				try {
					rawResource.close();
				} catch (Exception e) {}
			}
		}  	
		
		return null;
	}
	
	public static final Properties loadAppProperties(Context context, String filename) {
		FileInputStream fis = null;
		try {
			fis = context.openFileInput(filename);
			Properties p = new Properties();
			p.load(fis);
			
			return p;
		} catch (Exception e) {
			Log.e("Datastore","error reading prop file: " + e,e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {}
			}
		}  	
		
		return null;
	}
	
	public static final String loadAppProperty(Context context, String property_key, String filename) {
		Properties p = DataStore.loadAppProperties(context,filename);
		
		if (p == null) {
			return null;
		}
		
		Set keyObjs = p.keySet();
		for (Object keyObj:keyObjs) {
			String key = (String)keyObj;
			
			if (key.equals(property_key)) {
				return p.get(property_key).toString();
			}
		}
		
		return null;
	}
	
	public static final boolean overwriteAppProperties(Context context, PropertyKeyValue[] properties, String filename) {
		Properties p = new Properties();
		
		FileOutputStream fos = null;
		try {		
			fos = context.openFileOutput(filename, 
					Context.MODE_PRIVATE);
			
			if (properties != null) {
				for (PropertyKeyValue pky:properties) {
					p.setProperty(pky.getKey(), pky.getValue());
				}
			}
					
			p.store(fos, "");
			return true;
		} catch (Exception e) {
			Log.e("Datastore","error override/create prop file: " + e,e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception foe) {}
			}
		}
		return false;
	}
	
	public static final void updateAppProperties(Context context,PropertyKeyValue[] properties, String filename) {
		if (properties == null || properties.length == 0) return;
		
		FileInputStream fis = null;
		
		Properties p = new Properties();
		boolean isLoadOk = true;
		try {		
			fis = context.openFileInput(filename);
			p.load(fis);
		} catch (Throwable e) {
			isLoadOk = false;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception fe) {}
			}
		}
		
		if (isLoadOk) {
			FileOutputStream fos = null;
			try {		
				fos = context.openFileOutput(filename, 
						Context.MODE_PRIVATE);
				
				for (PropertyKeyValue pky:properties) {
					p.setProperty(pky.getKey(), pky.getValue());
				}
						
				p.store(fos, "");
			} catch (Exception e) {
				
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (Exception foe) {}
				}
			}
		}
	}
	
	public static final boolean updateAppProperties(Context context, Properties p, String filename) {
//Log.e("","COINSS: updateAppProp");		
		if (p == null || p.isEmpty()) {
			Log.e("","COINSS error1");
			return false;
		}
		
		FileOutputStream fos = null;
		try {		
			fos = context.openFileOutput(filename, 
					Context.MODE_PRIVATE);
						
//Log.e("","COINSS: updateAppProp store..: " + p.toString());
			p.store(fos, "");
			return true;
		} catch (Exception e) {
			Log.e("","COINSS error3",e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception foe) {}
			}
		}
		
		return false;
	}
	
	public static final boolean updateAppProperty(Context context,PropertyKeyValue property, String filename) {
		return updateAppProperty(context,property.getKey(),property.getValue(),filename);
	}
	
	public static final boolean updateAppProperty(Context context,String key,String value, String filename) {
//Log.e("","COINSS: updateAppProperty key: " + key + "; value: "+value);		
		if (key == null || value == null) return false;
		
		Properties p = loadAppProperties(context, filename);
		
		if (p != null) {
			p.put(key,value);
			
			return updateAppProperties(context,p,filename);
		}
		
		return false;
	}
}
