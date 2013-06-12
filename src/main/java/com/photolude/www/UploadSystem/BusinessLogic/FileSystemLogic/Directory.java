package com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.filechooser.*;

public class Directory {
	public static Image FolderImage;
	public static Image PicturesImage;
	public static Image FileImage;
	
	public String Name;
	public String Path;
	public Icon IconImage;
	public Image Image;
	public String Description;
	public Boolean IsDrive;
	
	private File file;
	
	public Directory(File file)
	{
		this.file = file;
		Name = file.getName();
		Path = file.getAbsolutePath();
		IconImage = FileSystemView.getFileSystemView().getSystemIcon(file);
		Description = FileSystemView.getFileSystemView().getSystemTypeDescription(file);
		IsDrive = FileSystemView.getFileSystemView().isDrive(file);
		
		if(Name.equalsIgnoreCase("My Pictures") || Name.equalsIgnoreCase("Pictures"))
		{
			Description = null;
			
			if(IconImage == null)
			{
				Image = PicturesImage;
			}
		}
		else if(Description == null || Description.equalsIgnoreCase("File Folder"))
		{
			Description = null;
			
			if(IconImage == null)
			{
				Image = FolderImage;
			}
		}
		if(Name.equalsIgnoreCase(""))
		{
			int index = Path.indexOf('\\');
			Name = Path.substring(0, index);
		}
	}
	
	public String toString()
	{
		String retval;
		
		if(Description != null)
		{
			retval = Description + " (" + Name + ")";
		}
		else
		{
			retval = Name;
		}
		
		return retval;
	}

	public Directory[] GetSubdirectories() {
		ArrayList<File> filesList = new ArrayList<File>();
		File[] fileArray = this.file.listFiles();
		Directory[] retval = null;
		
		if(fileArray != null)
		{
			for(int i = 0; i < fileArray.length; i++)
			{
				if(fileArray[i].isDirectory())
				{
					filesList.add(fileArray[i]);
				}
			}
			
			retval = new Directory[filesList.size()];
			for(int i = 0; i < filesList.size(); i++)
			{
				retval[i] = new Directory(filesList.get(i));
			}
		}
		
		return retval;
	}
	
	private static String GetFileExtension(String fileName)
	{
		String retval = "";
		String[] parts = fileName.split("\\.");
		
		if(parts.length > 1)
		{
			retval = parts[parts.length - 1];
		}
		return retval;
	}
	
	public File[] GetFiles(String mask)
	{
		String[] masks = mask.split("\\|");
		Hashtable<String, String> htMaskLookup = new Hashtable<String, String>();
		
		for(int i = 0; i < masks.length; i++)
		{
			htMaskLookup.put(masks[i], masks[i]);
		}
		
		File[] aFileArray = this.file.listFiles();
		ArrayList<File> filesList = new ArrayList<File>();
		File[] retval = null;
		
		if(aFileArray != null)
		{
			
			for(int i = 0; i < aFileArray.length; i++)
			{
				if(htMaskLookup.containsKey(GetFileExtension(aFileArray[i].getName())))
				{
					filesList.add(aFileArray[i]);
				}
			}
		}
		
		if(filesList.size() != 0)
		{
			retval = new File[filesList.size()];
			
			for(int i = 0; i < filesList.size(); i++)
			{
				retval[i] = filesList.get(i);
			}
		}
		
		return retval;
	}

	public boolean Exists() {
		return this.file.exists();
	}
}
