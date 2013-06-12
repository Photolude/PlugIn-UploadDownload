package com.photolude.www.UploadSystem.UI.Step3;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComponent;

import com.photolude.UI.Common.UILabel;


public class Step3Upload extends JComponent implements IUploadStatusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<IUploadStatusListener> m_listeners;
	
	public Step3Upload(File[] fFiles)
	{
		m_listeners = new ArrayList<IUploadStatusListener>();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(new Step3Menu());
		this.add(new UILabel("Uploading Files"));
		
		UploadStatus uploadStatus = new UploadStatus(fFiles);
		uploadStatus.AddUploadStatusListener(this);
		this.add(uploadStatus);
	}
	
	public void AddUploadStatusListener(IUploadStatusListener listener)
	{
		m_listeners.add(listener);
	}
	
	public void UploadStatus_UploadComplete()
	{
		for(int i = 0; i < m_listeners.size(); i++)
		{
			m_listeners.get(i).UploadStatus_UploadComplete();
		}
	}
}
