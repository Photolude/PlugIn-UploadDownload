package com.photolude.UI.Common;

/**
 * Defines a select-able object
 * 
 * @author Nikody Keating
 *
 */
public interface ISelectable {
	Boolean GetSelectedState();
	void SetSelected(Boolean bSelected);
	
	void SetMouseOver(Boolean bMouseOver);
}
