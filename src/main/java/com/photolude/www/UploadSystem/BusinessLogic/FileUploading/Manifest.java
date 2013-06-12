package com.photolude.www.UploadSystem.BusinessLogic.FileUploading;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.*;


public class Manifest {
	private File[] m_files;
	private HashMap<File, String> m_mapDestinations;
	private int m_nFolderCount;
	private String m_userName;

	public Manifest(File[] files, String userName) {
		this.m_files = files;
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

	public int GetFolderCount() {
		return this.m_nFolderCount;
	}

	public String GetRemotePath(File file) {
		String retval = null;
		if (m_mapDestinations.containsKey(file)) {
			retval = m_mapDestinations.get(file);
		}
		return retval;
	}

	public String FindDateTaken(Node node) {
		String retval = null;
		NamedNodeMap map = node.getAttributes();
		int length = map.getLength();

		for (int i = 0; i < length && retval == null; i++) {
			Node attribute = map.item(i);
			String attributeName = attribute.getNodeName();
			if (attributeName == "DateTaken") {
				retval = attribute.getNodeValue();
			}
		}

		Node child = node.getFirstChild();

		while (child != null && retval == null) {
			retval = FindDateTaken(child);

			child = child.getNextSibling();
		}

		return retval;
	}

	private String UriEncode(String value)
	{
		return value.replace("&", "%26");
	}
	
	public Boolean Save(String outputFile) {
		Boolean retval = true;
		try {
			FileOutputStream stream = new FileOutputStream(outputFile);

			stream.write(String.format("<manifest username=\"%s\">", UriEncode(this.m_userName)).getBytes());
			for (int i = 0; i < this.m_files.length; i++) {
				ImageMetadata metadata = new ImageMetadata(this.m_files[i]);

				String outputLine = String
						.format("<file name=\"%s\" remote=\"%s\" path=\"%s\" createddate=\"%s\" modifieddate=\"%s\" />",
								        UriEncode(metadata.GetName()), 
								        UriEncode(this.m_mapDestinations.get(this.m_files[i])), 
								        UriEncode(this.m_files[i].getAbsolutePath()), 
								        metadata.GetCreatedDate().toString(), 
								        metadata.GetModifiedDate().toString());

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
}
