package com.photolude.www.DownloadSystem;

import java.applet.Applet;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.photolude.www.UploadSystem.UI.Loading.ProgressBar;
import com.photolude.www.dialogs.ILogonSystem;

/**
 * This object is an applet designed to be used by a webpage to download
 * a specified number of images to a specified directory
 * 
 * @author Nikody Keating
 *
 */
public class FileDownloadManagerSystem extends Applet implements IDownloadEventListener {
	private String logonPage = null;
	private ProgressBar progressBar;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9043494876831505915L;

	public FileDownloadManagerSystem()
	{
	}

	public void init()
	{
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		
		//
		// Setup layout and start download
		//
		this.progressBar = new ProgressBar();
		progressBar.SetTotalSteps(1);
		progressBar.SetOnStep(0);
		this.add(this.progressBar);
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
		
		if(context.containsBean("downloadLogic") && context.containsBean("logonDialog"))
		{
			ILogonSystem logon = (ILogonSystem)context.getBean("logonDialog");
			IDownloadBusinessLogic logic = (IDownloadBusinessLogic)context.getBean("downloadLogic");
			
			logon.setLogonPage(getParameter("logonPage"));
			
			//
			// Find the destination folder
			//
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int dialogReturnValue = fileChooser.showOpenDialog(this);
			if(dialogReturnValue == JFileChooser.APPROVE_OPTION)
			{
				this.logonPage = getParameter("logonPage");
				if(this.logonPage != null &&
					logic.initialize(getParameter("ImageIds"), 
								 fileChooser.getSelectedFile().getAbsolutePath(), 
								 getParameter("domain"), 
								 getParameter("token"), 
								 getParameter("auth"), 
								 getParameter("downloadPage")))
				{
					progressBar.SetTotalSteps(logic.getTotalImages() + 1);
					progressBar.SetOnStep(0);
					
					logic.addListener(this);
					logic.start();
				}
			}
		}
		
		this.invalidate();
		this.doLayout();
		
		context.close();
	}

	@Override
	public void statusUpdated(String status, int step) {
		this.progressBar.SetOnStep(step);
		this.progressBar.SetStatus(status);
	}
}
