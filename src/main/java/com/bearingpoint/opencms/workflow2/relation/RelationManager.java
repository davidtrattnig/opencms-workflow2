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

package com.bearingpoint.opencms.workflow2.relation;

import org.apache.commons.logging.Log;
import org.opencms.main.CmsLog;

import com.bearingpoint.opencms.commons.dao.EntityNotFoundException;
import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.engine.I_WorkflowEngine;
import com.bearingpoint.opencms.workflow2.relation.dao.I_WorkflowRelationDAO;
import com.bearingpoint.opencms.workflow2.relation.domain.WorkflowRelation;

/**
 * RelationManager
 * 
 * Handles the relation resource <-> workflow Every workflow is assigned to one
 * resource & vice versa
 * 
 * @author David.Trattnig
 */
public class RelationManager implements I_RelationManager {

	private static final Log LOG = CmsLog.getLog(RelationManager.class);

	private static I_WorkflowRelationDAO workflowRelationDAO;
	private I_WorkflowEngine engine;
	
	/**
	 * Constructor for instantiation if no engine is attached
	 */
	public RelationManager() {
		engine = null;
	}
	
	/**
	 * Constructor for instantiation if an engine is attached
	 * @param engine
	 */
	public RelationManager(I_WorkflowEngine engine) {
		
		this.engine = engine;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bearingpoint.opencms.workflow2.I_RelationManager#addRelation(org.opencms.file.CmsResource,
	 *      java.lang.String)
	 */
	public void addRelation(ResourceIdentifier resource, String workflowId)
			throws WorkflowException {

		WorkflowRelation workflowRelation = new WorkflowRelation();
		workflowRelation.setResourceIdentifier(resource);
		workflowRelation.setWorkflowId(workflowId);
		try {
			workflowRelationDAO.storeWorkflowRelation(workflowRelation);
		} catch (RelationAlreadyDefinedException e) {
			LOG.error("Relation <"+workflowRelation+"> already defined!");
			throw new WorkflowException ("Could not create relation because it's already defined!", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bearingpoint.opencms.workflow2.I_RelationManager#resolve(org.opencms.file.CmsResource)
	 */
	public String resolve(ResourceIdentifier resource) throws WorkflowException {

		WorkflowRelation workflowRelation;
		
		try {
			if (resource.hasValidResouceUUID()) {
			
				workflowRelation = workflowRelationDAO
					.getWorkflowRelationByResourceUUID(resource
							.getResourceUUID());			
			} else {
				workflowRelation = workflowRelationDAO
					.getWorkflowRelationByResourcePath(resource
							.getResourcePath());
			}
		}
		catch (EntityNotFoundException e) {
			throw new WorkflowException(
					"*$* workflow-relation doesn't exist for resource '"
							+ resource.getResourcePath() + "'");
		}

		if (workflowRelation.getResourceIdentifier().equals(resource)) {

			LOG.info("the retrieved WORKFLOW RELATION (by UUID) seems to be exactly the requested one");
		}
		else {
			LOG.error("the retrieved WORKFLOW RELATION (by UUID) has a different path (may be moved within the VFS)");
		}
		
		return workflowRelation.getWorkflowId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bearingpoint.opencms.workflow2.I_RelationManager#removeRelation(org.opencms.file.CmsResource)
	 */
	public void removeRelation(ResourceIdentifier resource)
			throws WorkflowException {

		if (resource.hasValidResouceUUID()) {
			try {
				workflowRelationDAO.removeWorkflowRelationByResourceUUID(resource.getResourceUUID());
			}
			catch(Exception e) {
				workflowRelationDAO.removeWorkflowRelationByResourcePath(resource.getResourcePath());
			}
					
		} else {
			workflowRelationDAO.removeWorkflowRelationByResourcePath(resource.getResourcePath());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bearingpoint.opencms.workflow2.service.I_RelationManager#hasRelation(org.opencms.file.CmsResource)
	 */
	public boolean hasRelation(ResourceIdentifier resource) {

		try {
			String wfId = resolve(resource);
			if (wfId == null)
				return false; // ignore the Resource
		} catch (WorkflowException e) {
			return false;
		}

		return true;
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.relation.I_RelationManager#getWorkflowRelationDAO()
	 */
	public I_WorkflowRelationDAO getWorkflowRelationDAO() {
		return workflowRelationDAO;
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.relation.I_RelationManager#setWorkflowRelationDAO(com.bearingpoint.opencms.workflow2.relation.dao.I_WorkflowRelationDAO)
	 */
	public void setWorkflowRelationDAO(I_WorkflowRelationDAO workflowRelationDAO) {
		RelationManager.workflowRelationDAO = workflowRelationDAO;
	}

	/* (non-Javadoc)
	 * @see com.bearingpoint.opencms.workflow2.relation.I_RelationManager#getRelationForTask()
	 */
	public ResourceIdentifier getRelationForTask(long taskId) throws WorkflowException {
		
		if (engine!=null) {
			String workflowId = engine.getWorkflowIdOfTask(taskId);
			WorkflowRelation rel = workflowRelationDAO.getWorkflowRelation(workflowId);
			return rel.getResourceIdentifier();
		}
		
		throw new WorkflowException("Invalid call of method - no engine attached!");
	}
}
