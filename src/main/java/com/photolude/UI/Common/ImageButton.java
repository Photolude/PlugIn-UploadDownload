package com.photolude.UI.Common;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JComponent;

public class ImageButton extends JComponent implements ComponentListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Image m_imgImage;
	private Image m_imgOver;
	private Image m_imgDown;
	private Image m_imgDisabled;
	private ImageButtonState m_bsState;
		
	public ImageButton(Image image, Image over, Image down, Image disabled)
	{
		m_bsState = ImageButtonState.Enabled;
		m_imgImage = image;
		m_imgOver = over;
		m_imgDown = down;
		m_imgDisabled = disabled;
		
		Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
		this.setMaximumSize(size);
		this.setMinimumSize(size);
		this.setPreferredSize(size);
		
		this.addComponentListener(this);
		this.addMouseListener(this);
	}
	
	public void paint(Graphics g)
	{
		Image image;
		
		switch(m_bsState)
		{
		case Disabled:
			image = m_imgDisabled;
			break;
		case Over:
			image = m_imgOver;
			break;
		case Down:
			image = m_imgDown;
			break;
		default:
		case Enabled:
			image = m_imgImage;
			break;
		}
		
		g.drawImage(image, 0, 0, m_imgImage.getWidth(null), m_imgImage.getHeight(null), null);
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(ImageButtonState.Disabled != m_bsState)
		{
			m_bsState = ImageButtonState.Over;
			this.invalidate();
			this.repaint();
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if(ImageButtonState.Disabled != m_bsState)
		{
			m_bsState = ImageButtonState.Enabled;
			this.invalidate();
			this.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if(ImageButtonState.Disabled != m_bsState)
		{
			if(arg0.getButton() == 1)
			{
				m_bsState = ImageButtonState.Down;
				repaint();
			}
			
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(ImageButtonState.Disabled != m_bsState)
		{
			m_bsState = ImageButtonState.Over;
			repaint();
		}
	}
	
	public void SetDisabled()
	{
		m_bsState = ImageButtonState.Disabled;
		this.repaint();
	}
	
	public void SetEnabled()
	{
		m_bsState = ImageButtonState.Enabled;
		this.repaint();
	}
}
