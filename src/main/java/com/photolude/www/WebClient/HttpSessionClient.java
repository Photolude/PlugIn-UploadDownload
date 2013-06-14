package com.photolude.www.WebClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

/**
 * Defines a http session client, which can be used to communicate with the server
 * leveraging a specific user session.
 * 
 * @author Nikody Keating
 *
 */
public class HttpSessionClient implements IHttpSessionClient {
	private DefaultHttpClient client;
	private BasicCookieStore cookieStore = new BasicCookieStore();
	
	/**
	 * Creates an instance of a session based http client
	 * @param sessionToken the session token
	 * @param authentication the authentication token
	 * @param domain the domain to hook up to
	 */
	public HttpSessionClient(String sessionToken, String authentication, String domain)
	{
		HttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		
		Cookie[] cookies = new Cookie[(authentication == null)? 1 : 2];
		
		BasicClientCookie sessionCookie = new BasicClientCookie("ASP.NET_SessionId", sessionToken);
		sessionCookie.setDomain(domain);
		cookies[0] = sessionCookie;
		
		if(authentication != null)
		{
			BasicClientCookie authCookie = new BasicClientCookie(".ASPXAUTH", authentication);
			authCookie.setDomain(domain);
			cookies[1] = authCookie;
		}
		
		cookieStore.addCookies(cookies);
	}
	
	/**
	 * Downloads an image from the specified url to a destination directory
	 * @param source the source url
	 * @param destinationDirectory the destination to download to
	 * @return returns the file name which was downloaded based off of meta-data recieved from the server
	 */
	public String downloadImage(String source, String destinationDirectory)
	{
		initializeNewConnection();
		
		String fileName = null;
		HttpGet getAction = new HttpGet(source);
		
		HttpResponse response;
		try {
			response = client.execute(getAction);
			
			Header[] contentTypeHeader = response.getHeaders("Content-Type");
			String value = contentTypeHeader[0].getValue();
			int index = value.indexOf("text/plain");
			if(contentTypeHeader != null && index < 0)
			{
				//
				// Get the file name which should be saved as
				//
				Header[] header = response.getHeaders("Content-Disposition");
				
				if(header != null && header.length == 1)
				{
					fileName = header[0].getValue().split("=")[1];
				}
				
				//
				// Read the file to the disk
				//
				InputStream instream;
				
				instream = response.getEntity().getContent();
			
				OutputStream outstream = new FileOutputStream(destinationDirectory + "\\" + fileName);
				if(instream != null)
				{
					int count = 0;
					byte[] buffer = new byte[4067];
					
					do
					{
						count = instream.read(buffer);
						if(count > 0)
						{
							outstream.write(buffer, 0, count);
						}
					}while(count > 0);
				}
				outstream.close();
				instream.close();
			}
		} catch (ClientProtocolException e1) {
			fileName = null;
			e1.printStackTrace();
		} catch (IOException e1) {
			fileName = null;
			e1.printStackTrace();
		} catch (IllegalStateException e) {
			fileName = null;
			e.printStackTrace();
		}
		
		return fileName;
	}
	
	/**
	 * Performs an http post and attaches a file to it.
	 * @param uriPath the uri to post to
	 * @param filePath the path to the file to post
	 * @return the status code from the post
	 */
	public HttpResponse uploadFile(String uriPath, String filePath)
	{
		HttpResponse retval = null;
		initializeNewConnection();
		
		HttpPost httppost = new HttpPost(uriPath);
		
	    File file = new File(filePath);

	    MultipartEntity mpEntity = new MultipartEntity();
	    ContentBody cbFile = new FileBody(file);
	    
	    mpEntity.addPart("userfile", cbFile);

	    httppost.setEntity(mpEntity);
	    try {
	    	retval = this.client.execute(httppost);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
	    
	    return retval;
	}
	
	/**
	 * Gets the response from an http get
	 * @param url the url to get
	 * @return the response in text for
	 */
	public PlainTextResult httpGetPlainText(String url)
	{
		int statusCode = -1;
		
		initializeNewConnection();
		String retval = null;
		HttpGet request = new HttpGet(url);
		
		try {
			HttpResponse response = this.client.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
			if(response.getStatusLine().getStatusCode() == 200)
			{
				int character;
				
				InputStreamReader stream = new InputStreamReader(response.getEntity().getContent());
				StringBuilder output = new StringBuilder();
				do
				{
					character = stream.read();
					output.append((char)character);
				} while(character != -1);
				
				retval = output.toString();
				stream.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (new PlainTextResult()).setStatusCode(statusCode).setTextResponse(retval);
	}
	
	/**
	 * Posts the 
	 * @param url
	 * @param jsonObj
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public HttpResponse postJSON(String url, JSONObject jsonObj) throws ClientProtocolException, IOException
	{
		HttpResponse retval = null; 
		
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("json", jsonObj.toString()));
		
	    HttpPost httpPost = new HttpPost(url);
	    httpPost.setEntity(new UrlEncodedFormEntity(postParams, HTTP.UTF_8));
	    
	    this.initializeNewConnection();
	    
	    retval = this.client.execute(httpPost);
	    
	    return retval;
	}
	
	private void initializeNewConnection()
	{
		this.client = new DefaultHttpClient();
		this.client.setCookieStore(this.cookieStore);
	}
}
