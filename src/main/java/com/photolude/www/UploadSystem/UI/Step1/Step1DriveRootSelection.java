package com.photolude.www.UploadSystem.UI.Step1;

import java.util.ArrayList;
import javax.swing.*;

import com.photolude.UI.Common.ComponentHelper;
import com.photolude.UI.Common.IFolderSelectionListener;
import com.photolude.UI.Common.LargeImageFolder;
import com.photolude.UI.Common.Seperator;
import com.photolude.UI.Common.UILabel;
import com.photolude.www.UploadSystem.Styles;
import com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic.Directory;
import com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic.FileSystemAnalyzer;


/**
 * The step 1 screen
 * 
 * @author Nikody Keating
 *
 */
public class Step1DriveRootSelection extends JComponent implements IFolderSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<IFolderSelectionListener> listeners = new ArrayList<IFolderSelectionListener>();
	
	public Step1DriveRootSelection()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// Dynamically calculate size information
        Directory[] userDirectories = FileSystemAnalyzer.GetInstance().GetUserFolders();
        this.add(ComponentHelper.AlignLeft(new UILabel("Instructions:")));
        this.add(ComponentHelper.AlignLeft(new UILabel("Select where your photos are located.", Styles.NormalFont)));
        
        this.add(new Seperator("User Folders"));
        if(userDirectories != null)
        {
        	for(int i = 0; i < userDirectories.length; i++)
        	{
        		LargeImageFolder folder = new LargeImageFolder(userDirectories[i]);
        		folder.AddFolderSelectionListener(this);
        		this.add(folder);
        	}
        }
        
        this.add(new Seperator("System Drives"));
        Directory[] systemDrives = FileSystemAnalyzer.GetInstance().GetSystemDrives();
        
        if(systemDrives != null)
        {
        	for(int i = 0; i < systemDrives.length; i++)
        	{
        		LargeImageFolder folder = new LargeImageFolder(systemDrives[i]);
        		folder.AddFolderSelectionListener(this);
        		this.add(folder);
        	}
        }
        
        this.add(Box.createVerticalGlue());
	}
	
	/**
	 * Adds a listener for when folders are selected
	 * @param listener the new listener to add
	 */
	public void AddFolderSelectionListener(IFolderSelectionListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Invokes listener events
	 */
	@Override
	public void OnFolderSelected(LargeImageFolder source) 
	{
		for(IFolderSelectionListener listener : listeners)
		{
			listener.OnFolderSelected(source);
		}
	}

	/**
	 * Invokes listener events
	 */
	@Override
	public void OnFolderExpanded(LargeImageFolder source) {
		for(IFolderSelectionListener listener : listeners)
		{
			listener.OnFolderExpanded(source);
		}
	}
}
