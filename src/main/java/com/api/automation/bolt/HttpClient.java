package com.api.automation.bolt;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import com.api.automation.util.VerifyValueAPICommon;
import com.automation.bolt.common;

 public class HttpClient {

	public static CloseableHttpResponse httpCloseableResponse;	
	public static String AuthVal1;
	public static String AuthVal2;

	public static String trustStoreFilePath;
    public static String trustStorePwd;
    public static String keyStoreFilePath;
    public static String keyStorePwd;

    public static String runTimeError;
    public static HashMap<Integer, Object> jsonMap;

    //--Get method without header
    public CloseableHttpResponse getClientResponse(Object url, Object sslFlag, Object basicAuthFlag, Object getBasicAuthFlag) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    	CloseableHttpClient httpClient = getHttpBuilder(sslFlag, basicAuthFlag);
    	try{
    	   HttpGet httpget = new HttpGet((String) url);
    	   try {
    	      httpCloseableResponse = httpClient.execute(httpget);
    	   } catch(IOException e) {}
    	}catch(IllegalArgumentException e){}
    	
    	return httpCloseableResponse;
    }

    //--Get method with headers
    public CloseableHttpResponse getClientResponse(Object url, LinkedHashMap < Object, Object > headerMap, Object sslFlag, Object basicAuthFlag) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		try {

			CloseableHttpClient httpClient = getHttpBuilder(sslFlag, basicAuthFlag);
			HttpGet httpget = new HttpGet((String) url);

			for (Map.Entry<Object, Object> headerEntry : headerMap.entrySet()) {
				httpget.addHeader((String) headerEntry.getKey(), (String) headerEntry.getValue());
			}

			httpCloseableResponse = httpClient.execute(httpget);
		} catch(ClientProtocolException |
				NullPointerException |
				IllegalArgumentException |
				ArrayIndexOutOfBoundsException exp) {
			System.out.println(exp.getMessage());
		} catch(IOException ex) {
			System.out.println(ex.toString());
			VerifyValueAPICommon.verifyErrorMessage(ex.toString(), ex.getClass().getName());
		}
			return httpCloseableResponse;
    }
    
  //--DELETE method
    public CloseableHttpResponse deleteClientResponse(Object url, LinkedHashMap < Object, Object > headerMap, Object sslFlag, Object basicAuthFlag) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
     
	    try {
	        CloseableHttpClient httpClient = getHttpBuilder(sslFlag, basicAuthFlag);
	        HttpDelete httpdelete = new HttpDelete((String) url);
	     
	        try{
	           for (Map.Entry < Object, Object > headerEntry: headerMap.entrySet()) {
	              httpdelete.addHeader((String) headerEntry.getKey(), (String) headerEntry.getValue());
	           }
	        }catch (NullPointerException exp){}
	     
	        httpCloseableResponse = httpClient.execute(httpdelete);
	     
	    } catch(ClientProtocolException | NullPointerException | IllegalArgumentException exp) {
	        System.out.println(exp.getMessage());
	        VerifyValueAPICommon.verifyErrorMessage(exp.toString(), exp.getClass().getName());
	    } catch(IOException ex) {
	        System.out.println(ex.toString());
	        VerifyValueAPICommon.verifyErrorMessage(ex.toString(), ex.getClass().getName());
	    }
	    return httpCloseableResponse;
    }

    //--POST method
    public CloseableHttpResponse postClientRequest(Object url, String entityString, LinkedHashMap<Object, Object> headerMap, Object sslFlag, Object basicAuthFlag) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    	CloseableHttpClient httpClient =null;
    	HttpPost httppost =null; //post

		try {
			httpClient = getHttpBuilder(sslFlag, basicAuthFlag);
	    	httppost = new HttpPost((String) url); //post

			httppost.setEntity(new StringEntity(entityString));
		} catch(UnsupportedEncodingException | ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		} //pay load

		try{
			if(headerMap.size() !=0){
				//headers
				for (Map.Entry<Object, Object> headerEntry: headerMap.entrySet()) {
					httppost.addHeader((String) headerEntry.getKey(), (String) headerEntry.getValue());
				}
			}
		}catch (NullPointerException exp){}

		try {
			httpCloseableResponse = httpClient.execute(httppost);
		} catch(IOException | NullPointerException e) {		
			System.out.println(e.toString());
			//runTimeError =e.toString();
			VerifyValueAPICommon.verifyErrorMessage(e.toString(), e.getClass().getName());
		}

		return httpCloseableResponse;
    }

    //--POST method without payload
    public CloseableHttpResponse postClientRequest(Object url, LinkedHashMap<Object, Object> headerMap, Object sslFlag, Object basicAuthFlag) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    	CloseableHttpClient httpClient = getHttpBuilder(sslFlag, basicAuthFlag);
    	HttpPost httppost = new HttpPost((String) url); //post
    	
    	if(headerMap !=null) {
    		if(headerMap.size() !=0){
    	        //headers
    	        for (Map.Entry<Object, Object> headerEntry: headerMap.entrySet()) {
    	        	httppost.addHeader((String) headerEntry.getKey(), (String) headerEntry.getValue());
    	        }
            }
    	}
        
        try {
        	httpCloseableResponse = httpClient.execute(httppost);
        } catch(IOException e) {
	        System.out.println(e.toString());
	        VerifyValueAPICommon.verifyErrorMessage(e.toString(), e.getClass().getName());
        }

        return httpCloseableResponse;
    }

    //--PUT method with payload
    public CloseableHttpResponse putClientRequest(Object url, String entityString, LinkedHashMap<Object, Object> headerMap, Object sslFlag, Object basicAuthFlag) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

	    CloseableHttpClient httpClient =null;
	    HttpPut httpput =null; //put

        try {
			httpClient = getHttpBuilder(sslFlag, basicAuthFlag);
			httpput = new HttpPut((String) url); //put
			httpput.setEntity(new StringEntity(entityString));

			if(headerMap !=null) {
				if(headerMap.size() !=0){
					//headers
					for (Map.Entry<Object, Object> headerEntry: headerMap.entrySet()) {
						httpput.addHeader((String) headerEntry.getKey(), (String) headerEntry.getValue());
					}
				}
			}
        } catch(UnsupportedEncodingException |
				IllegalArgumentException |
				NullPointerException e) {
        	System.out.println(e.getMessage());
        }//pay load

        try {
        	httpCloseableResponse = httpClient.execute(httpput);
        } catch(IOException | IllegalArgumentException e) {
        	System.out.println(e.toString());
        	VerifyValueAPICommon.verifyErrorMessage(e.toString(), e.getClass().getName());
        }

        return httpCloseableResponse;
    }

    //--PUT method without payload
    public CloseableHttpResponse putClientRequest(Object url, LinkedHashMap<Object, Object> headerMap, Object sslFlag, Object basicAuthFlag) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    	CloseableHttpClient httpClient = getHttpBuilder(sslFlag, basicAuthFlag);
    	HttpPut httpput = new HttpPut((String) url); //put

        if(headerMap.size() !=0){
	        //headers
	        for (Map.Entry<Object, Object> headerEntry: headerMap.entrySet()) {
	        	httpput.addHeader((String) headerEntry.getKey(), (String) headerEntry.getValue());
	        }
        }

        try {
        	httpCloseableResponse = httpClient.execute(httpput);
        } catch(IOException e) {
	        System.out.println(e.toString());
	        VerifyValueAPICommon.verifyErrorMessage(e.toString(), e.getClass().getName());
        }

        return httpCloseableResponse;
    }

    public static CloseableHttpClient getHttpBuilder(Object sslFlag, Object basicAuthFlag) {
	    int timeout = 60;
	    InputStream trustStoreStream =null;
	    KeyStore trustStore =null;
	    TrustManagerFactory trustManagerFactory =null;
	    String keyStoreType =null;
	    InputStream identityStream =null;
	    KeyStore identity =null;
	    SSLContext sslContext =null;
	    KeyManagerFactory keyManagerFactory =null;
	    CloseableHttpClient httpClient =null;
	    boolean validKeyStore =true;
	    CredentialsProvider provider = new BasicCredentialsProvider();

	    if(sslFlag !=null && !sslFlag.toString().isEmpty()){
	    	jsonMap =common.uploadSSLCertConfiguration();
	    	String getSSLName =null;
	    	
	        for (Map.Entry<Integer,Object> entry : jsonMap.entrySet()){
	            try {            	
	            	getSSLName =entry.getValue().toString().split(",")[0];
		            keyStoreFilePath =entry.getValue().toString().split(",")[1];
		            keyStorePwd =entry.getValue().toString().split(",")[2];
		            trustStoreFilePath =entry.getValue().toString().split(",")[3];
		            trustStorePwd =entry.getValue().toString().split(",")[4];
	            }catch(ArrayIndexOutOfBoundsException | NullPointerException exp) {}
	        	
	            if(getSSLName.contentEquals(sslFlag.toString()))
	            	break;
	        }

	        if(!trustStoreFilePath.isEmpty() && !trustStorePwd.isEmpty()){
	            keyStoreType = KeyStore.getDefaultType();
	            Path trustStorePath = Paths.get(trustStoreFilePath);

                try {
	                trustStoreStream = Files.newInputStream(trustStorePath, StandardOpenOption.READ);
	                trustStore = KeyStore.getInstance(keyStoreType);
	                trustStore.load(trustStoreStream, trustStorePwd.toCharArray());
	                String trustManagerFactoryAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
	                trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm);
	                trustManagerFactory.init(trustStore);
	                trustStoreStream.close();

	                } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {}
	        }

	        if(!keyStoreFilePath.isEmpty() && !keyStorePwd.isEmpty()){
	
	            keyStoreType = KeyStore.getDefaultType();
	            Path identityPath = Paths.get(keyStoreFilePath);

                try {

                    identityStream = Files.newInputStream(identityPath, StandardOpenOption.READ);
                    identity = KeyStore.getInstance(keyStoreType);
                    identity.load(identityStream, keyStorePwd.toCharArray());
                    String keyManagerFactoryAlgorithm = KeyManagerFactory.getDefaultAlgorithm();

                    keyManagerFactory = KeyManagerFactory.getInstance(keyManagerFactoryAlgorithm);
                    keyManagerFactory.init(identity, keyStorePwd.toCharArray());
                    identityStream.close();

                    sslContext = SSLContext.getInstance("TLS");

                    if(trustManagerFactory ==null){
                    	sslContext.init(keyManagerFactory.getKeyManagers(),
										null,
										null);
                    }else{
                    	sslContext.init(keyManagerFactory.getKeyManagers(),
						                trustManagerFactory.getTrustManagers(),
						                null);
                    }

                } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyManagementException e) {
                	validKeyStore =false;
                }

            }

        }else
        	validKeyStore =false;
	        
	    if(basicAuthFlag !=null && !basicAuthFlag.toString().isEmpty()){
			AuthVal1 =(String) API_TestRunner.getAuth1;
			if(AuthVal1 ==null)
				AuthVal1 ="";
			
				AuthVal2 =(String) API_TestRunner.getAuth2;
                if(AuthVal2 ==null)
                	AuthVal2 ="";
                	
                if(basicAuthFlag.toString().contentEquals("Basic Auth")){
	                // user authentication
	                if(!AuthVal1.isEmpty() && !AuthVal2.isEmpty()){
	                	provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(AuthVal1, AuthVal2));
	                }
                }
	    }

	    // request time-out
	    RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
	    if(validKeyStore ==true){
	    	httpClient =HttpClients.custom()
			.setSSLContext(sslContext)
			.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
			.setDefaultRequestConfig(config)
			.setDefaultCredentialsProvider(provider)
			//.setSSLSocketFactory(sslConnectionSocketFactory)
			.build();
	    }else{
	    	try {
	    		httpClient =HttpClients.custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setDefaultRequestConfig(config)
                .setDefaultCredentialsProvider(provider)
                //.setSSLSocketFactory(sslConnectionSocketFactory)
                .build();
	    	} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
	    		System.err.println(e.getMessage());
	    	}
	    }
	    
	    return httpClient;
	}
}