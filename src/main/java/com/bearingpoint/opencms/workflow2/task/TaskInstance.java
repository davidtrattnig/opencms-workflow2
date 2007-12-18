/**
 * 
 */
package com.bearingpoint.opencms.workflow2.task;

import org.apache.commons.logging.Log;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;

import com.bearingpoint.opencms.workflow2.cms.CmsUtil;
import com.bearingpoint.opencms.workflow2.relation.ResourceIdentifier;


/**
 * TaskInstance
 * <p>
 * Instance of a task provided by the workflow engine.
 * <p>
 * 
 * @author David.Trattnig
 *
 */
public class TaskInstance {

	Long taskID;
	I_Task taskData;
	ResourceIdentifier resource;
	
	private static final Log LOG = CmsLog.getLog(TaskInstance.class);
	
	/**
	 * Hide default constructor 
	 */
	private TaskInstance() {}
	
	/**
	 * Constructor to initialize the task.
	 * <p>
	 * @param taskID
	 * @param taskData
	 * @throws TaskException
	 */
	public TaskInstance(Long taskID, I_Task taskData) throws TaskException {
		
		if (taskID == null || taskID <= 0) {
			throw new TaskException("Invalid TaskID!"); 
		}
		if (taskData == null) {
			throw new TaskException("Invalid TaskData!");
		}
				
		this.taskID = taskID;
		this.taskData = taskData;		
	}
	
	/**
	 * @return the taskData
	 */
	public I_Task getData() {
		return taskData;
	}
	
	/**
	 * @return the taskID
	 */
	public Long getId() {
		return taskID;
	}
	
	/**
	 * Checks if the task is pooled.
	 * <p>
	 * @return true if pooled
	 */
	public boolean getPooled() {
		
		if (taskData.getUserUUID() == null) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Retrieves the date the task has been created.
	 * <p>
	 * @return Date as String for presentation
	 */
	public String getCreateDate() {
				
		//TODO insert date presentation rules
		return taskData.getCreateDate().toString();
	}
	
	public String getDueDate() {
		
		try {
			return taskData.getDueDate().toString();
		}
		catch(TaskException e) {
			LOG.error("invalid due date", e);
			return "invalid due date!";
		}
	}
	
	public String getUserName() {
		
		if (!getPooled()) {
			try {
			return CmsUtil.getCmsObject()
							.readUser(taskData.getUserUUID()).toString();
			}
			catch (CmsException e) {
	//			TODO add log entry? + make configurable
				LOG.error("invalid user", e);
				return "invalid user";
			}
		}
		else {
			return "";
		}
	}
	
	public String getAssignedUserName() {
		
		try {
			return CmsUtil.getCmsObject()
							.readUser(taskData.getAssignedUserUUID()).toString();
			}
			catch (CmsException e) {
				LOG.error("invalid user", e);
				return "invalid user";
			}
	}

	public String getProjectName() {
		
		try {
			return CmsUtil.getCmsObject()
							.readProject(taskData.getProjectUUID()).toString();
			}
			catch (CmsException e) {
				LOG.error("invalid project", e);
				return "invalid project";
			}
	}

	public void attachResource(ResourceIdentifier resource) {

		this.resource = resource;
	}
		
	public String getResourceName() {
		
		return resource.getResourcePath();
	}
	
	public String getResourceId() {
		
		return resource.getResourceUUID();
	}
}
