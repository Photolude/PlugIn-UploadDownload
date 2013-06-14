package com.photolude.www.UploadSystem.BusinessLogic.FileUploading;

/**
 * Defines a listener object for the file upload events
 * 
 * @author Nikody Keating
 *
 */
public interface IUploadFilesListener {
	/**
	 * Notifies the listener that there has been a status change
	 */
	public void StatusChanged();
	
	/**
	 * Notifies the listener that the user must re-log on
	 */
	public void RequireLogOn();
}
