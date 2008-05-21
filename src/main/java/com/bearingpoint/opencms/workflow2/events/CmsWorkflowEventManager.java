/**
 * 
 */
package com.bearingpoint.opencms.workflow2.events;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.opencms.db.CmsPublishList;
import org.opencms.file.CmsResource;
import org.opencms.main.CmsEvent;
import org.opencms.main.CmsLog;
import org.opencms.main.I_CmsEventListener;

import com.bearingpoint.opencms.workflow2.WorkflowController;
import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.WorkflowPublishOnlineNotPermittedException;
import com.bearingpoint.opencms.workflow2.cms.CmsUtil;

/**
 * CmsWorkflowEventManager
 * <p>
 * @author David.Trattnig
 *
 */
public class CmsWorkflowEventManager implements I_CmsEventListener {

	private static WorkflowController wc;
	
	private static final Log LOG = CmsLog.getLog(CmsWorkflowEventManager.class);
	
	public CmsWorkflowEventManager() {
		wc=null;
	}
	
    public void cmsEvent(CmsEvent event) {
        
        Map eventData = event.getData();

        switch (event.getType()) {
        
            /* terminate workflows for all resources to be published: 
             * */
            case org.opencms.main.I_CmsEventListener.EVENT_BEFORE_PUBLISH_PROJECT:
            	
            	if (wc==null) {
	            	try {
						WorkflowController wc = new WorkflowController(CmsUtil.getAdminCmsObject());
					} catch (WorkflowException e) {
						LOG.fatal ("WF2| error while workflow controller instantiation!", e);
					}
            	}
            	
            	CmsPublishList publishResources = (CmsPublishList ) eventData.get(I_CmsEventListener.KEY_PUBLISHLIST);
            	List<CmsResource> resources = publishResources.getAllResources();
            	
            	for (CmsResource resource : resources) {
            		try {
						wc.actionPublish(resource);
					} catch (WorkflowException e) {
						LOG.error("WF2| error while performing workflow publish action!", e);
					} catch (WorkflowPublishOnlineNotPermittedException e) {
						LOG.error("WF2| unexpected constellation: you are not allowed to publish online!", e);
					}
					
					if (LOG.isDebugEnabled()) {
						LOG.debug("WF2| resource "+resource.getName()+" successfully published!");					}
            	}
            	
            	break;
        }
	}
}
