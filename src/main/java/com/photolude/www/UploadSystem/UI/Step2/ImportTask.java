package com.photolude.www.UploadSystem.UI.Step2;

import java.io.File;

/**
 * A runnable task to import the files into the manifest screen
 * 
 * @author Nikody Keating
 *
 */
public class ImportTask implements Runnable {
	
	private ManifestView m_manManifest;
	private File[] m_fFiles;
	
	/**
	 * Creates and starts the import task
	 * @param manManifest the manifest view to import the files to
	 * @param fFiles the files to import
	 */
	public ImportTask(ManifestView manManifest, File[] fFiles)
	{
		m_manManifest = manManifest;
		m_fFiles = fFiles;
		
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * The execution thread
	 */
	@Override
	public void run() {
		m_manManifest.AddManifestItems(m_fFiles);
	}
}
