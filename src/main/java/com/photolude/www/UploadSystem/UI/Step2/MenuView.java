package com.photolude.www.UploadSystem.UI.Step2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import com.photolude.UI.Common.ImageButton;
import com.photolude.UI.wizard.ImageDictionary;

/**
 * The menu view for step 2
 * 
 * @author Nikody Keating
 *
 */
public class MenuView extends JComponent implements MouseListener {
	private static final String BACK_BUTTON_TAG = "back";
	private static final String BACK_BOTTON_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/Back_Enabled.png";
	private static final String BACK_BUTTON_DISABLED_TAG = "back.disabled";
	private static final String BACK_BOTTON_DISABLED_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/Back_Disabled.png";
	private static final String BACK_BUTTON_OVER_TAG = "back.over";
	private static final String BACK_BUTTON_OVER_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/Back_Over.png";
	private static final String BACK_BUTTON_DOWN_TAG = "back.down";
	private static final String BACK_BUTTON_DOWN_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/Back_Down.png";
	
	private static final String ADD_SELECTED_TAG = "addselected";
	private static final String ADD_SELECTED_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/AddSelected_Enabled.png";
	private static final String ADD_SELECTED_DISABLED_TAG = "addselected.disabled";
	private static final String ADD_SELECTED_DISABLED_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/AddSelected_Disabled.png";
	private static final String ADD_SELECTED_OVER_TAG = "addselected.over";
	private static final String ADD_SELECTED_OVER_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/AddSelected_Over.png";
	private static final String ADD_SELECTED_DOWN_TAG = "addselected.down";
	private static final String ADD_SELECTED_DOWN_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/AddSelected_Down.png";
	
	private static final String UPLOAD_PHOTOS_TAG = "uploadphotos";
	private static final String UPLOAD_PHOTOS_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/UploadPhotos_Enabled.png";
	private static final String UPLOAD_PHOTOS_DISABLED_TAG = "uploadphotos.disabled";
	private static final String UPLOAD_PHOTOS_DISABLED_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/UploadPhotos.png";
	private static final String UPLOAD_PHOTOS_OVER_TAG = "uploadphotos.over";
	private static final String UPLOAD_PHOTOS_OVER_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/UploadPhotos_Over.png";
	private static final String UPLOAD_PHOTOS_DOWN_TAG = "uploadphotos.down";
	private static final String UPLOAD_PHOTOS_DOWN_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/UploadPhotos_Down.png";
	
	private static final String REMOVE_TAG = "remove";
	private static final String REMOVE_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/RemoveSelected_Enabled.png";
	private static final String REMOVE_DISABLED_TAG = REMOVE_TAG + ".disabled";
	private static final String REMOVE_DISABLED_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/RemoveSelected_Disabled.png";
	private static final String REMOVE_OVER_TAG = REMOVE_TAG + ".over";
	private static final String REMOVE_OVER_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/RemoveSelected_Over.png";
	private static final String REMOVE_DOWN_TAG = REMOVE_TAG + ".down";
	private static final String REMOVE_DOWN_RESOURCE = "http://${server}/Scripts/UploadSystem/Upload/RemoveSelected_Down.png";
	
	private static final long serialVersionUID = 1L;
	private ArrayList<IMenuViewListener> m_alListeners;
	private ImageButton m_ibAddSelected;
	private ImageButton m_ibUploadAll;
	private ImageButton m_ibBack;
	private ImageButton m_ibRemoveSelected;
	
	public MenuView()
	{
		m_alListeners = new ArrayList<IMenuViewListener>();
	}
	
	public void AddResourceList(HashMap<String, String> resourceList)
	{
		if(!resourceList.containsKey(BACK_BUTTON_TAG)) resourceList.put(BACK_BUTTON_TAG, BACK_BOTTON_RESOURCE);
		if(!resourceList.containsKey(BACK_BUTTON_OVER_TAG)) resourceList.put(BACK_BUTTON_OVER_TAG, BACK_BUTTON_OVER_RESOURCE);
		if(!resourceList.containsKey(BACK_BUTTON_DISABLED_TAG)) resourceList.put(BACK_BUTTON_DISABLED_TAG, BACK_BOTTON_DISABLED_RESOURCE);
		if(!resourceList.containsKey(BACK_BUTTON_DOWN_TAG)) resourceList.put(BACK_BUTTON_DOWN_TAG, BACK_BUTTON_DOWN_RESOURCE);
		
		if(!resourceList.containsKey(ADD_SELECTED_TAG)) resourceList.put(ADD_SELECTED_TAG, ADD_SELECTED_RESOURCE);
		if(!resourceList.containsKey(ADD_SELECTED_DISABLED_TAG)) resourceList.put(ADD_SELECTED_DISABLED_TAG, ADD_SELECTED_DISABLED_RESOURCE);
		if(!resourceList.containsKey(ADD_SELECTED_OVER_TAG)) resourceList.put(ADD_SELECTED_OVER_TAG, ADD_SELECTED_OVER_RESOURCE);
		if(!resourceList.containsKey(ADD_SELECTED_DOWN_TAG)) resourceList.put(ADD_SELECTED_DOWN_TAG, ADD_SELECTED_DOWN_RESOURCE);
		
		if(!resourceList.containsKey(UPLOAD_PHOTOS_TAG)) resourceList.put(UPLOAD_PHOTOS_TAG, UPLOAD_PHOTOS_RESOURCE);
		if(!resourceList.containsKey(UPLOAD_PHOTOS_DISABLED_TAG)) resourceList.put(UPLOAD_PHOTOS_DISABLED_TAG, UPLOAD_PHOTOS_DISABLED_RESOURCE);
		if(!resourceList.containsKey(UPLOAD_PHOTOS_OVER_TAG)) resourceList.put(UPLOAD_PHOTOS_OVER_TAG, UPLOAD_PHOTOS_OVER_RESOURCE);
		if(!resourceList.containsKey(UPLOAD_PHOTOS_DOWN_TAG)) resourceList.put(UPLOAD_PHOTOS_DOWN_TAG, UPLOAD_PHOTOS_DOWN_RESOURCE);
		
		if(!resourceList.containsKey(REMOVE_TAG)) resourceList.put(REMOVE_TAG, REMOVE_RESOURCE);
		if(!resourceList.containsKey(REMOVE_DISABLED_TAG)) resourceList.put(REMOVE_DISABLED_TAG, REMOVE_DISABLED_RESOURCE);
		if(!resourceList.containsKey(REMOVE_OVER_TAG)) resourceList.put(REMOVE_OVER_TAG, REMOVE_OVER_RESOURCE);
		if(!resourceList.containsKey(REMOVE_DOWN_TAG)) resourceList.put(REMOVE_DOWN_TAG, REMOVE_DOWN_RESOURCE);
	}
	
