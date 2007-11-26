/* ***************************************************************************
 * $RCSfile$ $Revision: 1435 $ $Date: 2006-10-24 10:57:56 +0200 (Di, 24 Okt 2006) $
 * 
 * Copyright (c) 2005 BearingPoint INFONOVA GmbH, Austria.
 *
 * This software is the confidential and proprietary information of
 * BearingPoint INFONOVA GmbH, Austria. You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with INFONOVA.
 *
 * BEARINGPOINT INFONOVA MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. BEARINGPOINT INFONOVA SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
 * MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
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
