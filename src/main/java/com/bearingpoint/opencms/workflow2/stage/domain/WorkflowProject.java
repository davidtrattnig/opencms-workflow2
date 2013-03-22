/* ***************************************************************************
 * This library is part of INFONOVA OpenCms Modules.
 * Common Modules for the Open Source Content Management System: OpenCms.
 *
 * Copyright (c) 2007 INFONOVA GmbH, Austria.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about INFONOVA GmbH, Austria, please
 * see the company website: http://www.infonova.at/
 *
 * For further information about INFONOVA OpenCms Modules, please see the
 * project website: http://sourceforge.net/projects/bp-cms-commons/
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *****************************************************************************/

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
