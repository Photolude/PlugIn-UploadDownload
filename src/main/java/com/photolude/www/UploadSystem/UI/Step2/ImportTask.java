package com.photolude.www.UploadSystem.UI.Step2;

import java.io.File;

public class ImportTask implements Runnable {
	
	private ManifestView m_manManifest;
	private File[] m_fFiles;
	
	public ImportTask(ManifestView manManifest, File[] fFiles)
	{
		m_manManifest = manManifest;
		m_fFiles = fFiles;
		
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		m_manManifest.AddManifestItems(m_fFiles);
	}
}
