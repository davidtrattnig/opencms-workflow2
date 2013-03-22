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

package com.bearingpoint.opencms.workflow2.engine;

import java.util.List;

import org.opencms.file.CmsUser;
import org.opencms.util.CmsUUID;

import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.task.I_Task;
import com.bearingpoint.opencms.workflow2.task.TaskInstance;

/**
 * I_WorkflowEngine
 * <p>
 * Interface to implement the workflow engine delegations/interaction methods.
 * <p>
 * @author David.Trattnig
 */
public interface I_WorkflowEngine {
	
	/**
	 * Returns the the name/ID of the attached engine.
	 * <p>
	 * @return
	 */
	public String getEngineID();
	
	/**
	 *  startWorkflow
	 *  <p>
	 *  creates a process instance
	 *  <p>
	 *  @return workflow id
	 *  @throws WorkflowException 
	 */
	public String startWorkflow() throws WorkflowException;
		
	/**
	 * Performs the specified transition within the workflow.
	 * <p>
	 * @param processID
	 * @param TRANSITION
	 * @return
	 * @throws WorkflowTransitionNotAvailableException
	 */
	public void performTransition(String processID, final Transition TRANSITION) throws WorkflowTransitionNotAvailableException;
	
	/**
	 * Performs the specified transition within the workflow.
	 * Attaches the given task to the process context. At the 
	 * next task node the task will be created.
	 * <p>
	 * @param processID
	 * @param TRANSITION
	 * @param task
	 * @throws WorkflowTransitionNotAvailableException
	 */
	public void performTransition(String processID, final Transition TRANSITION, I_Task task) throws WorkflowTransitionNotAvailableException;

	/**
	 * Retrieves all tasks which are open inside the workflow engine
	 * which are not cancelled or closed.
	 * <p>
	 * @return list of all tasks
	 */
	public List<TaskInstance> getAllTasks();
	
	/**
	 * Retrieves all tasks whiche are open and assigned 
	 * to the given user.
	 * <p>
	 * @param user
	 * @return list of tasks
	 */
	public List<TaskInstance> getUserTasks(CmsUUID user);
	
	/**
	 * Retrieves a list of all open tasks for a specific workflow.
	 * <p>
	 * @param processID
	 * @return list of tasks
	 */
	public List<TaskInstance> getWorkflowTasks(String processID);
	
//	/**
//	 * Retrieves all tasks for a group which are NOT assigned to
//	 * an specific user. Inside the workflow engine it may be 
//	 * handled as "tasks for role" or similiar.
//	 * <p>
//	 * @param group
//	 * @return list of tasks
//	 */
//	public List<TaskInstance> getGroupTasks(CmsUUID group);
	
	/**
	 * Retrieves all tasks for a project which are NOT assigned to
	 * an specific user. Inside the workflow engine it may be 
	 * handled as "tasks for role" or similiar.
	 * <p>
	 * @param group
	 * @return list of tasks
	 */
	public List<TaskInstance> getProjectTasks(CmsUUID project);
	
	/**
	 * Assigns a task to the given user.
	 * If parameter <code>user</code> is null
	 * the task will be pooled (assignment will
	 * be removed).
	 * <p>
	 * 
	 * @param id task id
	 * @param user user to assign the task to
	 */
	public void assignTask(String id, CmsUUID user);
		
	/**
	 * Retrieves one specific task identified by id.
	 * <p>
	 * @param id
	 * @return task instance
	 */
	public TaskInstance getTask(String id);
	
	/**
	 * Pools a task which means that the assigned user
	 * is removed (could be taken by any user).
	 * <p>
	 * @param id task id
	 */
	public void poolTask(String id);
	
	/**
	 * Closes the task with the given id.
	 * <p>
	 * @param id
	 */
	public void closeTask(String id);
	
	/**
	 * Adds a comment to a given task identfied by id.
	 * <p>
	 * @param taskId
	 * @param user
	 * @param comment
	 */
	public void addCommentToTask(String taskId, CmsUUID user, String comment);
	
	/**
	 * Checks if the given workflow id is valid
	 * (existing within the engine database).
	 * <p>
	 * @param id
	 * @return
	 */
	public boolean isValidWorkflowID(String id);
	
	/**
	 * Retrieves the workflow id the given task
	 * is assigned to.
	 * <p>
	 * @param taskId
	 * @return workflow id as String
	 */
	public String getWorkflowIdOfTask(long taskId);
}
