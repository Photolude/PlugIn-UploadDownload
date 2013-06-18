package com.photolude.www.UploadSystem.UI.Step3;
import java.applet.Applet;
import java.io.File;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JComponent;

import com.photolude.UI.Common.UILabel;
import com.photolude.UI.wizard.WizardPageBase;
import com.photolude.www.UploadSystem.UI.Step2.IUploadPage;
import com.photolude.www.WebClient.IHttpSessionClient;

/**
 * Step 3 in the upload wizard, which uploads the files to the server
 * 
 * @author Nikody Keating
 *
 */
@SuppressWarnings("serial")
public class Step3Upload extends WizardPageBase implements IUploadStatusListener, IUploadPage {
	
	private UploadStatus uploadStatus;
	
	public void setHttpClient(IHttpSessionClient client){ this.uploadStatus.setHttpClient(client); }
	public IHttpSessionClient getHttpClient(){ return this.uploadStatus.getHttpClient(); }
	
	/**
	 * Initializes the object with the files to be uploaded 
	 * @param fFiles
	 */
	public Step3Upload()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(new Step3Menu());
		this.add(new UILabel("Uploading Files"));
		
		this.uploadStatus = new UploadStatus();
		uploadStatus.AddUploadStatusListener(this);
		this.add(uploadStatus);
	}
	
	/**
	 * Gets called when the upload has completed and invokes this components listeners
	 */
	public void UploadStatus_UploadComplete()
	{
		this.firePageNext();
	}

	@Override
	public HashMap<String, String> initialize(Applet applet) {
		HashMap<String, String> retval = super.initialize(applet);
		
		this.uploadStatus.initialize(applet);
		
		return retval;
	}

	@Override
	public JComponent getUI() {
		this.uploadStatus.start();
		return super.getUI();
	}

	@Override
	public void setFilesToUpload(File[] files) {
		this.uploadStatus.setFilesToUpload(files);
	}
}
