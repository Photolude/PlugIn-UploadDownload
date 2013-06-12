package com.photolude.www.UploadSystem.UI.Step2;

import javax.swing.*;

public class JPanelAdv extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JPanel m_jpPanel;
	
	public JPanelAdv()
	{
		m_jpPanel = new JPanel();
	}

	protected int GetComponentOffset(JComponent component)
	{
		int retval = -1;
		for(int i = 0; i < m_jpPanel.getComponentCount() && retval == -1; i++)
		{
			if(m_jpPanel.getComponent(i) == component)
			{
				retval = i;
			}
		}
		
		return retval;
	}
}
