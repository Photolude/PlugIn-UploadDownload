package com.photolude.UploadSystem.BusinessLogic.FileUploading.UnitTests.MockingObjects;

import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;

public class TestStatusLine implements StatusLine {
	private int status;
	
	public TestStatusLine(int status)
	{
		this.status = status;
	}

	@Override
	public ProtocolVersion getProtocolVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReasonPhrase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStatusCode() {
		// TODO Auto-generated method stub
		return this.status;
	}
}
