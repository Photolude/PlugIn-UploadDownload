package com.photolude.www.DownloadSystem;

import java.applet.Applet;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.photolude.www.UploadSystem.UI.Loading.ProgressBar;
import com.photolude.www.WebClient.HttpSessionClient;

/**
 * This object is an applet designed to be used by a webpage to download
 * a specified number of images to a specified directory
 * 
 * @author Nikody Keating
 *
 */
public class FileDownloadManagerSystem extends Applet implements Runnable {
	private int[] imageIds = null;
	private String destination = null;
	private String source = null;
	private String cookie = null;
	private String auth = null;
	private ProgressBar progressBar;
	private Thread thread;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9043494876831505915L;

	public FileDownloadManagerSystem()
	{
	}

	public void init()
	{
		Logger log = Logger.getLogger(FileDownloadManagerSystem.class);
		
		log.info("Loading download system\n");
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		
		//
		// Find the destination folder
		//
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int dialogReturnValue = fileChooser.showOpenDialog(this);
		if(dialogReturnValue == JFileChooser.APPROVE_OPTION)
		{
			this.destination = fileChooser.getSelectedFile().getAbsolutePath();
			//
			// Parse parameters into type safe values
			//
			log.info("Getting parameters\n");
			String imageIdsString = getParameter("ImageIds");
			this.source = getParameter("Source");
			this.cookie = getParameter("Cookie");
			this.auth = getParameter("Auth");
			
			if(imageIdsString != null)
			{
				log.info("Parsing Image Ids\n");
				String[] imageIdsParts = imageIdsString.split(",");
				imageIds = new int[imageIdsParts.length];
				
				for(int i = 0; i < imageIds.length; i++)
				{
					imageIds[i] = Integer.parseInt(imageIdsParts[i]);
				}
			}
			
			log.info("Setting Layout\n");
			//
			// Setup layout and start download
			//
			progressBar = new ProgressBar();
			progressBar.SetTotalSteps(imageIds.length + 1);
			progressBar.SetOnStep(0);
			
			this.add(progressBar);
			
			this.invalidate();
			this.doLayout();
			this.thread = new Thread(this);
			this.thread.start();
		}
	}

	@Override
	public void run() {
		Log log = LogFactory.getLog(FileDownloadManagerSystem.class);
		log.info("Starting to download " + imageIds.length + " photos\n");
		
		
		for(int i = 0; i < imageIds.length; i++)
		{
			log.info("Downloading " + i + " of " + imageIds.length);
			progressBar.SetStatus("Downloading " + (i + 1) + " of " + (imageIds.length));
			progressBar.SetOnStep(i);
			
			HttpSessionClient client = new HttpSessionClient(this.cookie, this.auth, this.source);
			
			String fileName = client.downloadImage("https://" + source + "/Content/ImageRender.ashx?imageId=" + imageIds[i] + "&large=true&Save=true", this.destination);
			
			if(fileName != null)
			{
				log.info("File:" + fileName);
			}
			else
			{
				log.error("A problem occured while downloading\n");
			}
		}
		
		progressBar.SetStatus("Complete");
		progressBar.SetOnStep(imageIds.length + 1);
	}
}
