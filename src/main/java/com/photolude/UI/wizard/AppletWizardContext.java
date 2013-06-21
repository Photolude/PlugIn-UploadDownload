package com.photolude.UI.wizard;

import java.applet.Applet;

public class AppletWizardContext implements IWizardContext {

	private Applet applet;
	
	public AppletWizardContext(Applet applet)
	{
		this.applet = applet; 
	}
	
	@Override
	public String getReferenceAsString(String referenceName) {
		return this.applet.getParameter(referenceName);
	}
}
