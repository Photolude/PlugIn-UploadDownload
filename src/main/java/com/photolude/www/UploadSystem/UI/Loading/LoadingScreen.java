package com.photolude.www.UploadSystem.UI.Loading;

import java.applet.Applet;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.photolude.UI.Common.UILabel;
import com.photolude.www.UploadSystem.ImageDictionary;
import com.photolude.www.UploadSystem.ImageList;
import com.photolude.www.UploadSystem.Styles;
import com.photolude.www.UploadSystem.BusinessLogic.FileSystemLogic.Directory;
import com.photolude.www.UploadSystem.UI.Step1.*;


public class LoadingScreen extends JComponent implements Runnable {
	private static final long serialVersionUID = 1L;
	private ProgressBar m_progressBar;
	private LinkedHashMap<ImageList, String> m_itemsToLoad;
	private String m_sFolderImage;
	private String m_sPictureImage;
	private Applet m_applet;
	private List<ILoadingListener> m_listeners;

	public LoadingScreen(Applet applet, String m_server)
	{
		this.m_applet = applet;
		this.m_listeners = new LinkedList<ILoadingListener>();
		
		this.m_itemsToLoad = new LinkedHashMap<ImageList, String>();
		this.m_itemsToLoad.put(ImageList.UploadPhotos_Disabled, "http://" + m_server + "/Scripts/UploadSystem/Upload/UploadPhotos.png");
		this.m_itemsToLoad.put(ImageList.UploadPhotos, "http://" + m_server + "/Scripts/UploadSystem/Upload/UploadPhotos_Enabled.png");
		this.m_itemsToLoad.put(ImageList.UploadPhotos_Over, "http://" + m_server + "/Scripts/UploadSystem/Upload/UploadPhotos_Over.png");
		this.m_itemsToLoad.put(ImageList.UploadPhotos_Down, "http://" + m_server + "/Scripts/UploadSystem/Upload/UploadPhotos_Down.png");
		
		this.m_itemsToLoad.put(ImageList.AddSelected, "http://" + m_server + "/Scripts/UploadSystem/Upload/AddSelected_Enabled.png");
		this.m_itemsToLoad.put(ImageList.AddSelected_Disabled, "http://" + m_server + "/Scripts/UploadSystem/Upload/AddSelected_Disabled.png");
		this.m_itemsToLoad.put(ImageList.AddSelected_Down, "http://" + m_server + "/Scripts/UploadSystem/Upload/AddSelected_Down.png");
		this.m_itemsToLoad.put(ImageList.AddSelected_Over, "http://" + m_server + "/Scripts/UploadSystem/Upload/AddSelected_Over.png");
		
		this.m_itemsToLoad.put(ImageList.Back, "http://" + m_server + "/Scripts/UploadSystem/Upload/Back_Enabled.png");
		this.m_itemsToLoad.put(ImageList.Back_Disabled, "http://" + m_server + "/Scripts/UploadSystem/Upload/Back_Disabled.png");
		this.m_itemsToLoad.put(ImageList.Back_Down, "http://" + m_server + "/Scripts/UploadSystem/Upload/Back_Down.png");
		this.m_itemsToLoad.put(ImageList.Back_Over, "http://" + m_server + "/Scripts/UploadSystem/Upload/Back_Over.png");
		
		this.m_itemsToLoad.put(ImageList.RemoveSelected, "http://" + m_server + "/Scripts/UploadSystem/Upload/RemoveSelected_Enabled.png");
		this.m_itemsToLoad.put(ImageList.RemoveSelected_Disabled, "http://" + m_server + "/Scripts/UploadSystem/Upload/RemoveSelected_Disabled.png");
		this.m_itemsToLoad.put(ImageList.RemoveSelected_Down, "http://" + m_server + "/Scripts/UploadSystem/Upload/RemoveSelected_Down.png");
		this.m_itemsToLoad.put(ImageList.RemoveSelected_Over, "http://" + m_server + "/Scripts/UploadSystem/Upload/RemoveSelected_Over.png");
		
		this.m_sFolderImage = "http://" + m_server + "/icons/folder.png";
		this.m_sPictureImage = "http://" + m_server + "/icons/pictures.png";
		
		this.m_progressBar = new ProgressBar();
		this.m_progressBar.SetTotalSteps((this.m_itemsToLoad.size() + 2) * 2);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(Step1DriveRootSelection.AlignLeft(new UILabel("Loading")));
		this.add(this.m_progressBar);
        this.add(Step1DriveRootSelection.AlignLeft(new UILabel("Please wait while the upload manager prepairs itself.", Styles.NormalFont)));
	}
	
	public void AddListener(ILoadingListener listener)
	{
		if(!this.m_listeners.contains(listener))
		{
			this.m_listeners.add(listener);
		}
	}
	
	public void Start()
	{
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		Set<ImageList> keys = this.m_itemsToLoad.keySet();
		int nOnStep = 0;
		
		//
		// Load System
		//
		ArrayList<Image> imageList = new ArrayList<Image>();
		try {
			Directory.FolderImage = this.m_applet.getImage(new URL(this.m_sFolderImage));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		imageList.add(Directory.FolderImage);
		this.m_progressBar.SetOnStep(++nOnStep);
		
		try {
			Directory.PicturesImage = this.m_applet.getImage(new URL(this.m_sPictureImage));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		imageList.add(Directory.PicturesImage);
		this.m_progressBar.SetOnStep(++nOnStep);
		
		ImageDictionary dictionary = ImageDictionary.GetInstance();
		
		ImageIO.setUseCache(false);
		for(ImageList item : keys)
		{
			if(this.m_itemsToLoad.containsKey(item))
			{
				Image image = null;
				this.m_progressBar.SetOnStep(++nOnStep);
				
				while(image == null)
				{
					try {
						System.out.println("Loading: " + this.m_itemsToLoad.get(item));
						image = ImageIO.read(new URL(this.m_itemsToLoad.get(item)));
						//Image image = this.m_applet.getImage(new URL(this.m_itemsToLoad.get(item)));
						
						image.getGraphics();
						
						if(image != null)
						{
							System.out.println("Succeeded Loading: " + this.m_itemsToLoad.get(item));
							dictionary.Add(item, image);
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
						this.m_progressBar.SetOnStep(++nOnStep);
					}
				}
				catch(Exception e)
				{
				}
			}
		}
		
		for(ILoadingListener listener : this.m_listeners)
		{
			listener.LoadingCompleted();
		}
	}
}
