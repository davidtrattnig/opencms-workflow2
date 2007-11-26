/**
 * 
 */
package com.bearingpoint.opencms.workflow2;

import java.util.List;

import org.apache.commons.logging.Log;
import org.opencms.db.CmsPublishList;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsProject;
import org.opencms.file.CmsResource;
import org.opencms.main.CmsLog;
import org.opencms.main.I_CmsEventListener;
import org.opencms.main.OpenCms;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.io.DefaultResourceLoader;

import com.bearingpoint.opencms.commons.springmanager.SpringManager;
import com.bearingpoint.opencms.workflow2.engine.I_WorkflowEngine;
import com.bearingpoint.opencms.workflow2.engine.Transition;
import com.bearingpoint.opencms.workflow2.engine.WorkflowTransitionNotAvailableException;
import com.bearingpoint.opencms.workflow2.relation.I_RelationManager;
import com.bearingpoint.opencms.workflow2.relation.RelationIntegrityListener;
import com.bearingpoint.opencms.workflow2.relation.RelationManager;
import com.bearingpoint.opencms.workflow2.relation.ResourceIdentifier;
import com.bearingpoint.opencms.workflow2.stage.I_ProjectManager;
import com.bearingpoint.opencms.workflow2.stage.ProjectManager;
import com.bearingpoint.opencms.workflow2.task.I_Task;
import com.bearingpoint.opencms.workflow2.task.I_TaskManager;
import com.bearingpoint.opencms.workflow2.task.Task;
import com.bearingpoint.opencms.workflow2.task.TaskManager;

/**
 * WorkflowControllerImpl
 * <p>
 * Implementation of the Workflow Controller Class
 * which represents the main entry point for workflow operations
 * <p>
 * @author David.Trattnig
 * 
 */
public class WorkflowControllerImpl {

//	private static final String SPRING_CONFIG = "src/main/opencms/system/modules/com.bearingpoint.opencms.workflow2/config/spring/spring-config.xml";
	private static final String SPRING_ENGINE_BEAN = "workflow.workflowEngine";
	
	private static boolean engineNotAttached;	
	
	private I_WorkflowEngine _engine;
	private static I_RelationManager _relationManager;
	private I_ProjectManager _projectManager;
	private I_TaskManager _taskManager;
	private CmsObject _cms;
	
	private static final Log LOG = CmsLog.getLog(WorkflowControllerImpl.class);
	
	/**
	 * Constructor
	 */
	protected WorkflowControllerImpl(CmsObject cms) throws WorkflowException {
		
//		DefaultResourceLoader drl = new DefaultResourceLoader();		
//		BeanFactory factory = new XmlBeanFactory(drl.getResource(SPRING_CONFIG));		
//		_engine = (I_WorkflowEngine) factory.getBean(SPRING_ENGINE_BEAN);
		
		try {
			_engine = (I_WorkflowEngine) SpringManager.getBean(SPRING_ENGINE_BEAN);
			
			if (_engine == null) {
				engineNotAttached = true;
			}
			else {
				engineNotAttached = false;
			}
		}
		catch(NoSuchBeanDefinitionException e) {		
			engineNotAttached = true;
		}
		catch(NullPointerException e) {
			engineNotAttached = true;
		}

		_cms = cms;
		_relationManager = new RelationManager(_engine);				
		_taskManager = new TaskManager(_cms, _engine, _relationManager);

//		try {
//			_projectManager = (I_ProjectManager) SpringManager.getBean(SPRING_PROJECTMANAGER_BEAN);
//		}
//		catch (NoSuchBeanDefinitionException e) {
//			LOG.fatal("Could not instantiate project manager", e);
//		}
		_projectManager = new ProjectManager(cms);
			
		//register eventlistener to verify the integrity of the 
		//resource-workflow relations:
		I_CmsEventListener listener = new RelationIntegrityListener(_relationManager);
		OpenCms.addCmsEventListener(listener, 
				new int[] {(I_CmsEventListener.EVENT_RESOURCE_MOVED)});
				
	}
	
	/**
	 * Hidden default constructor 
	 */
	private WorkflowControllerImpl() {}
	
	/***********************************/
	/**** Manager retrieval methods ****/
	/***********************************/
	
	/**
	 * Retrieves the instance of the relations manager
	 * which handles relationships between CmsResources
	 * and Workflows (identified by workflow ids).
	 * 
	 * @return Relation Manager
	 */
	protected I_RelationManager getRelationManager() {
		return _relationManager;
	}
	
