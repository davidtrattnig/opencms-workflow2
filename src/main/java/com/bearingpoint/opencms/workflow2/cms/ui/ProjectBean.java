/**
 * 
 */
package com.bearingpoint.opencms.workflow2.cms.ui;

import com.bearingpoint.opencms.workflow2.stage.ProjectWrapper;

/**
 * @author David.Trattnig
 *
 */
public class ProjectBean {
	
	private String name;
	private String id;
	private String stage;
	
	public ProjectBean(ProjectWrapper wrapper) {
		
		this.name = wrapper.getName();
		this.id = wrapper.getId();
		this.stage = wrapper.getStage().toString();
	}
	
	public ProjectBean() {
		name="";
		id="";
		stage="";
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	
}
