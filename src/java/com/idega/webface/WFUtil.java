/*
 * Created on 4.3.2004 by  tryggvil in project com.project
 */
package com.idega.webface;

import java.io.IOException;
import java.util.List;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionListener;

import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;

/**
 * WFUtil //TODO: tryggvil Describe class
 * Copyright (C) idega software 2004
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFUtil {
	
	private static String BUNDLE_IDENTIFIER="com.idega.webface";
	
	public static IWBundle getBundle(){
		return getBundle(FacesContext.getCurrentInstance());
	}

	public static IWBundle getBundle(FacesContext context){
		return IWContext.getIWContext(context).getIWMainApplication().getBundle(BUNDLE_IDENTIFIER);
	}
	
	public static IWResourceBundle getResourceBundle(FacesContext context){
		return getBundle(context).getResourceBundle(context.getExternalContext().getRequestLocale());
	}
	
	public static IWResourceBundle getResourceBundle(){
		return getResourceBundle(FacesContext.getCurrentInstance());
	}
	
	/**
	 * Returns an html text component.
	 */
	public static HtmlOutputText getText(String s) {
		HtmlOutputText t = new HtmlOutputText();
		t.setValue(s);
		return t;
	}
	
	/**
	 * Returns an html text area component. 
	 */
	public static HtmlInputTextarea getTextArea(String id, String value, String width, String height) {
		HtmlInputTextarea a = new HtmlInputTextarea();
		a.setId(id);
		a.setValue(value);
		a.setStyle("width:" + width + ";height:" + height + ";");
		setInputStyle(a);
		return a;
	}
	
	/**
	 * Returns an html command link.
	 */
	public static HtmlCommandLink getLink(String id, String value) {
		HtmlCommandLink l = new HtmlCommandLink();
		l.setId(id);
		l.setValue(value);
		return l;
	}
	
	/**
	 * Returns an html command list link.
	 */
	public static HtmlCommandLink getListLink(String id, String value) {
		HtmlCommandLink l = getLink(id, value);
		l.setStyleClass("wf_listlink");
		return l;
	}
	
	/**
	 * Returns an html command list link with value binding.
	 */
	public static HtmlCommandLink getListLinkVB(String id, String ref) {
		HtmlCommandLink l = new HtmlCommandLink();
		l.setStyleClass("wf_listlink");
		l.setId(id);
		l.setValueBinding("value", createValueBinding("#{" + ref + "}"));
		return l;
	}
	
	/**
	 * Returns an html list text with value binding.
	 */
	public static HtmlOutputText getListTextVB(String ref) {
		HtmlOutputText t = new HtmlOutputText();
//		t.setStyleClass("wf_listtext");
		t.setValueBinding("value", createValueBinding("#{" + ref + "}"));
		return t;
	}
	
	/**
	 * Returns an html input text.
	 */
	public static HtmlInputText getInputText(String id, String value) {
		HtmlInputText t = new HtmlInputText();
		t.setId(id);
		t.setValue(value);
		setInputStyle(t);
		return t;
	}
	
	/**
	 * Returns an html select one menu.
	 */
	public static HtmlSelectOneMenu getSelectOneMenu(String id) {
		HtmlSelectOneMenu m = new HtmlSelectOneMenu();
		m.setId(id);
		setInputStyle(m);
		return m;
	}
	
	/**
	 * Returns an html command button.
	 */
	public static HtmlCommandButton getButton(String id, String value) {
		HtmlCommandButton b = new HtmlCommandButton();
		b.setId(id);
		b.setValue(value);
		b.setImmediate(true);
		setInputStyle(b);
		return b;
	}
	
	/**
	 * Returns an html command button.
	 */
	public static HtmlCommandButton getButton(String id, String value, ActionListener actionListener) {
		HtmlCommandButton b = getButton(id, value);
		b.addActionListener(actionListener);
		return b;
	}

	/**
	 * Returns the idega page header banner. 
	 */
	public static UIComponent getBannerBox() {
		WFContainer box = new WFContainer();
		box.setStyleClass("wf_bannerbox");
		
		HtmlGraphicImage logo = new HtmlGraphicImage();
		logo.setStyleClass("wf_bannerimg");
		logo.setUrl("icons/idegalogo_20px.gif  ");
		box.add(logo);
		
		WFContainer c = new WFContainer();
		c.setStyleClass("wf_bannertext");
		WFPlainTextOutput t = new WFPlainTextOutput();
		t.setValue(" idega<i>Web</i> Content   ");
		c.add(t);
		box.add(c);
		
		return box;
	}

	/**
	 * Sets the default css class for the specified input component. 
	 */
	public static UIComponent setInputStyle(UIComponent component) {
		if (component instanceof HtmlInputText) {
			((HtmlInputText) component).setStyleClass("wf_inputcomponent");
		} else if (component instanceof HtmlInputTextarea) {
			((HtmlInputTextarea) component).setStyleClass("wf_inputcomponent");			
		} else if (component instanceof HtmlSelectOneMenu) {
			((HtmlSelectOneMenu) component).setStyleClass("wf_selectonemenu");			
		} else if (component instanceof HtmlCommandButton) {
			((HtmlCommandButton) component).setStyleClass("wf_button");			
		}
		return component;
	}
	
	/**
	 * Sets the default css class for the specified block for the specified child component. 
	 */
	public static WFBlock setBlockStyle(WFBlock block, UIComponent child) {
		if (child instanceof WFViewMenu) {
			block.setMainAreaStyleClass("wf_viewmenublockarea");
			block.setWidth("100%");
		}
		return block;
	}
	
	/**
	 * Adds a UIParameter to the specified component. 
	 */
	public static void addParameter(UIComponent component, String name, String value) {
		UIParameter p = new UIParameter();
		p.setName(name);
		p.setValue(value);			
		component.getChildren().add(p);		
	}
	
	/**
	 * Returns the value for the parameter with the specified name. 
	 */
	public static String getParameter(UIComponent component, String name) {
		List parameters = component.getChildren();
		for (int i = 0; i < parameters.size(); i++) {
			UIComponent child = (UIComponent) parameters.get(i);
			if (child instanceof UIParameter) {
				UIParameter p = (UIParameter) child;
				if (((String) p.getName()).equals(name)) {
					return (String) p.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * Render the specified facet.
	 */
	public static void renderFacet(FacesContext context, UIComponent component, String facetName) throws IOException {
		UIComponent facet = (UIComponent) (component.getFacets().get(facetName));
		if (facet != null) {
			facet.encodeBegin(context);
			facet.encodeChildren(context);
			facet.encodeEnd(context);
		}
	}
	
	/**
	 * Convenience method to retrieve the JSF application.
	 */
	public static Application getApplication() {
		ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
		Application app = appFactory.getApplication();
		return app;
	}
	
	/**
	 * Returns a value binding instance.
	 * @param ref Value binding expression 
	 */
	public static ValueBinding createValueBinding(String ref) {
		return getApplication().createValueBinding(ref);
	}
	
	/**
	 * Sets a value binding for the specified component.
	 */
	public static void setValueBinding(UIComponent component, String attributeName, String attribute) {
		component.setValueBinding(attributeName, createValueBinding("#{" + attribute + "}"));
	}
}
