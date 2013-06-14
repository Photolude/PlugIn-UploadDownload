package com.photolude.www.UploadSystem.UI.Step2;

import com.photolude.UI.Common.LargeImageFolder;

/**
 * Defines a listener for selectable components
 * 
 * @author Nikody Keating
 *
 */
public interface ISelectableComponentListener {
	/**
	 * Occurs when a large folder is clicked
	 * @param lifFolder the folder which was clicked
	 */
	public void LargeFolderClick(LargeImageFolder lifFolder);
	
	/**
	 * Occurs when items are selected, along with the number of items selected
	 * @param Source the source where this event occured
	 * @param nCount the number of items selected
	 */
	public void ItemsSelected(Object Source, int nCount);
}
