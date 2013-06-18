package com.photolude.www.WebClient;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

/**
 * Defines a http session client interface
 * 
 * @author Nikody Keating
 *
 */
public interface IHttpSessionClient {
	
	public IHttpSessionClient setSessionCookies(String domain, String sessionToken, String authentication);
	
	/**
	 * downloads an image to the destination directory
	 * @param source the http source url
	 * @param destinationDirectory the destination directory to return to
	 * @return 
	 */
	public String downloadImage(String source, String destinationDirectory);
	
	/**
	 * Performs an http post and attaches a file to it.
	 * @param uriPath the uri to post to
	 * @param filePath the path to the file to post
	 * @return the status code from the post
	 */
	public HttpResponse uploadFile(String uriPath, String filePath);
	
	/**
	 * Gets the response from an http get
	 * @param url the url to get
	 * @return the response in text for
	 */
	public PlainTextResult httpGetPlainText(String url);
	
	/**
	 * Posts the 
	 * @param url
	 * @param jsonObj
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse postJSON(String url, JSONObject jsonObj) throws ClientProtocolException, IOException;
}
