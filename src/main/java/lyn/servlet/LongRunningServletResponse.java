package lyn.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;  
   
@WebServlet("/LongRunningServletResponse")  
public class LongRunningServletResponse extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
   
    protected void doGet(HttpServletRequest request,  
            HttpServletResponse response) throws ServletException, IOException {  
    	
        doPost(request, response);
    }  
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	long startTime = System.currentTimeMillis();  
        System.out.println("LongRunningServlet Start::Name="  
                + Thread.currentThread().getName() + "::ID="  
                + Thread.currentThread().getId());  
   
     
        int secs = 5000;  
        longProcessing(secs);  
        
        PrintWriter out = response.getWriter();  
        long endTime = System.currentTimeMillis(); 
        
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        System.out.println("当前时间：" + sdf.format(d));
        out.write("Processing done for " + sdf.format(d) + " milliseconds!!");  
        System.out.println("LongRunningServlet Start::Name="  
                + Thread.currentThread().getName() + "::ID="  
                + Thread.currentThread().getId() + "::Time Taken="  
                + (endTime - startTime) + " ms.");  
        System.out.println("----------------------------------------------------");
    }
    
   
    private void longProcessing(int secs) {  
        // wait for given time before finishing  
        try {  
            Thread.sleep(secs);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
    
    public static void main(String[] args) {
		/*for (int i = 0; i<20; i++) {
			String rs = HttpClientUtils.doGet("http://192.168.0.212:8080/up-base/LongRunningServletResponse");
			System.out.println(rs);
		}*/
    	/*String rs = HttpClientUtils.doPost("http://192.168.0.212:8080/up-base/LongRunningServletResponse","\"id\":\"id\"");
		System.out.println(rs);*/
    	
    	/*new Thread(new Runnable() {
			public void run() {
				
			}
		});
		 */
    	CloseableHttpClient httpclient = HttpClientBuilder.create().setMaxConnPerRoute(20).build();
    	long s = System.currentTimeMillis(); 
    	for (int i = 0; i<20; i++) {
    		int f =i;
    		new Thread(new Runnable() {
    			public void run() {
    				HttpPost httpPost = new HttpPost("http://192.168.0.212:8080/up-base/LongRunningServletResponse");
    				RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000000).setConnectTimeout(20000000).setSocketTimeout(2000000).build();
    				httpPost.setConfig(requestConfig);
    				httpPost.setHeader("Accept","application/json");
    				httpPost.setHeader("Content-Type", "application/json");
    				StringEntity entity = new StringEntity("","UTF-8");
    				httpPost.setEntity(entity);
    				CloseableHttpResponse response = null;
    				
    				try {
    					response = httpclient.execute(httpPost);
    					StatusLine status = response.getStatusLine();
    					int state = status.getStatusCode();
    					if(state==HttpStatus.SC_OK) {
    						//HttpEntity responseEntity = response.getEntity();
    						//return EntityUtils.toString(responseEntity);
    						//System.out.println(rs);
    						 long e = System.currentTimeMillis(); 
    						 System.out.println("i:"+f+";"+(e-s));
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
    			}
    		}).start();
			//String rs = HttpClientUtils.doPost("http://192.168.0.212:8080/up-base/LongRunningServletResponse","");
			
			
			
			
			
		}
	}
   
}  