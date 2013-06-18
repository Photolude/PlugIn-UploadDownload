package com.photolude.www.UploadSystem.UI.Step1;

import com.photolude.UI.wizard.IWizardPage;
import com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic.Directory;

public interface IPathNavigator extends IWizardPage {
	void setDirectory(Directory directory);
}
