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
package com.bearingpoint.opencms.workflow2;

import java.util.List;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.opencms.file.CmsProject;
import org.opencms.file.CmsResource;
import org.opencms.file.CmsUser;
import org.opencms.test.OpenCmsTestCase;
import org.opencms.test.OpenCmsTestProperties;

import com.bearingpoint.opencms.commons.springmanager.SpringManager;
import com.bearingpoint.opencms.workflow2.engine.NoEngineAttachedException;
import com.bearingpoint.opencms.workflow2.stage.I_ProjectManager;
import com.bearingpoint.opencms.workflow2.stage.ProjectWrapper;

public class TestWorkflowController extends OpenCmsTestCase {

		
    /**
     * Default JUnit constructor.
     * <p>
     * 
     * @param arg0
     *            JUnit parameters
     */
    public TestWorkflowController(String arg0) {
        super(arg0);
    }
	
    /**
     * Test suite for this test class.
     * <p>
     * Setup is done without importing vfs data.
     * 
     * @return the test suite
     */
    public static Test suite() {
        OpenCmsTestProperties.initialize(org.opencms.test.AllTests.TEST_PROPERTIES_PATH);

        TestSuite suite = new TestSuite();
        suite.setName(TestWorkflowController.class.getName());

        suite.addTest(new TestWorkflowController("testWorkflowControllerWithoutEngine"));
        
        //OpenCms Initializations:
        TestSetup wrapper = new TestSetup(suite) {

            @SuppressWarnings("unchecked")
            @Override
            protected void setUp() {
                setupOpenCms("xmlcontent", "/");

            }

            @Override
            protected void tearDown() {
                removeOpenCms();
            }
        };

        return wrapper;
    }


	public void testWorkflowControllerWithoutEngine() throws Exception {
				
		//CASE1: get a valid workflow controller object
        SpringManager.reloadBeanFactory(getCmsObject());
        I_WorkflowController wc = new WorkflowController(getCmsObject());
        assertNotNull(wc);
        
        //CASE2: check if the manager retrieval works fine
        I_ProjectManager pm = wc.getProjectManager();
        assertNotNull(pm);
        assertNotNull(wc.getRelationManager());
        
        //CASE3: task management should be not possible
        try {
        	wc.getTaskManager();
        	fail();
        }
        catch (NoEngineAttachedException e) {
        	//this exception has to be thrown..
        	//..as no engine is attached
        }
        
		//CASE3: create some new projects
        List<CmsProject> allProjects;
        allProjects = getCmsObject().getAllManageableProjects();
		CmsProject p1 = getCmsObject().createProject("project1", "", "Administrators", "Administrators");
		CmsProject p2 = getCmsObject().createProject("project2", "", "Administrators", "Administrators");
		getCmsObject().writeProject(p1);
		getCmsObject().writeProject(p2);
		allProjects.add(p1);
		allProjects.add(p2);
		pm.updateProjectWorkflow(allProjects);
			//...now there should be 3 projects: offline, project1 & project2
		
		//CASE4: read a valid resource from CMS
		CmsResource res = getCmsObject().readResource("resource1.txt");
		assertNotNull(res);
		
		//CASE5: publish resource to project1
		CmsUser user = getCmsObject().getRequestContext().currentUser();
		List<ProjectWrapper> validProjects = pm.getValidTargetProjects(user, res);
		CmsProject nextProject = null;
		for (ProjectWrapper p : validProjects) {
			if (p.getId().toString().equals(p1.getUuid().toString())) {
				nextProject = p.getCmsProject();
				break;
			}
		}
		assertNotNull(nextProject);
		wc.actionApprove(res, nextProject);
		
		//CASE6: publish to project2
		for (ProjectWrapper p : validProjects) {
			if (p.getId().toString().equals(p2.getUuid().toString())) {
				nextProject = p.getCmsProject();
				break;
			}
		}
		assertNotNull(nextProject);
		wc.actionApprove(res, nextProject);
		
		//CASE7: reject to offline project
		validProjects = pm.getValidRejectProjects(res);
		nextProject = validProjects.get(0).getCmsProject();
		wc.actionReject(res, nextProject);

	}
}
