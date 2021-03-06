package com.photolude.UI.Common;

/**
 * Defines a listener for folder selection events
 * 
 * @author Nikody Keating
 *
 */
public interface IFolderSelectionListener {
	/**
	 * This function gets called when a folder is selected
	 * @param source the source object which was selected
	 */
	void OnFolderSelected(LargeImageFolder source);
	
	/**
	 * This function gets called when a folder is expanded
	 * @param source the source folder which was expanded
	 */
	void OnFolderExpanded(LargeImageFolder source);
}
