package com.photolude.www.UploadSystem.UI.Step3;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.io.*;
import java.util.ArrayList;

import com.photolude.UI.wizard.IWizardContext;
import com.photolude.www.UploadSystem.Styles;
import com.photolude.www.UploadSystem.BusinessLogic.FileUploading.IUploadFilesListener;
import com.photolude.www.UploadSystem.BusinessLogic.FileUploading.UploadFilesLogic;
import com.photolude.www.WebClient.IHttpSessionClient;
import com.photolude.www.dialogs.ILogonSystem;

/**
 * The status object used to display meaningful status to the user during the upload process
 * 
 * @author Nikody Keating
 *
 */
public class UploadStatus extends Container implements Runnable, IUploadFilesListener {
	private static final long serialVersionUID = 1L;

	public static String UserName;
	public static String UploadPage;
	public static String StatusPage;
	
	private Thread m_tThread;
	private ArrayList<IUploadStatusListener> m_listeners;
	
	private UploadFilesLogic logic;
	
	private IHttpSessionClient httpClient;
	public void setHttpClient(IHttpSessionClient client){ this.httpClient = client; }
	public IHttpSessionClient getHttpClient(){ return this.httpClient; }
	
	private ILogonSystem logonSystem = null;
	public ILogonSystem getLogonSystem() { return this.logonSystem; }
	public void setLogonSystem(ILogonSystem system) { this.logonSystem = system; }
	
	/**
	 * Initializes this object with files to be uploaded, and starts the upload thread
	 * 
	 * @param files the files to be uploaded
	 */
	public UploadStatus()
	{
		m_listeners = new ArrayList<IUploadStatusListener>();
	}
	
	public void initialize(IWizardContext context)
	{
		UploadStatus.UserName = context.getReferenceAsString("userName");
		UploadStatus.UploadPage = context.getReferenceAsString("uploadPage");
		UploadStatus.StatusPage = context.getReferenceAsString("statusPage");
		
		this.logic = new UploadFilesLogic(UserName, UploadPage, StatusPage, this.logonSystem, this.httpClient, this);
	}
	
	public void setFilesToUpload(File[] files)
	{
		this.logic.setFiles(files);
	}
	
	public void start()
	{
		m_tThread = new Thread(this);
		m_tThread.start();
	}

	/**
	 * Adds a listener to this object
	 * 
	 * @param listener the new listener to add
	 */
	public void AddUploadStatusListener(IUploadStatusListener listener)
	{
		m_listeners.add(listener);
	}
	
	/**
	 * Paints this object to the screen
	 */
	public void paint(Graphics g) {
		int nDrawingWidth = this.getWidth() - 100;
		g.setColor(Color.darkGray);
		g.drawRect(50, 50, nDrawingWidth, 50);
		g.setColor(Color.green);
		g.fillRect(51, 51, (int)((nDrawingWidth - 2) * this.logic.getPercentComplete()), 48);
		
		g.setColor(Color.black);
		g.setFont(Styles.NormalFont);
		g.drawString(this.logic.getUploadStatusText(), 52, 122);
	}
	
	/**
	 * The thread which is uploading files
	 */
	@Override
	public void run() {
		this.validate();
		
		this.logic.uploadFiles();
		
		//
		// Notify upload complete
		//
		for(int i = 0; i < m_listeners.size(); i++)
		{
			m_listeners.get(i).UploadStatus_UploadComplete();
		}
	}

	/**
	 * Gets invoked when the upload status changes
	 */
	@Override
	public void statusChanged() {
		this.repaint();
	}
}
