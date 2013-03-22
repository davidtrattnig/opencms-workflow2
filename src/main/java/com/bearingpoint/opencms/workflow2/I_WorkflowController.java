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
package com.bearingpoint.opencms.workflow2;

import org.opencms.db.CmsPublishList;
import org.opencms.file.CmsProject;
import org.opencms.file.CmsResource;
import org.springframework.transaction.annotation.Transactional;

import com.bearingpoint.opencms.workflow2.engine.NoEngineAttachedException;
import com.bearingpoint.opencms.workflow2.relation.I_RelationManager;
import com.bearingpoint.opencms.workflow2.stage.I_ProjectManager;
import com.bearingpoint.opencms.workflow2.task.I_Task;
import com.bearingpoint.opencms.workflow2.task.I_TaskManager;

/**
 * I_WorkflowController
 * <p>
 * Main interface for controlling the workflow functionalities.
 * <p>
 * @author David.Trattnig
 *
 */
public interface I_WorkflowController {

	/***********************************/
	/**** Manager retrieval methods ****/
	/***********************************/
	
	/**
	 * Retrieves the instance of the relations manager
	 * which handles relationships between CmsResources
	 * and Workflows (identified by workflow ids).
	 * <p>
	 * @return Relation Manager
	 */
	public I_RelationManager getRelationManager();
	
	/**
	 * Manages the workflow nodes defined as OpenCms projects.
	 * Returns valid target projects for given resource states.
	 * <p>
	 * @return Project Manager
	 */
	public I_ProjectManager getProjectManager();
	
	/**
	 * Manages the tasks assigned to workflows, user, projects.
	 * <p>
	 * @return Task Manager
	 */
	public I_TaskManager getTaskManager() throws NoEngineAttachedException;
	
	/**
	 * Publishes a list of resources to the target project and creates a default task
	 * defined via the workflow configuarion.
	 * Additionally the -publish- transition of the workflow is performed.
	 * <p>
	 * @param publishList
	 * @param targetProject
	 * @throws WorkflowException
	 * @throws WorkflowPublishNotPermittedException
	 */
	@Transactional
	public abstract void actionApprove(CmsPublishList publishList, CmsProject targetProject)
	 	throws WorkflowException, WorkflowPublishNotPermittedException;	
	
	/**
	 * Publishes a single resource to the target project and creates a default task
	 * defined via the workflow configuration.
	 * Additionally the -publish- transition of the workflow is performed.  
	 *<p>
	 * @param resource
	 * @param targetProject
	 * @throws WorkflowException
	 * @throws WorkflowPublishNotPermittedException
	 */	
	@Transactional
	public abstract void actionApprove(CmsResource resource, CmsProject targetProject)
			throws WorkflowException, WorkflowPublishNotPermittedException;

	/**
	 * Publishes a single resource to the target project and creates the assigned task
	 * defined via the workflow configuration.
	 * Additionally the -publish- transition of the workflow is performed.  
	 *<p>
	 * @param resource
	 * @param targetProject
	 * @throws WorkflowException
	 * @throws WorkflowPublishNotPermittedException
	 */	
	@Transactional
	public abstract void actionApproveWithTask(CmsResource resource, CmsProject targetProject, I_Task task)
			throws WorkflowException, WorkflowPublishNotPermittedException;

	/**
	 * Publishes a resource to the online project and removes/closed the 
	 * attached workflow.
	 * <p>
	 * @param resource
	 * @throws WorkflowException
	 */
	@Transactional
	public abstract void actionPublish(CmsResource resource)
			throws WorkflowException, WorkflowPublishOnlineNotPermittedException;
	
	/**
	 * Moves the given resource to the given target project.
	 * Additionally the -reject- transition of the workflow is performed.
	 * <p>
	 * @param resource
	 * @throws WorkflowException
	 */
	@Transactional
	public abstract void actionReject(CmsResource resource, CmsProject targetProject)
			throws WorkflowException, WorkflowRejectNotPermittedException;
	

}