package com.photolude.www.UploadSystem;

import java.io.File;

import com.photolude.UI.wizard.IWizardPage;

public interface IUploadPage extends IWizardPage {
	void setFilesToUpload(File[] files);
}
