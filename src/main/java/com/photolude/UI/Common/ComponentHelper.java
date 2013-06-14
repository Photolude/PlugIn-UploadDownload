package com.photolude.UI.Common;

import java.awt.Component;

import javax.swing.JComponent;

public abstract class ComponentHelper {
	public static Component AlignLeft(JComponent component)
	{
		component.setAlignmentX(Component.LEFT_ALIGNMENT);
		return component;
	}
}
