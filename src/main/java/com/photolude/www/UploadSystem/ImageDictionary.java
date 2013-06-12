package com.photolude.www.UploadSystem;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class ImageDictionary {
	private static ImageDictionary instance = null;
	private Map<ImageList,Image> dictionary;
	
	private ImageDictionary()
	{
		dictionary = new HashMap<ImageList,Image>();
	}
	
	public static ImageDictionary GetInstance()
	{
		if(instance == null)
		{
			instance = new ImageDictionary();
		}
		return instance;
	}
	
	public Image Add(ImageList key, Image image)
	{
		dictionary.put(key, image);
		return image;
	}
	
	public Image Get(ImageList key)
	{
		return dictionary.get(key);
	}
}
