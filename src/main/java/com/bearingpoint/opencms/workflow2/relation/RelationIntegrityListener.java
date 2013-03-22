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

import java.util.List;

import org.apache.commons.logging.Log;
import org.opencms.file.CmsResource;
import org.opencms.main.CmsEvent;
import org.opencms.main.CmsLog;
import org.opencms.main.I_CmsEventListener;
import org.springframework.transaction.annotation.Transactional;

import com.bearingpoint.opencms.workflow2.WorkflowException;

/**
 * @author David.Trattnig
 *
 */
public class RelationIntegrityListener implements I_CmsEventListener {
	
	private static I_RelationManager relationManager = null;
		
	private RelationIntegrityListener() {}

	private static final Log LOG = CmsLog.getLog(RelationIntegrityListener.class);
	
	
	public RelationIntegrityListener(I_RelationManager relationManager) throws WorkflowException {
			
		if (relationManager == null) {
			throw new WorkflowException("relation manager is null");
		}
		
		this.relationManager = relationManager;
	}
	
	public void cmsEvent(CmsEvent event) {
		 
		 /* if a resource has been moved
		  * the resource path inside the relation
		  * has to be updated
		  */
		 if (event.getTypeInteger() == EVENT_RESOURCE_MOVED) {
			 
			 List<CmsResource> resList = (List<CmsResource>) event.getData().get("resources");			 
			 CmsResource res = resList.get(2);
			 
			 try {
				updateRelation(new ResourceIdentifier(res));
			} catch (WorkflowException e) {
				LOG.error("error while creating resource identifier for resource '"+res.getRootPath()+"'");
			}
		 }
	}
	
	@Transactional
	public void updateRelation(ResourceIdentifier resource) {
		
		String workflowID = null;
		try {
			workflowID = relationManager.resolve(resource);
			
			if (workflowID != null) {
				
				try {
					relationManager.removeRelation(resource);
					relationManager.addRelation(resource, workflowID);
				} catch (WorkflowException e) {
					LOG.error("Error while removing relation for resource'"+resource.getResourcePath()+"'");
				}			
			}
		} catch (WorkflowException e1) {
			LOG.info("No existing relation for resource '"+resource.getResourcePath()+"' found");
		}		
	}
		
}
