package com.photolude.UI.wizard;

import java.util.HashMap;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class JWizard extends JComponent implements IWizardPageListener, ILoadingListener, IWizard {
	private IWizardPage firstPage;
	public IWizardPage getFirstPage() { return firstPage; }
	public void setFirstPage(IWizardPage firstPage) { this.firstPage = firstPage; }
	
	private IWizardPage currentPage = null;
	private Stack<IWizardPage> pageStack = new Stack<IWizardPage>();
	private LoadingScreen loadingScreen;
	
	public void start(IWizardContext context, HashMap<String,String> tokenMap)
	{
		this.loadingScreen = new LoadingScreen(context, tokenMap);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.loadingScreen.addListener(this);
		this.add(this.loadingScreen);
		
		this.loadingScreen.start(this.getFirstPage());
	}
	
	public JComponent getUI()
	{
		return this;
	}
	
	private void loadPage(IWizardPage page)
	{
		this.currentPage = page;
		this.currentPage.addListener(this);
		this.add(this.currentPage.getUI());
		
		this.validate();
		this.repaint();
	}

	@Override
	public void pageNext() {
		IWizardPage nextPage = this.currentPage.getNextPage();
		
		if(this.currentPage != null)
		{
			pageStack.add(this.currentPage);
		}
		
		unhookCurrentPage();
		loadPage(nextPage);
	}

	@Override
	public void pageBack() {
		IWizardPage lastPage = this.pageStack.pop();
		
		unhookCurrentPage();
		loadPage(lastPage);
	}
	
	private void unhookCurrentPage()
	{
		if(this.currentPage != null)
		{
			this.invalidate();
			this.removeAll();
			this.currentPage.removeListener(this);
			this.remove(this.currentPage.getUI());
		}
	}

	@Override
	public void LoadingCompleted() {
		this.remove(loadingScreen);
		
		this.loadPage(this.firstPage);
	}
}
