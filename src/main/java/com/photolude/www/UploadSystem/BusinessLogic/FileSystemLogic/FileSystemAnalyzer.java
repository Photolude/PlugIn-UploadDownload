package com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic;
import java.io.*;

/**
 * Analises the file system and provides access to general concepts on the file system
 * across multiple systems
 * 
 * @author Nikody Keating
 *
 * TODO: Add support for Linux
 * TODO: Add support for Apple
 */
public class FileSystemAnalyzer {
	private static FileSystemAnalyzer instance;
	
	/**
	 * Gets the singleton instance of the file system analyser
	 * @return the FileSystemAnalyzer instance
	 */
	public static synchronized FileSystemAnalyzer GetInstance()
	{
		if(instance == null)
		{
			instance = new FileSystemAnalyzer();
		}
		
		return instance;
	}
	
	/**
	 * Gets the system drives associated with the current system
	 * @return a set of directory objects which describe the system drives
	 */
	public Directory[] GetSystemDrives()
	{
		File[] rootFiles = File.listRoots();
		
		Directory[] retval = new Directory[rootFiles.length];
		for(int i = 0; i < rootFiles.length; i++)
		{
			retval[i] = new Directory(rootFiles[i]);
		}
		
		return retval;
	}
	
	/**
	 * Gets the special user folders for the specified machine
	 * @return
	 */
	public Directory[] GetUserFolders()
	{
		Directory[] retval = null;
		String osName = System.getProperty("os.name");
		
		// Get directories for windows 7
		if(osName.equalsIgnoreCase("windows 7"))
		{
			retval = new Directory[2];
			String myPicturesPath = System.getProperty("user.home") + File.separator + "My Pictures";
			retval[0] = new Directory(new File(myPicturesPath));
			
			if(retval[0].Exists() == false)
			{
				myPicturesPath = System.getProperty("user.home") + File.separator + "Pictures";
				retval[0] = new Directory(new File(myPicturesPath));
			}
			
			String myDesktopPath = System.getProperty("user.home") + File.separator + "Desktop";
			retval[1] = new Directory(new File(myDesktopPath));
		}
		
		return retval;
	}
}
