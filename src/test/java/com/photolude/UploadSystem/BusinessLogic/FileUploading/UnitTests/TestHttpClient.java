package com.photolude.UploadSystem.BusinessLogic.FileUploading.UnitTests;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.photolude.www.WebClient.IHttpSessionClient;
import com.photolude.www.WebClient.PlainTextResult;

public class TestHttpClient implements IHttpSessionClient {
	public String downloadImage_Result;
	public HttpResponse uploadFile_Result;
	public PlainTextResult httpGetPlainText_Result;
	public HttpResponse postJSON_Result;
	
	@Override
	public String downloadImage(String source, String destinationDirectory) {
		// TODO Auto-generated method stub
		return this.downloadImage_Result;
	}

	@Override
	public HttpResponse uploadFile(String uriPath, String filePath) {
		// TODO Auto-generated method stub
		return this.uploadFile_Result;
	}

	@Override
	public PlainTextResult httpGetPlainText(String url) {
		// TODO Auto-generated method stub
		return this.httpGetPlainText_Result;
	}

	@Override
	public HttpResponse postJSON(String url, JSONObject jsonObj) throws ClientProtocolException, IOException {
		return this.postJSON_Result;
	}

}
