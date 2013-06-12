package com.photolude.www.UploadSystem.UI.Loading;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import com.photolude.www.UploadSystem.Styles;


public class ProgressBar extends Container {
	private static final long serialVersionUID = 1L;

	private int m_nOnStep = 0;
	private int m_nTotalSteps = 1;
	private String m_sStatus = "";
	
	public void paint(Graphics g) {
		int nDrawingWidth = this.getWidth() - 1;
		g.setColor(Color.darkGray);
		g.drawRect(0, 0, nDrawingWidth, 50);
		g.setColor(Color.green);
		g.fillRect(1, 1, (nDrawingWidth - 2) * m_nOnStep / m_nTotalSteps, 48);
		
		g.setColor(Color.black);
		g.setFont(Styles.NormalFont);
		g.drawString(m_sStatus, 2, 72);
	}
	
	public void SetStatus(String sNewStatus)
	{
		this.m_sStatus = sNewStatus;
	}
	
	public void SetTotalSteps(int nTotalSteps)
	{
		this.m_nTotalSteps = nTotalSteps;
	}
	
	public void SetOnStep(int nOnStep)
	{
		this.m_nOnStep = nOnStep;
		this.repaint();
	}
}
