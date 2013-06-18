package com.photolude.www.UploadSystem.UI.Step1;

import java.applet.Applet;
import java.util.HashMap;

import javax.swing.*;

import com.photolude.UI.Common.ComponentHelper;
import com.photolude.UI.Common.IFolderSelectionListener;
import com.photolude.UI.Common.LargeImageFolder;
import com.photolude.UI.Common.Seperator;
import com.photolude.UI.Common.UILabel;
import com.photolude.UI.wizard.ImageDictionary;
import com.photolude.UI.wizard.WizardPageBase;
import com.photolude.www.UploadSystem.Styles;
import com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic.Directory;
import com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic.FileSystemAnalyzer;


/**
 * The step 1 screen
 * 
 * @author Nikody Keating
 *
 */
@SuppressWarnings("serial")
public class Step1DriveRootSelection extends WizardPageBase implements IFolderSelectionListener {
	public static final String FOLDER_TOKEN = "folder";
	public static final String FOLDER_IMAGE_PATH = "http://${server}/Scripts/UploadSystem/Upload/icons/folder.png";
	
	public Step1DriveRootSelection()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	}
	
	/**
	 * Invokes listener events
	 */
	@Override
	public void OnFolderSelected(LargeImageFolder source) 
	{
		((IPathNavigator)this.nextPage).setDirectory(source.GetDirectory());
		
		this.firePageNext();
	}

	/**
	 * Invokes listener events
	 */
	@Override
	public void OnFolderExpanded(LargeImageFolder source) {
		OnFolderSelected(source);
	}

	@Override
	public HashMap<String, String> initialize(Applet applet) {
		HashMap<String, String> retval = super.initialize(applet);
		
		// Dynamically calculate size information
        Directory[] userDirectories = FileSystemAnalyzer.GetInstance().GetUserFolders();
        this.add(ComponentHelper.AlignLeft(new UILabel("Instructions:")));
        this.add(ComponentHelper.AlignLeft(new UILabel("Select where your photos are located.", Styles.NormalFont)));
        
        //
        // Get user folders
        //
        this.add(new Seperator("User Folders"));
        if(userDirectories != null)
        {
        	for(int i = 0; i < userDirectories.length; i++)
        	{
        		LargeImageFolder folder = new LargeImageFolder(userDirectories[i], ImageDictionary.GetInstance().Get(FOLDER_TOKEN));
        		folder.AddFolderSelectionListener(this);
        		this.add(folder);
        	}
        }
        
        //
        // Get system drives
        //
        this.add(new Seperator("System Drives"));
        Directory[] systemDrives = FileSystemAnalyzer.GetInstance().GetSystemDrives();
        
        if(systemDrives != null)
        {
        	for(int i = 0; i < systemDrives.length; i++)
        	{
        		LargeImageFolder folder = new LargeImageFolder(systemDrives[i], ImageDictionary.GetInstance().Get(FOLDER_TOKEN));
        		folder.AddFolderSelectionListener(this);
        		this.add(folder);
        	}
        }
        
        this.add(Box.createVerticalGlue());
		
		if(!retval.containsKey(FOLDER_TOKEN)) retval.put(FOLDER_TOKEN, FOLDER_IMAGE_PATH);
		
		return retval;
	}
}
