/*
 * $Id: WFLinkMenu.java,v 1.4 2005/03/06 17:44:04 tryggvil Exp $
 * Created on 2.11.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import com.idega.util.FacesUtil;


/**
 * A menu whose menu items are plain html links.
 * 
 *  Last modified: $Date: 2005/03/06 17:44:04 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.4 $
 */
public class WFLinkMenu extends WFMenu {


	
	private Map links;
	
	/**
	 * 
	 */
	public WFLinkMenu() {
	}
	
	
	public void setMenuHeader(String text,String url){
		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(url);
		link.getChildren().add(WFUtil.getText(text));
		setMenuHeader(link);
	}
	
	
	public HtmlOutputLink addLink(String text,String url){
		//boolean isSelected = isUrlSelected(url);
		boolean isSelected = false;
		return addLink(text,url,isSelected);
	}
	
	public HtmlOutputLink addLink(String text,String url,boolean selected){
		String menuItemId = getNextMenuItemId();
		return addLink(text,url,menuItemId,selected);
	}
	
	public HtmlOutputLink addLink(String text,String url,String menuItemId){
		//boolean isSelected = isUrlSelected(url);
		boolean isSelected = false;
		return addLink(text,url,menuItemId,false);
	}
	
	
	public HtmlOutputLink addLink(String text,String url,String menuItemId,boolean selected){
		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(url);
		link.setId(menuItemId);
		link.getChildren().add(WFUtil.getText(text));
		addToLinkMap(menuItemId,url);
		this.setMenuItem(menuItemId,link);
		if(selected){
			setSelectedMenuItemId(menuItemId);
		}
		return link;
	}
	
	public Map getLinks(){
		if(links==null){
			links=new HashMap();
		}
		return links;
	}
	
	
	protected void addToLinkMap(String menuItemId,String url){
		getLinks().put(menuItemId,url);
	}
	
	public Set getMenuItemIds(){
		//return getLinks().keySet();
		return super.getMenuItemIds();
	}
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = links;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		links = ((Map) values[1]);
	}
	
	public void encodeBegin(FacesContext context) throws IOException{
		detectSelectedMenuItem(context);
		super.encodeBegin(context);
	}
	
	protected void detectSelectedMenuItem(FacesContext context){
		String requestUrl = FacesUtil.getRequestUri(context,true);
		
		//String requestServletPath = context.getExternalContext().getRequestServletPath();
		//String requestInfo = context.getExternalContext().getRequestPathInfo();
		//HttpServletRequest req = (HttpServletRequest)context.getExternalContext().getRequest();
		//String requestUrl = req.getRequestURI();
		for (Iterator iter = getMenuItemIds().iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			String url = (String)getLinks().get(id);
			if(url!=null){
				if(requestUrl.startsWith(url)){
				//if(url.equals(requestUrl)){
					this.setSelectedMenuItemId(id);
				}
			}
		}
	}
	
	
}
