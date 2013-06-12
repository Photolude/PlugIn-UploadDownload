package com.photolude.www.UploadSystem.UI.Step2;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JComponent;

import com.photolude.UI.Common.IFolderSelectionListener;
import com.photolude.UI.Common.ISelectable;
import com.photolude.UI.Common.LargeImageFolder;


public class SelectableComponent extends JPanelAdv implements MouseListener, IFolderSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ISelectable m_uifLastFileSelected;
	protected Boolean m_bSupportFolders = false;
	private int m_nSelectedObjects = 0;
	private ArrayList<ISelectableComponentListener> m_listeners = new ArrayList<ISelectableComponentListener>();
	
	public SelectableComponent()
	{
		m_jpPanel.addMouseListener(this);
	}
	
	public void ResetSelectedObjects()
	{
		for(int i = 0; i < m_jpPanel.getComponentCount(); i++)
		{
			Object item = m_jpPanel.getComponent(i);
			
			if(ISelectable.class.isAssignableFrom(item.getClass()))
			{
				((ISelectable)item).SetSelected(false);
			}
		}
		
		m_nSelectedObjects = 0;
	}
	
	@Override
	public void mouseClicked(MouseEvent meMouseEvent) {
		Object source = meMouseEvent.getSource();
		
		if(!meMouseEvent.isControlDown() && !meMouseEvent.isShiftDown())
		{
			for(int i = 0; i < m_jpPanel.getComponentCount(); i++)
			{
				Object obj = m_jpPanel.getComponent(i);
				
				if(ISelectable.class.isAssignableFrom(obj.getClass()))
				{
					((ISelectable)obj).SetSelected(false);
				}
			}
		}
		
		if(meMouseEvent.isShiftDown() && m_uifLastFileSelected != null && ISelectable.class.isAssignableFrom(source.getClass()))
		{
			int nStartOffset = GetComponentOffset((JComponent)m_uifLastFileSelected);
			int nEndOffset = GetComponentOffset((JComponent)source);
			
			if(nStartOffset > nEndOffset)
			{
				int nOffset = nEndOffset;
				nEndOffset = nStartOffset;
				nStartOffset = nOffset;
			}
			
			for(int i = nStartOffset; i <= nEndOffset; i++)
			{
				Object obj = m_jpPanel.getComponent(i);
				
				if(ISelectable.class.isAssignableFrom(obj.getClass()))
				{
					((ISelectable)obj).SetSelected(true);
				}
			}
		}
		else if(m_bSupportFolders && source.getClass() == LargeImageFolder.class && m_uifLastFileSelected == source)
		{
			LargeImageFolder folder = (LargeImageFolder)source;
			Boolean bIsExpanded = folder.GetExpanded();
			
			//folder.mouseClicked(meMouseEvent);
			((ISelectable)source).SetSelected(true);
			
			if(bIsExpanded == folder.GetExpanded())
			{
				this.FireFolderSelected(folder);
			}
		}
		else if(ISelectable.class.isAssignableFrom(source.getClass()))
		{
			ISelectable file = (ISelectable)source;
			
			file.SetSelected(!file.GetSelectedState());
			
			if(file.GetSelectedState())
			{
				m_uifLastFileSelected = file;
			}
			else
			{
				m_uifLastFileSelected = null;
			}
		}
		
		if(m_bSupportFolders && source.getClass() == LargeImageFolder.class)
		{
			LargeImageFolder folder = (LargeImageFolder)source;
			folder.AddFolderSelectionListener(this);
			folder.mouseClicked(meMouseEvent);
			folder.RemoveFolderSelectionListener(this);
		}
		
		int nSelectedCount = 0;
		for(int i = 0; i < m_jpPanel.getComponentCount(); i++)
		{
			Object item = m_jpPanel.getComponent(i);
			if(ISelectable.class.isAssignableFrom(item.getClass()) &&
					((ISelectable)item).GetSelectedState())
			{
				nSelectedCount++;
			}
		}
		
		if(nSelectedCount != m_nSelectedObjects)
		{
			m_nSelectedObjects = nSelectedCount;
			for(int i = 0; i < m_listeners.size(); i++)
			{
				m_listeners.get(i).ItemsSelected(this, m_nSelectedObjects);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(ISelectable.class.isAssignableFrom(e.getSource().getClass()))
		{
			((ISelectable)e.getSource()).SetMouseOver(true);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(ISelectable.class.isAssignableFrom(e.getSource().getClass()))
		{
			((ISelectable)e.getSource()).SetMouseOver(false);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void AddSelectedComponentListener(ISelectableComponentListener listener)
	{
		m_listeners.add(listener);
	}

	private void FireFolderSelected(LargeImageFolder lifFolder)
	{
		for(int i = 0; i < m_listeners.size(); i++)
		{
			m_listeners.get(i).LargeFolderClick(lifFolder);
		}
	}

	@Override
	public void OnFolderSelected(LargeImageFolder source) {
		if(!m_bSupportFolders)
		{
			FireFolderSelected(source);
		}
	}

	@Override
	public void OnFolderExpanded(LargeImageFolder source) {
		FireFolderSelected(source);
	}
}
