/**
 * 
 */
package com.bearingpoint.opencms.workflow2.task;

import java.util.ArrayList;
import java.util.List;

import org.opencms.file.CmsGroup;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsProject;
import org.opencms.file.CmsUser;
import org.opencms.util.CmsUUID;

import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.engine.I_WorkflowEngine;
import com.bearingpoint.opencms.workflow2.relation.I_RelationManager;
import com.bearingpoint.opencms.workflow2.relation.ResourceIdentifier;

/**
 * I_TaskManager
 * <p>
 * Provides handling for tasks.
 * <p>
 * @author David.Trattnig
 *
 */
public class TaskManager implements I_TaskManager {

	I_WorkflowEngine engine;
	I_RelationManager relManager;
	CmsObject cms;
	
	/**
	 * Constructor for initializing the Task Manager
	 * with the workflow engine.
	 * <p>
	 * @param engine
	 */
	public TaskManager(CmsObject cms, I_WorkflowEngine engine, I_RelationManager relManager) {
		this.cms = cms;
		this.engine = engine;
		this.relManager = relManager;
	}
	
	/**
	 * Private default constructor
	 */
	private TaskManager() {}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_TaskManager#assignTask(java.lang.String, org.opencms.file.CmsUser)
	 */
	public void assignTask(String id, CmsUser user) throws TaskException {
				
		if (id==null 
				|| id.length()<=0
				|| engine.getTask(id) == null) {
			
			throw new TaskException("Invalid task id");
		}
		
		CmsUUID userID = null;
		if (user != null) {
			userID = user.getId();
		}
		engine.assignTask(id, userID);
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_TaskManager#getTask(java.lang.String)
	 */
	public TaskInstance getTask(String id) throws TaskNotAvailableException, TaskException {

		if (id == null || id.length()<=0) {
			throw new TaskException("Invalid id");
		}
		
		TaskInstance task = engine.getTask(id);
		if (task == null) {
			throw new TaskNotAvailableException("Task with id not available!");
		}
		
		return task;
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_TaskManager#getTasks()
	 */
	public List<TaskInstance> getTasks() {

		List<TaskInstance> tasks = engine.getAllTasks();
		
		//ensure no null objects for GUI access:
		if (tasks.size()==0) {
			tasks = new ArrayList();
		}
		
		return tasks;
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_TaskManager#getTasks(org.opencms.file.CmsProject)
	 */
	public List<TaskInstance> getTasks(CmsProject project) throws TaskException {
		
		//TODO add implementation
		throw new TaskException("not implemented!!");
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_TaskManager#getTasks(org.opencms.file.CmsUser)
	 */
	public List<TaskInstance> getTasks(CmsUser user) throws TaskException {

		if (user == null) {
			throw new TaskException ("Invalid user (null)");
		}
		List<TaskInstance> tasks = engine.getUserTasks(user.getId());
		
		return tasks;
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_TaskManager#getTasks(org.opencms.file.CmsGroup)
	 */
	public List<TaskInstance> getTasks(CmsGroup group) throws TaskException {

		if (group == null) {
			throw new TaskException("group is null");
		}
		
		return engine.getGroupTasks(group.getId());
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_TaskManager#getTasks(com.bearingpoint.opencms.workflow2.relation.ResourceIdentifier)
	 */
	public List<TaskInstance> getTasks(ResourceIdentifier resource) throws WorkflowException, TaskException {

		if (resource == null) {
			throw new TaskException ("Invalid resource identifier (null)");
		}
		
		String wfID = relManager.resolve(resource);
		List<TaskInstance> tasks = engine.getWorkflowTasks(wfID);
		return tasks;
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_TaskManager#poolTask(java.lang.String)
	 */
	public void poolTask(String id) throws TaskException {
		
		assignTask(id, null);
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_TaskManager#closeTask(java.lang.String)
	 */
	public void closeTask(String id) throws TaskException {
		
		if (id==null || id.length()<=0) {
			throw new TaskException ("Invalid task id");			
		}
		
		engine.closeTask(id);
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.task.I_TaskManager#addCommentToTask(java.lang.String, org.opencms.util.CmsUUID, java.lang.String)
	 */
	public void addCommentToTask(String taskId, CmsUser user, String comment) throws TaskException {
		
		if (taskId == null || taskId.length()<=0) {
			throw new TaskException ("Invalid task id");
		}
		else if (user == null) {
			throw new TaskException ("Invalid user (null)");
		}
		else if (comment == null || comment.length()<=0) {
			throw new TaskException ("Invalid comment");
		}
		
		engine.addCommentToTask(taskId, user.getId(), comment);
	}
}
