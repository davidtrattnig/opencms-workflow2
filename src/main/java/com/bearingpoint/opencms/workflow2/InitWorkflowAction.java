/* ***************************************************************************
* $RCSfile$ $Revision: 1434 $ $Date: 2006-10-20 21:58:02 +0200 (Fr, 20 Okt 2006) $
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
package com.bearingpoint.opencms.workflow2;

import org.apache.commons.logging.Log;
import org.opencms.configuration.CmsConfigurationManager;
import org.opencms.file.CmsObject;
import org.opencms.main.CmsLog;
import org.opencms.module.A_CmsModuleAction;
import org.opencms.module.CmsModule;

import com.bearingpoint.opencms.workflow2.cms.CmsUtil;

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
    	
    	LOG.debug("*$* start workflow initialization..");    			
        
    	CmsUtil.setCmsObject(adminCms);
        //initEventListener();
        WorkflowDataSource.initDatasourceData(configurationManager);
       
       
        LOG.debug("*$* workflow initialization finished!");
    }

    
    /**
     * Assigns the Event Listener for the Workflow Module
     * to perform workflow changes after e.g. publish events.
     */
//    public void initEventListener() {
//    	
//    	//TODO attach proper event listener
//    	WorkflowEventListener eventListener = new WorkflowEventListener();
//    	OpenCms.addCmsEventListener(eventListener);  
//    }
    

        
}
