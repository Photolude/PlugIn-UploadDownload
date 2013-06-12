package com.photolude.www.UploadSystem.UI.Step2;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.photolude.UI.Common.LargeImageFolder;
import com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic.Directory;


public class Step2SelectFolder extends JComponent implements IMenuViewListener, ISelectableComponentListener {
	private static final long serialVersionUID = 1L;
	private ArrayList<IStep2Listener> m_listeners;
	private NavigationSelectionView m_compNavigationView;
	private ManifestView m_compManifestView;
	private MenuView m_compMenuView;
	private TextArea m_photoBoxName;
	
	public Step2SelectFolder(Directory directory)
	{
		this.m_listeners = new ArrayList<IStep2Listener>();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		m_compMenuView = new MenuView();
		m_compMenuView.AddMenuViewListener(this);
		m_compMenuView.Initialize();
		this.add(m_compMenuView);
		
		Label label = new Label();
		label.setText("Upload to Photobox:");
		this.m_photoBoxName = new TextArea();
		
		JPanel jpPhotoBoxArea = new JPanel();
		jpPhotoBoxArea.setLayout(new BoxLayout(jpPhotoBoxArea, BoxLayout.X_AXIS));
		jpPhotoBoxArea.add(this.m_photoBoxName);
		
		//this.add(jpPhotoBoxArea);
		
		
		this.add(Box.createRigidArea(new Dimension(10, 20)));
		
		JPanel jpLowerArea = new JPanel();
		jpLowerArea.setLayout(new BoxLayout(jpLowerArea, BoxLayout.X_AXIS));
		jpLowerArea.setAlignmentX(LEFT_ALIGNMENT);
		
		m_compNavigationView = new NavigationSelectionView();
		m_compNavigationView.Initialize(directory);
		m_compNavigationView.AddSelectedComponentListener(this);
		jpLowerArea.add(m_compNavigationView);
		
		m_compManifestView = new ManifestView();
		m_compManifestView.Initialize();
		m_compManifestView.AddSelectedComponentListener(this);
		jpLowerArea.add(m_compManifestView);
		
		this.add(jpLowerArea);
	}

	@Override
	public void OnAddSelected() {
		File[] files = m_compNavigationView.GetSelectedObjects();
		
		if(files != null)
		{
			new ImportTask(m_compManifestView, files);
			m_compMenuView.SetUploadAllEnabled(true);
		}
		
		m_compNavigationView.ResetSelectedObjects();
		m_compMenuView.SetAddSelectedEnabled(false);
	}

	@Override
	public void OnUploadAll() {
		
		File[] fFiles = m_compManifestView.GetItems();
		
		if(fFiles.length > 0)
		{
			m_compManifestView.Unload();
			m_compNavigationView.Unload();
			for(int i = 0; i < m_listeners.size(); i++)
			{
				m_listeners.get(i).Step2_Complete(fFiles);
			}
		}
	}

	@Override
	public void LargeFolderClick(LargeImageFolder lifFolder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ItemsSelected(Object source, int nCount) {
		if(source == m_compNavigationView)
		{
			m_compMenuView.SetAddSelectedEnabled(nCount > 0);
		}
		else if(source == m_compManifestView)
		{
			m_compMenuView.SetRemoveSelectedEnabled(nCount > 0);
		}
	}

	@Override
	public void OnRemoveSelected() {
		m_compManifestView.RemoveSelected();
		m_compMenuView.SetRemoveSelectedEnabled(false);
		
		m_compMenuView.SetUploadAllEnabled(m_compManifestView.GetFileCount() > 0);
	}

	@Override
	public void OnBack() {
		for(int i = 0; i < m_listeners.size(); i++)
		{
			m_listeners.get(i).Step2_Back();
		}
	}
	
	public void AddStep2Listeners(IStep2Listener listener)
	{
		m_listeners.add(listener);
	}
}
