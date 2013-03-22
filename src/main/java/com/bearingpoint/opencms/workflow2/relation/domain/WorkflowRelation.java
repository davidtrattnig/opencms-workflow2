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
