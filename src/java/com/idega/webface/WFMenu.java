/*
 * $Id: WFMenu.java,v 1.14 2006/05/08 13:53:59 laddi Exp $
 * Created on 27.10.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


/**
 *  This is an abstract base class for all "menu" style components, 
 *  such as Tab bars, Task bars, Vertical "side" menus etc.<br>
 *  These are usually rendered as an unordered list in HTML.
 * 
 *  Last modified: $Date: 2006/05/08 13:53:59 $ by $Author: laddi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.14 $
 */
public class WFMenu extends WFContainer {

	private String menuStyleClass = "wf_menu";
	private String _buttonSelectedStyleClass = TAB_ITEMSELECTEDSTYLECLASS;
	private String _buttonDeselectedStyleClass = TAB_ITEMDESELECTEDSTYLECLASS;
	private String _menuHeaderStyleClass = TAB_ITEMDESELECTEDSTYLECLASS;
	private Set _buttonIds = null;
	private String _selectedButtonId = null;
	
	private static String RENDERER_TYPE="wf_menu";
	public static String FACET_MENUHEADER="menu_header";
	
	public static String getWFRendererType() {
		return RENDERER_TYPE;
	}
	
	// Standard styles
	
	/**
	 *The "tab" style classes
	 */
	private static String TAB_MENUSTYLECLASS = "wf_tabbar";
	private static String TAB_ITEMSELECTEDSTYLECLASS = "selected";//"wf_tabbarbuttonselected";
	private static String TAB_ITEMDESELECTEDSTYLECLASS = "deselected";//"wf_tabbarbuttondeselected";
	private static String TAB_MENUHEADERSTYLECLASS = "wf_menuheader";
	private static String TAB_CONTAINERCLASS = TAB_MENUSTYLECLASS+"container";
	
	/**
	 *The "vertical" style classes
	 */
	private static String VERTICAL_MENUSTYLECLASS = "wf_viewmenu";
	private static String VERTICAL_MENUHEADERSTYLECLASS = "wf_menuheader";
	private static String VERTICAL_ITEMSELECTEDSTYLECLASS = "selected";//"wf_viewmenubuttonselected";
	private static String VERTICAL_ITEMDESELECTEDSTYLECLASS = "deselected";//"wf_viewmenubuttondeselected";
	private static String VERTICAL_CONTAINERCLASS = VERTICAL_MENUSTYLECLASS+"container";
	

	public WFMenu() {
		super();
		setVerticalStyle();
		setRendererType(RENDERER_TYPE);
		//setMenuStyleClass(TAB_MENUSTYLECLASS);
	}
	
	public UIComponent getMenuHeader(){
		return this.getFacet(FACET_MENUHEADER);
	}
	
	public void setMenuHeader(UIComponent menuHeader){
		if(menuHeader instanceof WFContainer){
			getFacets().put(FACET_MENUHEADER,menuHeader);
		}
		else{
			WFContainer cont = new WFContainer();
			cont.setStyleClass(this.getMenuHeaderStyleClass());
			cont.getChildren().add(menuHeader);
			getFacets().put(FACET_MENUHEADER,cont);
		}
	}
	
	/**
	 * Sets the menu to be "tab" style
	 */
	public void setTabStyle(){
		setMenuStyleClass(TAB_MENUSTYLECLASS);
		setSelectedMenuItemStyleClass(TAB_ITEMSELECTEDSTYLECLASS);
		setDeselectedMenuItemStyleClass(TAB_ITEMDESELECTEDSTYLECLASS);
		setMenuHeaderStyleClass(TAB_MENUHEADERSTYLECLASS);
		setStyleClass(TAB_CONTAINERCLASS);
	}
	
	/**
	 * Sets the menu to be "vertical menu" style
	 */
	public void setVerticalStyle(){
		setMenuStyleClass(VERTICAL_MENUSTYLECLASS);
		setSelectedMenuItemStyleClass(VERTICAL_ITEMSELECTEDSTYLECLASS);
		setDeselectedMenuItemStyleClass(VERTICAL_ITEMDESELECTEDSTYLECLASS);
		setMenuHeaderStyleClass(VERTICAL_MENUHEADERSTYLECLASS);
		setStyleClass(VERTICAL_CONTAINERCLASS);
	}
	
	/**
	 * @return Returns the _menuHeaderStyleClass.
	 */
	public String getMenuHeaderStyleClass() {
		return this._menuHeaderStyleClass;
	}
	/**
	 * @param headerStyleClass The _menuHeaderStyleClass to set.
	 */
	public void setMenuHeaderStyleClass(String headerStyleClass) {
		this._menuHeaderStyleClass = headerStyleClass;
	}
	/**
	 * Returns the css class for this menu.
	 */
	public String getMenuStyleClass() {
		return this.menuStyleClass;
		//return getStyleClass();
	}

	/**
	 * Returns the css class for selected task bar button.
	 */
	public String getSelectedMenuItemStyleClass() {
		return this._buttonSelectedStyleClass;
	}

