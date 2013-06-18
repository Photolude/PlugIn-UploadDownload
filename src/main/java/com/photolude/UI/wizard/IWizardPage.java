package com.photolude.UI.wizard;

import java.applet.Applet;
import java.util.HashMap;

import javax.swing.JComponent;

public interface IWizardPage {
	HashMap<String, String> initialize(Applet applet);
	JComponent getUI();
	void addListener(IWizardPageListener listener);
	void removeListener(IWizardPageListener listener);
	IWizardPage getNextPage();
}
