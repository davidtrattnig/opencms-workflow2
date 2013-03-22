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
package com.bearingpoint.opencms.workflow2.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.opencms.util.CmsUUID;

import com.bearingpoint.opencms.workflow2.relation.ResourceIdentifier;

/**
 * I_Task
 * <p>
 * Defines a workflow task.
 * <p>
 * @author David.Trattnig
 *
 */
public interface I_Task {

	
	/**
	 * @return the assignedUserUUID
	 */
	public abstract CmsUUID getAssignedUserUUID();

	/**
	 * @return the comments
	 */
	public abstract List<TaskComment> getComments();

	/**
	 * @return the createDate
	 */
//	public abstract Calendar getCreateDate();

	/**
	 * @return the taskTitle
	 */
	public abstract String getTaskTitle();

	/**
	 * @return the userUUID
	 */
	public abstract CmsUUID getUserUUID();

	/**
	 * @return due date
	 * @throws TaskException
	 */
//	public abstract Date getDueDate() throws TaskException;
	
	/**
	 * Retrieves the project the task is assigned to
	 * <p>
	 * @return project id 
	 */
	public abstract CmsUUID getProjectUUID();
	
	/**
	 * Assigns the task to a project.
	 * <p>
	 * @param projectID
	 */
	public abstract void setProjectUUID(CmsUUID projectID);
	
	/**
	 * Adds a comment to the task. Most likely called via the engine implementation.
	 * <p>
	 * @param user
	 * @param comment
	 * @param date
	 */
	public void addComment(String user, String comment, Date date);
}