package com.photolude.UI.wizard;

import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.photolude.UI.Common.ComponentHelper;
import com.photolude.UI.Common.UILabel;
import com.photolude.www.UploadSystem.Styles;
import com.photolude.www.UploadSystem.UI.Loading.ProgressBar;

/**
 * The screen which shows a progress bar and loads web resources
 * 
 * @author Nikody Keating
 *
 */
public class LoadingScreen extends JComponent implements Runnable {
	private static final long serialVersionUID = 1L;
	private ProgressBar progressBar;
	private HashMap<String, String> tokenOverrideMap;
	private IWizardContext context;
	private List<ILoadingListener> listeners = new LinkedList<ILoadingListener>();
	private IWizardPage firstPage;

	/**
	 * Creates a loading screen and registers resources which are to be loaded
	 * @param applet the applet the loader is attached to
	 * @param m_server the server to download the images from
	 */
	public LoadingScreen(IWizardContext context, HashMap<String,String> tokenOverrideMap)
	{
		this.context = context;
		this.tokenOverrideMap = tokenOverrideMap;
		
		this.progressBar = new ProgressBar();
		this.progressBar.SetTotalSteps(1);
		this.progressBar.SetOnStep(0);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(ComponentHelper.AlignLeft(new UILabel("Loading")));
		this.add(this.progressBar);
        this.add(ComponentHelper.AlignLeft(new UILabel("Please wait while the upload manager prepairs itself.", Styles.NormalFont)));
	}
	
	/**
	 * Starts the loading process
	 */
	public void start(IWizardPage firstPage)
	{
		this.firstPage = firstPage;
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * The loading thread function
	 */
	@Override
	public void run() {
		HashMap<String, String> itemsToLoad = this.firstPage.initialize(this.context);
		this.progressBar.SetTotalSteps((itemsToLoad.size() + 2) * 2);
		Set<String> keys = itemsToLoad.keySet();
		int nOnStep = 0;
		
		//
		// Load System
		//
		ArrayList<Image> imageList = new ArrayList<Image>();
		
		this.progressBar.SetOnStep(++nOnStep);
		
		ImageDictionary dictionary = ImageDictionary.GetInstance();
		
		ImageIO.setUseCache(false);
		for(String item : keys)
		{
			if(itemsToLoad.containsKey(item))
			{
				Image image = null;
				this.progressBar.SetOnStep(++nOnStep);
				
				while(image == null)
				{
					try {
						String resourcePath = itemsToLoad.get(item);
						for(String token : this.tokenOverrideMap.keySet())
						{
							resourcePath = resourcePath.replace(token, this.tokenOverrideMap.get(token));
						}
						System.out.println("Loading: " + resourcePath);
						
						image = ImageIO.read(new URL(resourcePath));
						
						image.getGraphics();
						
						if(image != null)
						{
							System.out.println("Succeeded Loading: " + resourcePath);
							dictionary.add(item, image);
							imageList.add(image);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		long end = System.currentTimeMillis() + 3000;
		while(imageList.size() > 0 && System.currentTimeMillis() < end)
		{
			for(int i = 0; i < imageList.size(); i++)
			{
				try
				{
					int height = imageList.get(i).getHeight(null);
					if(height > 10)
					{
						System.out.println(height);
						imageList.remove(i);
						i--;
						this.progressBar.SetOnStep(++nOnStep);
					}
				}
				catch(Exception e)
				{
				}
			}
		}
		
		for(ILoadingListener listener : this.listeners)
		{
			listener.LoadingCompleted();
		}
	}

	public void addListener(ILoadingListener listener) {
		if(!this.listeners.contains(listener))
		{
			this.listeners.add(listener);
		}
	}

	public void removeListener(ILoadingListener listener) {
		if(this.listeners.contains(listener))
		{
			this.listeners.remove(listener);
		}
	}
}
