package com.photolude.www.UploadSystem.UI.Step2;

/**
 * Defines a listener for a Menu in the wizard view
 * 
 * @author Nikody Keating
 *
 */
public interface IMenuViewListener {
	/**
	 * Called when the wizard add button is pressed
	 */
	public void OnAddSelected();
	/**
	 * Called when the wizard upload all button is pressed
	 */
	public void OnUploadAll();
	/**
	 * Called when the remove button is pressed
	 */
	public void OnRemoveSelected();
	/**
	 * Called when the wizard back button is pressed
	 */
	public void OnBack();
}
