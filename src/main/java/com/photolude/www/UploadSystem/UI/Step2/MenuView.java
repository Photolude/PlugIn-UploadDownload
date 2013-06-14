package com.photolude.www.UploadSystem.UI.Step2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import com.photolude.UI.Common.ImageButton;
import com.photolude.www.UploadSystem.*;

/**
 * The menu view for step 2
 * 
 * @author Nikody Keating
 *
 */
public class MenuView extends JComponent implements MouseListener {
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
	
	/**
	 * Initializes the menu view
	 */
	public void Initialize()
	{
		int nControlPanelWidth = 0;
		int nControlPanelHeight = 0;
		
		this.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		ImageDictionary dictionary = ImageDictionary.GetInstance();
		
		{
			m_ibBack = new ImageButton(dictionary.Get(ImageList.Back), dictionary.Get(ImageList.Back_Over), dictionary.Get(ImageList.Back_Down), dictionary.Get(ImageList.Back_Disabled));
			m_ibBack.addMouseListener(this);
			this.add(m_ibBack);
		}
		
		this.add(Box.createRigidArea(new Dimension(5, 1)));
		
		{
			m_ibAddSelected = new ImageButton(dictionary.Get(ImageList.AddSelected), dictionary.Get(ImageList.AddSelected_Over), dictionary.Get(ImageList.AddSelected_Down), dictionary.Get(ImageList.AddSelected_Disabled));
			m_ibAddSelected.addMouseListener(this);
			m_ibAddSelected.SetDisabled();
			this.add(m_ibAddSelected);
		
			nControlPanelWidth = dictionary.Get(ImageList.AddSelected).getWidth(null);
			nControlPanelHeight = dictionary.Get(ImageList.AddSelected).getHeight(null) + 10;
		}
		
		this.add(Box.createHorizontalGlue());
		
		{
			m_ibRemoveSelected = new ImageButton(dictionary.Get(ImageList.RemoveSelected), dictionary.Get(ImageList.RemoveSelected_Over), dictionary.Get(ImageList.RemoveSelected_Down), dictionary.Get(ImageList.RemoveSelected_Disabled));
			m_ibRemoveSelected.addMouseListener(this);
			m_ibRemoveSelected.SetDisabled();
			this.add(m_ibRemoveSelected, BorderLayout.WEST);
		}
		
		this.add(Box.createRigidArea(new Dimension(5, 1)));
		
		{
			m_ibUploadAll = new ImageButton(dictionary.Get(ImageList.UploadPhotos), dictionary.Get(ImageList.UploadPhotos_Over), dictionary.Get(ImageList.UploadPhotos_Down), dictionary.Get(ImageList.UploadPhotos_Disabled));
			m_ibUploadAll.addMouseListener(this);
			m_ibUploadAll.SetDisabled();
			this.add(m_ibUploadAll, BorderLayout.WEST);
		}
		
		this.setMinimumSize(new Dimension(nControlPanelWidth * (this.getComponentCount() + 1), nControlPanelHeight));
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
