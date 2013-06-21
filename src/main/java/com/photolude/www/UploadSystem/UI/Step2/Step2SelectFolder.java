package com.photolude.www.UploadSystem.UI.Step2;

import java.awt.*;
import java.io.File;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.photolude.UI.Common.LargeImageFolder;
import com.photolude.UI.wizard.IWizardContext;
import com.photolude.UI.wizard.WizardPageBase;
import com.photolude.www.UploadSystem.IUploadPage;
import com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic.Directory;
import com.photolude.www.UploadSystem.UI.Step1.IPathNavigator;
import com.photolude.www.UploadSystem.UI.Step1.Step1DriveRootSelection;


public class Step2SelectFolder extends WizardPageBase implements IMenuViewListener, ISelectableComponentListener, IPathNavigator {
	private static final long serialVersionUID = 1L;
	private NavigationSelectionView compNavigationView;
	private ManifestView compManifestView;
	private MenuView compMenuView;
	private TextArea photoBoxName;
	
	public void setDirectory(Directory directory)
	{
		compNavigationView.Initialize(directory);
	}
	
	public Step2SelectFolder()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.compMenuView = new MenuView();
		this.compMenuView.AddMenuViewListener(this);
		this.add(this.compMenuView);
		
		Label label = new Label();
		label.setText("Upload to Photobox:");
		this.photoBoxName = new TextArea();
		
		JPanel jpPhotoBoxArea = new JPanel();
		jpPhotoBoxArea.setLayout(new BoxLayout(jpPhotoBoxArea, BoxLayout.X_AXIS));
		jpPhotoBoxArea.add(this.photoBoxName);
		
		//this.add(jpPhotoBoxArea);
		
		this.add(Box.createRigidArea(new Dimension(10, 20)));
		
		JPanel jpLowerArea = new JPanel();
		jpLowerArea.setLayout(new BoxLayout(jpLowerArea, BoxLayout.X_AXIS));
		jpLowerArea.setAlignmentX(LEFT_ALIGNMENT);
		
		this.compNavigationView = new NavigationSelectionView();
		this.compNavigationView.AddSelectedComponentListener(this);
		jpLowerArea.add(this.compNavigationView);
		
		this.compManifestView = new ManifestView();
		this.compManifestView.Initialize();
		this.compManifestView.AddSelectedComponentListener(this);
		jpLowerArea.add(this.compManifestView);
		
		this.add(jpLowerArea);
	}

	/**
	 * Called when the add button is pressed on the menu
	 */
	@Override
	public void OnAddSelected() {
		File[] files = this.compNavigationView.GetSelectedObjects();
		
		if(files != null)
		{
			new ImportTask(this.compManifestView, files);
			this.compMenuView.SetUploadAllEnabled(true);
		}
		
		this.compNavigationView.ResetSelectedObjects();
		this.compMenuView.SetAddSelectedEnabled(false);
	}

	/**
	 * Called when the upload all button is pressed on the menu
	 */
	@Override
	public void OnUploadAll() {
		
		File[] files = this.compManifestView.GetItems();
		
		if(files.length > 0)
		{
			((IUploadPage)this.nextPage).setFilesToUpload(files);
			this.compManifestView.Unload();
			this.compNavigationView.Unload();
			this.firePageNext();
		}
	}
	
	/**
	 * Called when the remove button is pressed on the menu
	 */
	@Override
	public void OnRemoveSelected() {
		this.compManifestView.RemoveSelected();
		this.compMenuView.SetRemoveSelectedEnabled(false);
		
		this.compMenuView.SetUploadAllEnabled(this.compManifestView.GetFileCount() > 0);
	}

	/**
	 * Called when the back button is pressed on the menu
	 */
	@Override
	public void OnBack() {
		this.firePageBack();
	}

	/**
	 * Gets called when a large folder is clicked
	 */
	@Override
	public void LargeFolderClick(LargeImageFolder lifFolder) {
		// Do nothing
	}

	/**
	 * Gets called when items are selected by either the navigation or the manifest views
	 */
	@Override
	public void ItemsSelected(Object source, int nCount) {
		if(source == this.compNavigationView)
		{
			this.compMenuView.SetAddSelectedEnabled(nCount > 0);
		}
		else if(source == this.compManifestView)
		{
			this.compMenuView.SetRemoveSelectedEnabled(nCount > 0);
		}
	}

	@Override
	public HashMap<String, String> initialize(IWizardContext context) {
		HashMap<String, String> retval = super.initialize(context);
		
		if(!retval.containsKey(Step1DriveRootSelection.FOLDER_TOKEN)) retval.put(Step1DriveRootSelection.FOLDER_TOKEN, Step1DriveRootSelection.FOLDER_IMAGE_PATH);
		
		this.compMenuView.AddResourceList(retval);
		
		return retval;
	}

	@Override
	public JComponent getUI() {
		this.compMenuView.Initialize();
		
		return this;
	}
}
