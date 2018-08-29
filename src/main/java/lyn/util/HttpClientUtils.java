package lyn.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {

	public static String doGet(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(httpGet);
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				return EntityUtils.toString(responseEntity);
			}
		} catch (IOException e) {
		    e.printStackTrace();
		}finally {
		    try {
		    	httpclient.close();//释放资源
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		return null;
	}
	
	/**
	 * post请求
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static String doPost(String url, String json) {

		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000000).setConnectTimeout(20000000).setSocketTimeout(2000000).build();
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-Type", "application/json");
		StringEntity entity = new StringEntity(json,"UTF-8");
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;
		
		try {
			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			if(state==HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				return EntityUtils.toString(responseEntity);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(null!=response) {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
