package com.photolude.UploadSystem.BusinessLogic.FileUploading.UnitTests.MockingObjects;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.photolude.www.WebClient.IHttpSessionClient;
import com.photolude.www.WebClient.PlainTextResult;

public class TestHttpClient implements IHttpSessionClient {
	private String domain = null;
	public String getDomain(){return this.domain;}
	public void setDomain(String domain){this.domain = domain;}
	
	private String sessionToken;
	public String getSessionToken() { return this.sessionToken; }
	public void setSessionToken(String value) { this.sessionToken = value; }
	
	private String authentication;
	public String getAuthentication() { return this.authentication; }
	public void setAuthentication(String value) { this.authentication = value; }
	
	public String downloadImageRetval = null;
	public Queue<HttpResponse> uploadFileRetval = new LinkedList<HttpResponse>();
	public Queue<PlainTextResult> httpGetPlainTextRetval = new LinkedList<PlainTextResult>();
	public Queue<HttpResponse> postJSONRetval = new LinkedList<HttpResponse>();
	
	@Override
	public IHttpSessionClient setSessionCookies(String domain, String sessionToken, String authentication) 
	{
		this.domain = domain;
		this.sessionToken = sessionToken;
		this.authentication = authentication;
		return this;
	}

	@Override
	public String downloadImage(String source, String destinationDirectory) 
	{
		return this.downloadImageRetval;
	}

	@Override
	public HttpResponse uploadFile(String uriPath, String filePath) 
	{
		return this.uploadFileRetval.poll();
	}

	@Override
	public PlainTextResult httpGetPlainText(String url) 
	{
		return this.httpGetPlainTextRetval.poll();
	}

	@Override
	public HttpResponse postJSON(String url, JSONObject jsonObj) throws ClientProtocolException, IOException 
	{
		return this.postJSONRetval.poll();
	}

}
