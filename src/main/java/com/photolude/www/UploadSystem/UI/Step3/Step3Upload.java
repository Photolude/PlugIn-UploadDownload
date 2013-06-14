package com.photolude.www.UploadSystem.UI.Step3;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComponent;

import com.photolude.UI.Common.UILabel;

/**
 * Step 3 in the upload wizard, which uploads the files to the server
 * 
 * @author Nikody Keating
 *
 */
public class Step3Upload extends JComponent implements IUploadStatusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<IUploadStatusListener> m_listeners;
	
	/**
	 * Initializes the object with the files to be uploaded 
	 * @param fFiles
	 */
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
	 * Gets called when the upload has completed and invokes this components listeners
	 */
	public void UploadStatus_UploadComplete()
	{
		for(int i = 0; i < m_listeners.size(); i++)
		{
			m_listeners.get(i).UploadStatus_UploadComplete();
		}
	}
}
