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
