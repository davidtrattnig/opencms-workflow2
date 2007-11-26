/* ***************************************************************************
* $RCSfile$ $Revision: 1266 $ $Date: 2006-04-25 11:12:50 +0200 (Di, 25 Apr 2006) $
* 
* Copyright (c) 2005 BearingPoint INFONOVA GmbH, Austria.
*
* This software is the confidential and proprietary information of
* BearingPoint INFONOVA GmbH, Austria. You shall not disclose such
* Confidential Information and shall use it only in accordance with the
* terms of the license agreement you entered into with INFONOVA.
*
* BEARINGPOINT INFONOVA MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
* SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT
* NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
* A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. BEARINGPOINT INFONOVA SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
* MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*****************************************************************************/
package com.bearingpoint.opencms.workflow2.engine;

/**
 * @author david.trattnig
 * 
 */
public class WorkflowTransitionNotAvailableException extends Exception {
	private static final long serialVersionUID = 1L;
		
	/**
	 * Constructor for the WorkflowException 
	 */
	public WorkflowTransitionNotAvailableException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message message defines the exception message
	 */
	public WorkflowTransitionNotAvailableException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause defines the reason
	 */
	public WorkflowTransitionNotAvailableException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message defines the exception message
	 * @param cause defines the reasion
	 */
	public WorkflowTransitionNotAvailableException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
