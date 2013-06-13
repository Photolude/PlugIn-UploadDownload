package com.photolude.UI.Common;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;

import com.photolude.www.UploadSystem.Styles;

/**
 * This is a UI component which draws a string at a specific size with a
 * specific font
 * 
 * @author Nikody Keating
 *
 */
public class UILabel extends JComponent implements ComponentListener {

	private String text;
	private Font font;
	private Dimension preferedSize;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a UI Label with the specified text
	 * @param strText the string to be rendered
	 */
	public UILabel(String text)
	{
		this.text = text;
		this.font = Styles.TitleFont;
		this.addComponentListener(this);
		this.setAlignmentX(JComponent.LEFT_ALIGNMENT);
	}
	
	/**
	 * Creates a Label with the specified text and font
	 * @param text the text to draw
	 * @param font the font to use
	 */
	public UILabel(String text, Font font)
	{
		this.text = text;
		this.font = font;
		this.addComponentListener(this);

		this.setAlignmentX(JComponent.LEFT_ALIGNMENT);
	}
	
	public void paint(Graphics g) 
	{
		g.setFont(this.font);
		g.drawString(this.text, 0, this.getHeight() * 2 / 3);
		
		if(null == preferedSize)
		{
			this.setAlignmentX(JComponent.LEFT_ALIGNMENT);
			preferedSize = UIHelper.SetSizeBasedOnString(g, this.font, this.text, this, 0);
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
