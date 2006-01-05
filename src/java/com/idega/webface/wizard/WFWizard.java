/*
 * $Id: WFWizard.java,v 1.3 2006/01/05 15:56:21 laddi Exp $
 * Created on 10.1.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.wizard;

import com.idega.webface.WFBlock;


/**
 *  <p>
 *  Class to display a "wizard" type interface with a step-through of actions.
 *  </p>
 *  Last modified: $Date: 2006/01/05 15:56:21 $ by $Author: laddi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.3 $
 */
public class WFWizard extends WFBlock{
	
	//private WFContainer header;
	//private WFContainer mainArea;
	//private WFContainer footer;
	
	private int currentStep;
	private int totalSteps;
	
	/**
	 * @return Returns the currentStep.
	 */
	public int getCurrentStep() {
		return currentStep;
	}
	
	/**
	 * @param currentStep The currentStep to set.
	 */
	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}
	
	/**
	 * @return Returns the totalSteps.
	 */
	public int getTotalSteps() {
		return totalSteps;
	}
	
	/**
	 * @param totalSteps The totalSteps to set.
	 */
	public void setTotalSteps(int totalSteps) {
		this.totalSteps = totalSteps;
	}
	
}