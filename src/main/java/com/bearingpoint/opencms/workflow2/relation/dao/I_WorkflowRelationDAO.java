/* ***************************************************************************
* $RCSfile$ $Revision: 1266 $ $Date: 2006-04-25 11:12:50 +0200 (Di, 25 Apr 2006) $
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
