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
package com.bearingpoint.opencms.workflow2.relation.domain;

import com.bearingpoint.opencms.workflow2.relation.ResourceIdentifier;


public class WorkflowRelation {

    private Long _id;
    private ResourceIdentifier _ri;
    private String _workflowId;
    
    /**
	 * @return the _ri
	 */
	public ResourceIdentifier getResourceIdentifier() {
		return _ri;
	}
	/**
	 * @param _ri the _ri to set
	 */
	public void setResourceIdentifier(ResourceIdentifier resource) {
		this._ri = resource;
	}
	/**
     * @return Returns the id.
     */
    public Long getId() {
        return _id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(Long id) {
        this._id = id;
    }
      
    /**
     * @return Returns the workflowId.
     */
    public String getWorkflowId() {
        return _workflowId;
    }
    /**
     * @param workflowId The workflowId to set.
     */
    public void setWorkflowId(String workflowId) {
        this._workflowId = workflowId;
    }
    
    /**
     * Retrieves the OpenCms UUID of the resource
     * 
     * @return the UUID
     */
    public String getUuid() {
    	return this._ri.getResourceUUID();
    }
    
    /**
     * Retrieves the resource path of the resource identifier.
     * 
     * @return full resource path
     */
    public String getResource() {
    	return this._ri.getResourcePath();
    }
    
    /**
     * Sets the OpenCms UUID of the resource
     * 
     * @return the UUID
     */
    public void setUuid(String uuid) {
    	
    	if (_ri == null) {
    		_ri = ResourceIdentifier.createRIWithResourceUUID(uuid);
    	}
    	else {
    		ResourceIdentifier.updateResourceUUID(_ri, uuid);
    	}
    }
    
    /**
     * Sets the resource path of the resource identifier.
     * 
     * @return full resource path
     */
    public void setResource(String path) {
    	
    	if (_ri == null) {
    		_ri = ResourceIdentifier.createRIWithResourcePath(path);
    	}
    	else {
    		ResourceIdentifier.updateResourcePath(_ri, path);
    	}
    }
    
}
