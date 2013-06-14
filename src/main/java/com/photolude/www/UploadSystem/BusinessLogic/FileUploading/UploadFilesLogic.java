package com.photolude.www.UploadSystem.BusinessLogic.FileUploading;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.photolude.www.WebClient.IHttpSessionClient;
import com.photolude.www.WebClient.PlainTextResult;

/**
 * This class performs the actual upload business logic
 * 
 * @author Nikody
 *
 */
public class UploadFilesLogic {
	private String uploadToken;
	private Manifest manifest;
	private File[] files;
	private int finishedUploads;
	private IHttpSessionClient client;
	private String uploadPage;
	private String statusPage;
	private String logonPage;
	private IUploadFilesListener listener;
	
	private int onStep;
	
	/**
	 * Gets the percentage complete
	 * @return a value between 0 and 1
	 */
	public double getPercentComplete()
	{
		double retval = 0;
		if(files != null && files.length != 0)
		{
			retval = (double)this.onStep / (double)files.length;
			
			if(retval > 1.0)
			{
				retval = 1;
			}
		}
		return retval;
	}
	
	private String uploadStatus;
	/**
	 * Gets the upload status text
	 * @return
	 */
	public String getUploadStatusText(){return uploadStatus;}
	
	/**
	 * Creates an instance of the object with the specified settings
	 * @param userName the user name to use
	 * @param files the files to upload
	 * @param uploadPage the webpage which is used to upload to
	 * @param statusPage the webpage which is used to retrieve upload & processing status
	 * @param logonPage the webpage which is used to log-in
	 * @param client the session based http client
	 * @param listener a listener to events which occur during processing
	 */
	public UploadFilesLogic(String userName, File[] files, String uploadPage, String statusPage, String logonPage, IHttpSessionClient client, IUploadFilesListener listener)
	{
		this.uploadPage = uploadPage;
		this.statusPage = statusPage;
		this.logonPage = logonPage;
		
		this.onStep = 0;
		
		this.manifest = new Manifest(files, userName);
		
		this.files = files;
		this.uploadStatus = "Prepairing to Upload";
		
		this.client = client;
		this.listener = listener;
	}
	
