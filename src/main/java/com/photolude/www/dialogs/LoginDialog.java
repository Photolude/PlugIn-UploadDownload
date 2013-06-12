package com.photolude.www.dialogs;

import java.awt.*;
import javax.swing.*;

public class LoginDialog
{
    private String email;
    public String getEmail(){return email;}
    
    private String password;
    public String getPassword(){return password;}

    public LoginDialog() {
        getIDandPassword();
    }

    // modal dialog to get user ID and password
    static String[] ConnectOptionNames = { "Login", "Cancel" };
    static String   ConnectTitle = "Login";
    void getIDandPassword()
    {
        JPanel      connectionPanel;
	
	 	// Create the labels and text fields.
		JLabel     userNameLabel = new JLabel("Email:   ", JLabel.RIGHT);
	 	JTextField userNameField = new JTextField("");
		JLabel     passwordLabel = new JLabel("Password:   ", JLabel.RIGHT);
		JTextField passwordField = new JPasswordField("");
		connectionPanel = new JPanel(false);
		connectionPanel.setLayout(new BoxLayout(connectionPanel, BoxLayout.X_AXIS));
		JPanel namePanel = new JPanel(false);
		namePanel.setLayout(new GridLayout(0, 1));
		namePanel.add(userNameLabel);
		namePanel.add(passwordLabel);
		JPanel fieldPanel = new JPanel(false);
		fieldPanel.setLayout(new GridLayout(0, 1));
		fieldPanel.add(userNameField);
		fieldPanel.add(passwordField);
		connectionPanel.add(namePanel);
		connectionPanel.add(fieldPanel);
	
        // Connect or quit
		if(JOptionPane.showOptionDialog(null, connectionPanel, 
	                                        ConnectTitle,
	                                        JOptionPane.OK_CANCEL_OPTION, 
	                                        JOptionPane.INFORMATION_MESSAGE,
	                                        null, ConnectOptionNames, 
	                                        ConnectOptionNames[0]) != 0) 
        {
		    System.exit(0);
		}
		
        this.email = userNameField.getText();
        this.password = passwordField.getText();
    }
}