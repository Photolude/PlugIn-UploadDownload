package com.photolude.UploadSystem.BusinessLogic.FileUploading.UnitTests.MockingObjects;

import com.photolude.www.UploadSystem.BusinessLogic.FileUploading.IUploadFilesListener;

public class TestUploadFileListener implements IUploadFilesListener {
	int statusChangedCount = 0;

	@Override
	public void statusChanged() {
		this.statusChangedCount++;
	}

}
