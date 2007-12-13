/**
 * 
 */
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
