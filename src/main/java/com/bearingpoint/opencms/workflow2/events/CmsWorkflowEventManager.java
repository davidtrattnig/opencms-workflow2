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
						wc = new WorkflowController(CmsUtil.getAdminCmsObject());
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
