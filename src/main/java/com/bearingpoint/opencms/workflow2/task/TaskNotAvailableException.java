/**
 * 
 */
package com.bearingpoint.opencms.workflow2.task;

/**
 * @author david.trattnig
 * @version 0.1, 01/19/2006
 * 
 */
public class TaskNotAvailableException extends Exception {
	private static final long serialVersionUID = 1L;
		
	/**
	 * Constructor for the WorkflowException 
	 */
	public TaskNotAvailableException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message message defines the exception message
	 */
	public TaskNotAvailableException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause defines the reason
	 */
	public TaskNotAvailableException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message defines the exception message
	 * @param cause defines the reasion
	 */
	public TaskNotAvailableException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
