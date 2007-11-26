/**
 * 
 */
package com.bearingpoint.opencms.workflow2.stage.domain;

import org.opencms.util.CmsUUID;


/**
 * A project representing a workflow stage.
 * <p>
 * @author David.Trattnig
 *
 */
public class WorkflowProject {
	
	//represents the stage of the workflow
	private Long id;
	private Integer stage;
	private String projectID;
	
	/**
	 * @return the projectID
	 */
	public String getProjectID() {
		return projectID;
	}
	
	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	
	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(CmsUUID projectID) {
		this.projectID = projectID.toString();
	}
	
	/**
	 * @return the stage
	 */
	public Integer getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(Integer stage) {
		this.stage = stage;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
}
