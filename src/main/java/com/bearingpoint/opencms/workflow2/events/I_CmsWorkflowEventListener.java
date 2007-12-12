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

	public static final int EVENT_WORKFLOW_STARTED = 661;
	
	public static final int EVENT_WORKFLOW_PUBLISHED = 662;
	
	public static final int EVENT_WORKFLOW_REJECTED = 663;
	
	public static final int EVENT_WORKFLOW_FINISHED = 664;
	
	public static final int EVENT_WORKFLOW_TASK_CREATED = 665;
	
	public static final int EVENT_WORKFLOW_TASK_ASSIGNED = 666;
	
	public static final int EVENT_WORKFLOW_TASK_POOLED = 667;
	
	public static final int EVENT_WORKFLOW_TASK_FINISHED = 668;
	
}
