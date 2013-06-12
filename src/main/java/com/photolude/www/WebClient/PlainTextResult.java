package com.photolude.www.WebClient;

public class PlainTextResult {
	private String textResponse;
	public String getTextResponse(){return this.textResponse;}
	public PlainTextResult setTextResponse(String value)
	{
		this.textResponse = value;
		return this;
	}
	
	private int statusCode;
	public int getStatusCode(){return this.statusCode;}
	public PlainTextResult setStatusCode(int value)
	{
		this.statusCode = value;
		return this;
	}
}
