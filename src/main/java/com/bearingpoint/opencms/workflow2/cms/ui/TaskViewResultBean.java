/**
 * 
 */
package com.bearingpoint.opencms.workflow2.cms.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.opencms.file.CmsGroup;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsProject;
import org.opencms.file.CmsUser;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;
import org.opencms.main.OpenCms;
import org.opencms.util.CmsUUID;

import com.bearingpoint.opencms.workflow2.I_WorkflowController;
import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.engine.NoEngineAttachedException;
import com.bearingpoint.opencms.workflow2.relation.I_RelationManager;
import com.bearingpoint.opencms.workflow2.relation.ResourceIdentifier;
import com.bearingpoint.opencms.workflow2.task.I_TaskManager;
import com.bearingpoint.opencms.workflow2.task.TaskException;
import com.bearingpoint.opencms.workflow2.task.TaskInstance;

/**
 * TaskViewResultBean
 * <p>
 * Container which holds the results for the 
 * task view ready for presentation.
 * <p>
 * @author David.Trattnig
 *
 */
public class TaskViewResultBean {

	public static final String HEADLINE = "%1 tasks in %2 areas found.";
	
	I_RelationManager relManager;
	I_TaskManager taskManager;
	CmsUser user;
	CmsObject cms;
	List<TaskInstance> userTasks;
	List<TaskInstance> pooledTasks;
	List<TaskInstance> managerTasks;
	List<TaskInstance> otherTasks; 
	boolean noWorkflowEngineAttached = false;
	Integer areaCount, taskCount;
	
    /** The log object. */
    private static final Log LOG = CmsLog.getLog(TaskViewResultBean.class);
    
	/**
	 * Constructor
	 */
	public TaskViewResultBean(CmsObject cms, I_WorkflowController wc) throws CmsException, TaskException {
		
    	try {
			taskManager = wc.getTaskManager();			
		} catch (NoEngineAttachedException e) {
			LOG.info("No workflow engine attached");
			noWorkflowEngineAttached = true;
			return;
		}
		
		this.relManager = wc.getRelationManager();
		this.cms = cms;
		this.user = cms.getRequestContext().currentUser();
		this.areaCount = 0;
		this.taskCount = 0;
		
		if (fillUserTasks()) {
			areaCount++;
			taskCount += userTasks.size();
		}
		if (fillPooledTasks()) {
			areaCount++;
			taskCount += pooledTasks.size();
		}
		if (fillManagerTasks()) {
			areaCount++;
			taskCount += managerTasks.size();
		}
		if (fillOtherTasks()) {
			areaCount++;
			taskCount += otherTasks.size();
		}
		
		attachResources(userTasks);
		attachResources(pooledTasks);
		attachResources(managerTasks);
		attachResources(otherTasks);
	}
	
	//TODO where to place???
	private void attachResources(List<TaskInstance> tasks) {
		
		ResourceIdentifier resource;
		
		for (TaskInstance task : tasks) {
			
			try {
				resource = relManager.getRelationForTask(task.getId());
				task.attachResource(resource);
			}
			catch (WorkflowException e) {
				LOG.fatal("Unexepected constellation: no engine availalbe");
			}						
		}
	}
	
	public List<TaskInstance> getUserTasks() {
		return userTasks;
	}
	
	public List<TaskInstance> getPooledTasks() {
		return pooledTasks;
	}	
	
	public List<TaskInstance> getManagerTasks() {
		return managerTasks;
	}	

	public List<TaskInstance> getOtherTasks() {
		return otherTasks;
	}
	
	private boolean fillUserTasks() {
				
		//read user task:
		try {
			userTasks = taskManager.getTasks(user);
		} catch (TaskException e) {
			LOG.error("error while reading tasks for user "+user.getName());
		}
				
		return (userTasks.size()>0) ? true : false;
	}
	
	private boolean fillPooledTasks() throws CmsException {
		
		List<CmsGroup> groupsOfUser=null;
		pooledTasks = new ArrayList<TaskInstance>();
		
		//read group tasks:
		try {
			groupsOfUser = cms.getGroupsOfUser(user.getName(), false);
	    	for (CmsGroup group : groupsOfUser) {
	    		pooledTasks.addAll(taskManager.getTasks(group));
	    	}
		} catch (TaskException e) {
			LOG.error("error while reading tasks for user "+user.getName());
		}
		return (pooledTasks.size()>0) ? true : false;
	}
	
	private boolean fillManagerTasks() throws CmsException, TaskException {
		
		//read project manager tasks (all tasks which are assigned to members
		//of projects where the current user is project manager:
		List<CmsProject> allProjects = cms.getAllAccessibleProjects(); //OpenCms.getOrgUnitManager().getGroups(cms, user.getOuFqn(), true);
		List<CmsGroup> userGroups = cms.getGroupsOfUser(user.getName(), false);
		managerTasks = new ArrayList();
		
		for (CmsProject project : allProjects) {			
			for (CmsGroup group : userGroups) {
				
				//found group where the current user is project manager:
				if (project.getManagerGroupId().toString().equals(group.getId())) {
					
					//read all users of the manager group
					CmsGroup mngrGroup = getGroupById(project.getGroupId());
					List<CmsUser> mngrUsers = cms.getUsersOfGroup(mngrGroup.getName());
					
					//add all tasks assigned to users
					for (CmsUser user : mngrUsers) {
						managerTasks.addAll(taskManager.getTasks(user));
					}										
				}
			}
		}
		return (managerTasks.size()>0) ? true : false;
	}
	
	private boolean fillOtherTasks() {
		
		//read all other tasks (and remove existing ones in other areas) -
		//these tasks are just for the admin view:
		otherTasks = taskManager.getTasks();
		List<TaskInstance> existingTasks = new ArrayList<TaskInstance>();
		
		for (TaskInstance task : otherTasks) {
			for (TaskInstance userTask : userTasks) {
				if (task.getId().equals(userTask.getId())) {
					existingTasks.add(task);
				}
			}
			for (TaskInstance pooledTask : pooledTasks) {
				if (task.getId().equals(pooledTask.getId())) {
					existingTasks.add(task);
				}
			}
			for (TaskInstance managerTask : managerTasks) {
				if (task.getId().equals(managerTask.getId())) {
					existingTasks.add(task);
				}
			}			
		}
		otherTasks.removeAll(existingTasks);
		return (otherTasks.size()>0) ? true : false;
	}
	
	private CmsGroup getGroupById(CmsUUID uuid) throws CmsException {
		
		List<CmsGroup> groups;
		
		groups = OpenCms.getOrgUnitManager().getGroups(cms, user.getOuFqn(), true);

		for (CmsGroup group : groups) {
			if (group.getId().toString().equals(uuid.toString())) {
				return group;
			}
		}		
		return null;
	}
		
    /**
     * Checks if a workflow engine is attached.
     * If not, task functionality is not available.
     * <p>
     * @return true if a workflow engine is attached.
     */
    public boolean getIsWorkflowEngineAttached() {
    	return !noWorkflowEngineAttached;
    }
    
    public String getHeadline() {
    	    	
    	String headline = HEADLINE.replace("%1", taskCount.toString());
    	headline = headline.replace("%2", areaCount.toString());
    	return headline;    	
    }
}
