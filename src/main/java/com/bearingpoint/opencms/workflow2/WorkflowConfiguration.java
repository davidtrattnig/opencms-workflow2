/* ***************************************************************************
 * This library is part of INFONOVA OpenCms Modules.
 * Common Modules for the Open Source Content Management System: OpenCms.
 *
 * Copyright (c) 2007 INFONOVA GmbH, Austria.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about INFONOVA GmbH, Austria, please
 * see the company website: http://www.infonova.at/
 *
 * For further information about INFONOVA OpenCms Modules, please see the
 * project website: http://sourceforge.net/projects/bp-cms-commons/
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *****************************************************************************/

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
