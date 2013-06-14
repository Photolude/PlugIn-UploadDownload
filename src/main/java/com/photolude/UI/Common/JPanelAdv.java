package com.photolude.UI.Common;

import javax.swing.*;

/**
 * Defines an advanced panel which can be used to offer specific functionality around child component information
 * 
 * @author Nikody Keating
 *
 */
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

	/**
	 * Gets the child component's index offset
	 * @param component the component to find
	 * @return the index of the specified component
	 */
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
