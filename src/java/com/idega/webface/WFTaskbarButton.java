/*
 * $Id: WFTaskbarButton.java,v 1.2 2004/06/08 16:14:47 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;

/**
 * ...  
 * <p>
 * Last modified: $Date: 2004/06/08 16:14:47 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */
public class WFTaskbarButton extends HtmlCommandLink {

	private boolean _selected = false;

	/**
	 * Default constructor. 
	 */
	public WFTaskbarButton() {
		super();
	}

	/**
	 * Constructs a taskbar button with the specified label text. 
	 */
	public WFTaskbarButton(String buttonLabel) {
		this();
		getChildren().add(WFUtil.getText(buttonLabel));
	}
	
	/**
	 * Returns the selected status for this button.
	 */
	public boolean getSelected() {
		return _selected;
	}
	
	/**
	 * Sets the selected status for this button.
	 */
	public void setSelected(boolean isSelected) {
		_selected = isSelected;
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		if (!_selected) {
			super.encodeBegin(context);
		}
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		if (!_selected) {
			super.encodeChildren(context);
		}
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		if (!_selected) {
			super.encodeEnd(context);
		} else {
			if (getChildren().size() > 0) {
				UIComponent c = (UIComponent) getChildren().get(0);
				if (c != null) {
					c.encodeBegin(context);
					c.encodeChildren(context);
					c.encodeEnd(context);
				}
			}
		}
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = new Boolean(_selected);
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		_selected = ((Boolean) values[1]).booleanValue();
	}
}
