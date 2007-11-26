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

	
//	public static enum STATE {
//		
//	    /**
//	     * STATE_POOLED means that the task is assigned to the whole group
//	     */
//		STATE_POOLED,
//		
//	    /**
//	     * STATE_ACCEPTED means that the task is assigned to a specific user
//	     */
//		STATE_ACCEPTED,
//		
//	    /**
//	     * STATE_LOOSE means that the task has an invalid assignment 
//	     * e.g. the assigned user/group has been deleted meanwhile 
//	     */
//		STATE_LOOSE
//	};
	
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
	public abstract Calendar getCreateDate();

	/**
	 * @return the resource
	 */
	//public abstract ResourceIdentifier getResource();

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
	public abstract Date getDueDate() throws TaskException;
	
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