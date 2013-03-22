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
