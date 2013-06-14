package com.photolude.www.UploadSystem.UI.Step2;

import java.awt.*;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

import com.photolude.UI.Common.SelectableComponent;
import com.photolude.UI.Common.UIFile;
import com.photolude.UI.Common.UILabel;
import com.photolude.www.UploadSystem.Styles;

/**
 * Defines a UI component which will display the manifest
 * 
 * @author Nikody Keating
 *
 */
public class ManifestView extends SelectableComponent {

	private static final long serialVersionUID = 1L;
	public static int s_ControlPanelHeight = 80;
	private JScrollPane m_spScrollArea;
	
	/**
	 * Initializes the Manifest view
	 */
	public void Initialize()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Styles.Scroll_Background);
		
		this.add(new UILabel("Upload Manifest:"));
		
		m_jpPanel.setLayout(new BoxLayout(m_jpPanel, BoxLayout.Y_AXIS));
		m_jpPanel.setBackground(Styles.Scroll_Background);
		m_jpPanel.add(Box.createGlue());
		
		m_spScrollArea = new JScrollPane(m_jpPanel);
		m_spScrollArea.setMinimumSize(new Dimension(100, 20));
		m_spScrollArea.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		this.add(m_spScrollArea);
	}
	
	/**
	 * Adds files to the manifest
	 * @param files
	 */
	public synchronized void AddManifestItems(File[] files) 
	{
		m_jpPanel.remove(m_jpPanel.getComponentCount() - 1);
		
		for(int i = 0; i < files.length; i++)
		{
			Boolean bFound = false;
			for(int ii = 0; ii < m_jpPanel.getComponentCount() && !bFound; ii++)
			{
				Object obj = m_jpPanel.getComponent(ii);
				
				if(obj.getClass() == UIFile.class &&
						((UIFile)obj).GetFile().getAbsolutePath() == files[i].getAbsolutePath())
				{
					bFound = true;
				}
			}
			
			if(!bFound)
			{
				UIFile file = new UIFile(files[i], 10);
				
				file.addMouseListener(this);
				m_jpPanel.add(file);
			}
		}
		m_jpPanel.add(Box.createGlue());
		
		invalidate();
		m_spScrollArea.validate();
		m_spScrollArea.repaint();
		this.validate();
	}
	
	/**
	 * Removes the selected files in the manifest
	 */
	public void RemoveSelected()
	{
		for(int i = m_jpPanel.getComponentCount() - 1; i >= 0 ; i--)
		{
			Object component = m_jpPanel.getComponent(i);
			
			if(component.getClass() == UIFile.class &&
				((UIFile)component).GetSelectedState() == true)
			{
				((UIFile)component).StopLoading();
				m_jpPanel.remove(i);
			}
		}
		
		m_jpPanel.validate();
		repaint();
	}
	
	/**
	 * Gets all the files in the manifest
	 * @return
	 */
	public File[] GetItems()
	{
		File[] retval = new File[GetFileCount()];
		
		for(int i = 0 ; i < GetFileCount(); i++)
		{
			Object obj = m_jpPanel.getComponent(i);
			if(UIFile.class.isAssignableFrom(obj.getClass()))
			{
				retval[i] = ((UIFile)m_jpPanel.getComponent(i)).GetFile();
			}
		}
		
		return retval;
	}
	
	/**
	 * Gets the number of items in the manifest
	 * @return the file count
	 */
	public int GetFileCount()
	{
		return m_jpPanel.getComponentCount() - 1;
	}
	
	/**
	 * Unloads the manifest view
	 */
	public void Unload()
	{
		for(int i = m_jpPanel.getComponentCount() - 1; i >= 0 ; i--)
		{
			Object component = m_jpPanel.getComponent(i);
			m_jpPanel.remove(i);
			
			if(component.getClass() == UIFile.class)
			{
				((UIFile)component).StopLoading();
			}
		}
	}
}
