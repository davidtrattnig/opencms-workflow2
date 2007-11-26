/**
 * 
 */
package com.bearingpoint.opencms.workflow2.stage;

import org.opencms.file.CmsProject;

import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.stage.domain.WorkflowProject;

/**
 * ProjectWrapper
 * <p>
 * Wraps CmsProject and WorkflowProject for displaying
 * at the frontend.
 * <p>
 * @author David.Trattnig
 *
 */
public class ProjectWrapper {
	
	private CmsProject cmsProject;
	private WorkflowProject wfProject;
	
	public ProjectWrapper(CmsProject cmsProject, WorkflowProject wfProject) throws WorkflowException {
		
		if (cmsProject == null || wfProject == null) {
			throw new WorkflowException("provided projects are not valid.");
		}
		else if (!cmsProject.getUuid().toString().equals(wfProject.getProjectID())) {
			throw new WorkflowException("provided projects are not the same.");
		}
		
		this.cmsProject = cmsProject;
		this.wfProject = wfProject;
	}
	
	private ProjectWrapper() {
		
	}
	
	public String getId() {
		
		return cmsProject.getUuid().toString();
	}
	
	public String getName() {

		return cmsProject.getName();
	}
	
	public Integer getStage() {
		
		return wfProject.getStage();
	}

	public CmsProject getCmsProject() {
		return cmsProject;
	}

}
