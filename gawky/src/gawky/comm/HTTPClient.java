package gawky.comm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class HTTPClient {
	public String sendGET(String url) {
		String result = "";
		return result;
	}
	
	public String sendPOST(String url) {
		String result = "";
		
		return result;
	}
	
	 public static void main(String[] args) throws Exception {
		upper();
	}
	
	public static  void upper() throws Exception {
		
		
		File input = new File( "dateiname" );
		PostMethod post = new PostMethod( "url" );
		post.setRequestBody( new FileInputStream(input) );
		if ( input.length() < Integer.MAX_VALUE )
		  post.setRequestContentLength((int)input.length());
		else
		  post.setRequestContentLength(EntityEnclosingMethod.CONTENT_LENGTH_CHUNKED);
		post.setRequestHeader( "Content-type", "text/xml; charset=ISO-8859–1" );
		
		HttpClient httpclient = new HttpClient();
		 
		httpclient.executeMethod( post );
		post.releaseConnection();

		 

		
		
		
	    // Create a trust manager that does not validate certificate chains
	    TrustManager[] trustAllCerts = new TrustManager[] 
	    {
	        new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(
	                java.security.cert.X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(
	                java.security.cert.X509Certificate[] certs, String authType) {
	            }
	        }
	    };
	    
	    // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
       
        
        HostnameVerifier hv = new HostnameVerifier()
        {
            public boolean verify(String urlHostName, SSLSession session)
            {
                System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                        + session.getPeerHost());
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(hv);

	    URL url = new URL("https://www.amazon.de/");
	    
	    // Now you can access an https URL without having the certificate in the truststore
	    
    
        // Send data
        URLConnection conn = url.openConnection();
        
        //POST
// 		  // Construct data
//        String data = URLEncoder.encode("key1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
//        data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");
//        conn.setDoOutput(true);
//        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//        wr.write(data);
//        wr.flush();
//        wr.close();
    
        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        String line;
        while ((line = rd.readLine()) != null) {
        	System.out.println(line);
        }
        
        rd.close();
	}
}
