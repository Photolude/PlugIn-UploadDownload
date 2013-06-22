package com.photolude.UploadSystem.BusinessLogic.FileUploading.UnitTests.MockingObjects;

import com.photolude.www.dialogs.ILogonSystem;

public class TestLogonDialog implements ILogonSystem {

	public int logonCount = 0;
	public String logonPage;
	@Override
	public String getLogonPage() {
		return this.logonPage;
	}

	@Override
	public void setLogonPage(String page) {
		this.logonPage = page;
	}

	@Override
	public void logon() {
		logonCount++;
	}

}
