package com.photolude.UI.Common;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * This class is a helper class for UI utilities
 * 
 * @author Nikody Keating
 *
 */
public class UIHelper {
	/**
	 * Gets the dimensions of a string
	 * @param g a graphics object
	 * @param font the font to use
	 * @param string the string to measure
	 * @param component the component which is being drawn to
	 * @param paddingX the padding on the x axis
	 * @return
	 */
	public static Dimension SetSizeBasedOnString(Graphics g, Font font, String string, Component component, int paddingX)
	{
		Graphics2D graphics2D = (Graphics2D)g;
		
		Rectangle2D rect = font.getStringBounds(string, graphics2D.getFontRenderContext());
		Dimension retval = new Dimension((int)rect.getWidth() + paddingX, (int)rect.getHeight());
		
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
	
	/**
	 * Draws a gradient
	 * @param g the graphics object to use to draw with
	 * @param x the left position
	 * @param y the top position
	 * @param width with width of the gradient
	 * @param height the height of the gradient
	 * @param color1 the first color of the gradient
	 * @param color2 the second color of the gradient
	 */
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
