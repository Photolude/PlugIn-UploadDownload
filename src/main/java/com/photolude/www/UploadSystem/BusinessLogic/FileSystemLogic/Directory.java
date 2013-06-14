package com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * This object defines a directory object which can be used by the UI
 * 
 * @author Nikody Keating
 *
 */
public class Directory {
	private static Image folderImage;
	/**
	 * Gets the image used for displaying folders
	 * @return an Image object
	 */
	public static Image getFolderImage(){ return folderImage; }
	/**
	 * Sets the image used to display folders
	 * @param value the image to set
	 */
	public static void setFolderImage(Image value){ folderImage = value; }
	
	public static Image picturesImage;
	/**
	 * Gets the image used for pictures
	 * @return an image object
	 */
	public static Image getPicturesImage(){ return picturesImage; }
	/**
	 * Sets the image to be used for pictures
	 * @param value an image object
	 */
	public static void setPicturesImage(Image value){ picturesImage = value; }
	
	public String Name;
	public String Path;
	public Icon IconImage;
	public Image Image;
	public String Description;
	public Boolean IsDrive;
	
	private File file;
	
	/**
	 * Creates a directory object based off a java file object
	 * @param file the object representing the physical directory located on disc
	 */
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
				Image = picturesImage;
			}
		}
		else if(Description == null || Description.equalsIgnoreCase("File Folder"))
		{
			Description = null;
			
			if(IconImage == null)
			{
				Image = folderImage;
			}
		}
		if(Name.equalsIgnoreCase(""))
		{
			int index = Path.indexOf('\\');
			Name = Path.substring(0, index);
		}
	}
	
	/**
	 * Gets the display name
	 */
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

	/**
	 * Gets the subdirectory for the current directory
	 * @return an array of subdirectories
	 */
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
	
	/**
	 * Gets the files based off of the masking limitations
	 * @param mask the mask to limit the file search by
	 * @return all the files in the current directory which match the file mask
	 */
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

	/**
	 * Checks to see if the directory still exists
	 * @return true if the directory exists
	 */
	public boolean Exists() {
		return this.file.exists();
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
}
