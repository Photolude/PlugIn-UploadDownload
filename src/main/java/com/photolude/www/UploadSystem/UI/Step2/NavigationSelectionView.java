package com.photolude.www.UploadSystem.UI.Step2;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import com.photolude.UI.Common.*;
import com.photolude.UI.wizard.ImageDictionary;
import com.photolude.www.UploadSystem.Styles;
import com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic.Directory;
import com.photolude.www.UploadSystem.UI.Step1.Step1DriveRootSelection;

/**
 * This is the file system view which is used to display the users local files
 * 
 * @author Nikody Keating
 *
 */
public class NavigationSelectionView extends SelectableComponent implements ISelectableComponentListener {
	private static final long serialVersionUID = 1L;
	private static String s_strFileMask = "png|jpg|bmp|gif|jpeg|JPG";
	private static int	s_nOffsetStep = 20;
	
	private JPanel m_jpNavigationPanel;
	
	/**
	 * Initializes the navigation based on a specific directory
	 * @param directory the directory to show as the root
	 */
	public void Initialize(Directory directory)
	{
		this.m_bSupportFolders = true;
		this.AddSelectedComponentListener((ISelectableComponentListener)this);
		int nWidth = 300;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Styles.Scroll_Background);
		
		this.add(new UILabel("File System:"));
		
		m_jpNavigationPanel = m_jpPanel;
		m_jpNavigationPanel.setBackground(Styles.Scroll_Background);
		m_jpNavigationPanel.setLayout(new BoxLayout(m_jpNavigationPanel, BoxLayout.Y_AXIS));
		
		m_jpNavigationPanel.setMaximumSize(new Dimension(nWidth, 1200000));
		m_jpNavigationPanel.setMinimumSize(new Dimension(nWidth, 10));
		
		LargeImageFolder rootFolder = new LargeImageFolder(directory, ImageDictionary.GetInstance().Get(Step1DriveRootSelection.FOLDER_TOKEN), 0);
		m_jpNavigationPanel.add(rootFolder);
		rootFolder.validate();
		
		Directory[] subDirectories = directory.GetSubdirectories();
		
		if(subDirectories != null)
		{
			for(int i = 0; i < subDirectories.length; i++)
			{
				LargeImageFolder folder= new LargeImageFolder(subDirectories[i], ImageDictionary.GetInstance().Get(Step1DriveRootSelection.FOLDER_TOKEN), s_nOffsetStep);
				folder.setAlignmentX(JComponent.LEFT_ALIGNMENT);
				folder.addMouseListener(this);
				m_jpNavigationPanel.add(folder);
				folder.validate();
			}
		}
		
		File[] files = directory.GetFiles(s_strFileMask);
		if(files != null)
		{
			for(int i = 0; i < files.length; i++)
			{
				UIFile file = new UIFile(files[i], s_nOffsetStep);
				file.addMouseListener(this);
				m_jpNavigationPanel.add(file);
			}
		}
		
		JScrollPane jspScrollArea = new JScrollPane(m_jpNavigationPanel);
		jspScrollArea.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		this.add(jspScrollArea);
	}

	/**
	 * Gets the selected files in this view
	 * @return the files selected
	 */
	public File[] GetSelectedObjects()
	{
		ArrayList<File> arrayList = new ArrayList<File>();
		
		for(int i = 0; i < m_jpNavigationPanel.getComponentCount(); i++)
		{
			Object obj = m_jpNavigationPanel.getComponent(i);
			
			if(ISelectable.class.isAssignableFrom(obj.getClass()) &&
					((ISelectable)obj).GetSelectedState())
			{
				if(LargeImageFolder.class.isAssignableFrom(obj.getClass()))
				{
					File[] files = GetFilesFromDirectory(((LargeImageFolder)obj).GetDirectory());
					
					for(int ii = 0; ii < files.length; ii++)
					{
						arrayList.add(files[ii]);
					}
				}
				else if(UIFile.class.isAssignableFrom(obj.getClass()))
				{
					arrayList.add(((UIFile)obj).GetFile());
				}
			}
		}
		
		File[] retval = new File[arrayList.size()];
		for(int i = 0; i < retval.length; i++)
		{
			retval[i] = arrayList.get(i);
		}
		
		return retval;
	}
	
	/**
	 * Handles large folder click events
	 */
	@Override
	public void LargeFolderClick(LargeImageFolder lifFolder)
	{
		int offset = GetComponentOffset((JComponent)lifFolder) + 1;
		
		if(!lifFolder.GetExpanded())
		{
			Directory directory = lifFolder.GetDirectory();
			
			Directory[] directories = directory.GetSubdirectories();
			int nPositionOffset = 0;
			if(directories != null)
			{
				for(int i = 0; i < directories.length; i++)
				{
					LargeImageFolder lifSubDir = new LargeImageFolder(directories[i], ImageDictionary.GetInstance().Get(Step1DriveRootSelection.FOLDER_TOKEN), lifFolder.GetLeftOffset() + s_nOffsetStep);
					lifSubDir.addMouseListener(this);
					m_jpNavigationPanel.add(lifSubDir, offset + i);
				}
				
				nPositionOffset = directories.length;
			}
			
			File[] files = directory.GetFiles(s_strFileMask);
			
			if(files != null)
			{
				for(int i = 0; i < files.length; i++)
				{
					UIFile file = new UIFile(files[i], lifFolder.GetLeftOffset() + s_nOffsetStep);
					file.addMouseListener(this);
					m_jpNavigationPanel.add(file, offset + i + nPositionOffset);
				}
			}
			lifFolder.SetExpanded(true);
		}
		else
		{
			Directory directory = lifFolder.GetDirectory();
			
			Directory[] directories = directory.GetSubdirectories();
			if(directories != null)
			{
				for(int i = 0; i < directories.length; i++)
				{
					m_jpNavigationPanel.remove(offset);
				}
			}
			
			File[] files = directory.GetFiles(s_strFileMask);
			if(files != null)
			{
				for(int i = 0; i < files.length; i++)
				{
					m_jpNavigationPanel.remove(offset);
				}
			}
			lifFolder.SetExpanded(false);
		}
		
		lifFolder.SetSelected(false);
		m_uifLastFileSelected = null;
		this.getParent().validate();
	}
	
	/**
	 * Unloads this object to ensure there are no threads running in the background
	 */
	public void Unload()
	{
		for(int i = 0; i < m_jpNavigationPanel.getComponentCount(); i++)
		{
			Object obj = m_jpNavigationPanel.getComponent(i);
			
			if(UIFile.class.isAssignableFrom(obj.getClass()))
			{
				((UIFile)obj).StopLoading();
			}
		}
	}

	/**
	 * Occurs when items are selected
	 */
	@Override
	public void ItemsSelected(Object Source, int nCount) {
	}
	
	private File[] GetFilesFromDirectory(Directory dir)
	{
		ArrayList<File> arrayList = new ArrayList<File>();
		
		Directory[] directories = dir.GetSubdirectories();
		
		for(int i = 0; i < directories.length; i++)
		{
			File[] files = GetFilesFromDirectory(directories[i]);
			
			for(int ii = 0; ii < files.length; ii++)
			{
				arrayList.add(files[ii]);
			}
		}
		
		File[] files = dir.GetFiles(s_strFileMask);
		
		if(files != null)
		{
			for(int i = 0; i < files.length; i++)
			{
				arrayList.add(files[i]);
			}
		}
		
		File[] retval = new File[arrayList.size()];
		for(int i = 0; i < retval.length; i++)
		{
			retval[i] = arrayList.get(i);
		}
		
		return retval;
	}
}
