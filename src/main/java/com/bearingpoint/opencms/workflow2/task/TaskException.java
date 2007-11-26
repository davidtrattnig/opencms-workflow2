/**
 * 
 */
package com.bearingpoint.opencms.workflow2.task;

/**
 * @author david.trattnig
 * @version 0.1, 01/19/2006
 * 
 */
public class TaskException extends Exception {
	private static final long serialVersionUID = 1L;
		
	/**
	 * Constructor for the WorkflowException 
	 */
	public TaskException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message message defines the exception message
	 */
	public TaskException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause defines the reason
	 */
	public TaskException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message defines the exception message
	 * @param cause defines the reasion
	 */
	public TaskException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
