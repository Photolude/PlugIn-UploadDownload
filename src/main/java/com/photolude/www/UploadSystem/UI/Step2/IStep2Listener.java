package com.photolude.www.UploadSystem.UI.Step2;

import java.io.File;

/**
 * Defines a listener for the second step in the wizard
 * @author Nikody Keating
 *
 */
public interface IStep2Listener {
	
	/**
	 * Occurs when step 2 has been completed
	 * @param fFiles the files which have been added to the manifest to be uploaded
	 */
	public void Step2_Complete(File[] fFiles);
	
	/**
	 * Occurs when the back button is pressed on step 2
	 */
	public void Step2_Back();
}
