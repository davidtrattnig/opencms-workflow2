/**
 * 
 */
package com.bearingpoint.opencms.workflow2.stage;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.opencms.file.CmsGroup;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsProject;
import org.opencms.file.CmsResource;
import org.opencms.file.CmsUser;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;
import org.opencms.util.CmsUUID;

import com.bearingpoint.opencms.commons.springmanager.SpringManager;
import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.cms.CmsMirPrBelongsToOtherInvisible;
import com.bearingpoint.opencms.workflow2.cms.CmsUtil;
import com.bearingpoint.opencms.workflow2.stage.dao.I_WorkflowProjectListDAO;
import com.bearingpoint.opencms.workflow2.stage.domain.WorkflowProject;

/**
 * ProjectManager
 * <p>
 * Manages the workflow nodes defined as OpenCms projects.
 * Returns valid target projects for given resource states.
 * <p>
 * 
 * @author David.Trattnig
 */
public class ProjectManager implements I_ProjectManager {

	private static final String SPRING_PROJECTMANAGER_DAO = "workflow.workflowProjectDAO";


	private static I_WorkflowProjectListDAO _dao;
	private CmsObject _cms;
		
	private static final Log LOG = CmsLog.getLog(ProjectManager.class);
	
	/**
	 * Constructor which initializes the project manager
	 * with the CmsObject and the workflow engine.
	 * <p>
	 * @param engine
	 */
	public ProjectManager(CmsObject cms) {
		
		_cms = cms;
		_dao = (I_WorkflowProjectListDAO) SpringManager.getBean(SPRING_PROJECTMANAGER_DAO);		
	}
	
	/**
	 * If you use this constructor you have to call
	 * <code>setCmsObject(CmsObject cms)</code> and
	 * <code>initDaoObjects()</code>
	 * afterwards. otherwise most operations will fail.
	 */
	public ProjectManager() {}

