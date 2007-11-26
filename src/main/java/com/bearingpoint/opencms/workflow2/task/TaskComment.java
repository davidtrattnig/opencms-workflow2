package com.bearingpoint.opencms.workflow2.task;

import java.io.Serializable;
import java.util.Date;

import org.opencms.util.CmsUUID;

/**
 * TaskComment
 * <p>
 * Bean handling the comments for the tasks
 * <p>
 * @author David.Trattnig
 */

public class TaskComment implements Serializable {
	
    static final long serialVersionUID = 1L;
    
    /** the task the comment belongs to **/
	private I_Task task;
	
	/** the user created the comment **/
	private CmsUUID user;
	
	/** the actual comment */
	private String message;
	
	/** the date the comment has been created **/
	private Date date;

	/**
	 * Protected constructor - task comments should be just created
	 * via the task bean.
	 * <p>
	 * @param id
	 * @param taskId
	 * @param actorId
	 * @param message
	 * @param date
	 */
	protected TaskComment(I_Task task, CmsUUID user, String message) {
						
		this.task = task;		
		this.user = user;		
		this.message = message;
		this.date = new Date();
	}
	
	protected TaskComment(I_Task task, CmsUUID user, String message, Date date) {
		
		this.task = task;		
		this.user = user;		
		this.message = message;
		this.date = date;
	}
	
	/**
	 * private default constructor
	 */
	private TaskComment() {
		
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the task
	 */
	public I_Task getTask() {
		return task;
	}

	/**
	 * @return the user
	 */
	public CmsUUID getUser() {
		return user;
	}

	
}
