/**
 * 
 */
package com.bearingpoint.opencms.workflow2.task;

import java.util.List;

import org.opencms.file.CmsGroup;
import org.opencms.file.CmsProject;
import org.opencms.file.CmsUser;

import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.relation.ResourceIdentifier;

/**
 * I_TaskManager
 * <p>
 * Provides handling for tasks.
 * <p>
 * @author David.Trattnig
 *
 */
public interface I_TaskManager {

	/**
	 * Retrieves all tasks for the given user 
	 * which are open (not closed or rejected).
	 * <p>
	 * @param user
	 * @return list of task instances
	 */
	public List<TaskInstance> getTasks(CmsUser user) throws TaskException;
	
//	/**
//	 * Retrieves all tasks for a group which are NOT assigned to
//	 * an specific user. Inside the workflow engine it may be 
//	 * handled as "tasks for role" or similiar.
//	 * <p>
//	 * @param group
//	 * @return list of tasks
//	 */
//	public List<TaskInstance> getTasks(CmsGroup group) throws TaskException;
	
	/**
	 * Retrieves all tasks which belong to a specific project (pooled)
	 * or to any user of this project. Just returns tasks which are
	 * open (not closed or rejected).
	 * Within the workflow engine this may be represented as "tasks for role",
	 * "tasks for group" or similar.
	 * <p>
	 * @param project
	 * @return list of task instances
	 */
	public List<TaskInstance> getTasks(CmsProject project) throws TaskException;
	
	/**
	 * Retrieves all tasks which are not closed or rejected.
	 * <p>
	 * @return list of task instances
	 */
	public List<TaskInstance> getTasks();

	/**
	 * Retrieves all tasks assigned to the given resource
	 * which are not closed or rejected. Normally the result
	 * will be just one task - in some cases (error while termination)
	 * there could be more.
	 * <p>
	 * @return list of task instances
	 */
	public List<TaskInstance> getTasks(ResourceIdentifier resource) throws WorkflowException, TaskException;
	
	/**
	 * Retrieves one specific task identified by id.
	 * <p>
	 * @param id
	 * @return task instance
	 */
	public TaskInstance getTask(String id) throws TaskNotAvailableException, TaskException;		
		
	
	/**
	 * Pools a task which means that the assigned user
	 * is removed (could be taken by any user).
	 * <p>
	 * @param id task id
	 */
	public void poolTask(String id) throws TaskException;
	
	/**
	 * Assigns a task to the given user. If the user parameter
	 * is null the task will be pooled.
	 * <p>
	 * 
	 * @param id task id
	 * @param user user to assign the task to
	 */
	public void assignTask(String id, CmsUser user) throws TaskException;
	
	/**
	 * Closes the task with the given id.
	 * <p>
	 * @param id
	 */
	public void closeTask(String id) throws TaskException;
	
	/**
	 * Adds a comment to a given task identfied by id.
	 * <p>
	 * @param taskId
	 * @param user
	 * @param comment
	 */
	public void addCommentToTask(String taskId, CmsUser user, String comment) throws TaskException;

}
