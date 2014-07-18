package com.bustiblelemons.json;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BasicJsonReader {
	private final static int SERVER_DELAY = 15000;
	private static HttpClient lastHttpClinet = null;
	private static HttpClient defaultHttpClient = null;
	
	
	public static HttpClient getLastHttpClient(){
		return lastHttpClinet;
	}
	
	public static void setDefaultHttpClient(HttpClient client){
		defaultHttpClient = client;
	}

	public static JSONObject getJsonFrom(String url) throws JSONException {
		InputStream is = getJsonStream(url);
		if(is == null)
			throw new JSONException("Input Stream Error");
		String raw_json_string = convertStreamToString(is);
		return new JSONObject(raw_json_string);
	}
	public static JSONArray getJsonArrayFrom(String url) throws JSONException {
		InputStream is = getJsonStream(url);
		if(is == null)
			throw new JSONException("Input Stream Error");
		String raw_json_string = convertStreamToString(is);
		return new JSONArray(raw_json_string);
	}
	
	public static JSONObject getJSONFromUrlWithPost(String url,
			HashMap<String, String> post) throws JSONException{
		return new JSONObject(getStringFromUrlWithPost(url,post));
	}
	public static JSONObject getJSONFromUrlWithMultipartPost(String url,
			HashMap<String, String> postData, HashMap<String,byte[]> addictional_data)
					throws JSONException {
		return new JSONObject(getStringFromUrlWithMultipartPost(url,postData,addictional_data));
	}
	public static JSONArray getJSONArrayFromUrlWithPost(String url,
			HashMap<String, String> post) throws JSONException{
		return new JSONArray(getStringFromUrlWithPost(url,post));
	}
	public static JSONArray getJSONArrayFromUrlWithMultipartPost(String url,
			HashMap<String, String> postData, HashMap<String,byte[]> addictional_data)
					throws JSONException {
		return new JSONArray(getStringFromUrlWithMultipartPost(url,postData,addictional_data));
	}

	public static String getStringFromUrlWithPost(String url,
			HashMap<String, String> post) throws JSONException {
		InputStream is = getJsonStreamWithPost(url, post);
		if(is==null)
			throw new JSONException("Inpout Stream is Null");
		return convertStreamToString(is);
		
	}
	public static String getStringFromUrlWithMultipartPost(String url,
		HashMap<String, String> postData, HashMap<String,byte[]> addictional_data)
				throws JSONException {
		InputStream is = getJsonStreamWithMultipartPost(url, postData,addictional_data);
		if(is==null)
			throw new JSONException("Inpouty Stream is Null");
		return convertStreamToString(is);
	}
	
	public static HttpClient createAndroidHttpClient(){
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				HTTP.UTF_8);
		params.setParameter(CoreProtocolPNames.USER_AGENT,
				"Apache-HttpClient/Android");
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, SERVER_DELAY);
		params.setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK,
				false);
		return new DefaultHttpClient(params);
	}
	
	protected static HttpClient getHttpClient(HttpClient def){
		HttpClient client = null;
		if(def==null){
			client =  createAndroidHttpClient();
		}
		else{
			client = new DefaultHttpClient(def.getParams());
			DefaultHttpClient tmp = (DefaultHttpClient)def;
			((DefaultHttpClient)client).setCookieStore(tmp.getCookieStore());
		}
		lastHttpClinet = client;
		return client;
	}

	private static InputStream getJsonStreamWithPost(String url,
			HashMap<String, String> post) {
		InputStream is = null;
		try {
			HttpPost httppost = new HttpPost(url);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			for (String key : post.keySet())
				nameValuePairs.add(new BasicNameValuePair(key, post.get(key)));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

			HttpResponse response = null;

			response = getHttpClient(defaultHttpClient).execute(httppost);
			is = response.getEntity().getContent();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}
	private static InputStream getJsonStreamWithMultipartPost(String url,
			HashMap<String, String> postData,HashMap<String, byte[]> addictional_data) {
		InputStream is = null;
		try {
			
			HttpPost httppost = new HttpPost(url);
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			for (String key : postData.keySet())
				
				reqEntity.addPart(key, new StringBody( postData.get(key),"text/plain",
		                Charset.forName("UTF-8")));
			for (String key : addictional_data.keySet()){
				try{
				ByteArrayBody bab = new ByteArrayBody(addictional_data.get(key), key);
		        reqEntity.addPart(key, bab);
				}catch(Exception e){
					 e.printStackTrace();
				}
			}
			httppost.setEntity(reqEntity);
			HttpResponse response = getHttpClient(defaultHttpClient).execute(httppost);
			
//
			is = response.getEntity().getContent();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}

	public static InputStream getJsonStream(String url) {
		InputStream is = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			
			// Execute HTTP Get Request
			HttpResponse response = getHttpClient(defaultHttpClient).execute(httpGet);
			is = response.getEntity().getContent();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return is;
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"),
					8192);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		StringBuilder string_builder = new StringBuilder(8192);

		String tmp = "";
		try {
			while ((tmp = reader.readLine()) != null)
				string_builder.append(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return string_builder.toString();
	}
	
	
	
}
