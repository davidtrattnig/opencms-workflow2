/**
 * 
 */
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
