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
