/*
 * $Id: WFTaskbar.java,v 1.5 2004/06/23 13:23:43 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.webface.event.WFTaskbarEvent;
import com.idega.webface.event.WFTaskbarListener;

/**
 * Manages task bar buttons and corresponding perspectives. 
 * A perspective can be any component that is rendered when
 * its task bar button is pressed.   
 * <p>
 * Last modified: $Date: 2004/06/23 13:23:43 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.5 $
 */
public class WFTaskbar extends WFContainer implements ActionListener {
	
	private String _selectedButtonId = null;
	private List _buttonIds = null;
	private String _taskbarStyleClass = null;
	private String _mainAreaStyleClass = null;
	private String _buttonSelectedStyleClass = null;
	private String _buttonDeselectedStyleClass = null;
	
	/**
	 * Default contructor.
	 */
	public WFTaskbar() {
		_buttonIds = new ArrayList();
		setTaskbarStyleClass("wf_taskbar");
		setMainAreaStyleClass("wf_taskbarmainarea");
		setButtonSelectedStyleClass("wf_taskbarbuttonselected");
		setButtonDeselectedStyleClass("wf_taskbarbuttondeselected");
	}

	/**
	 * Returns the css class for this task bar.
	 */
	public String getTaskbarStyleClass() {
		return _taskbarStyleClass;
	}

	/**
	 * Returns the css class for the main container perspective area.
	 */
	public String getMainAreaStyleClass() {
		return _mainAreaStyleClass;
	}

	/**
	 * Returns the css class for selected task bar button.
	 */
	public String getButtonSelectedStyleClass() {
		return _buttonSelectedStyleClass;
	}

	/**
	 * Returns the css class for deselected task bar buttons.
	 */
	public String getButtonDeselectedStyleClass() {
		return _buttonDeselectedStyleClass;
	}

	/**
	 * Returns the id for the selected button.
	 */
	public String getSelectedButtonId() {
		return _selectedButtonId;
	}

	/**
	 * Sets the css class for this taskbar. 
	 */
	public void setTaskbarStyleClass(String taskbarStyleClass) {
		_taskbarStyleClass = taskbarStyleClass;
	}

	/**
	 * Sets the css class for the perspecitive main area container. 
	 */
	public void setMainAreaStyleClass(String mainAreaStyleClass) {
		_mainAreaStyleClass = mainAreaStyleClass;
	}

	/**
	 * Sets the css class for selected task bar button. 
	 */
	public void setButtonSelectedStyleClass(String buttonSelectedStyleClass) {
		_buttonSelectedStyleClass = buttonSelectedStyleClass;
	}

	/**
	 * Sets the css class for deselected task bar button. 
	 */
	public void setButtonDeselectedStyleClass(String buttonDeselectedStyleClass) {
		_buttonDeselectedStyleClass = buttonDeselectedStyleClass;
	}

	/**
	 * Sets the id for the selected button.
	 */
	public void setSelectedButtonId(String selectedButtonId) {
		_selectedButtonId = selectedButtonId;
	}
	
	/**
	 * Adds a tastbar button with its corresponding perspective component.
	 */
	public WFTaskbarButton addButton(String buttonId, String buttonLabel, UIComponent perspective) {
		WFTaskbarButton button = new WFTaskbarButton(buttonLabel);
		button.setId(buttonId);
//		button.setValue(buttonLabel);
		button.addActionListener(this);
		button.setImmediate(true);
		_buttonIds.add(buttonId);
		if (_selectedButtonId == null) {
			_selectedButtonId = buttonId;
		}
		getFacets().put("button_" + buttonId, button);
		WFContainer c = new WFContainer();
		c.setStyleClass(getMainAreaStyleClass());
		c.add(perspective);
		getFacets().put("perspective_" + buttonId, c);
		return button; 
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
		ResponseWriter out = context.getResponseWriter();
		// Taskbar buttons
		out.startElement("table", null);
		if (getTaskbarStyleClass() != null) {
			out.writeAttribute("class", getTaskbarStyleClass(), null);
		}
		out.writeAttribute("id", "" + getId(), null);
		out.startElement("tr", null);
		Iterator iter = _buttonIds.iterator();
		while (iter.hasNext()) {
			String buttonId = (String) iter.next();
			String buttonStyleClass = getButtonDeselectedStyleClass();
			WFTaskbarButton button = (WFTaskbarButton) getFacet("button_" + buttonId);
			if (buttonId.equals(_selectedButtonId)) {
				button.setSelected(true);
				buttonStyleClass = getButtonSelectedStyleClass();
			} else {
				button.setSelected(false);
			}
			out.startElement("td", null);
			if (buttonStyleClass != null) {
				out.writeAttribute("class", buttonStyleClass, null);
			}
			renderFacet(context, "button_" + buttonId);
			out.endElement("td");
		}
		out.endElement("tr");
		out.endElement("table");
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		renderFacet(context, "perspective_" + _selectedButtonId);
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
		Object values[] = new Object[7];
		values[0] = super.saveState(ctx);
		values[1] = _selectedButtonId;
		values[2] = _buttonIds;
		values[3] = _taskbarStyleClass;
		values[4] = _mainAreaStyleClass;
		values[5] = _buttonSelectedStyleClass;
		values[6] = _buttonDeselectedStyleClass;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		_selectedButtonId = (String) values[1];
		_buttonIds = (List) values[2];
		_taskbarStyleClass = (String) values[3];
		_mainAreaStyleClass = (String) values[4];
		_buttonSelectedStyleClass = (String) values[5];
		_buttonDeselectedStyleClass = (String) values[6];
	}
	
	/**
	 * Register the specified listener for taskbar events.
	 */
	public void addTaskbarListener(WFTaskbarListener listener) {
		addFacesListener(listener);
	}

	/**
	 * Remove the specified listener for taskbar events.
	 */
	public void removeTaskbarListener(WFTaskbarListener listener) {
		removeFacesListener(listener);
	}

	/**
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
	 */
	public void processAction(ActionEvent event) throws AbortProcessingException {
		WFTaskbarButton button = (WFTaskbarButton) event.getComponent();
		WFTaskbar taskbar = (WFTaskbar) button.getParent();
		taskbar.setSelectedButtonId(button.getId());
		WFTaskbarEvent e = new WFTaskbarEvent(taskbar);
		taskbar.queueEvent(e);
	}
}
