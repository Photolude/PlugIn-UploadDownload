package com.photolude.www.UploadSystem.UI.Step3;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.io.*;
import java.util.ArrayList;

import com.photolude.www.UploadSystem.Styles;
import com.photolude.www.UploadSystem.BusinessLogic.FileUploading.IUploadFilesListener;
import com.photolude.www.UploadSystem.BusinessLogic.FileUploading.UploadFilesLogic;
import com.photolude.www.WebClient.HttpSessionClient;
import com.photolude.www.dialogs.LoginDialog;


public class UploadStatus extends Container implements Runnable, IUploadFilesListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String UserName;
	public static String Token;
	public static String Authentication;
	public static String Domain;
	public static String UploadPage;
	public static String StatusPage;
	public static String LogonPage;
	
	private Thread m_tThread;
	private ArrayList<IUploadStatusListener> m_listeners;
	
	private UploadFilesLogic logic;
	
	public UploadStatus(File[] files)
	{
		this.logic = new UploadFilesLogic(UserName, files, UploadPage, StatusPage, LogonPage, new HttpSessionClient(Token, Authentication, Domain), this);
		
		m_listeners = new ArrayList<IUploadStatusListener>();
		
		m_tThread = new Thread(this);
		m_tThread.start();
	}

	public void AddUploadStatusListener(IUploadStatusListener listener)
	{
		m_listeners.add(listener);
	}
	
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
	
	@Override
	public void run() {
		this.validate();
		
		this.logic.UploadFiles();
		
		//
		// Notify upload complete
		//
		for(int i = 0; i < m_listeners.size(); i++)
		{
			m_listeners.get(i).UploadStatus_UploadComplete();
		}
	}

	@Override
	public void RequireLogOn() {
		LoginDialog dialog = new LoginDialog();
		this.logic.LogOn(dialog.getEmail(), dialog.getPassword());
	}

	@Override
	public void StatusChanged() {
		this.repaint();
	}
}
