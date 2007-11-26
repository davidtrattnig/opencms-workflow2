/* ***************************************************************************
 * $RCSfile$ $Revision: 1312 $ $Date: 2006-05-10 14:59:48 +0200 (Mi, 10 Mai 2006) $
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

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bearingpoint.opencms.commons.dao.EntityNotFoundException;
import com.bearingpoint.opencms.commons.dao.TooManyRowsFoundException;
import com.bearingpoint.opencms.workflow2.relation.RelationAlreadyDefinedException;
import com.bearingpoint.opencms.workflow2.relation.domain.WorkflowRelation;

/**
 * WorkflowRelationHibernateDAO
 * <p>
 * 
 * @author David.Trattnig
 *
 */
public class WorkflowRelationHibernateDAO extends HibernateDaoSupport implements I_WorkflowRelationDAO {

    @SuppressWarnings("unchecked")
    public WorkflowRelation getWorkflowRelationByResourcePath(String resource) throws TooManyRowsFoundException{
        
        String query = "FROM WorkflowRelation WHERE resource=?";        
        List<WorkflowRelation> relation = getHibernateTemplate().find(query, resource);
        
        if (relation.isEmpty()) {
        	throw new EntityNotFoundException("Could not find relation with resourcePath: "+resource,"resource",resource);
        } else if (relation.size()>1) {
            throw new TooManyRowsFoundException(
                    WorkflowRelation.class.getSimpleName(),
                    new String[] { "resource" }, 
                    new String[] {resource});
        }
        else {
            return relation.get(0);
        }                      

    }
    
    /* (non-Javadoc)
     * @see com.bearingpoint.opencms.workflow2.relation.dao.I_WorkflowRelationDAO#getWorkflowRelationByResourceUUID(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public WorkflowRelation getWorkflowRelationByResourceUUID(String uuid) {
        	
        String query = "FROM WorkflowRelation WHERE uuid=?";        
        List<WorkflowRelation> relation = getHibernateTemplate().find(query, uuid);
        
        if (relation.isEmpty()) {
        	throw new EntityNotFoundException("Could not find relation with UUID: "+uuid,"uuid",uuid);
        } else if (relation.size()>1) {
            throw new TooManyRowsFoundException(
                    WorkflowRelation.class.getSimpleName(),
                    new String[] { "uuid" }, 
                    new String[] {uuid});
        }
        else {
            return relation.get(0);
        }
    }
    
    /* (non-Javadoc)
     * @see com.bearingpoint.opencms.workflow2.relation.dao.I_WorkflowRelationDAO#getWorkflowRelation(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public WorkflowRelation getWorkflowRelation(String workflowId) throws EntityNotFoundException{
        
        String query = "FROM WorkflowRelation WHERE workflowId=?";        
        List<WorkflowRelation> relation = getHibernateTemplate().find(query, workflowId);
        
        if (relation.isEmpty()) {
        	 throw new EntityNotFoundException("Could not find relation with id:"+workflowId,"workflowId",workflowId);
        } else if (relation.size()>1) {
            throw new TooManyRowsFoundException(
                    WorkflowRelation.class.getSimpleName(),
                    new String[] { "workflowId" }, 
                    new String[] { workflowId });
        }
        else {
            return relation.get(0);
        } 
    }
    
    /* (non-Javadoc)
     * @see com.bearingpoint.opencms.workflow2.relation.dao.I_WorkflowRelationDAO#storeWorkflowRelation(com.bearingpoint.opencms.workflow2.relation.domain.WorkflowRelation)
     */
    @Transactional
    public void storeWorkflowRelation(WorkflowRelation relation) throws RelationAlreadyDefinedException {
        
    	try {
    		getWorkflowRelationByResourcePath(relation.getResource());
    		throw new RelationAlreadyDefinedException("Relation already defined");    				    			
    	}
    	catch (EntityNotFoundException e1) {
    		try {
    			getWorkflowRelationByResourceUUID(relation.getUuid());
    			throw new RelationAlreadyDefinedException("Relation already defined");
    		}
    		catch(EntityNotFoundException e2) {
    			getHibernateTemplate().save(relation);
    		}    		    		
    	}    	       
    }
    

	/* (non-Javadoc)
     * @see com.bearingpoint.opencms.workflow2.relation.dao.I_WorkflowRelationDAO#removeWorkflowRelationByResourcePath(java.lang.String)
     */
    @Transactional
    public void removeWorkflowRelationByResourcePath(String resourcePath) {
    	
    	WorkflowRelation relation = getWorkflowRelationByResourcePath(resourcePath);
        Object pojo = getHibernateTemplate().load(WorkflowRelation.class, relation.getId());
        getHibernateTemplate().delete(pojo);        
    }
    
    /* (non-Javadoc)
     * @see com.bearingpoint.opencms.workflow2.relation.dao.I_WorkflowRelationDAO#removeWorkflowRelationByResourceUUID(java.lang.String)
     */
    @Transactional
    public void removeWorkflowRelationByResourceUUID(String uuid) {
    	
    	WorkflowRelation relation = getWorkflowRelationByResourceUUID(uuid);
        Object pojo = getHibernateTemplate().load(WorkflowRelation.class, relation.getId());
        getHibernateTemplate().delete(pojo); 	
    }
 
}
