package com.idega.webface;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.idega.faces.IWBaseComponent;
/**
 * Title:        idegaclasses
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega
 * @author <a href="tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class WFContainer extends IWBaseComponent
{
	private static String HTML_TABLE_TAG="TABLE";
	private static String HTML_DIV_TAG="DIV";
	
	private static boolean imagesSet = false;
	
	/*private static Image topleft;
	private static Image topright;
	private static Image bottomleft;
	private static Image bottomright;
	private static Image top;
	private static Image bottom;
	private static Image left;
	private static Image right;*/
	
	private String _lightColor = "#FFFFFF";
	private String _darkColor = "#999999";
	private String backgroundColor;
	private String width;
	private String height;
	private String styleClass;

	
	public WFContainer()
	{
		//setBackgroundColor(IWConstants.DEFAULT_LIGHT_INTERFACE_COLOR);

		//setAllMargins(0);
	}

	public void setLightShadowColor(String color)
	{
		_lightColor = color;
	}
	public void setDarkShadowColor(String color)
	{
		_darkColor = color;
	}

	/**
	 * @return
	 * 
	 * @uml.property name="backgroundColor"
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param string
	 * 
	 * @uml.property name="backgroundColor"
	 */
	public void setBackgroundColor(String string) {
		backgroundColor = string;
	}

	/**
	 * @return Returns the height.
	 * 
	 * @uml.property name="height"
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height The height to set.
	 * 
	 * @uml.property name="height"
	 */
	public void setHeight(String height) {
		this.setStyleAttribute("height", height);
		this.height = height;
	}

	/**
	 * @return Returns the styleClass.
	 * 
	 * @uml.property name="styleClass"
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * @param styleClass The styleClass to set.
	 * 
	 * @uml.property name="styleClass"
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * @return Returns the width.
	 * 
	 * @uml.property name="width"
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width The width to set.
	 * 
	 * @uml.property name="width"
	 */
	public void setWidth(String width) {
		this.setStyleAttribute("width", width);
		this.width = width;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext ctx) throws IOException {
		ResponseWriter out = ctx.getResponseWriter();
		//RenderUtils.ensureAllTagsFinished();
		out.startElement(getMarkupElementType(),this);
		if(this.getStyleClass()!=null){
			out.writeAttribute("class",this.getStyleClass(),null);
		}
		if(this.getStyleAttribute()!=null){
			out.writeAttribute("style",this.getStyleAttribute(),null);
		}
		
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		super.encodeChildren(context);
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext ctx) throws IOException {
		ResponseWriter out = ctx.getResponseWriter();
		// TODO Auto-generated method stub
		out.endElement(getMarkupElementType());
	}
	
	protected String getMarkupElementType(){
		return HTML_DIV_TAG;
	}
}
