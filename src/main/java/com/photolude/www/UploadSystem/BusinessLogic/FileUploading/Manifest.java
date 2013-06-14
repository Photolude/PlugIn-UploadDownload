package com.photolude.www.UploadSystem.BusinessLogic.FileUploading;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Generates a manifest for the specified set of files
 * 
 * @author Nikody Keating
 *
 */
public class Manifest {
	private File[] files;
	private HashMap<File, String> m_mapDestinations;
	private int m_nFolderCount;
	private String m_userName;

	/**
	 * Creates a manifest for the specified files
	 * @param files the files which are to include in the manifest
	 * @param userName the user to associate with the manifest
	 */
	public Manifest(File[] files, String userName) {
		this.files = files;
		this.m_userName = userName;
		this.m_mapDestinations = new HashMap<File, String>();

		ArrayList<String> folderList = new ArrayList<String>();
		
		if(files != null)
		{
			for (int i = 0; i < files.length; i++) {
				String parentPath = files[i].getParentFile().getAbsolutePath();
				if (!folderList.contains(parentPath)) {
					folderList.add(parentPath);
				}
	
				int nFolderIndex = folderList.indexOf(parentPath);
				String sRemote = nFolderIndex + "/" + files[i].getName();
				m_mapDestinations.put(files[i], sRemote);
			}
		}

		this.m_nFolderCount = folderList.size();
	}

	/**
	 * Gets the number of unique folders
	 * @return the number of unique folders
	 */
	public int GetFolderCount() {
		return this.m_nFolderCount;
	}

	/**
	 * Gets the remote path which identifies this file remotely
	 * @param file the file object to get the remote path
	 * @return a string representing the relative remote processing path
	 */
	public String GetRemotePath(File file) {
		String retval = null;
		if (m_mapDestinations.containsKey(file)) {
			retval = m_mapDestinations.get(file);
		}
		return retval;
	}
	
	/**
	 * Saves the manifest as a file
	 * @param outputFile the path where to safe the file
	 * @return true if we were able to save the file
	 */
	public Boolean Save(String outputFile) {
		Boolean retval = true;
		try {
			FileOutputStream stream = new FileOutputStream(outputFile);

			stream.write(String.format("<manifest username=\"%s\">", UriEncode(this.m_userName)).getBytes());
			for (File file : this.files)
			{
				String outputLine = String
						.format("<file name=\"%s\" remote=\"%s\" path=\"%s\" createddate=\"%s\" modifieddate=\"%s\" />",
								        UriEncode(file.getName()), 
								        UriEncode(this.m_mapDestinations.get(file)), 
								        UriEncode(file.getAbsolutePath()), 
								        (new Date(file.lastModified())).toString(), 
								        (new Date(file.lastModified())).toString());

				stream.write(outputLine.getBytes());
			}
			stream.write("</manifest>".getBytes());
			stream.close();
		} catch (FileNotFoundException e) {
			retval = false;
		} catch (IOException e) {
			retval = false;
		}

		return retval;
	}
	
	private String UriEncode(String value)
	{
		return value.replace("&", "%26");
	}
}