	/**
	 * Initializes the menu view
	 */
	public void Initialize()
	{
		if(this.m_ibBack == null)
		{
			int nControlPanelWidth = 0;
			int nControlPanelHeight = 0;
			
			this.setAlignmentX(JComponent.LEFT_ALIGNMENT);
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			ImageDictionary dictionary = ImageDictionary.GetInstance();
			
			{
				m_ibBack = new ImageButton(dictionary.Get(BACK_BUTTON_TAG), dictionary.Get(BACK_BUTTON_OVER_TAG), dictionary.Get(BACK_BUTTON_DOWN_TAG), dictionary.Get(BACK_BUTTON_DISABLED_TAG));
				m_ibBack.addMouseListener(this);
				this.add(m_ibBack);
			}
			
			this.add(Box.createRigidArea(new Dimension(5, 1)));
			
			{
				m_ibAddSelected = new ImageButton(dictionary.Get(ADD_SELECTED_TAG), dictionary.Get(ADD_SELECTED_OVER_TAG), dictionary.Get(ADD_SELECTED_DOWN_TAG), dictionary.Get(ADD_SELECTED_DISABLED_TAG));
				m_ibAddSelected.addMouseListener(this);
				m_ibAddSelected.SetDisabled();
				this.add(m_ibAddSelected);
			
				nControlPanelWidth = dictionary.Get(ADD_SELECTED_TAG).getWidth(null);
				nControlPanelHeight = dictionary.Get(ADD_SELECTED_TAG).getHeight(null) + 10;
			}
			
			this.add(Box.createHorizontalGlue());
			
			{
				m_ibRemoveSelected = new ImageButton(dictionary.Get(REMOVE_TAG), dictionary.Get(REMOVE_OVER_TAG), dictionary.Get(REMOVE_DOWN_TAG), dictionary.Get(REMOVE_DISABLED_TAG));
				m_ibRemoveSelected.addMouseListener(this);
				m_ibRemoveSelected.SetDisabled();
				this.add(m_ibRemoveSelected, BorderLayout.WEST);
			}
			
			this.add(Box.createRigidArea(new Dimension(5, 1)));
			
			{
				m_ibUploadAll = new ImageButton(dictionary.Get(UPLOAD_PHOTOS_TAG), dictionary.Get(UPLOAD_PHOTOS_OVER_TAG), dictionary.Get(UPLOAD_PHOTOS_DOWN_TAG), dictionary.Get(UPLOAD_PHOTOS_DISABLED_TAG));
				m_ibUploadAll.addMouseListener(this);
				m_ibUploadAll.SetDisabled();
				this.add(m_ibUploadAll, BorderLayout.WEST);
			}
			
			this.setMinimumSize(new Dimension(nControlPanelWidth * (this.getComponentCount() + 1), nControlPanelHeight));
		}
		
	}

	/**
	 * Adds a listener to the menu
	 * 
	 * @param listener the new listener
	 */
	public void AddMenuViewListener(IMenuViewListener listener)
	{
		m_alListeners.add(listener);
	}
	
	/**
	 * Gets called when a mouse is clicked over this view
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Object source = arg0.getSource();
		for(int i = 0; i < m_alListeners.size(); i++)
		{
			if(source == m_ibAddSelected)
			{
				m_alListeners.get(i).OnAddSelected();
			}
			else if(source == m_ibRemoveSelected)
			{
				m_alListeners.get(i).OnRemoveSelected();
			}
			else if(source == m_ibBack)
			{
				m_alListeners.get(i).OnBack();
			}
			else if(source == m_ibUploadAll)
			{
				m_alListeners.get(i).OnUploadAll();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	/**
	 * Sets the add button either enabled or disabled
	 * @param bEnabled true is enabled, false is disabled
	 */
	public void SetAddSelectedEnabled(Boolean bEnabled)
	{
		if(bEnabled)
		{
			m_ibAddSelected.SetEnabled();
		}
		else
		{
			m_ibAddSelected.SetDisabled();
		}
	}
	
	/**
	 * Sets the remove button as either enabled or disabled
	 * @param bEnabled true is enabled, false is disabled
	 */
	public void SetRemoveSelectedEnabled(Boolean bEnabled)
	{
		if(bEnabled)
		{
			m_ibRemoveSelected.SetEnabled();
		}
		else
		{
			m_ibRemoveSelected.SetDisabled();
		}
	}
	
	/**
	 * Sets the upload all button either enabled or disabled
	 * @param bEnabled true is enabled, false is disabled
	 */
	public void SetUploadAllEnabled(Boolean bEnabled)
	{
		if(bEnabled)
		{
			m_ibUploadAll.SetEnabled();
		}
		else
		{
			m_ibUploadAll.SetDisabled();
		}
	}
}
