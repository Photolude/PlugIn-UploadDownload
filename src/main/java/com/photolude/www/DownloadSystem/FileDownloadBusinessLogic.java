package com.photolude.www.DownloadSystem;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.photolude.www.WebClient.IHttpSessionClient;
import com.photolude.www.dialogs.ILogonSystem;

public class FileDownloadBusinessLogic implements Runnable, IDownloadBusinessLogic {
	private List<Integer> imageIds = new ArrayList<Integer>();
	private Thread thread;
	private String destinationDirectory;
	private String downloadPage = null;
	private IHttpSessionClient httpClient = null;
	public void setHttpClient(IHttpSessionClient client){ this.httpClient = client; }
	public IHttpSessionClient getHttpClient(){ return this.httpClient; }
	
	public int getTotalImages(){ return this.imageIds.size(); }
	
	List<IDownloadEventListener> listeners = new ArrayList<IDownloadEventListener>();
	public void addListener(IDownloadEventListener listener)
	{
		this.listeners.add(listener);
	}
	
	private ILogonSystem logonSystem = null;
	public ILogonSystem getLogonSystem() { return this.logonSystem; }
	public void setLogonSystem(ILogonSystem system) { this.logonSystem = system; } 
	
	private void fireStatusUpdateEvent(String status, int step)
	{
		for(IDownloadEventListener listener : this.listeners)
		{
			listener.statusUpdated(status, step);
		}
	}
	
	public boolean initialize(String imageIdsString, String destinationDirectory, String domain, String sessionCookie, String authentication, String downloadPage)
	{
		boolean retval = this.httpClient != null &&
							downloadPage != null &&
							destinationDirectory != null &&
							domain != null &&
							sessionCookie != null &&
							imageIdsString != null;
		
		if(retval)
		{
			this.downloadPage = downloadPage;
			this.destinationDirectory = destinationDirectory;
			this.httpClient.setSessionCookies(domain, sessionCookie, authentication);
			this.imageIds = new ArrayList<Integer>();
			
			if(imageIdsString != null)
			{
				String[] imageIdsParts = imageIdsString.split(",");
				this.imageIds = new ArrayList<Integer>();
				
				
				for(int i = 0; i < imageIdsParts.length; i++)
				{
					int val = Integer.parseInt(imageIdsParts[i]);
					
					if(!this.imageIds.contains(val))
					{
						this.imageIds.add(val);
					}
				}
			}
		}
		return true;
	}
	
	public void start()
	{
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	@Override
	public void run() {
		Log log = LogFactory.getLog(FileDownloadManagerSystem.class);
		log.info("Starting to download " + imageIds.size() + " photos\n");
		
		for(int i = 0; i < imageIds.size(); i++)
		{
			String fileName = null;
			
			do
			{
				fileName = null;
				log.info("Downloading " + i + " of " + imageIds.size());
				fireStatusUpdateEvent("Downloading " + (i + 1) + " of " + (imageIds.size()), i);
				
				fileName = this.httpClient.downloadImage(this.downloadPage + "?imageId=" + imageIds.get(i) + "&large=true&Save=true", this.destinationDirectory);
				
				if(fileName != null)
				{
					log.info("File:" + fileName);
				}
				else
				{
					this.logonSystem.logon();
				}
			} while(fileName == null);
		}
		
		this.fireStatusUpdateEvent("Complete", imageIds.size() + 1);
	}
}
