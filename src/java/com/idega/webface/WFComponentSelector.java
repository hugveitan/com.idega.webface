/*
 * $Id: WFComponentSelector.java,v 1.1 2004/06/08 16:12:48 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * Container holding child components of which only selected children will be rendered. 
 * <p>
 * Last modified: $Date: 2004/06/08 16:12:48 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */
public class WFComponentSelector extends WFContainer {
	
	private Map _selectedChildIds = null;
	private String _dividerText = null;
	
	/**
	 * Default contructor.
	 */
	public WFComponentSelector() {
		super();
	}

	/**
	 * Sets the child with the specified id to be rendered or not.
	 */
	public void setSelectedId(String selectedChildId, boolean renderChild) {
		if (_selectedChildIds == null) {
			_selectedChildIds = new HashMap();
		}
		if (renderChild) {
			_selectedChildIds.put(selectedChildId, selectedChildId);
		} else {
			_selectedChildIds.remove(selectedChildId);
		}
	}	
	
	/**
	 * Sets the text to write between each selected child.
	 */
	public void setDividerText(String dividerText) {
		_dividerText = dividerText;
	}
	
	/**
	 * Returns the text to write between each selected child.
	 */
	public String getDividerText() {
		return _dividerText;
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		if (_selectedChildIds == null) {
			return;
		}
		
		ResponseWriter out = context.getResponseWriter();
		
		for (Iterator iter = getChildren().iterator(); iter.hasNext();) {
			UIComponent child = (UIComponent) iter.next();
			if (_selectedChildIds.get(child.getId()) != null) {
				child.encodeBegin(context);
				child.encodeChildren(context);
				child.encodeEnd(context);
			}
			if (_dividerText != null) {
				out.write(_dividerText);
			}
		}
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[3];
		values[0] = super.saveState(ctx);
		values[1] = _selectedChildIds;
		values[2] = _dividerText;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		_selectedChildIds = (Map) values[1];
		_dividerText = (String) values[2];
	}
}
