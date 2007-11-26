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
import org.opencms.test.OpenCmsTestCase;
import org.opencms.test.OpenCmsTestProperties;

import com.bearingpoint.opencms.commons.springmanager.SpringManager;
import com.bearingpoint.opencms.workflow2.stage.I_ProjectManager;
import com.bearingpoint.opencms.workflow2.stage.ProjectWrapper;

public class TestProjectManager extends OpenCmsTestCase {

		
    /**
     * Default JUnit constructor.
     * <p>
     * 
     * @param arg0
     *            JUnit parameters
     */
    public TestProjectManager(String arg0) {
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
        suite.setName(TestProjectManager.class.getName());

        suite.addTest(new TestProjectManager("testMyProjectManager"));
        
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


	public void testMyProjectManager() throws Exception {
		
		final String WFID = "1";
		
		//CASE1: get a valid project manager object
        SpringManager.reloadBeanFactory(getCmsObject());		
        I_ProjectManager projectManager = (I_ProjectManager) SpringManager.getBean("workflow.projectManager");
        projectManager.setCmsObject(getCmsObject());
		assertNotNull(projectManager);
		
		//CASE2: verify that there is no existing workflow project
		List<ProjectWrapper> allProjects = projectManager.getAllWorkflowProjects();
		assertEquals(0, allProjects.size());
		
		//CASE3: add the existing project
		List<CmsProject> cmsProjects = getCmsObject().getAllManageableProjects();
		for (int i=0;i<cmsProjects.size();i++) {
			projectManager.addProject(cmsProjects.get(i), i);
		}
		
		//CASE5: create some new projects
		CmsProject p1 = getCmsObject().createProject("project1", "", "Users", "Users");
		CmsProject p2 = getCmsObject().createProject("project2", "", "Users", "Users");
		getCmsObject().writeProject(p1);
		getCmsObject().writeProject(p2);		
		projectManager.addProject(p1, 0);
		projectManager.addProject(p2, 1);
		
		//CASE6: swap the project order, a bit
		projectManager.updateProjectStage(p1.getUuid().toString(), 1);
		
		//CASE7: test if the previous first project is 
		//       now the last (3rd) project
		int p1stage = projectManager.getProjectStage(p1);
		int p2stage = projectManager.getProjectStage(p2);
		assertEquals(p1stage, 0);
		assertEquals(p1stage, 1);
		
		//CASE8: remove all projects
		allProjects.clear();
		projectManager.removeAllProjects();
				
	}
}
