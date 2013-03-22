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

package com.bearingpoint.opencms.workflow2;

import org.apache.commons.logging.Log;
import org.opencms.configuration.CmsConfigurationManager;
import org.opencms.file.CmsObject;
import org.opencms.main.CmsLog;
import org.opencms.main.I_CmsEventListener;
import org.opencms.main.OpenCms;
import org.opencms.module.A_CmsModuleAction;
import org.opencms.module.CmsModule;

import com.bearingpoint.opencms.workflow2.cms.CmsUtil;
import com.bearingpoint.opencms.workflow2.events.CmsWorkflowEventManager;

/**
 * InitWorkflowAction
 * <p>
 * Module Initialization Class which is loaded
 * while module import
 * <p>
 * @author David.Trattnig
 *
 */
public class InitWorkflowAction extends A_CmsModuleAction {
	private static final Log LOG = CmsLog.getLog(InitWorkflowAction.class);
    private static CmsObject cms=null;
		
    /**
     * @see org.opencms.module.I_CmsModuleAction#initialize(org.opencms.file.CmsObject, CmsConfigurationManager, CmsModule)
     */
    @Override
	public void initialize(CmsObject adminCms, CmsConfigurationManager configurationManager, CmsModule module) {
    	super.initialize(adminCms,configurationManager,module); 
    	
    	LOG.debug("WF2| start workflow initialization..");    			        
    	CmsUtil.setAdminCmsObject(adminCms);
        initEventListener();
        WorkflowDataSource.initDatasourceData(configurationManager);              
        LOG.debug("WF2| workflow initialization finished!");
    }
    
    /**
     * Assigns the Event Listener for the Workflow Module
     * to perform workflow changes after e.g. publish events.
     */
    public void initEventListener() {
    	
    	I_CmsEventListener eventListener = new CmsWorkflowEventManager();
    	OpenCms.addCmsEventListener(eventListener);  
    }
    

        
}
