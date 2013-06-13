package com.photolude.www.UploadSystem;

import java.applet.Applet;

/**
 * A step in the upload wizard
 * 
 * @author Nikody Keating
 *
 */
public interface IStep {
	/**
	 * Attaches the step to an applet instance
	 * 
	 * @param applet the applet to attach to
	 */
	void Attach(Applet applet);
	
	/**
	 * Clears the object of state
	 */
	void Clear();
}
