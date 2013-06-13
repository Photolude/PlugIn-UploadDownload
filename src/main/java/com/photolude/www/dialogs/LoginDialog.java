package com.photolude.www.dialogs;

import java.awt.*;
import javax.swing.*;

/**
 * A login dialog which asks for email and password
 * 
 * @author Nikody Keating
 *
 */
public class LoginDialog
{
    private String email;
    /**
     * Gets the email provided
     * @return the email address of the user
     */
    public String getEmail(){return email;}
    
    private String password;
    /**
     * Gets the password provided in the dialog
     * @return the user's password
     */
    public String getPassword(){return password;}

    /**
     * Creates a login dialog box and displays it.  The constructor will conclude
     * when the dialog is complete.
     */
    public LoginDialog() {
        getIDandPassword();
    }

    // modal dialog to get user ID and password
    static final String[] ConnectOptionNames = { "Login", "Cancel" };
    static final String   ConnectTitle = "Login";
    
    /**
     * Constructs and displays the login dialog box
     */
    private void getIDandPassword()
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