package com.photolude.www.UploadSystem;

import java.applet.Applet;
import java.awt.*;
import java.util.HashMap;

import javax.swing.BoxLayout;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.photolude.UI.wizard.AppletWizardContext;
import com.photolude.UI.wizard.IWizard;
import com.photolude.www.WebClient.IHttpSessionClient;
import com.photolude.www.dialogs.ILogonSystem;

/**
 * The upload wizard meant to be use on a webpage
 * 
 * @author Nikody Keating
 *
 */
public class UploadManager extends Applet {
	
	private IWizard wizard;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	
	public UploadManager()
	{
		Dimension dimension = new Dimension(800, 600); 
		this.setPreferredSize(dimension);
		
	}

	/**
	 * Initializes the wizard and starts the loading screen
	 */
	public void init()
	{		
		HashMap<String, String> tokenOverrideMap = new HashMap<String, String>();
		tokenOverrideMap.put("${server}", getParameter("Server"));
		
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
		
		if(context.containsBean("Wizard") && context.containsBean("logonDialog"))
		{
			ILogonSystem logonSystem = (ILogonSystem)context.getBean("logonDialog");
			logonSystem.setLogonPage(getParameter("logonPage"));
			
			IHttpSessionClient client = (IHttpSessionClient)context.getBean("httpClient");
			client.setSessionCookies(getParameter("Server"), getParameter("token"), getParameter("auth"));
			
			this.wizard = (IWizard)context.getBean("Wizard");
			this.invalidate();
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			this.add(this.wizard.getUI());
			this.wizard.start(new AppletWizardContext(this), tokenOverrideMap);
		}
		else
		{
			System.out.append("Could not load application context\n");
		}
		
		context.close();
	}
}
