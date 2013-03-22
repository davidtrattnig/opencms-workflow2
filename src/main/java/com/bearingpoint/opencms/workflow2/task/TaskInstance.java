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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.opencms.file.CmsUser;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;

import com.bearingpoint.opencms.workflow2.WorkflowConfiguration;
import com.bearingpoint.opencms.workflow2.cms.CmsUtil;
import com.bearingpoint.opencms.workflow2.relation.ResourceIdentifier;


/**
 * TaskInstance
 * <p>
 * Instance of a task provided by the workflow engine.
 * <p>
 * 
 * @author David.Trattnig
 *
 */
public class TaskInstance {

	private Long taskID;
	private I_Task taskData;
	private ResourceIdentifier resource;
	private Calendar createDate;
	
	private static final Log LOG = CmsLog.getLog(TaskInstance.class);
	
	/**
	 * Hide default constructor 
	 */
	private TaskInstance() {}
	
	/**
	 * Constructor to initialize the task.
	 * <p>
	 * @param taskID
	 * @param taskData
	 * @throws TaskException
	 */
	public TaskInstance(Long taskID, I_Task taskData, Calendar createDate) throws TaskException {
		
		if (taskID == null || taskID <= 0) {
			throw new TaskException("Invalid TaskID!"); 
		}
		if (taskData == null) {
			throw new TaskException("Invalid TaskData!");
		}
				
		this.taskID = taskID;
		this.taskData = taskData;
		this.createDate = createDate;
	}
	
	/**
	 * @return the taskData
	 */
	public I_Task getData() {
		return taskData;
	}
	
	/**
	 * @return the taskID
	 */
	public Long getId() {
		return taskID;
	}
	
	/**
	 * Checks if the task is pooled.
	 * <p>
	 * @return true if pooled
	 */
	public boolean getPooled() {
		
		if (taskData.getAssignedUserUUID() == null) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Retrieves the date the task has been created.
	 * <p>
	 * @return Date as String for presentation
	 */
	public String getCreateDate() {
				
		//TODO insert date presentation rules		
		DateFormat sdf = SimpleDateFormat.getDateInstance();
		return sdf.format(createDate.getTime());
	}
		
	/**
	 * Retrieves the user (name) created this task.
	 * <p>
	 * @return username
	 */
	public String getUserName() {
		
		try {
			CmsUser user = CmsUtil.getAdminCmsObject()
							.readUser(taskData.getUserUUID());
			return user.getFullName();
		}
		catch (Exception e) {
			//TODO "beautifulize"
			LOG.error("invalid user", e);
			return "<span style=\"color:red;\">INVALID USER!</span>";
		}

	}
	
	/**
	 * Retrieves the name of the user assigned to 
	 * this task.
	 * <p>
	 * @return username
	 */
	public String getAssignedUserName() {
		
		if (!getPooled()) {
			try {
				return CmsUtil.getAdminCmsObject()
								.readUser(taskData.getAssignedUserUUID()).toString();
			}
			catch (Exception e) {
				return "";
			}
		}
		else {
			return "";
		}			
	}

	/**
	 * Retrieves the name of the project this task 
	 * belongs to.
	 * <p>
	 * @return project name
	 */
	public String getProjectName() {
		
		try {
			return CmsUtil.getAdminCmsObject()
							.readProject(taskData.getProjectUUID()).getName();
		}
		catch (CmsException e) {
			//TODO "beautifulize"
			LOG.error("invalid project", e);
			return "<span style=\"color:red\">INVALID PROJECT!</span>";
		}
	}

	public void attachResource(ResourceIdentifier resource) {

		this.resource = resource;
	}
		
	public String getResourceName() {
		
		return resource.getResourcePath();
	}
	
	public String getResourceId() {
		
		return resource.getResourceUUID();
	}
	
	public String getDueDate() throws TaskException {
		
		Calendar calendar = (Calendar) createDate.clone();
        int dueDateDays = 0;
        
        try {
            dueDateDays = WorkflowConfiguration.getTaskDueDateDays();
        }
        catch (Exception e) {
            LOG.error("Couldn't load due-date-days from workflow configuration", e);
            throw new TaskException ("Couldn't load due-date-days from workflow configuration", e);
        }
        
        calendar.add(GregorianCalendar.DAY_OF_MONTH, dueDateDays);
        
		//TODO insert date presentation rules (same for create date)
		DateFormat sdf = SimpleDateFormat.getDateInstance();
		return sdf.format(calendar.getTime());
	}
}
