package com.photolude.www.UploadSystem.UI.Step4;

import java.applet.AppletContext;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

import com.photolude.UI.Common.*;
import com.photolude.www.UploadSystem.Styles;


public class Step4Complete extends JComponent implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AppletContext context;
	private Thread thread;
	public Step4Complete(AppletContext context)
	{
		this.context = context;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createGlue());
		this.add(new UILabel("Your upload is complete."));
		this.add(new UILabel("It may take a few minutes for us to process your photos into our secure system.", Styles.Title2Font));
		this.add(Box.createGlue());
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	private void NavigateToPictures()
	{
		try
		{
			this.context.showStatus("transitioning to view photos");
			this.context.showDocument(new URL("/LoggedIn/ViewPhotos?Recent=true"));
		}
		catch(MalformedURLException e)
		{
		}
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.NavigateToPictures();
	}
}
