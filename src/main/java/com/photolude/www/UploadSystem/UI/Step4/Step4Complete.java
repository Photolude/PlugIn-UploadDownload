package com.photolude.www.UploadSystem.UI.Step4;

import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

import com.photolude.UI.Common.*;
import com.photolude.UI.wizard.IWizardContext;
import com.photolude.UI.wizard.WizardPageBase;
import com.photolude.www.UploadSystem.Styles;

/**
 * The view which informs the user that the upload process has completed
 * 
 * @author Nikody Keating
 *
 */
public class Step4Complete extends WizardPageBase implements Runnable {

	private static final long serialVersionUID = 1L;
	private Thread thread;
	
	public Step4Complete()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createGlue());
		this.add(new UILabel("Your upload is complete."));
		this.add(new UILabel("It may take a few minutes for us to process your photos into our secure system.", Styles.Title2Font));
		this.add(Box.createGlue());
	}
	
	/**
	 * Navigates to the recent pictures of the webpage
	 */
	private void NavigateToPictures()
	{
	}

	/**
	 * Waits for a specified period of time before automatically redirecting the user to the recently uploaded pictures
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.NavigateToPictures();
	}

	@Override
	public HashMap<String, String> initialize(IWizardContext context) {
		HashMap<String, String> retval = super.initialize(context);
		
		return retval;
	}

	@Override
	public JComponent getUI() {
		this.thread = new Thread(this);
		this.thread.start();
		
		return super.getUI();
	}
}
