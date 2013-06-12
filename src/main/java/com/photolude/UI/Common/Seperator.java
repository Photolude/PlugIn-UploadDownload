package com.photolude.UI.Common;

import java.awt.*;

import javax.swing.JComponent;

import com.photolude.www.UploadSystem.Styles;


public class Seperator extends JComponent {
	private static final long serialVersionUID = 1L;
	private String title;
	
	public Seperator(String title)
	{
		Dimension dimension = new Dimension(800, 40);
		this.setPreferredSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		
		this.title = title;
		this.setAlignmentX(LEFT_ALIGNMENT);
	}
	public void paint(Graphics g) 
	{
		FontMetrics metrics = this.getFontMetrics(Styles.Seperator_Font);
		
		g.setColor(Styles.Seperator_FontColor);
		g.setFont(Styles.Seperator_Font);
		g.drawString(title, 2, getHeight() - 5);
		
		g.setColor(Styles.Seperator_Color);
		g.drawLine(metrics.stringWidth(title) + 5, getHeight() - 10, getWidth(), getHeight() - 10);
    }
}
