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

package com.bearingpoint.opencms.workflow2.events;

import org.opencms.main.I_CmsEventListener;

/**
 * I_CmsWorkflowEventListener
 * <p>
 * @author David.Trattnig
 *
 */
public interface I_CmsWorkflowEventListener extends I_CmsEventListener {

	/**
	 * Event which is fired when a workflow is started.
	 */
	public static final int EVENT_WORKFLOW_STARTED = 661;
	
	/**
	 * Event which is fired when a "approve" transition 
	 * is taken inside the workflow
	 */
	public static final int EVENT_WORKFLOW_TRANSITION_APPROVED = 662;
	
	/**
	 * Event which is fired when a "reject" transition 
	 * is taken inside the workflow
	 */
	public static final int EVENT_WORKFLOW_TRANSITION_REJECTED = 663;
	
	/**
	 * Event which is fired when a workflow is finished
	 */
	public static final int EVENT_WORKFLOW_FINISHED = 664;
	
	/**
	 * Event which is fired when a task is created
	 */
	public static final int EVENT_WORKFLOW_TASK_CREATED = 665;
	
	/**
	 * Event which is fired when a task get assigned to an user
	 */
	public static final int EVENT_WORKFLOW_TASK_ASSIGNED = 666;
	
	/**
	 * Event which is fired when a task gets pooled which means
	 * the assigned user has been removed
	 */
	public static final int EVENT_WORKFLOW_TASK_POOLED = 667;
	
	/**
	 * Event which is fired when a task gets closed
	 */
	public static final int EVENT_WORKFLOW_TASK_FINISHED = 668;
		
	/**
	 * Event which is fired when a task has been commented
	 */
	public static final int EVENT_WORKFLOW_TASK_COMMENTED = 669;
		
	/**
	 * Event which is fired when a resource has been moved to another
	 * VFS location within the current project.
	 */
	public static final int EVENT_WORKFLOW_RESOURCE_MOVED = 670;	
	
}