	/**
	 * Returns the css class for deselected task bar buttons.
	 */
	public String getDeselectedMenuItemStyleClass() {
		return this._buttonDeselectedStyleClass;
	}

	/**
	 * Sets the css class for this menu. 
	 */
	public void setMenuStyleClass(String menuStyleClass) {
		this.menuStyleClass = menuStyleClass;
		//setStyleClass(menuStyleClass);
	}

	/**
	 * Sets the css class for selected task bar button. 
	 */
	public void setSelectedMenuItemStyleClass(String buttonSelectedStyleClass) {
		this._buttonSelectedStyleClass = buttonSelectedStyleClass;
	}

	/**
	 * Sets the css class for deselected task bar button. 
	 */
	public void setDeselectedMenuItemStyleClass(String buttonDeselectedStyleClass) {
		this._buttonDeselectedStyleClass = buttonDeselectedStyleClass;
	}
	
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[7];
		values[0] = super.saveState(ctx);
		values[1] = this.menuStyleClass;
		values[2] = this._buttonSelectedStyleClass;
		values[3] = this._buttonDeselectedStyleClass;
		values[4] = this._selectedButtonId;
		values[5] = this._buttonIds;
		values[6] = this._menuHeaderStyleClass;
		return values;
	}
	
	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(ctx, values[0]);
		this.menuStyleClass = (String) values[1];
		this._buttonSelectedStyleClass = (String) values[2];
		this._buttonDeselectedStyleClass = (String) values[3];
		this._selectedButtonId = (String) values[4];
		this._buttonIds = (Set) values[5];
		this._menuHeaderStyleClass = (String)values[6];
	}

	public Set getMenuItemIds() {
		if(this._buttonIds==null){
			this._buttonIds = new LinkedHashSet();
		}
		return this._buttonIds;
	}
	
	/**
	 * Returns the id for the selected button.
	 */
	public String getSelectedMenuItemId() {
		return this._selectedButtonId;
	}

	/**
	 * Sets the id for the selected button.
	 */
	public void setSelectedMenuItemId(String selectedButtonId) {
		this._selectedButtonId = selectedButtonId;
	}

	/**
	 * Adds a new menuItem and generates the id of the item.
	 * @param menuItemComponent The item to add to the menu.
	 * @return Return the generated id for the item.
	 */
	public String addMenuItem(UIComponent menuItemComponent){
		String nextMenuItemId = getNextMenuItemId();
		setMenuItem(nextMenuItemId,menuItemComponent);
		return nextMenuItemId;
	}	

	/**
	 * Generates a unique id for a new menu item.
	 * @return
	 */
	protected String getNextMenuItemId(){   
		int maxValue = 0;
		String thisMenuId = getId();
		Iterator iter = getMenuItemIds().iterator(); 
		while (iter.hasNext()) {
			String element = (String) iter.next();
			int intValue = 0;
			int d = 1;
			int i = element.length();
			while ( --i  > -1) {
				char c = element.charAt(i);
				if (Character.isDigit(c)) {
					int k = Character.getNumericValue(c);
					intValue += (k * d);
					d = d * 10;
				}
				else {
					i = -1;
				}
			}
			if(intValue>maxValue) {
				maxValue=intValue;
			}
		}
		String prefix="_";
		if(thisMenuId!=null){
			prefix=thisMenuId+prefix;
		}
		return prefix.concat(String.valueOf(++maxValue));
	}
	
	
	
	/**
	 * Sets the menuComponent instance with the id given with menuItemId
	 * @param menuItemId
	 * @param menuItemComponent
	 */
	public void setMenuItem(String menuItemId,UIComponent menuItemComponent){
		/*getMenuItemIds().add(menuItemId);
		if (getSelectedMenuItemId() == null) {
			setSelectedMenuItemId(menuItemId);
		}*/
		if(!getMenuItemIds().contains(menuItemId)){
			getMenuItemIds().add(menuItemId);
		}
		String menuItemFacetKey = "menuitem_"+menuItemId;
		this.getFacets().put(menuItemFacetKey,menuItemComponent);
		String itemId = menuItemComponent.getId();
		if(itemId==null){
			String thisId = this.getId();
			if(thisId!=null){
				itemId = thisId+menuItemFacetKey;
				menuItemComponent.setId(itemId);
			}
		}
		//this.getFacets().put("button_"+menuItemId,menuItemComponent);
	}
	
	public UIComponent getMenuItem(String menuItemId){
		//return this.getFacet("button_"+menuItemId);
		UIComponent c = this.getFacet("menuitem_"+menuItemId);
		if(c==null && menuItemId != null){
			for (Iterator iter = getChildren().iterator(); iter.hasNext();) {
				UIComponent button = (UIComponent) iter.next();
				if(menuItemId.equals(button.getId())){
					c = button;
					break;
				}
			}
		}
		return c;
	}
	
	public boolean isRendered(){
		return super.isRendered();
	}
	
	public boolean isEmpty(){
		for (Iterator iter = getFacetsAndChildren(); iter.hasNext();) {
			UIComponent comp = (UIComponent) iter.next();
			if(comp.isRendered()){
				return false;
			}
		}
		return true;
	}
	
}
