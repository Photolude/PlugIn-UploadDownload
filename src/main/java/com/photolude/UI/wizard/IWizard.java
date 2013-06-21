package com.photolude.UI.wizard;

import java.util.HashMap;

import javax.swing.JComponent;

public interface IWizard {
	void start(IWizardContext context, HashMap<String,String> tokenMap);
	JComponent getUI();
}
