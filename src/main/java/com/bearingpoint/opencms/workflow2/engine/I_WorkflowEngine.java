/**
 * 
 */
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
	
	/**
	 * Retrieves all tasks for a group which are NOT assigned to
	 * an specific user. Inside the workflow engine it may be 
	 * handled as "tasks for role" or similiar.
	 * <p>
	 * @param group
	 * @return list of tasks
	 */
	public List<TaskInstance> getGroupTasks(CmsUUID group);
	
	/**
	 * Assigns a task to the given user.
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
