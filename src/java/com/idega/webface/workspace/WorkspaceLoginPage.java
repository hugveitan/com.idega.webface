/*
 * Created on 13.7.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.idega.webface.workspace;

import com.idega.core.localisation.presentation.LocalePresentationUtil;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Page;
import com.idega.presentation.PresentationObject;
import com.idega.presentation.Table;
import com.idega.presentation.app.IWControlCenter;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.util.reflect.FieldAccessor;
import com.idega.util.reflect.MethodInvoker;
import com.idega.webface.WFBezel;


public class WorkspaceLoginPage extends Page {
	private final static String IW_BUNDLE_IDENTIFIER = "com.idega.webface";
	private IWBundle iwb;
	private IWResourceBundle iwrb;
	String backgroundColor = "#B0B29D";

	public WorkspaceLoginPage() {
	}
	public String getBundleIdentifier() {
		return IW_BUNDLE_IDENTIFIER;
	}
	public void main(IWContext iwc) {
		iwb = this.getBundle(iwc);
		iwrb = this.getResourceBundle(iwc);

		Page thePage = this;
		//thePage.setBackgroundColor(backgroundColor);
		//thePage.setAllMargins(0);

		thePage.setTitle("idegaWeb Applications");

		Table frameTable = new Table(1, 1);
		frameTable.setWidth("100%");
		frameTable.setHeight("100%");
		frameTable.setCellpadding(0);
		frameTable.setCellspacing(0);
		frameTable.setAlignment(1, 1, "center");
		frameTable.setVerticalAlignment(1, 1, "middle");

		//Table mainTable = new Table(1, 4);
		WFBezel mainTable = new WFBezel();
		mainTable.setWidth("400px");
		mainTable.setHeight("300px");
		//mainTable.setCellspacing(0);
		//mainTable.setCellpadding(0);
		//mainTable.setBackgroundImage(1,1,iwb.getImage("logintiler.gif"));
		//mainTable.setAlignment(1, 2, "right");
		//mainTable.setAlignment(1, 3, "right");
		//mainTable.setAlignment(1, 4, "center");
		//mainTable.setVerticalAlignment(1, 1, "top");
		//mainTable.setVerticalAlignment(1, 2, "top");
		//mainTable.setVerticalAlignment(1, 3, "top");
		//mainTable.setVerticalAlignment(1, 4, "bottom");
		//mainTable.setHeight(4, "12");
		//mainTable.setColor("#FFFFFF");
		frameTable.add(mainTable, 1, 1);
		//mainTable.setStyleAttribute("border", "1px solid #000000");

		Table dropdownTable = new Table(1, 1);
		dropdownTable.setWidth(148);
		dropdownTable.setCellpadding(0);
		dropdownTable.setCellspacing(0);
		dropdownTable.setAlignment(1, 1, "center");
		//mainTable.setAlignment(1, 3, Table.HORIZONTAL_ALIGN_RIGHT);
		mainTable.add(dropdownTable);

		Form myForm = new Form();
		myForm.setEventListener(com.idega.core.localisation.business.LocaleSwitcher.class.getName());
		DropdownMenu dropdown = LocalePresentationUtil.getAvailableLocalesDropdown(iwc);
		dropdown.setStyleAttribute("font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 8pt; border-style:solid; border-width:1; border-color: #000000");
		myForm.add(dropdown);
		dropdownTable.add(myForm);


		boolean isLoggedOn = false;
		try {
			isLoggedOn = iwc.isLoggedOn();
		}
		catch (Exception e) {
			isLoggedOn = false;
		}

		if (isLoggedOn) {
			IWControlCenter iwcc = new IWControlCenter();
			//mainTable.setHeight(2, "165");
			//mainTable.setAlignment(1, 2, "center");
			//mainTable.setAlignment(1, 3, "right");
			//mainTable.setVerticalAlignment(1, 2, "middle");
			//mainTable.setVerticalAlignment(1, 3, "middle");
			mainTable.add(iwcc);
			//headerImage = iwrb.getImage("login/header_app_suite.jpg", "", 323, 196);

			try {
				WFLogin login = new WFLogin();
				login.setHeight("60");
				login.setWidth("70");
				login.setAllowCookieLogin(true);
				//MethodInvoker invoker = MethodInvoker.getInstance();
				//invoker.invokeMethodWithStringParameter(login, "setLogoutButtonImageURL", iwrb.getImageURI("login/logout.gif"));
				//invoker.invokeMethodWithStringParameter(login, "setHeight", "60");
				//invoker.invokeMethodWithStringParameter(login, "setWidth", "70");
				//invoker.invokeMethodWithStringParameter(login, "setLoginAlignment", "center");
				//invoker.invokeMethodWithBooleanParameter(login, "setViewOnlyLogoutButton", true);
				//invoker.invokeMethodWithBooleanParameter(login, "setAllowCookieLogin", true);
				
				mainTable.add(login);
			}
			catch (Exception e) {
				add(iwrb.getLocalizedString("login.init.error", "There was an error initialising the login component, most likely it is missing"));
				e.printStackTrace();
			}

		}

		else {
			//mainTable.setHeight(2, "175");
			//mainTable.setHeight(3, "50");
			//mainTable.setAlignment(1, 2, Table.HORIZONTAL_ALIGN_RIGHT);

			try {
				WFLogin login = new WFLogin();
				login.setHeight("60");
				login.setWidth("70");
				login.setAllowCookieLogin(true);
				//MethodInvoker invoker = MethodInvoker.getInstance();
				//invoker.invokeMethodWithStringParameter(login, "setLogoutButtonImageURL", iwrb.getImageURI("login/logout.gif"));
				//invoker.invokeMethodWithStringParameter(login, "setHeight", "60");
				//invoker.invokeMethodWithStringParameter(login, "setWidth", "70");
				//invoker.invokeMethodWithStringParameter(login, "setLoginAlignment", "center");
				//invoker.invokeMethodWithBooleanParameter(login, "setViewOnlyLogoutButton", true);
				//invoker.invokeMethodWithBooleanParameter(login, "setAllowCookieLogin", true);
				
				mainTable.add(login);
			}
			catch (Exception e) {
				add(iwrb.getLocalizedString("login.init.error", "There was an error initialising the login component, most likely it is missing"));
				e.printStackTrace();
			}

			//headerImage = iwrb.getImage("/login/header.jpg", "", 323, 196);
		}
		//Link lheaderLink = new Link(headerImage, iwc.getIWMainApplication().getApplicationContextURI());
		//mainTable.add(lheaderLink, 1, 1);
		thePage.add(frameTable);
	}

}