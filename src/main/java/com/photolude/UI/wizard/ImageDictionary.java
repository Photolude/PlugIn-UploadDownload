package com.photolude.UI.wizard;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * This object is a dictionary of images which are downloaded preemptively
 * so that they can be used efficiently later after loading time
 * @author Nikody Keating
 *
 */
public class ImageDictionary {
	private static ImageDictionary instance = null;
	private Map<String, Image> dictionary = new HashMap<String,Image>();
	
	/**
	 * This constructor is private in order to ensure this object is a singleton
	 */
	private ImageDictionary()
	{
	}
	
	/**
	 * Gets an instance of this object
	 * @return the singleton instance of the Image Dictionary
	 */
	public static ImageDictionary GetInstance()
	{
		if(instance == null)
		{
			instance = new ImageDictionary();
		}
		return instance;
	}
	
	/**
	 * Adds an image object for the specified key
	 * 
	 * @param key the key which is to be indexed
	 * @param image the image which is to be indexed
	 * @return the image which was added
	 */
	public Image add(String key, Image image)
	{
		dictionary.put(key, image);
		return image;
	}
	
	/**
	 * Gets an image based off of an enum key value
	 * @param key the enum key value which references a specific image
	 * @return the image object. If the image is not indexed then null is returned
	 */
	public Image Get(String key)
	{
		return dictionary.get(key);
	}
}
