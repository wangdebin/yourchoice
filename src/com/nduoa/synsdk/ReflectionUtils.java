package com.nduoa.synsdk;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.net.Uri;


public class ReflectionUtils {

	public static Uri getCarriersContentUri() throws ClassNotFoundException, SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, InstantiationException{
		return (Uri)getStaticField("android.provider.Telephony$Carriers", "CONTENT_URI");
	}
	
	public static String getCarriersApn() throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException, InstantiationException{
		return (String) getStaticField("android.provider.Telephony$Carriers", "APN");
	}
	
	public static String getCarriersProxy() throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException, InstantiationException{
		return (String) getStaticField("android.provider.Telephony$Carriers", "PROXY");
	}
	
	private static Object getStaticField(String clsName, String field) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException{
		Class<?> cls = Class.forName(clsName);
		Field fd = cls.getDeclaredField(field);
		return fd.get(null);
	}
	
	
	//FileUtils.setPermissions(strApkPath, FileUtils.S_IRWXU
//	| FileUtils.S_IRWXO | FileUtils.S_IRWXG, -1, -1);
	
	public static void setFilePermissionsAllRWX(String filePath){
		try {
			Class<?> cls = Class.forName("android.os.FileUtils");
			Object obj = cls.newInstance();
			Field uRwx = cls.getDeclaredField("S_IRWXU");
			Field oRwx = cls.getDeclaredField("S_IRWXO");
			Field gRwx = cls.getDeclaredField("S_IRWXG");
			
			int mode = uRwx.getInt(obj) | oRwx.getInt(obj) | gRwx.getInt(obj);
			Method setPerMtd = cls.getDeclaredMethod("setPermissions", String.class, int.class, int.class, int.class);
			setPerMtd.invoke(null, filePath, mode, -1, -1);
		} catch (Exception e) {
		}
		
	}
	
	//android.os.SystemProperties.get("ro.serialno");
	public static String getAndroidID(){
		try{
			Class<?> cls = Class.forName("android.os.SystemProperties");
			Method getMtd = cls.getDeclaredMethod("get", String.class);
			return (String) getMtd.invoke(null, "ro.serialno");
		}catch(Exception e){
			return "00000000";
		}
	}
	
}
