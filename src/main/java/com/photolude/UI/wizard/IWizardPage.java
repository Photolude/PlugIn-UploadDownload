package com.photolude.UI.wizard;

import java.util.HashMap;

import javax.swing.JComponent;

public interface IWizardPage {
	HashMap<String, String> initialize(IWizardContext context);
	JComponent getUI();
	void addListener(IWizardPageListener listener);
	void removeListener(IWizardPageListener listener);
	IWizardPage getNextPage();
}