	/**
	 * Initializes the cms object.
	 * <p>
	 * @param cms
	 */
	public void setCmsObject(CmsObject cms) {
		this._cms = cms;
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#getAllWorkflowProjects()
	 */
	public List<ProjectWrapper> getAllWorkflowProjects() throws WorkflowException {

		List<CmsProject> allProjects = null;
		List<ProjectWrapper> matchingProjects = new ArrayList<ProjectWrapper>();
		List<WorkflowProject> projectWorkflow;
		
		try {
			projectWorkflow = _dao.retrieveAllWorkflowProjects();
		} catch (NoProjectsAvailableException e1) {
			return new ArrayList();
		}
		
		if (projectWorkflow == null || projectWorkflow.size()<=0) {
			return new ArrayList();
		}
		
		try { 
			allProjects = CmsUtil.getAdminCmsObject().getAllManageableProjects();
			
		} catch (CmsException e) {
			throw new WorkflowException("error while reading all CMS projects", e);
		}
		
		boolean foundMatchingProject;
		for (WorkflowProject wfProject : projectWorkflow) {

			foundMatchingProject=false;
			for (CmsProject project : allProjects) {
				if (project.getUuid().toString().equals(wfProject.getProjectID())) {
					ProjectWrapper wrapper = new ProjectWrapper(project, wfProject);
					matchingProjects.add(wrapper);
					foundMatchingProject=true;
				}
			}
			
			if (!foundMatchingProject) {
				throw new WorkflowException ("There may be a wrong workflow configuration - the workflow project "+wfProject.getProjectID()+" doesn't exist (anymore)!");
			}
		}
		
		return matchingProjects;		
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#getValidRejectProjects(org.opencms.file.CmsResource)
	 */
	public List<ProjectWrapper> getValidRejectProjects(CmsResource resource) throws WorkflowException {

		CmsUUID projectID = resource.getProjectLastModified();
		List<ProjectWrapper> validProjects = new ArrayList<ProjectWrapper>();
		
		//check if resource hasn't been modified yet:
		if (!CmsMirPrBelongsToOtherInvisible.DEFAULT_PR_ID.equals(projectID.toString())) {
			List<ProjectWrapper> allProjects = getAllWorkflowProjects();
						
			for (ProjectWrapper project : allProjects) {
				
				if (!projectID.toString().equals(project.getId().toString())) {
					//the resource is rejectable to this project because
					//it's before the current project:
					validProjects.add(project);
				}
				else {
					//the current project in the workflow is reached:
					break;
				}
			}
		}
		return validProjects;
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#getValidTargetProjects(org.opencms.file.CmsUser)
	 */
	public List<ProjectWrapper> getValidTargetProjects(CmsUser currentUser, CmsResource resource) throws WorkflowException {

		List<CmsGroup> groups = null;
		List<CmsProject> userProjects = new ArrayList<CmsProject>();
		List<ProjectWrapper> validProjects = new ArrayList<ProjectWrapper>();
		List<ProjectWrapper> allProjects = getAllWorkflowProjects();
		
		try {
			userProjects = _cms.getAllAccessibleProjects();
		} catch (CmsException e) {
			LOG.error("couldn't retrieve current users projects");
		}
		
		//determine the next project (following project) in the project order of the workflow:
		for (int i=0;i<allProjects.size();i++) {
			for (CmsProject userProject : userProjects) {
				if (allProjects.get(i).getId().toString().equals(userProject.getUuid().toString())) {
					if (i<(allProjects.size()-1)) {
						validProjects.add(allProjects.get(i+1));
					}
				}
			}
		}
		
		//remove the current project the resource belongs to (if existing in the valid list):
		for (ProjectWrapper project : validProjects) {
			if (resource.getProjectLastModified().toString().equals(project.getId())) {
				validProjects.remove(project);
				break;
			}
		}
		
		return validProjects;
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#updateProjectWorkflow(java.util.List)
	 */
	public void updateProjectWorkflow(List<CmsProject> projects) throws WorkflowException {

		if (projects == null) {
			throw new WorkflowException ("project list is null");
		}
		
		List<WorkflowProject> wfProjects = new ArrayList<WorkflowProject>();
		WorkflowProject wfp;
		
		for (int i=0;i<projects.size();i++) {
			wfp = new WorkflowProject();
			wfp.setProjectID(projects.get(i).getUuid());
			wfp.setStage(i);
			wfProjects.add(wfp);
		}
		
		_dao.updateProjectWorkflow(wfProjects);
	}
	
	/**
	 * Spring DAO Setter
	 * @param dao
	 */
	public void setWorkflowProjectDAO(I_WorkflowProjectListDAO dao) {
		
		this._dao = dao;
	}
	

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#addProject(org.opencms.file.CmsProject, int)
	 */
	public void addProject(CmsProject project, int stage) throws WorkflowException {
		
		List<WorkflowProject> allProjects=null;
		List<WorkflowProject> newProjects=new ArrayList<WorkflowProject>();
		WorkflowProject newProject = new WorkflowProject();
		newProject.setProjectID(project.getUuid());
		newProject.setStage(stage);
		
		try {
			allProjects = _dao.retrieveAllWorkflowProjects();
		} catch (NoProjectsAvailableException e) {
			//no projects available
		}
		
		if (allProjects == null) {
			allProjects = new ArrayList<WorkflowProject>();
		}		
		
		if (stage > allProjects.size()) {
			throw new WorkflowException("No valid project stage passed!");
		}
				
		boolean stored=false;
						
		for (WorkflowProject p : allProjects) {
			
			//insert all projects before the new project:
			if (p.getStage() != stage) {
				newProjects.add(p);				
			}
			else { //current project stage found:
				newProjects.add(newProject);
				stored=true;
				
				//insert all projects after the new project
				for (int i=stage;i<allProjects.size();i++) {
					allProjects.get(i).setStage(i+1);
					newProjects.add(allProjects.get(i));
				}
				
				break;				
				
			}
		}
		
		
		if (!stored && allProjects.size()==stage) {
			newProjects.add(newProject);
			stored=true;
		}
		
		if (!stored) {
			throw new WorkflowException("Invalid state - new project couldn't be added");
		}
		
		_dao.updateProjectWorkflow(newProjects);
	}
	

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#deleteProject(int)
	 */
	public void deleteProject(int stage) throws WorkflowException {

		List<WorkflowProject> allProjects;
		try {
			allProjects = _dao.retrieveAllWorkflowProjects();
		} catch (NoProjectsAvailableException e) {
			throw new WorkflowException("Project with at stage "+stage+" not found!");
		}
		
		boolean found=false;
		for (WorkflowProject p : allProjects) {
			if (p.getStage() == stage) {
				allProjects.remove(p);
				found=true;
				break;
			}
		}
		
		if (!found) {
			throw new WorkflowException("Project with at stage "+stage+" not found!");
		}
		
		_dao.updateProjectWorkflow(allProjects);
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#deleteProject(org.opencms.file.CmsProject)
	 */
	public void deleteProject(CmsProject project) throws WorkflowException {
		
		this.deleteProject(project.getUuid().toString());
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#deleteProject(java.lang.String)
	 */
	public void deleteProject(String projectID) throws WorkflowException {
		
		List<WorkflowProject> allProjects;
		try {
			allProjects = _dao.retrieveAllWorkflowProjects();
		} catch (NoProjectsAvailableException e) {
			throw new WorkflowException("Project with id "+projectID+" not found!");
		}
		
		//removes given project:
		boolean found=false;
		for (WorkflowProject p : allProjects) {
			if (p.getProjectID().equals(projectID)) {
				allProjects.remove(p);
				found=true;
				break;
			}
		}
			
		if (!found) {
			throw new WorkflowException("Project with id "+projectID+" not found!");
		}

		//update stages:
		for (int i=0;i<allProjects.size();i++) {
			allProjects.get(i).setStage(i);
		}
		
		_dao.updateProjectWorkflow(allProjects);
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#getProjectById(java.lang.String)
	 */
	public ProjectWrapper getProjectById(String projectID) throws WorkflowException {
		
		List<ProjectWrapper> allProjects;
		ProjectWrapper desiredProject = null;
	
		allProjects = this.getAllWorkflowProjects();
		
		boolean found=false;
		for (ProjectWrapper p : allProjects) {
			if (p.getId().toString().equals(projectID)) {
				desiredProject = p;
				found=true;
				break;
			}
		}
		
		if (!found) {
			throw new WorkflowException("Project with id "+projectID+" not found!");
		}
		
		return desiredProject;
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#getProjectStage(CmsProject)
	 */
	public int getProjectStage(CmsProject project) throws WorkflowException {
		
		List<WorkflowProject> allProjects;
			
		try {
			allProjects = _dao.retrieveAllWorkflowProjects();
		} catch (NoProjectsAvailableException e) {
			throw new WorkflowException("No projects available");
		}
		
		for (WorkflowProject p : allProjects) {
			if (p.getId().equals(project.getUuid().toString())) {
				return p.getStage();
			}
		}

		throw new WorkflowException("Requested project "+project.getUuid().toString()+" not found");
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#updateProjectStage(int)
	 */
	public void updateProjectStage(String projectID, int stage) throws WorkflowException {
		
		removeProject(projectID);		
		CmsProject p;
		try {
			p = _cms.readProject(new CmsUUID(projectID));
		} catch (NumberFormatException e) {
			throw new WorkflowException("The passed projectID is no valid UUID");
		} catch (CmsException e) {
			throw new WorkflowException("Could not read project with given id from CMS");
		}
		addProject(p, stage);
		
	}
		
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#removeProject(java.lang.String)
	 */
	public void removeProject(String projectID) throws WorkflowException {
		
		List<WorkflowProject> projects;

		try {
			projects = _dao.retrieveAllWorkflowProjects();
		} catch (NoProjectsAvailableException e) {
			throw new WorkflowException("There are currently no projects defined!");
		}
		
		boolean found=false;
		for (WorkflowProject p : projects) {
			if (p.getProjectID().equals(projectID)) {
				
				projects.remove(p);
				found = true;
				break;
			}
		}	
		
		if (found==false) {
			throw new WorkflowException ("The given project with id "+projectID+" couldn't be found!");
		}
		
		_dao.updateProjectWorkflow(projects);
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#removeAllProjects()
	 */
	public void removeAllProjects() throws WorkflowException{
				
		updateProjectWorkflow(new ArrayList<CmsProject>());
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.stage.I_ProjectManager#getUnassignedProjects()
	 */
	public List<CmsProject> getUnassignedProjects() throws WorkflowException {
		
		List<CmsProject> cmsProjects = null;
		List<CmsProject> assignableProjects = new ArrayList<CmsProject>();
		List<WorkflowProject> wfProjects = null;
		
		try {
			cmsProjects = _cms.getAllManageableProjects();
		}
		catch(CmsException e) {
			throw new WorkflowException("Error while reading manageable cms projects", e);
		}
		
		try {
			wfProjects = _dao.retrieveAllWorkflowProjects();
		} catch (NoProjectsAvailableException e) {
			//no projects assigned to workflow -
			//--> all of them available for assignment
			return cmsProjects;
		}
		
		boolean found;
		for (CmsProject cp : cmsProjects) {
			
			found=false;
			for (WorkflowProject wp : wfProjects) {
				if (cp.getUuid().toString().equals(
						wp.getProjectID())) {
					
					found=true;
				}
					
			}
			
			if (!found) {
				assignableProjects.add(cp);
			}
		}
		
		return assignableProjects;
	}
}
