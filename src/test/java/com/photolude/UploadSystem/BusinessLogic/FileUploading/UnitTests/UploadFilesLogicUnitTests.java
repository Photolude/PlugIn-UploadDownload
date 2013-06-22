package com.photolude.UploadSystem.BusinessLogic.FileUploading.UnitTests;

import java.io.File;
import java.util.Calendar;

import org.junit.*;

import com.photolude.UploadSystem.BusinessLogic.FileUploading.UnitTests.MockingObjects.TestHttpClient;
import com.photolude.UploadSystem.BusinessLogic.FileUploading.UnitTests.MockingObjects.TestHttpResponse;
import com.photolude.UploadSystem.BusinessLogic.FileUploading.UnitTests.MockingObjects.TestLogonDialog;
import com.photolude.UploadSystem.BusinessLogic.FileUploading.UnitTests.MockingObjects.TestUploadFileListener;
import com.photolude.www.UploadSystem.BusinessLogic.FileUploading.UploadFilesLogic;
import com.photolude.www.WebClient.PlainTextResult;

public class UploadFilesLogicUnitTests {
	
	public static final String USER_NAME = "user name";
	public static final String UPLOAD_PAGE = "http://localhost/uploadPage";
	public static final String STATUS_PAGE = "http://localhost/statusPage";
	
	private TestHttpClient httpClient = new TestHttpClient();
	private TestLogonDialog logonSystem = new TestLogonDialog();
	private TestUploadFileListener listener = new TestUploadFileListener();
	
	private String TestDirPath = UploadFilesLogicUnitTests.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	
	public UploadFilesLogicUnitTests()
	{
		// Manifest Response
		this.httpClient.uploadFileRetval.add((new TestHttpResponse(200)).setEntity("{token=\"test\"}"));
	}
	
	@Test
	public void nullFile() throws Exception
	{
		UploadFilesLogic unitUnderTest = new UploadFilesLogic(USER_NAME, UPLOAD_PAGE, STATUS_PAGE, this.logonSystem, this.httpClient, this.listener);
		
		unitUnderTest.setFiles(null);
		
		double percent = unitUnderTest.getPercentComplete();
		String statusText = unitUnderTest.getUploadStatusText();
		Assert.assertTrue("Percent complete is not as expected (" + percent + ")", percent == 0);
		Assert.assertTrue("Status text is null", statusText != null);
	}
	
	@Test
	public void uploadNonExistantFile() throws Exception
	{
		this.httpClient.uploadFileRetval.add((new TestHttpResponse(200)).setEntity("{token=\"test\"}"));
		
		UploadFilesLogic unitUnderTest = new UploadFilesLogic(USER_NAME, UPLOAD_PAGE, STATUS_PAGE, this.logonSystem, this.httpClient, this.listener);
		
		File[] files = new File[]{new File("C:/doesnotexist.bmp")};
		unitUnderTest.setFiles(files);
		
		uploadAndVerify(unitUnderTest, 5, 1, 1);
	}
	
	@Test
	public void uploadSingleFile() throws Exception
	{
		this.httpClient.uploadFileRetval.add((new TestHttpResponse(200)).setEntity("{token=\"test\"}"));
		this.httpClient.httpGetPlainTextRetval.add(new PlainTextResult().setStatusCode(200).setTextResponse("true\n"));
		
		UploadFilesLogic unitUnderTest = new UploadFilesLogic(USER_NAME, UPLOAD_PAGE, STATUS_PAGE, this.logonSystem, this.httpClient, this.listener);
		
		File[] files = new File[]{new File(TestDirPath + "com/photolude/UploadSystem/BusinessLogic/FileUploading/UnitTests/UploadFilesLogicUnitTests.class")};
		unitUnderTest.setFiles(files);
		
		uploadAndVerify(unitUnderTest, 5, 1, 0);
	}
	
	@Test
	public void uploadMultipleFile() throws Exception
	{
		this.httpClient.uploadFileRetval.add((new TestHttpResponse(200)).setEntity("{token=\"test\"}"));
		this.httpClient.uploadFileRetval.add((new TestHttpResponse(200)).setEntity("{token=\"test\"}"));
		this.httpClient.httpGetPlainTextRetval.add(new PlainTextResult().setStatusCode(200).setTextResponse("true\n"));
		this.httpClient.httpGetPlainTextRetval.add(new PlainTextResult().setStatusCode(200).setTextResponse("true\n"));
		
		UploadFilesLogic unitUnderTest = new UploadFilesLogic(USER_NAME, UPLOAD_PAGE, STATUS_PAGE, this.logonSystem, this.httpClient, this.listener);
		
		File[] files = new File[]
				{
					new File(TestDirPath + "com/photolude/UploadSystem/BusinessLogic/FileUploading/UnitTests/UploadFilesLogicUnitTests.class"),
					new File(TestDirPath + "com/photolude/UploadSystem/BusinessLogic/FileUploading/UnitTests/TestHttpClient.class")
				};
		
		unitUnderTest.setFiles(files);
		
		uploadAndVerify(unitUnderTest, 5, 1, 0);
		
		File[] failedUploads = unitUnderTest.getFailedUploads();
		Assert.assertTrue("An upload failure occured", failedUploads != null && failedUploads.length == 0);
	}
	
	@Test
	public void uploadSameFileTwice() throws Exception
	{
		this.httpClient.uploadFileRetval.add((new TestHttpResponse(200)).setEntity("{token=\"test\"}"));
		this.httpClient.uploadFileRetval.add((new TestHttpResponse(200)).setEntity("{token=\"test\"}"));
		this.httpClient.httpGetPlainTextRetval.add(new PlainTextResult().setStatusCode(200).setTextResponse("true\n"));
		this.httpClient.httpGetPlainTextRetval.add(new PlainTextResult().setStatusCode(200).setTextResponse("true\n"));
		
		UploadFilesLogic unitUnderTest = new UploadFilesLogic(USER_NAME, UPLOAD_PAGE, STATUS_PAGE, this.logonSystem, this.httpClient, this.listener);
		
		File[] files = new File[]
				{
					new File(TestDirPath + "com/photolude/UploadSystem/BusinessLogic/FileUploading/UnitTests/UploadFilesLogicUnitTests.class"),
					new File(TestDirPath + "com/photolude/UploadSystem/BusinessLogic/FileUploading/UnitTests/UploadFilesLogicUnitTests.class")
				};
		
		unitUnderTest.setFiles(files);
		
		uploadAndVerify(unitUnderTest, 5, 1, 0);
		
		File[] failedUploads = unitUnderTest.getFailedUploads();
		Assert.assertTrue("An upload failure occured", failedUploads != null && failedUploads.length == 0);
	}
	
	private void uploadAndVerify(UploadFilesLogic logic, int timeoutSeconds, double percentComplete, int expectedFailedUploads) throws Exception
	{
		Calendar timeout = Calendar.getInstance();
		timeout.add(Calendar.SECOND, timeoutSeconds);
	
		logic.uploadFiles();
		
		Assert.assertTrue("Upload did not complete", logic.getPercentComplete() == percentComplete);
		
		File[] failedUploads = logic.getFailedUploads();
		Assert.assertTrue("Failed upload array is null", failedUploads != null);
		Assert.assertTrue("The number of failed uploads was not expected (expected: " + expectedFailedUploads + ", actual: " + failedUploads.length, failedUploads.length == expectedFailedUploads);
	}
}
