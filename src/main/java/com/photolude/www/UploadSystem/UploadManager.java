package com.photolude.www.UploadSystem;

import java.applet.Applet;
import java.awt.*;
import java.io.File;

import javax.swing.BoxLayout;

import com.photolude.UI.Common.IFolderSelectionListener;
import com.photolude.UI.Common.LargeImageFolder;
import com.photolude.UI.Common.UILabel;
import com.photolude.www.UploadSystem.UI.Loading.ILoadingListener;
import com.photolude.www.UploadSystem.UI.Loading.LoadingScreen;
import com.photolude.www.UploadSystem.UI.Step1.Step1DriveRootSelection;
import com.photolude.www.UploadSystem.UI.Step2.IStep2Listener;
import com.photolude.www.UploadSystem.UI.Step2.Step2SelectFolder;
import com.photolude.www.UploadSystem.UI.Step3.IUploadStatusListener;
import com.photolude.www.UploadSystem.UI.Step3.Step3Upload;
import com.photolude.www.UploadSystem.UI.Step3.UploadStatus;
import com.photolude.www.UploadSystem.UI.Step4.Step4Complete;

/**
 * The upload wizard meant to be use on a webpage
 * 
 * @author Nikody Keating
 *
 */
public class UploadManager extends Applet implements IFolderSelectionListener, IStep2Listener, IUploadStatusListener, ILoadingListener {
	private String server;
	private String token;
	private String authentication;
	
	private static final String m_sVersion = "Version 1.0.23";
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	
	public UploadManager()
	{
		Dimension dimension = new Dimension(800, 600); 
		this.setPreferredSize(dimension);
		
	}

	/**
	 * Initializes the wizard and starts the loading screen
	 */
	public void init()
	{
		this.server = getParameter("Server");
		this.token = getParameter("token");
		this.authentication = getParameter("auth");
		UploadStatus.Token = this.token;
		UploadStatus.Authentication = this.authentication;
		UploadStatus.Domain = this.server;
		UploadStatus.UserName = getParameter("userName");
		UploadStatus.UploadPage = getParameter("uploadPage");
		UploadStatus.StatusPage = getParameter("statusPage");
		UploadStatus.LogonPage = getParameter("logonPage");
		
		this.invalidate();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		LoadingScreen loadingScreen = new LoadingScreen(this, this.server);
		
		UILabel label = new UILabel(m_sVersion);
		this.add(label);
		
		loadingScreen.AddListener(this);
		this.add(loadingScreen);
		
		loadingScreen.Start();
	}

	/*
	 * (non-Javadoc)
	 * @see com.photolude.UI.Common.IFolderSelectionListener#OnFolderSelected(com.photolude.UI.Common.LargeImageFolder)
	 */
	@Override
	public void OnFolderSelected(LargeImageFolder source) 
	{
		this.removeAll();
		
		//this.repaint();
		
		Step2SelectFolder obj = new Step2SelectFolder(source.GetDirectory());
		obj.AddStep2Listeners(this);
		this.add(obj);
		
		this.validate();
		this.repaint();
	}

	/*
	 * (non-Javadoc)
	 * @see com.photolude.www.UploadSystem.UI.Step2.IStep2Listener#Step2_Complete(java.io.File[])
	 */
	@Override
	public void Step2_Complete(File[] fFiles) {
		this.removeAll();
		
		Step3Upload obj = new Step3Upload(fFiles);
		obj.AddUploadStatusListener(this);
		this.add(obj);
		this.validate();
	}

	/*
	 * (non-Javadoc)
	 * @see com.photolude.www.UploadSystem.UI.Step2.IStep2Listener#Step2_Back()
	 */
	@Override
	public void Step2_Back() {
		this.removeAll();
		//
		// Attach Step 1
		//
		Step1DriveRootSelection obj = new Step1DriveRootSelection();
		obj.AddFolderSelectionListener(this);
		this.add(obj);
		this.validate();
	}

	/*
	 * (non-Javadoc)
	 * @see com.photolude.www.UploadSystem.UI.Step3.IUploadStatusListener#UploadStatus_UploadComplete()
	 */
	@Override
	public void UploadStatus_UploadComplete() {
		this.removeAll();
		this.add(new Step4Complete(this.getAppletContext()));
		this.validate();
	}

	/*
	 * (non-Javadoc)
	 * @see com.photolude.UI.Common.IFolderSelectionListener#OnFolderExpanded(com.photolude.UI.Common.LargeImageFolder)
	 */
	@Override
	public void OnFolderExpanded(LargeImageFolder folder) {
		// Do nothing
	}

	/*
	 * (non-Javadoc)
	 * @see com.photolude.www.UploadSystem.UI.Loading.ILoadingListener#LoadingCompleted()
	 */
	@Override
	public void LoadingCompleted() {
		this.removeAll();

		//
		// Attach Step 1
		//
		UILabel label = new UILabel(m_sVersion);
		this.add(label);
		
		Step1DriveRootSelection obj = new Step1DriveRootSelection();
		obj.AddFolderSelectionListener(this);
		this.add(obj);
		
		this.validate();
		this.repaint();
	}
}
