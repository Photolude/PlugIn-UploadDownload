package com.photolude.UI.wizard;

import java.applet.Applet;
import java.util.HashMap;

import javax.swing.JComponent;

public interface IWizard {
	void start(Applet applet, HashMap<String,String> tokenMap);
	JComponent getUI();
}
