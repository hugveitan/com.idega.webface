/*
 * $Id: WFViewMenuButton.java,v 1.2 2004/06/08 16:14:47 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

/**
 * Button for WFViewMenu components.  
 * <p>
 * Last modified: $Date: 2004/06/08 16:14:47 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */
public class WFViewMenuButton extends WFTaskbarButton {

	/**
	 * Default constructor. 
	 */
	public WFViewMenuButton() {
		super();
	}

	/**
	 * Constructs a view menu button with the specified label text. 
	 */
	public WFViewMenuButton(String buttonLabel) {
		super(buttonLabel);
	}
	
}
