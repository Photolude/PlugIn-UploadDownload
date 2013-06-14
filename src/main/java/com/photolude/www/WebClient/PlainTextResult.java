package com.photolude.www.WebClient;

/**
 * This object is used to convey a result of a simple plain text query to a url.
 * 
 * @author Nikody Keating
 *
 */
public class PlainTextResult {
	private String textResponse;
	/**
	 * Gets the text response
	 * @return the body of text returned from the server
	 */
	public String getTextResponse(){return this.textResponse;}
	/**
	 * Sets the text from the server
	 * @param value the text to set
	 * @return this object for later processing
	 */
	public PlainTextResult setTextResponse(String value)
	{
		this.textResponse = value;
		return this;
	}
	
	private int statusCode;
	/**
	 * Gets the status code from the result
	 * @return the status code
	 */
	public int getStatusCode(){return this.statusCode;}
	/**
	 * sets the status code for this result
	 * @param value the status code to set
	 * @return this object for later processing
	 */
	public PlainTextResult setStatusCode(int value)
	{
		this.statusCode = value;
		return this;
	}
}