	/**
	 * Manages the tasks assigned to workflows, user, projects.
	 * <p>
	 * @return Task Manager
	 */
	public I_TaskManager getTaskManager() {

		return _taskManager;
	}
	
	public I_ProjectManager getProjectManager() {
		
		return _projectManager;
	}
	
	/************************/
	/**** Action methods ****/
	/************************/
		
	protected void actionApprove(CmsPublishList publishList, CmsProject targetProject)
								throws WorkflowException, WorkflowPublishNotPermittedException {
		
		@SuppressWarnings("unchecked")
		List<CmsResource> all = publishList.getAllResources();
		
		if (all != null && all.size() > 0) {
			for (CmsResource resource : all)  {
				actionApprove(resource, targetProject);
			}
		}
	}
	
	//transactional
	protected void actionApprove(CmsResource resource, CmsProject targetProject) throws WorkflowException, WorkflowPublishNotPermittedException {
				
		String message = WorkflowConfiguration.getDefaultTaskMessage();
		I_Task defaultTask = new Task(targetProject.getUuid(), resource.getUserLastModified(), "dummyTITLE", message);		
		actionApproveWithTask(resource, targetProject, defaultTask);				
	}
	
	//transactional
	protected void actionApproveWithTask(CmsResource resource, CmsProject targetProject, I_Task task) throws WorkflowException, WorkflowPublishNotPermittedException {

		//get workflow id
		String processID = getWorkflowID(new ResourceIdentifier(resource));
				
		//perform publish transition
		try {
			_engine.performTransition(processID, Transition.APPROVE, task);
		}
		catch (WorkflowTransitionNotAvailableException e) {
			throw new WorkflowPublishNotPermittedException("There is no publish transition for the current workflow state", e);
		}
	}
	
	//transactional
	protected void actionPublish(CmsResource resource) throws WorkflowException, WorkflowPublishOnlineNotPermittedException {
		
		//get workflow id
		String processID = getWorkflowID(new ResourceIdentifier(resource));
		
		//perform publish transition
		try {
			_engine.performTransition(processID, Transition.PUBLISH);
		}
		catch (WorkflowTransitionNotAvailableException e) {
			throw new WorkflowPublishOnlineNotPermittedException("There is no publish online transition for the current workflow state", e);
		}
		
		_relationManager.removeRelation(new ResourceIdentifier(resource));
	}
	
	//transactional
	protected void actionReject(CmsResource resource, CmsProject targetProject) throws WorkflowException, WorkflowRejectNotPermittedException {

		//get workflow id
		String processID = getWorkflowID(new ResourceIdentifier(resource));
		
		//perform publish transition
		try {
			_engine.performTransition(processID, Transition.REJECT);
		}
		catch (WorkflowTransitionNotAvailableException e) {
			throw new WorkflowRejectNotPermittedException("There is no reject transition for the current workflow state", e);
		}
	}
		
	/**
	 * Retrieves the current workflow engine object
	 * 
	 * @return I_WorkflowEngine
	 */
	protected I_WorkflowEngine getEngine() {
		return _engine;
	}				
	
	/**
	 * Retrieves the workflow id for the given resource.
	 * If there is not workflow yet it will be created.
	 * <p>
	 * 
	 * @param resource
	 * @return workflow id
	 */
	private String getWorkflowID(ResourceIdentifier resource) throws WorkflowException {
				
		String processID = null;
		
		try {
			//search for existing workflow
			if (getRelationManager().hasRelation(resource)) {
				//found workflow
				processID = getRelationManager().resolve(resource);	
				
				//check if the received processID still exists:
				if (!_engine.isValidWorkflowID(processID)) {
					//remove relation
					getRelationManager().removeRelation(resource);
					
					//create new workflow
					processID = _engine.startWorkflow();
					
					//store resource-workflow relation
					getRelationManager().addRelation(resource, processID);
				}				
			}
			//no workflow found
			else {
				//create workflow
				processID = _engine.startWorkflow();
				
				//store resource-workflow relation
				getRelationManager().addRelation(resource, processID);
			}
		} catch (WorkflowException e) {
			throw new WorkflowException ("Error while workflow retrieval.", e);
		}
		finally {
			if (processID == null) {
				throw new WorkflowException ("There seems to be an error while workflow instantiation - workflow id is null.");
			}
		}
		return processID;
	}
	
	/**
	 * Checks if an implementation of a workflow engine is attached.
	 * If not the functionality of the workflow module will be reduced
	 * (no task and state handling).
	 * <p>
	 * @return true if no engine attached
	 */
	protected static boolean isEngineNotAttached() {
		return engineNotAttached;
	}
	
}