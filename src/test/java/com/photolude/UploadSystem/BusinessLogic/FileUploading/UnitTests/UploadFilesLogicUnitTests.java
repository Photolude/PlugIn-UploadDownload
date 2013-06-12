package com.photolude.UploadSystem.BusinessLogic.FileUploading.UnitTests;

import org.junit.*;

import com.photolude.www.UploadSystem.BusinessLogic.FileUploading.UploadFilesLogic;

public class UploadFilesLogicUnitTests {
	
	@Test
	public void ConstructNullFiles()
	{
		UploadFilesLogic unitUnderTest = new UploadFilesLogic(null, null, null, null, null, null, null);
		
		double percent = unitUnderTest.getPercentComplete();
		String statusText = unitUnderTest.getUploadStatusText();
		Assert.assertTrue("Percent complete is not as expected (" + percent + ")", percent == 0);
		Assert.assertTrue("Status text is null", statusText != null);
	}
}
