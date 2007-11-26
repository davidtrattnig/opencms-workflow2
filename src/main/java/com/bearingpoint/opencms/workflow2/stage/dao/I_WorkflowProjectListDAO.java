/**
 * 
 */
package com.bearingpoint.opencms.workflow2.stage.dao;

import java.util.List;

import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.stage.NoProjectsAvailableException;
import com.bearingpoint.opencms.workflow2.stage.domain.WorkflowProject;

/**
 * I_WorkflowProjectListDAO
 * <p>
 * @author David.Trattnig
 *
 */
public interface I_WorkflowProjectListDAO {

	/**
	 * Updates the current workflow with the given workflow list. 
	 * 
	 * @param project list defining the complete workflow
	 */
	public void updateProjectWorkflow(List<WorkflowProject> project);
	
	/**
	 * Retrieves all projects which represent the workflow
	 * <p>
	 * @return all projects defining the workflow.
	 * @throws WorkflowException
	 */
	public List<WorkflowProject> retrieveAllWorkflowProjects() throws NoProjectsAvailableException;

}
