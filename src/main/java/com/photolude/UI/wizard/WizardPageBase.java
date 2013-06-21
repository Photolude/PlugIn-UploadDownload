package com.photolude.UI.wizard;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public abstract class WizardPageBase extends JComponent implements IWizardPage {
	
	private ArrayList<IWizardPageListener> listeners = new ArrayList<IWizardPageListener>();
	protected IWizardPage nextPage = null;
	public IWizardPage getNextPage() { return this.nextPage; }
	public void setNextPage(IWizardPage page) { this.nextPage = page; }
	
	public JComponent getUI()
	{
		return this;
	}
	
	public void addListener(IWizardPageListener listener)
	{
		synchronized(this.listeners)
		{
			this.listeners.add(listener);
		}
	}
	
	public void removeListener(IWizardPageListener listener)
	{
		synchronized(this.listeners)
		{
			this.listeners.remove(listener);
		}
	}
	
	protected void firePageNext()
	{
		synchronized(this.listeners)
		{
			for(IWizardPageListener listener : this.listeners)
			{
				listener.pageNext();
			}
		}
	}
	
	protected void firePageBack()
	{
		synchronized(this.listeners)
		{
			for(IWizardPageListener listener : this.listeners)
			{
				listener.pageBack();
			}
		}
	}
	
	public HashMap<String, String> initialize(IWizardContext context) {
		HashMap<String, String> retval = null;
		
		if(this.nextPage != null) retval = this.nextPage.initialize(context);
		
		if(retval == null) retval = new HashMap<String, String>();
		
		return retval;
	}
}
