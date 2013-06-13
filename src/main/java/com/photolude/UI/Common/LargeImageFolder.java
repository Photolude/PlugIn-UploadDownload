package com.photolude.UI.Common;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JComponent;

import com.photolude.www.UploadSystem.Styles;
import com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic.Directory;

/**
 * This is a UI component which represents an image folder
 * 
 * @author Nikody Keating
 *
 */
public class LargeImageFolder extends JComponent implements MouseListener, ISelectable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<IFolderSelectionListener> folderSelectionListeners = new ArrayList<IFolderSelectionListener>();
	private Directory directory;
	private int leftOffset;
	private Dimension m_dSize;
	
	private Boolean m_bIsMouseOver = false;
	private Boolean m_bIsSelected = false;
	private Boolean m_bIsExpanded = false;
	
	public LargeImageFolder(Directory directory)
	{
		this.directory = directory;
		this.leftOffset = 0;

		this.addMouseListener(this);
		this.setAlignmentX(LEFT_ALIGNMENT);
	}
	
	public LargeImageFolder(Directory directory, int leftOffset)
	{
		this(directory);
		this.leftOffset = leftOffset;
	}
	
	public Directory GetDirectory()
	{
		return this.directory;
	}
	
	public int GetLeftOffset()
	{
		return this.leftOffset;
	}
	
	public void paint(Graphics g) 
	{
		// Dynamically calculate size information
		if(m_dSize == null)
		{
			UIHelper.SetSizeBasedOnString(g, Styles.FolderFont, directory.toString(), this, leftOffset + 25);
		}
		
		Color background1 = null;
		Color background2 = null;
		if(m_bIsSelected && m_bIsMouseOver)
		{
			background1 = Styles.Folder_SelectedAndOverBackground1;
			background2 = Styles.Folder_SelectedAndOverBackground2;
		}
		else if(m_bIsSelected)
		{
			background1 = Styles.Folder_SelectedBackground1;
			background2 = Styles.Folder_SelectedBackground2;
		}
		else if(m_bIsMouseOver)
		{
			background1 = Styles.Folder_MouseOverBackground1;
			background2 = Styles.Folder_MouseOverBackground2;
		}
		
		if(background1 != null)
		{
			UIHelper.DrawGradient(g, leftOffset, 0, getWidth(), getHeight(), background1, background2);
		}
		
		g.setColor(Color.darkGray);
		g.drawRect(leftOffset - 12, getHeight() / 2 - 5, 10, 10);
		
		if(this.m_bIsExpanded)
		{
			g.drawString("-", leftOffset - 8, 15);
		}
		else
		{
			g.drawString("+", leftOffset - 10, 15);
		}
		
		if(directory.IconImage != null)
		{
			int imageY = (getHeight() / 2) - (directory.IconImage.getIconHeight() / 2);
			directory.IconImage.paintIcon(this, g, leftOffset, imageY);
		}
		else if(directory.Image != null)
		{
			int imageY = (getHeight() / 2) - (directory.Image.getHeight(null) / 2);
			g.drawImage(directory.Image, leftOffset, imageY, null);
		}
		
		g.setColor(Styles.FolderFontColor);
        g.setFont(Styles.FolderFont);
        g.drawString(directory.toString(), leftOffset + 20, (getHeight() / 2) + 7);
    }
	
	public void AddFolderSelectionListener(IFolderSelectionListener newListener)
	{
		folderSelectionListeners.add(newListener);
	}
	
	public void RemoveFolderSelectionListener(IFolderSelectionListener listener)
	{
		folderSelectionListeners.remove(listener);
	}
	
	private void FireFolderSelectedEvent()
	{
		for(int i = 0; i < folderSelectionListeners.size(); i++)
		{
			folderSelectionListeners.get(i).OnFolderSelected(this);
		}
	}
	
	private void FireExpandEvent()
	{
		for(int i = 0; i < folderSelectionListeners.size(); i++)
		{
			folderSelectionListeners.get(i).OnFolderExpanded(this);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point mousePoint = e.getPoint();
		
		if(this.leftOffset - 12 < mousePoint.x &&
				mousePoint.x < this.leftOffset)
		{
			FireExpandEvent();
		}
		
		FireFolderSelectedEvent();
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		m_bIsMouseOver = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		m_bIsMouseOver = false;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
	}

	@Override
	public Boolean GetSelectedState() {
		return m_bIsSelected;
	}

	@Override
	public void SetSelected(Boolean bSelected) {
		
		if(m_bIsSelected != bSelected)
		{
			m_bIsSelected = bSelected;
			repaint();
		}
	}
	
	public void SetExpanded(Boolean bExpanded)
	{
		m_bIsExpanded = bExpanded;
	}
	
	public Boolean GetExpanded()
	{
		return m_bIsExpanded;
	}

	@Override
	public void SetMouseOver(Boolean bMouseOver) {
		// TODO Auto-generated method stub
		
	}
}
