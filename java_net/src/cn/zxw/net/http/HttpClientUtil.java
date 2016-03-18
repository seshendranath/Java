package cn.zxw.net.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientUtil {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		System.out.println("取得数据：");
		System.out.println(getRequest("http://localhost:39999/cloudform/posdata/day/2015?p=1"));
		
	}
	public static String getRequest(String url) throws ClientProtocolException, IOException{
		// 创建HttpClient实例
		HttpClient httpclient = new DefaultHttpClient();
		// 创建Get方法实例
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String res =null;
		if (entity != null) {
			InputStream instream = entity.getContent();
			res = IOUtils.toString(instream);
			// Do not need the rest
			httpget.abort();
		}
		return res;
	}
	

	
}
