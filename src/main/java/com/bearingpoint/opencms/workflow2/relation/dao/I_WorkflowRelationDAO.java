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

package com.bearingpoint.opencms.workflow2.relation.dao;

import com.bearingpoint.opencms.workflow2.relation.RelationAlreadyDefinedException;
import com.bearingpoint.opencms.workflow2.relation.domain.WorkflowRelation;


public interface I_WorkflowRelationDAO {
	
    /**
     * Retrieves a workflow relation by resource path
     * 
     * @param resource
     * @return workflow relation
     */
    public WorkflowRelation getWorkflowRelationByResourcePath(String resource);
    
    /**
     * Retrieves a workflow relation by UUID
     * 
     * @param resource
     * @return workflow relation
     */
    public WorkflowRelation getWorkflowRelationByResourceUUID(String resource);
    
    /**
     * Retrieves a workflow relation by workflow ID
     * 
     * @param workflowId
     * @return workflow relation
     */
    public WorkflowRelation getWorkflowRelation(String workflowId);
    
    /**
     * Stores a workflow relation to the mapping database
     * 
     * @param relation
     */
    public void storeWorkflowRelation(WorkflowRelation relation) throws RelationAlreadyDefinedException ;
    
    /**
     * Removes a workflow relation identified by the resource path
     * 
     * @param resource
     */
    public void removeWorkflowRelationByResourcePath(String resource);
    
    /**
     * removes a workflow relation identified by the resource UUID
     * 
     * @param uuid
     */
    public void removeWorkflowRelationByResourceUUID(String uuid);
    

    

}
