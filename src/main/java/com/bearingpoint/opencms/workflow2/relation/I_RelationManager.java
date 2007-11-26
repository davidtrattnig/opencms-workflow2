/* ***************************************************************************
* $RCSfile$ $Revision: 1268 $ $Date: 2006-04-25 17:54:39 +0200 (Di, 25 Apr 2006) $
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

import org.opencms.main.CmsException;

import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.relation.dao.I_WorkflowRelationDAO;

/**
 * I_RelationManager
 * <p>
 * Handles the relation between workflow and cms objects:
 *    * resource <-> workflow instance
 *    * project <-> workflow node
 * <p>   
 * Every workflow is assigned to one resource & vice versa
 * <p>
 * @author David.Trattnig
 */
public interface I_RelationManager {

	/**
     * adds a new workflow relation
     * <p>
	 * @param resource
	 * @param workflowId
	 * @throws CmsException
	 * @throws WorkflowException
	 */
	public void addRelation(ResourceIdentifier resource, String workflowId) throws WorkflowException;
    
    /**
     * Resolve a resource-id to the corresponding workflow-id
     * <p>
     * @param resource
     * @return workflow-id
     * @throws WorkflowException if no valid workflow could be found
     */
	public String resolve(ResourceIdentifier resource) throws WorkflowException;
    
	/**
     * removes an existing workflow-relation
     * <p>
	 * @param resource
	 * @throws WorkflowException
	 * @throws CmsException if the relation couldn't been removed
	 */
	public void removeRelation(ResourceIdentifier resource) throws WorkflowException;
    
    
    /**
     * checks if a given resource has a relation
     * to a workflow
     * <p>
     * @param resource
     */
    public boolean hasRelation(ResourceIdentifier resource);
    
    
    /**
     *  Setter for spring to fulfill the dependency to the WorkflowRelatioDAO
     */
    public void setWorkflowRelationDAO(I_WorkflowRelationDAO workflowRelationDAO);
    
	/**
	 * @return Returns the workflowRelationDAO.
	 */
	public I_WorkflowRelationDAO getWorkflowRelationDAO();
	
	/**
	 * Returns a RelationIdentifier for the given task.
	 * Note: Tasks belong to workflow ids, workflow ids
	 * belong to relations (relations are consisting of
	 * workflow ids and resource identifier). 
	 * 
	 * @return Returns a ResourceIdentfier for the given task
	 */
	public ResourceIdentifier getRelationForTask(long taskId) throws WorkflowException;
}
