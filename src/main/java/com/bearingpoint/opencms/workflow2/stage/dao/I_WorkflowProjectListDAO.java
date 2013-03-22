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
