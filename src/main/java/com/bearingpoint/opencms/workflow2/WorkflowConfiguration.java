/**
 * 
 */
package com.bearingpoint.opencms.workflow2;

/**
 * WorkflowConfiguration
 * <p>
 * Handles the configuration for the entire workflow module.
 * <p>
 *  
 * @author David.Trattnig
 */
public class WorkflowConfiguration {
	
	public static String WORKFLOW_ENABLED = "true";
	
	public static int TASK_DUE_DATE_DAYS = 5;
	public static String TASK_DEFAULT_MESSAGE = "Please review the resource attached to this task";
	public static String TASK_DEFAULT_TITLE = "New resource %1 in project %2";
	
	
	/**
	 * Checks if the workflow functionality is switched on.
	 * <p>
	 * @return true if workflow is enabled, otherwise false
	 */
	public static boolean isWorkflowEnabled() {
		
		if ("true".equals(WORKFLOW_ENABLED)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Switches the whole workflow functionality on/off
	 * <p>
	 * @param state true - enable, false - disable
	 */
	public static void setWorkflowEnabled(boolean state) {
		
		if (state) {
			WORKFLOW_ENABLED = "true";
		}
		
		WORKFLOW_ENABLED = "false";
	}
	
	/**
	 * Returns the amount of days to the due date.
	 * <p>
	 * @return days to the due date.
	 */
	public static int getTaskDueDateDays() {
		return TASK_DUE_DATE_DAYS;
	}
	
	/**
	 * Returns the message for default tasks.
	 * <p>
	 * @return message
	 */
	public static String getDefaultTaskMessage() {
		return TASK_DEFAULT_MESSAGE;				
	}
	
	public static String getDefaultTaskTitle() {
		return TASK_DEFAULT_TITLE;
	}
		
}
