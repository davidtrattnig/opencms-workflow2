/**
 * 
 */
package com.bearingpoint.opencms.workflow2.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;

import com.bearingpoint.opencms.workflow2.WorkflowConfiguration;
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

	private Long taskID;
	private I_Task taskData;
	private ResourceIdentifier resource;
	private Calendar createDate;
	
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
	public TaskInstance(Long taskID, I_Task taskData, Calendar createDate) throws TaskException {
		
		if (taskID == null || taskID <= 0) {
			throw new TaskException("Invalid TaskID!"); 
		}
		if (taskData == null) {
			throw new TaskException("Invalid TaskData!");
		}
				
		this.taskID = taskID;
		this.taskData = taskData;
		this.createDate = createDate;
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
		DateFormat sdf = SimpleDateFormat.getDateInstance();
		return sdf.format(createDate.getTime());
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
			catch (Exception e) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("no valid user assigned to task #"+this.getId());
				}
				return "";
			}
	}

	public String getProjectName() {
		
		try {
			return CmsUtil.getCmsObject()
							.readProject(taskData.getProjectUUID()).getName();
		}
		catch (CmsException e) {
			LOG.error("invalid project", e);
			return "<span style=\"color:red\">INVALID PROJECT!</span>";
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
	
	public Date getDueDate() throws TaskException {
		
		Calendar calendar = (Calendar) createDate.clone();
        int dueDateDays = 0;
        
        try {
            dueDateDays = WorkflowConfiguration.getTaskDueDateDays();
        }
        catch (Exception e) {
            LOG.error("Couldn't load due-date-days from workflow configuration", e);
            throw new TaskException ("Couldn't load due-date-days from workflow configuration", e);
        }
        
        calendar.add(GregorianCalendar.DAY_OF_MONTH, dueDateDays);
        return calendar.getTime(); 
	}
}
