package com.photolude.www.dialogs;

import java.awt.*;
import javax.swing.*;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import com.photolude.www.WebClient.HttpSessionClient;
import com.photolude.www.WebClient.IHttpSessionClient;

/**
 * A login dialog which asks for email and password
 * 
 * @author Nikody Keating
 *
 */
public class LogonDialog implements ILogonSystem
{
	private JPanel connectionPanel = null;
	private JTextField userNameField = null;
	private JTextField passwordField = null;
	
    private String email;
    private String password;
    
    private String logonPage = null;
    public String getLogonPage() { return this.logonPage; }
    public void setLogonPage(String page) { this.logonPage = page; }
    
    private IHttpSessionClient httpClient = null;
    public IHttpSessionClient getHttpClient() { return this.httpClient; }
    public void setHttpClient(IHttpSessionClient client) { this.httpClient = client; }

    /**
     * Creates a login dialog box and displays it.  The constructor will conclude
     * when the dialog is complete.
     */
    public LogonDialog() {
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
	 	// Create the labels and text fields.
		JLabel     userNameLabel = new JLabel("Email:   ", JLabel.RIGHT);
	 	this.userNameField = new JTextField("");
		JLabel     passwordLabel = new JLabel("Password:   ", JLabel.RIGHT);
		this.passwordField = new JPasswordField("");
		this.connectionPanel = new JPanel(false);
		this.connectionPanel.setLayout(new BoxLayout(this.connectionPanel, BoxLayout.X_AXIS));
		JPanel namePanel = new JPanel(false);
		namePanel.setLayout(new GridLayout(0, 1));
		namePanel.add(userNameLabel);
		namePanel.add(passwordLabel);
		JPanel fieldPanel = new JPanel(false);
		fieldPanel.setLayout(new GridLayout(0, 1));
		fieldPanel.add(this.userNameField);
		fieldPanel.add(this.passwordField);
		this.connectionPanel.add(namePanel);
		this.connectionPanel.add(fieldPanel);
    }
    
    private void showDialog()
    {
    	// Connect or quit
		if(JOptionPane.showOptionDialog(null, this.connectionPanel, 
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

	@Override
	public void logon() {
		if(this.logonPage != null)
		{
			showDialog();
			
			JSONObject requestData = new JSONObject();
			try {
				requestData.put("email", this.email);
				requestData.put("password", this.password);
				
				HttpResponse response = this.httpClient.postJSON(this.logonPage, requestData);
				
				JSONObject jsonResponse = HttpSessionClient.convertResponseToJSON(response);
				if(jsonResponse != null)
				{
					String result = jsonResponse.getString("result");
					if(result == "Access Denied")
					{
						//TODO: Show message box
					}
					else if(result != "Succeeded")
					{
						//TODO: Show login failed
					}
				}
				else
				{
					//TODO: Notify that could not connect to the server
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			//TODO: display configuration error message
		}
	}
}