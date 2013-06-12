package com.photolude.www.UploadSystem.BusinessLogic.FileUploading;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.metadata.IIOMetadataNode;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


public class ImageMetadata {
	private Date modifiedDate;
	private Date createdDate;
	private String name;
	
	public ImageMetadata(File file)
	{
		this.modifiedDate = new Date(file.lastModified());
		this.name = file.getName();
		this.createdDate = this.modifiedDate;
	}
	
	public String GetName()
	{
		return this.name;
	}
	
	public Date GetModifiedDate()
	{
		return this.modifiedDate;
	}
	
	public Date GetCreatedDate()
	{
		return this.createdDate;
	}
	
	void displayMetadata(Node root) {
        displayMetadata(root, 0);
    }
	
	int Parse2Bytes(byte[] bytes, int offset, int format)
	{
		int retval;
		
		if(format == 0)
		{
			retval = ((int)bytes[offset] << 8) + (int)bytes[offset + 1];
		}
		else
		{
			retval = ((int)bytes[offset + 1] << 8) + (int)bytes[offset];
		}
		return retval;
	}
	
	int Parse4Bytes(byte[] bytes, int offset, int format)
	{
		int retval;
		
		if(format == 0)
		{
			retval = ((int)bytes[offset] << 24) + ((int)bytes[offset + 1] << 16) + ((int)bytes[offset + 2] << 8) + (int)bytes[offset + 3];
		}
		else
		{
			retval = ((int)bytes[offset + 3] << 24) + ((int)bytes[offset + 2] << 16) + ((int)bytes[offset + 1] << 8) + (int)bytes[offset + 0];
		}
		return retval;
	}
	
	void displayMetadata(Node node, int level) {
        // print open tag of element
        NamedNodeMap map = node.getAttributes();
        if (map != null) {

            // print attribute values
            int length = map.getLength();
            
            if(length > 0)
            {
            	Node attr = (IIOMetadataNode)map.item(0);
	            String nodeName = attr.getNodeName();
	            String nodeValue = attr.getNodeValue();
	            if( nodeName.equals("MarkerTag") && nodeValue.equals("225"))
            	{
	            	IIOMetadataNode metaData = (IIOMetadataNode)node;
	            	byte[] object = (byte[])metaData.getUserObject();
	            	char[] text = new char[object.length];
	            	for(int i = 0; i < object.length; i++)
	            	{
	            		text[i] = (char)object[i];
	            	}
	            	
	            	int initialOffset = 6;
	            	
	            	if(text[0] == 'E' && text[1] == 'x' && text[2] == 'i' && text[3] == 'f')
	            	{
		            	int format = (object[0 + initialOffset] == 77 && object[1 + initialOffset] == 77)? 0 : 1;
		            	//int startAddress = ((int)object[7 + initialOffset] << 24) + ((int)object[6 + initialOffset] << 16) + ((int)object[5 + initialOffset] << 8) + object[4 + initialOffset];
		            	int exifTagCount = (object[9 + initialOffset] << 8) + object[8 + initialOffset];
		            	int byteOffset = 10 + initialOffset;
		            	for(int i = 0; i < exifTagCount; i++)
	            		{
		            		int markerType = Parse2Bytes(object, byteOffset, format);
		            		int dataType = Parse2Bytes(object, byteOffset + 2, format);
		            		
		            		//
		            		// Component count is currently not being used
		            		//int components =  Parse4Bytes(object, byteOffset + 4, format);
		            		
		            		int dataOrOffset = Parse4Bytes(object, byteOffset + 8, format);
		            		
		            		String value = "";
	            			if(dataType == 2)
	            			{
	            				for(int ii = dataOrOffset + initialOffset; text[ii] != '\0'; ii++)
	            				{
	            					value += text[ii];
	            				}
	            				
	            				if(markerType == 306)
	                			{
	                				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
	                				try {
										this.createdDate = dateFormat.parse(value);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
	                			}
	            			}
	            			
	            			byteOffset += 12;
	            		}
	            	}
            	}
            }
        }

        Node child = node.getFirstChild();
        if (child == null) {
            // no children, so close element and return
            return;
        }

        // children, so close current tag
        while (child != null) {
            // print children recursively
            displayMetadata(child, level + 1);
            child = child.getNextSibling();
        }

        // print close tag of element
    }
}
