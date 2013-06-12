package com.photolude.UI.Common;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;

import com.photolude.www.UploadSystem.Styles;


public class UILabel extends JComponent implements ComponentListener {

	private String m_strText;
	private Font m_fFont;
	private Dimension m_dimPreferedSize;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UILabel(String strText)
	{
		m_strText = strText;
		m_fFont = Styles.TitleFont;
		this.addComponentListener(this);
		this.setAlignmentX(JComponent.LEFT_ALIGNMENT);
	}
	
	public UILabel(String strText, Font fFont)
	{
		m_strText = strText;
		m_fFont = fFont;
		this.addComponentListener(this);
		//Dimension size = new Dimension(800, 25);
		//this.setMaximumSize(size);
		//this.setMinimumSize(size);
		//this.setPreferredSize(size);
		this.setAlignmentX(JComponent.LEFT_ALIGNMENT);
	}
	
	public void paint(Graphics g) 
	{
		//g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		g.setFont(m_fFont);
		g.drawString(m_strText, 0, this.getHeight() * 2 / 3);
		
		if(null == m_dimPreferedSize)
		{
			this.setAlignmentX(JComponent.LEFT_ALIGNMENT);
			m_dimPreferedSize = UIHelper.SetSizeBasedOnString(g, m_fFont, m_strText, this, 0);
		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
