package com.nduoa.synsdk;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SynPayment {
	
	private static final String HOST_API = "http://market.nduoa.com/synpayment";
	
	public static String commit(Context context, double money, String type, long time, String channel){
		String _money = String.format("%.2f", money);
		String _time = "" + time;
		return commit(context, _money, type, _time, channel);
	}
	
	/*同步交易记录到N多服务器中，返回错误信息，成功返回null
	 *@money: 2.50 注意是半角
	 *@type: rmb, dollar, nt,,, and so on.
	 *@time: milli second from 1970.
	 *@channel: this string comes from aidl.
	 * */
	public static String commit(Context context, String money, String type, String time, String channel){
		HttpClient httpClient = new DefaultHttpClient();
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 30*1000);
		HttpConnectionParams.setSoTimeout(params, 30*1000);
		HttpClientParams.setRedirecting(params, true);
		
		doProxy(context, httpClient);
		String result = null;
		try {
			HttpPost httpPost = new HttpPost(HOST_API);
			
			JSONObject keyJSON = new JSONObject();
			keyJSON.put("packageName", context.getPackageName());
			keyJSON.put("imei", getIMEI(context));
			keyJSON.put("money", money);
			keyJSON.put("type", type);
			keyJSON.put("time", time);
			keyJSON.put("channel", channel);
			keyJSON.put("key", getKey(context, money, type, time, channel));
			
			String newReq = UrlEncode(keyJSON.toString());
			ByteArrayEntity reqEntity = new ByteArrayEntity(
					("code=" + newReq).getBytes("utf-8"));
			reqEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(reqEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int httpCode = httpResponse.getStatusLine().getStatusCode();
			if (httpCode != HttpURLConnection.HTTP_OK) {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					InputStream is = entity.getContent();
					result = readFromStream(is);
					is.close();
				}else{
					result = "unkown error";
				}
				httpPost.abort();
			} else {
				httpPost.abort();
				result = null;
			}
		} catch (Exception e) {
			if(e != null)
				result = e.getClass().getName() + " : " + e.getMessage();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}
	
	private static String readFromStream(InputStream stream) {
		ArrayByteBlock block = new ArrayByteBlock();
		byte[] bytes = new byte[2048];
		while (true) {
			int len;
			try {
				len = stream.read(bytes);
				if (len == -1)
					break;
				block.addByteBlock(bytes, 0, len);
			} catch (IOException e) {
				return null;
			}
		}
		return block.toStr();
	}
	
	private static void doProxy(Context context, HttpClient ahttpClient) {
		String type = getConnectType(context);
		if ("wifi".equals(type)) {
			ahttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					null);
		} else if ("mobile".equals(type)) {
			String mProxyAddr = getProxyAddr(context);
			if (null != mProxyAddr && mProxyAddr.trim().length() > 0) {
				HttpHost proxy = new HttpHost(mProxyAddr, 80);
				ahttpClient.getParams().setParameter(
						ConnRoutePNames.DEFAULT_PROXY, proxy);
			}
		} else {
			ahttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					null);
		}
	}
	
	private static String getConnectType(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = null;
		State wifi = null;
		NetworkInfo info = cm.getNetworkInfo(0);
		NetworkInfo info1 = cm.getNetworkInfo(1);
		if (info != null) {
			mobile = info.getState();
		}

		if (info1 != null) {
			wifi = info1.getState();
		}
		String strResult = "";
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			strResult = "wifi";
		} else if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
			strResult = "mobile";
		}
		return strResult;
	}

	private static String getProxyAddr(Context context) {
		String connectType = getConnectType(context);
		if (!"wifi".equals(connectType)) {
			ConnectivityManager connMgr = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo _info = connMgr.getActiveNetworkInfo();
			if (_info != null) {
				try {
					String apn = _info.getExtraInfo();
					if (apn != null) {
						Cursor c = context.getContentResolver().query(
								ReflectionUtils.getCarriersContentUri(), null,
								null, null, null);

						if (c.moveToFirst()) {
							do {
								int apnIdx = c.getColumnIndex(ReflectionUtils
										.getCarriersApn());
								int proxyIdx = c.getColumnIndex(ReflectionUtils
										.getCarriersProxy());
								String apnName = c.getString(apnIdx)
										.toUpperCase();
								String proxy = c.getString(proxyIdx);
								if (apnName.contains(apn.toUpperCase())) {
									return proxy;
								}
							} while (c.moveToNext());
						}
					}
				} catch (Exception e) {
					return null;
				}
			}

			Cursor cursor = context.getContentResolver().query(
					Uri.parse("content://telephony/carriers/preferapn"), null,
					null, null, null);
			if (cursor.moveToFirst()) {
				try {
					int proxyIdx = cursor.getColumnIndex(ReflectionUtils
							.getCarriersProxy());
					String proxyAddr = cursor.getString(proxyIdx);
					return proxyAddr;
				} catch (Exception e) {
					return null;
				}
			}
		}
		return null;
	}

	private static String UrlEncode(String s) {
		StringBuilder buf = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); ++i) {
			char ch = s.charAt(i);
			if (ch == '+') {
				buf.append("%2B");
			} else if (ch == '&') {
				buf.append("%26");
			} else if(ch == '&'){
				buf.append("%24");
			}else if(ch == '%'){
				buf.append("%25");
			}
			else
				buf.append(ch);
		}
		return buf.toString();
	}
	
	private static String getKey(Context context, String money, String type, String time, String channel){
	
		String packageName = context.getPackageName();
		String compositeStr = channel + packageName + getIMEI(context) + money + type + time + channel;
		try {
			byte[] output = MessageDigest.getInstance("MD5").digest(compositeStr.getBytes());
			return new String(output);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "00000000000000000000000000000000";
	}
	
	private static String getIMEI(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		if(isEmpty(imei)){
			imei = "0000000000000000";
		}
		return imei;
	}
	
	private static boolean isEmpty(String _value){
		boolean f = false;
		if(_value == null || _value.trim().length()<=0){
			f = true;
		}
		return f;
	}
}
