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
