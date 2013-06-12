package com.photolude.UI.Common;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class UIHelper {
	public static Dimension SetSizeBasedOnString(Graphics g, Font fFont, String string, Component component, int nPaddingX)
	{
		Graphics2D graphics2D = (Graphics2D)g;
		
		Rectangle2D rect = fFont.getStringBounds(string, graphics2D.getFontRenderContext());
		Dimension retval = new Dimension((int)rect.getWidth() + nPaddingX, (int)rect.getHeight());
		
		component.setMaximumSize(retval);
		component.setMinimumSize(retval);
		component.setPreferredSize(retval);
		
		component.invalidate();
		
		Container parent = component.getParent();
		
		if(parent != null)
		{
			parent.validate();
		}
		
		return retval;
	}
	
	public static void DrawGradient(Graphics g, int x, int y, int width, int height, Color color1, Color color2)
	{
		Graphics2D graphics2D = (Graphics2D)g;
		
		{
			GradientPaint gradient = new GradientPaint(width / 2, y, color1, width / 2, height / 2, color2);
			
			Rectangle2D rect = new Rectangle2D.Double(x, y, width, height / 2);
			
			graphics2D.setPaint(gradient);
			graphics2D.fill(rect);
		}
		
		{
			GradientPaint gradient = new GradientPaint(width / 2, height / 2, color2, width / 2, height, color1);
			
			Rectangle2D rect = new Rectangle2D.Double(x, y + height / 2, width, height);
			
			graphics2D.setPaint(gradient);
			graphics2D.fill(rect);
		}
	}
}
