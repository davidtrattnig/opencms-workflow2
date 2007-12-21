/**
 * 
 */
package com.bearingpoint.opencms.workflow2.events;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.opencms.main.CmsLog;
import org.opencms.main.OpenCms;

/**
 * WorkflowEventGun
 * <p>
 * Utility class which fires workflow related events.
 * The event handling can be done as usual OpenCms events.
 * <p>
 * @author David.Trattnig
 *
 */
public class WorkflowEventGun {

	private static final Log LOG = CmsLog.getLog(WorkflowEventGun.class);
	
	public static final I_CmsWorkflowEventListener events=null;
	
	/**
	 * Generic fire event method to fire unspecified events.
	 * You have to take care that EVENT_ID 
	 * <p>
	 * @param EVENT_ID
	 * @param object
	 */
	public static void fireEvent(final int EVENT_ID, Object object) {
		
		Map data =Collections.singletonMap("data", object);
		OpenCms.fireCmsEvent(EVENT_ID, data);
		if (LOG.isInfoEnabled()) {
			LOG.info("WF2 EVENT FIRED [ID:"+EVENT_ID+"]");
		}
	}
	
	/**
	 * Fires an event when a workflow has been started.
	 * <p>
	 * @param workflowID
	 */
	public static void fireEventWorkflowStarted(Long workflowID) {
		fireEvent(I_CmsWorkflowEventListener.EVENT_WORKFLOW_STARTED, workflowID);
		if (LOG.isInfoEnabled()) {
			LOG.info("WF2 EVENT FIRED: Workflow with id '"+workflowID+"' started");
		}
	}
	
	/**
	 * Fires an event when a workflow has been completed.
	 * <p>
	 * @param workflowID
	 */
	public static void fireEventWorkflowFinished(Long workflowID) {
		fireEvent(I_CmsWorkflowEventListener.EVENT_WORKFLOW_FINISHED, workflowID);
		if (LOG.isInfoEnabled()) {
			LOG.info("WF2 EVENT FIRED: Workflow with id '"+workflowID+"' finished");
		}
	}
	
	/**
	 * Fires an event when a task has been pooled, which means
	 * that the assigned user has been removed.
	 * <p>
	 * @param taskID
	 */
	public static void fireEventTaskPooled(Long taskID) {
		
		fireEvent(I_CmsWorkflowEventListener.EVENT_WORKFLOW_TASK_POOLED, taskID);
		if (LOG.isInfoEnabled()) {
			LOG.info("WF2 EVENT FIRED: Task with id '"+taskID+"' pooled");
		}
	}
	
	public static void fireEventTaskAssigned(Long taskID, String userID) {
		
		Object[] taskData = new Object[2];
		taskData[0] = taskID;
		taskData[1] = userID;
		fireEvent(I_CmsWorkflowEventListener.EVENT_WORKFLOW_TASK_ASSIGNED, taskData);
		if (LOG.isInfoEnabled()) {
			LOG.info("WF2 EVENT FIRED: Task with id '"+taskID
					+"' assigned to user with UUID '"+userID+"'");
		}
	}
		
	/**
	 * Fires an evetn when a task has been closed/is finished
	 * <p>
	 * @param taskID
	 */
	public static void fireEventTaskClosed(Long taskID) {
		
		fireEvent(I_CmsWorkflowEventListener.EVENT_WORKFLOW_TASK_FINISHED, taskID);
		if (LOG.isInfoEnabled()) {
			LOG.info("WF2 EVENT FIRED: Task with id '"+taskID+"' closed");
		}
	}
	
	/**
	 * Fires an event when a user added a commented to the task.
	 * <p>
	 * @param taskID
	 * @param userID
	 * @param comment
	 */
	public static void fireEventTaskCommented(Long taskID, String userID, String comment) {
		
		Object[] taskData = new Object[3];
		taskData[0] = taskID;
		taskData[1] = userID;
		taskData[2] = comment;
		fireEvent(I_CmsWorkflowEventListener.EVENT_WORKFLOW_TASK_COMMENTED, taskData);	
		if (LOG.isInfoEnabled()) {
			LOG.info("WF2 EVENT FIRED: Task with id '"+taskID
					+"' commented by user with UUID '"
					+userID+"' (comment: '"+comment+"'");
		}
	}
	
	/**
	 * Fires an event when a task has been created.
	 * <p>
	 * @param taskID
	 * @param taskName
	 */
	public static void fireEventTaskCreated(Long taskID, String taskName) {
		
		Object[] taskData = new Object[2];
		taskData[0] = taskID;
		taskData[1] = taskName;
		fireEvent(I_CmsWorkflowEventListener.EVENT_WORKFLOW_TASK_CREATED, taskData);	
		if (LOG.isInfoEnabled()) {
			LOG.info("WF2 EVENT FIRED: Task '"+taskName+"' with id '"+taskID+"' created");
		}
	}
	
}
