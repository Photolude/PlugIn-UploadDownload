package com.photolude.UI.Common;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import com.photolude.www.UploadSystem.Styles;


public class UIFile extends JComponent implements ItemListener, Runnable, ISelectable {
	private static final long serialVersionUID = 1L;

	private static int MAX_WIDTH_HEIGHT = 18;
	
	private Thread m_thread;
	private File m_fFile;
	private BufferedImage m_imgImage;
	private Checkbox m_cbCheckBox;
	private Dimension m_dimPreferedSize;
	private int m_nPaddingX;
	
	private Boolean m_bIsMouseOver;
	public Boolean m_bIsSelected;
	private Boolean m_bKillThread;
	
	public UIFile(File fFile, int nPaddingX)
	{
		m_fFile = fFile;
		m_bIsSelected = false;
		m_bIsMouseOver = false;
		m_nPaddingX = nPaddingX;
		
		this.setAlignmentX(LEFT_ALIGNMENT);
		
		m_bKillThread = false;
		m_thread = new Thread(this);
		m_thread.start();
	}
	
	public File GetFile()
	{
		return m_fFile;
	}
	
	public void paint(Graphics g) 
	{
		if(m_dimPreferedSize == null)
		{
			//m_dimPreferedSize = UIHelper.SetSizeBasedOnString(g, Styles.GetInstance().NormalFont, m_fFile.getName(), m_cbCheckBox, 20);
			m_dimPreferedSize = UIHelper.SetSizeBasedOnString(g, Styles.NormalFont, m_fFile.getName(), this, 25 + m_nPaddingX);
		}
		
		
		if(m_bIsSelected && m_bIsMouseOver)
		{
			UIHelper.DrawGradient(g, m_nPaddingX, 0, getWidth(), getHeight(), Styles.File_SelectedAndOverBackground1, Styles.File_SelectedAndOverBackground2);
		}
		else if(m_bIsSelected)
		{
			UIHelper.DrawGradient(g, m_nPaddingX, 0, getWidth(), getHeight(), Styles.File_SelectedBackground1, Styles.File_SelectedBackground2);
		}
		else if(m_bIsMouseOver)
		{
			UIHelper.DrawGradient(g, m_nPaddingX, 0, getWidth(), getHeight(), Styles.Folder_MouseOverBackground1, Styles.Folder_MouseOverBackground2);
		}
		
		if(m_imgImage != null)
		{
			int nVerticalSpacing = (int)(((float)this.getHeight() / 2) - ((float)m_imgImage.getHeight() / 2));
			g.drawImage(m_imgImage, m_nPaddingX, nVerticalSpacing, m_nPaddingX + m_imgImage.getWidth(), nVerticalSpacing + m_imgImage.getHeight(), 0, 0, m_imgImage.getWidth(), m_imgImage.getHeight(), null);
		}
		g.setFont(Styles.NormalFont);
		g.setColor(Styles.NormalFont_Color);
		g.drawString(m_fFile.getName(), m_nPaddingX + 20, getHeight() * 4 / 5);
	}
	
	public void itemStateChanged(ItemEvent ie)
	{
		Object item = ie.getSource();
		if(item != null && item == m_cbCheckBox)
		{
			this.m_bIsSelected = m_cbCheckBox.getState();
		}
	}

	public void SetMouseOver(Boolean bMouseOver)
	{
		if(m_bIsMouseOver != bMouseOver)
		{
			m_bIsMouseOver = bMouseOver;
			repaint();
		}
	}
	
	public void SetSelected(Boolean bSelected)
	{
		if(m_bIsSelected != bSelected)
		{
			m_bIsSelected = bSelected;
			repaint();
		}
	}
	
	public Boolean GetSelectedState()
	{
		return m_bIsSelected;
	}
	
	public void StopLoading()
	{
		m_bKillThread = true;
	}

	@Override
	public void run() {
		
		m_imgImage = LoadThumbnail(this);
		this.repaint();
	}
	
	private static synchronized BufferedImage LoadThumbnail(UIFile caller)
	{
		BufferedImage retval = null;
		
		if(!caller.m_bKillThread)
		{
			try 
			{
				Image largeImage = null;
				int nHeight = 0;
				int nWidth = 0;
				
				largeImage = ImageIO.read(caller.m_fFile);
				
				nWidth = largeImage.getWidth(null);
				nHeight = largeImage.getHeight(null);
				
				if(nHeight > nWidth)
				{
					nWidth = nWidth / (nHeight / MAX_WIDTH_HEIGHT);
					nHeight = MAX_WIDTH_HEIGHT;	
				}
				else
				{
					nHeight = nHeight / (nWidth / MAX_WIDTH_HEIGHT);
					nWidth = MAX_WIDTH_HEIGHT;
				}
				
				retval = new BufferedImage(nWidth, nHeight, BufferedImage.TYPE_INT_ARGB);
				retval.getGraphics().drawImage(largeImage, 0, 0, retval.getWidth(), retval.getHeight(), 0, 0, largeImage.getWidth(null), largeImage.getHeight(null), null);
			} 
			catch (IOException e) 
			{
				retval = null;
			}
		}
		return retval;
	}
}
