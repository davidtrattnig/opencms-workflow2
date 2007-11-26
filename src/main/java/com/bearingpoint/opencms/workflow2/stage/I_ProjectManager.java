/**
 * 
 */
package com.bearingpoint.opencms.workflow2.stage;

import java.util.List;

import org.opencms.file.CmsObject;
import org.opencms.file.CmsProject;
import org.opencms.file.CmsResource;
import org.opencms.file.CmsUser;

import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.stage.domain.WorkflowProject;

/**
 * I_ProjectManger
 * <p>
 * Manages the workflow nodes defined as OpenCms projects.
 * Returns valid target projects for given resource states.
 * <p>
 * The online project is excluded in all of these lists as 
 * it is a special project.
 * 
 * @author David.Trattnig
 * @since 2007/07/20 
 */
public interface I_ProjectManager {

	/**
	 * Initializes the cms object.
	 * <p>
	 * @param cms
	 */
	public void setCmsObject(CmsObject cms);
		
	/**
	 * Retrieves all projects which represent the stages
	 * of the current workflow. The order of the elements
	 * is equal to the stages.
	 * <p>
	 * @param resource
	 * @return list of project nodes
	 */
	public List<ProjectWrapper> getAllWorkflowProjects() throws WorkflowException;
	
	/**
	 * Retrieves all projects the current resource can published by the current user.
	 * The valid target project is evaluated by the following scheme:
	 * <p>
	 * 1. read the groups of the current user<br/>
	 * 2. get the projects which have these user groups assigned<br/>
	 * 3. determine all projects following to these user projects (identified by the stage order number)<br/>
	 * 4. return these projects<br/>
	 *  <p>
	 * @param resource
	 * @return valid projects the resource can be moved to.
	 */
	public List<ProjectWrapper> getValidTargetProjects(CmsUser currentUser, CmsResource resource) throws WorkflowException;
	
	/**
	 * Retrieves all projects the resource can be moved to when hitting the 
	 * reject button. The valid project is evaluated by following process:
	 * <p>
	 * 1. retrieve the project the given resource belongs to<br/>
	 * 2. return all projects which are before the current project in the<br/>
	 *    workflow project order (identified by the stage order number).<br/>
	 * <p>
	 * @param currentUser
	 * @return valid projects the resource can be moved to.
	 * @throws WorkflowException
	 */
	public List<ProjectWrapper> getValidRejectProjects(CmsResource resource) throws WorkflowException;
	
	/**
	 * Updates the workflow by deleting the current one and inserting the given one.
	 * The order of the projects inside the list a representing the workflow.
	 * <p>
	 * @param project
	 * @throws WorkflowException
	 */
	public void updateProjectWorkflow(List<CmsProject> projects) throws WorkflowException;	
	
	/**
	 * Adds the given projected at the workflow level
	 * defined as stage.
	 * <p>
	 * @param stage
	 */
	public void addProject(CmsProject project, int stage) throws WorkflowException;
	
	/**
	 * Deletes a project at the given workflow level (stage)
	 * <p>
	 * @param stage
	 */
	public void deleteProject(int stage) throws WorkflowException;
	
	/**
	 * Deletes the given project from the workflow.
	 * <p>
	 * @param project
	 */
	public void deleteProject(CmsProject project) throws WorkflowException;
	
	/**
	 * Deletes the given project identified by ID from the workflow.
	 * <p>
	 * @param project
	 */
	public void deleteProject(String projectID) throws WorkflowException;
	
	/**
	 * Retrieves a project identified by the given project id.
	 * <p>
	 * 
	 * @param projectID
	 * @throws WorkflowException
	 * @return WorkflowProject the requested project
	 */
	public ProjectWrapper getProjectById(String projectID) throws WorkflowException;
	
	/**
	 * Retrieves the stage of a given project.
	 * <p>
	 * @param project
	 * @return
	 * @throws WorkflowException
	 */
	public int getProjectStage(CmsProject project) throws WorkflowException;
	
	/**
	 * Updates the current stage of a project to the given one.
	 * <p>
	 * @param projectID
	 * @param stage
	 * @throws WorkflowException
	 */
	public void updateProjectStage(String projectID, int stage) throws WorkflowException;
	
	/**
	 * Removes all workflow projects.
	 * <p>
	 */
	public void removeAllProjects() throws WorkflowException;
	
	/**
	 * Retrieves all projects which are manageable by the current user
	 * and which are not assigned to the workflow.
	 * <p>
	 * @return list of available projects to be assigned
	 */
	public List<CmsProject> getUnassignedProjects() throws WorkflowException;
	
	/**
	 * Removes the project with the given ID.
	 * <p>
	 * @param projectID
	 * @throws WorkflowException
	 */
	public void removeProject(String projectID) throws WorkflowException;
	
}
