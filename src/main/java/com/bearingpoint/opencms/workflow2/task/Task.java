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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.opencms.main.CmsLog;
import org.opencms.util.CmsUUID;

import com.bearingpoint.opencms.workflow2.WorkflowConfiguration;
import com.bearingpoint.opencms.workflow2.WorkflowException;

/**
 * TaskBean
 * <p>
 * Bean for the Tasks in the Workflow GUIs
 * <p>
 * @author David.Trattnig
 * @version 0.2
 * 
 */
public class Task implements Serializable, I_Task {
	
	static final long serialVersionUID = 1L;
	private static final Log LOG = CmsLog.getLog(Task.class);
		

	/**
	 * The resource the task is referring to
	 */
	//private ResourceIdentifier resource=null;
		
	/**
	 * Title of the task
	 */
	private String taskTitle;
		
	/**
	 * List of comments the task is containing
	 */
	private List<TaskComment> comments;
			
	/** 
	 * user created the task 
	 */
	private CmsUUID userUUID;
	
	/** 
	 * the user group the task is assigned 
	 */
	private CmsUUID projectUUID;
	
	/**
	 * The user the task is assigned to
	 */
	private CmsUUID assignedUserUUID;


    /**
     * Main constructor for creating tasks. 
     * <p>
     * @param resource
     * @param userUUID
     * @param comment
     */	 	
    public Task(CmsUUID projectUUID, CmsUUID userUUID, String title, List<TaskComment> comments) {
    	      
    	this.taskTitle = title;     
        //this.createDate = new GregorianCalendar();      
        this.userUUID = userUUID;
        this.projectUUID = projectUUID;
        this.comments = new ArrayList<TaskComment>();
        
        if (comments != null) {
        	for (TaskComment tc : comments) {
        		
        		this.comments.add(tc);
        	}
        }
    }
    
    /**
     * Constructor to create tasks via workflow engine implementation.
     * <p>
     * @param previousUser
     * @param project
     * @param user
     * @param title
     */
    public Task(String previousUser, String project, String user, String title) {
    	
    	if (project!=null && project.length()>0) {
    		this.projectUUID = new CmsUUID(project);
    	}
    	else {
    		this.projectUUID = null;
    	}
    	
    	if (user!=null && user.length()>0) {
    		this.assignedUserUUID = new CmsUUID(user);
    	}
    	else {
    		this.assignedUserUUID = null;
    	}
    	
    	try {
    		this.userUUID = new CmsUUID(previousUser);
    	}
    	catch (NullPointerException e) {
    		this.userUUID = null;
    		LOG.warn("There may be a problem with task '"+title+"' - id is null");
    	}
    	
    	this.taskTitle = title;
    	this.comments = new ArrayList<TaskComment>();       	    
    }
    
    /**
     * Main constructor for creating tasks. 
     * <p>
     * @param resource
     * @param userUUID
     * @param groupUUID
     * @param title of the task
     * @param comment
     */
    public Task(CmsUUID projectUUID, CmsUUID userUUID, String title, String comment) {
    	
    	this(projectUUID, userUUID, title, new ArrayList<TaskComment>());
    	comments.add(new TaskComment(this, userUUID, comment));
    }
        
    /**
     * Private default constructor.
     */
    protected Task() {}


	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_Task#getAssignedUserUUID()
	 */
	public CmsUUID getAssignedUserUUID() {
		return assignedUserUUID;
	}


	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_Task#getComments()
	 */
	public List<TaskComment> getComments() {
		return comments;
	}


	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_Task#getCreateDate()
	 */
//	public Calendar getCreateDate() {
//		return createDate;
//	}


	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_Task#getResource()
	 */
//	public ResourceIdentifier getResource() {
//		return resource;
//	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_Task#getTaskTitle()
	 */
	public String getTaskTitle() {
		return taskTitle;
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_Task#getUserUUID()
	 */
	public CmsUUID getUserUUID() {
		return userUUID;
	}
     	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_Task#getProjectUUID()
	 */
	public CmsUUID getProjectUUID() {

		return this.projectUUID;
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_Task#setProjectUUID(org.opencms.util.CmsUUID)
	 */
	public void setProjectUUID(CmsUUID projectID) {

		this.projectUUID = projectID;		
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_Task#addComment(org.opencms.util.CmsUUID, java.lang.String, java.util.Date)
	 */
	public void addComment(String user, String comment, Date date) {
		
		comments.add(new TaskComment(this, new CmsUUID(user), comment, date));
	}
}