	/**
	 * Upload the files to the server
	 */
	public void UploadFiles()
	{
		this.finishedUploads = 1;
		
		//
		// Create temporary manifest file for upload
		//
		String tempDirectory = System.getProperty("java.io.tmpdir");
		File fManifest = new File(String.format("%s%s", tempDirectory, "MomentBoxManifest.xml"));
		int numberAddition = 0;
		while(fManifest.exists())
		{
			fManifest = new File(String.format("%s%s%s", tempDirectory, "MomentBoxManifest.xml", ++numberAddition));
		}
		
		this.manifest.Save(fManifest.getAbsolutePath());

		//
		// Upload Manifest
		//
		Boolean bManifestUploaded = false;
		
		while(!bManifestUploaded)
		{
			URIBuilder uri;
			try {
				uri = (new URIBuilder(this.uploadPage))
						.addParameter("remote", "manifest.xml")
						.addParameter("fileLength", "" + fManifest.length())
						.addParameter("token", this.uploadToken);
				
				HttpResponse response = client.uploadFile(uri.toString(), fManifest.getAbsolutePath()); 
			    switch(response.getStatusLine().getStatusCode())
			    {
			    case 200:
			    	JSONObject json = convertResponseToJSON(response); 
					
					if(json != null)
					{
						try {
							this.uploadToken = json.getString("token");
							bManifestUploaded = true;
						} catch (JSONException e) {
						}
					}
			    	
			    	break;
			    case 302:
			    	this.listener.RequireLogOn();
			    	break;
			    default:
			    	break;
			    }
			    
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		
		//
		// Remove manifest file as we're finished with it
		//
		fManifest.delete();

		//
		// Upload files one at a time
		//
		for(int i = 0; i < this.files.length; i++)
		{
			File file = this.files[i];
			if(file.length() > 0)
			{
				String newStatus = "Uploading " + this.finishedUploads + " of " + (this.files.length + 1);
				
				if(this.finishedUploads == 1)
				{
					newStatus = "Preparing reliable secure upload";
				}
				
				SetStatus(newStatus, this.finishedUploads);
				
				//
				// Loop until the file is uploaded
				//
				while(!UploadFile(file))
				{
				}
				
				this.finishedUploads++;
			}
		}
	}
	
	/**
	 * Performs a log on with the specified values
	 * @param userName the user name to logon with
	 * @param password the password to logon with
	 */
	public void LogOn(String userName, String password)
	{
		JSONObject requestData = new JSONObject();
		try {
			requestData.put("email", userName);
			requestData.put("password", password);
			
			HttpResponse response = this.client.postJSON(this.logonPage, requestData);
			
			JSONObject jsonResponse = convertResponseToJSON(response);
			if(jsonResponse != null)
			{
				String result = jsonResponse.getString("result");
				if(result == "Access Denied")
				{
					//TODO: Show message box
				}
				else if(result != "Succeeded")
				{
					//TODO: Show login failed
				}
			}
			else
			{
				//TODO: Notify that could not connect to the server
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean UploadFile(File file)
	{
		
		boolean retval = false;
		
		String remote = this.manifest.GetRemotePath(file);
		
		URIBuilder uploadUri;
		try {
			//
			// Upload file
			//
			uploadUri = new URIBuilder(this.uploadPage)
							.addParameter("remote", remote)
							.addParameter("token", this.uploadToken)
							.addParameter("fileLength", "" + file.length());

			boolean fileUploaded = true;
			do
			{
				JSONObject json = convertResponseToJSON(client.uploadFile(uploadUri.toString(), file.getAbsolutePath())); 
				
				if(json != null)
				{
					fileUploaded = true;
					try {
						this.uploadToken = json.getString("token");
					} catch (JSONException e) {
						fileUploaded = false;
					}
				}
				else
				{
					fileUploaded = false;
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}while(!fileUploaded);
		
			//
			// Check the status of the file to ensure its processed
			//
			URIBuilder statusUri;
			statusUri = new URIBuilder(this.statusPage)
									.addParameter("remote", remote)
									.addParameter("token", this.uploadToken);
		
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, 5);
			Date timeout = calendar.getTime();
			
			do
			{
				PlainTextResult result = client.httpGetPlainText(statusUri.toString());

				switch(result.getStatusCode())
				{
					case 200:
						String responseText = result.getTextResponse().replaceAll("\r\n", "");
						
						//Remove the extra char
						responseText = responseText.substring(0, responseText.length() - 1);
						
						retval = responseText.equalsIgnoreCase("true");
						break;
					default:
						retval = false;
						break;
				}
			}while(!retval && timeout.after(Calendar.getInstance().getTime()));
		} catch (URISyntaxException e1) {
			retval = false;
		}
		
		return retval;
	}
	
	private JSONObject convertResponseToJSON(HttpResponse response)
	{
		Logger logger = Logger.getLogger(this.getClass());
		JSONObject jsonResponse = null;
		if(response != null && response.getStatusLine().getStatusCode() == 200)
		{
			try {
				InputStream stream = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream)); 
				StringBuilder contents = new StringBuilder();
				String line;
				
				while((line = reader.readLine()) != null)
				{
					contents.append(line);
					contents.append("\n");
				}
			
				jsonResponse = new JSONObject(contents.toString().trim());
			} catch (JSONException e) {
				logger.error(e.toString());
			} catch (IOException e1) {
				logger.error(e1.toString());
			}
		}
		else if(response != null)
		{
			logger.warn("Status code was not 200 (actual: " + response.getStatusLine().getStatusCode() + ").  Not converting to json object");
		}
		return jsonResponse;
	}
	
	private void SetStatus(String newStatus, int onStep)
	{
		if(!this.uploadStatus.equals(newStatus) || onStep != this.onStep)
		{
			this.uploadStatus = newStatus;
			this.onStep = onStep;
			this.listener.StatusChanged();
		}
	}
}
