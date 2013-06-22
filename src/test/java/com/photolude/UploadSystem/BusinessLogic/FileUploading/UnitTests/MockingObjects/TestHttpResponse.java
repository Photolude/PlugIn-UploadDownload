package com.photolude.UploadSystem.BusinessLogic.FileUploading.UnitTests.MockingObjects;

import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpParams;

public class TestHttpResponse implements HttpResponse {

	private int status;
	private HttpEntity entity;
	
	public TestHttpResponse(int status)
	{
		this.status = status;
	}
	
	@Override
	public void addHeader(Header arg0) {
	}

	@Override
	public void addHeader(String arg0, String arg1) {
	}

	@Override
	public boolean containsHeader(String arg0) {
		return false;
	}

	@Override
	public Header[] getAllHeaders() {
		return null;
	}

	@Override
	public Header getFirstHeader(String arg0) {
		return null;
	}

	@Override
	public Header[] getHeaders(String arg0) {
		return null;
	}

	@Override
	public Header getLastHeader(String arg0) {
		return null;
	}

	@Override
	public HttpParams getParams() {
		return null;
	}

	@Override
	public ProtocolVersion getProtocolVersion() {
		return null;
	}

	@Override
	public HeaderIterator headerIterator() {
		return null;
	}

	@Override
	public HeaderIterator headerIterator(String arg0) {
		return null;
	}

	@Override
	public void removeHeader(Header arg0) {
	}

	@Override
	public void removeHeaders(String arg0) {
	}

	@Override
	public void setHeader(Header arg0) {
	}

	@Override
	public void setHeader(String arg0, String arg1) {
	}

	@Override
	public void setHeaders(Header[] arg0) {
	}

	@Override
	public void setParams(HttpParams arg0) {
	}

	@Override
	public HttpEntity getEntity() {
		return this.entity;
	}

	@Override
	public Locale getLocale() {
		return null;
	}

	@Override
	public StatusLine getStatusLine() {
		return new TestStatusLine(this.status);
	}

	public TestHttpResponse setEntity(String content)
	{
		this.entity = new ByteArrayEntity(content.getBytes());
		return this;
	}
	
	@Override
	public void setEntity(HttpEntity arg0) {
		this.entity = arg0;
	}

	@Override
	public void setLocale(Locale arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setReasonPhrase(String arg0) throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatusCode(int arg0) throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatusLine(StatusLine arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatusLine(ProtocolVersion arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatusLine(ProtocolVersion arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

}
