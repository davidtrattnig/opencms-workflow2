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
import org.opencms.file.CmsObject;
import org.opencms.file.CmsProject;
import org.opencms.file.CmsResource;

import com.bearingpoint.opencms.workflow2.engine.NoEngineAttachedException;
import com.bearingpoint.opencms.workflow2.relation.I_RelationManager;
import com.bearingpoint.opencms.workflow2.stage.I_ProjectManager;
import com.bearingpoint.opencms.workflow2.task.I_Task;
import com.bearingpoint.opencms.workflow2.task.I_TaskManager;

/**
 * WorkflowController
 * <p>
 * Main interface to access the workflow functionalities.
 * <p>
 * @author David.Trattnig
 * 
 */
public class WorkflowController implements I_WorkflowController {
		
	WorkflowControllerImpl impl;	
	
	/**
	 * Constructor
	 * <p>
	 * @param cms
	 * @throws WorkflowException
	 */
	public WorkflowController(CmsObject cms) throws WorkflowException {

		if (cms==null) {
			throw new WorkflowException("Invalid CmsObject (null)");
		}
		
		impl = new WorkflowControllerImpl(cms);
		
		if (impl==null) {
			throw new WorkflowException("Could not instantiate WorkflowControllerImpl");
		}		
	}
	
	/**
	 * Hidden default constructor.
	 */
	private WorkflowController() {}
	
	/***********************************/
	/**** Manager retrieval methods ****/
	/***********************************/
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.I_WorkflowController#getRelationManager()
	 */
	public I_RelationManager getRelationManager() {
		
		return impl.getRelationManager();
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.I_WorkflowController#getTaskManager()
	 */
	public I_TaskManager getTaskManager() throws NoEngineAttachedException {
		
		if (WorkflowControllerImpl.isEngineNotAttached()) {
			throw new NoEngineAttachedException("no engine attached!");
		}
		
		return impl.getTaskManager();
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.I_WorkflowController#getProjectManager()
	 */
	public I_ProjectManager getProjectManager() {
				
		//the project manager singleton is returned:
		return impl.getProjectManager();
	}
	
	
	/************************/
	/**** Action methods ****/
	/************************/
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.I_WorkflowController#actionPublish(org.opencms.db.CmsPublishList)
	 */
	public void actionApprove(CmsPublishList publishList, CmsProject targetProject)
						throws WorkflowException, WorkflowPublishNotPermittedException {
		
		if (WorkflowControllerImpl.isEngineNotAttached()) {
			return;
		}

		if (publishList == null) {
			throw new WorkflowException("CmsPublishList is null");
		}
		if (targetProject == null) {
			throw new WorkflowException("Target project is null");
		}
		
		if (isWorkflowSwitchedOn()) {
			impl.actionApprove(publishList, targetProject);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.I_WorkflowController#actionPublish(org.opencms.file.CmsResource)
	 */
	public void actionApprove(CmsResource resource, CmsProject targetProject) throws WorkflowException, WorkflowPublishNotPermittedException {
		
		if (WorkflowControllerImpl.isEngineNotAttached()) {
			return;
		}

		if (resource == null) {
			throw new WorkflowException("CmsResource is null");
		}
		
		if (isWorkflowSwitchedOn()) {
			impl.actionApprove(resource, targetProject);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.I_WorkflowController#actionPublishWithTask(org.opencms.file.CmsResource, com.bearingpoint.opencms.workflow2.task.I_Task)
	 */
	public void actionApproveWithTask(CmsResource resource, CmsProject targetProject, I_Task task) throws WorkflowException, WorkflowPublishNotPermittedException {
		
		if (WorkflowControllerImpl.isEngineNotAttached()) {
			return;
		}
		
		if (resource == null) {
			throw new WorkflowException("CmsResource is null");
		}		
		else if (task == null) {
			throw new WorkflowException("CmsResource is null");
		}
		
		if (isWorkflowSwitchedOn()) {		
			impl.actionApproveWithTask(resource, targetProject, task);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.I_WorkflowController#actionPublishOnline(org.opencms.file.CmsResource)
	 */
	public void actionPublish(CmsResource resource) throws WorkflowException, WorkflowPublishOnlineNotPermittedException {
		
		if (WorkflowControllerImpl.isEngineNotAttached()) {
			return;
		}
		
		if (resource == null) {
			throw new WorkflowException("CmsResource is null");
		}
		
		impl.actionPublish(resource);
	}
	
	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.I_WorkflowController#actionReject(org.opencms.file.CmsResource)
	 */
	public void actionReject(CmsResource resource, CmsProject targetProject) throws WorkflowException, WorkflowRejectNotPermittedException {
		
		if (WorkflowControllerImpl.isEngineNotAttached()) {
			return;
		}
		
		if (resource == null) {
			throw new WorkflowException("CmsResource is null");
		}
		
		if (isWorkflowSwitchedOn()) {	
			impl.actionReject(resource, targetProject);
		}
	}
		
	/**
	 * Checks if the whole workflow functionality is switched on.
	 * <p>
	 * @return true if the workflow module is enabled.
	 */
	public static boolean isWorkflowSwitchedOn() {
		return WorkflowConfiguration.isWorkflowEnabled();
	}
	
	/**
	 * Switches the whole workflow functionality on.
	 * This is stored inside the workflow configuration.
	 */
	public static void switchWorkflowOn() {
		WorkflowConfiguration.setWorkflowEnabled(true);
	}
		
	/**
	 * Switches the whole workflow functionality off.
	 * This is stored inside the workflow configuration.
	 */
	public static void switchWorkflowOff() {
		WorkflowConfiguration.setWorkflowEnabled(false);
	}

}
