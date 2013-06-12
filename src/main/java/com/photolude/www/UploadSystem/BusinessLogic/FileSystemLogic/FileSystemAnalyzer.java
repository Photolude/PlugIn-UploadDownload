package com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic;
import java.io.*;


public class FileSystemAnalyzer {
	private static FileSystemAnalyzer instance;
	
	public static synchronized FileSystemAnalyzer GetInstance()
	{
		if(instance == null)
		{
			instance = new FileSystemAnalyzer();
		}
		
		return instance;
	}
	
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
	
	public Directory[] GetUserFolders()
	{
		Directory[] retval = null;
		String osName = System.getProperty("os.name");
		
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
