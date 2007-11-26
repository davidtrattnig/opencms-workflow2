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

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.opencms.file.CmsResource;
import org.opencms.main.I_CmsEventListener;
import org.opencms.main.OpenCms;
import org.opencms.test.OpenCmsTestCase;
import org.opencms.test.OpenCmsTestProperties;
import org.opencms.util.CmsUUID;

import com.bearingpoint.opencms.commons.dao.EntityNotFoundException;
import com.bearingpoint.opencms.commons.springmanager.SpringManager;
import com.bearingpoint.opencms.workflow2.relation.I_RelationManager;
import com.bearingpoint.opencms.workflow2.relation.RelationAlreadyDefinedException;
import com.bearingpoint.opencms.workflow2.relation.RelationIntegrityListener;
import com.bearingpoint.opencms.workflow2.relation.ResourceIdentifier;
import com.bearingpoint.opencms.workflow2.relation.dao.I_WorkflowRelationDAO;
import com.bearingpoint.opencms.workflow2.relation.domain.WorkflowRelation;

public class TestWorkflowRelation extends OpenCmsTestCase {

		
    /**
     * Default JUnit constructor.
     * <p>
     * 
     * @param arg0
     *            JUnit parameters
     */
    public TestWorkflowRelation(String arg0) {
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
        suite.setName(TestWorkflowRelation.class.getName());

        suite.addTest(new TestWorkflowRelation("testInvalidResourceIdentifier"));
        suite.addTest(new TestWorkflowRelation("testWorkflowRelationDAO"));
        suite.addTest(new TestWorkflowRelation("testRelationManager"));
        
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
	
    public void testInvalidResourceIdentifier() {
    	
    	final CmsUUID uuid = new CmsUUID();
    	final CmsResource invalidUUIDResource = new CmsResource(null, null,
    			"unittest", 1, false, 0, null, null, 0,
    			null, 0, null, 0, 0, 0, 0, 0, 0);
    	final CmsResource invalidPathResource = new CmsResource(null, uuid,
    			null, 1, false, 0, null, null, 0,
    			null, 0, null, 0, 0, 0, 0, 0, 0);
    	
    	@SuppressWarnings("unused")
		ResourceIdentifier invalidResID = null;
    	
		
		try {
			invalidResID = new ResourceIdentifier(invalidUUIDResource);
			fail();
		}
		catch(WorkflowException e) {
			if (!e.getMessage().equals("CmsResource UUID is null")) {
				fail();
			}
		}
		
		try {
			invalidResID = new ResourceIdentifier(invalidPathResource);
			fail();
		}
		catch(WorkflowException e) {
			if (!e.getMessage().equals("CmsResource Path is null or invalid")) {
				fail();
			}
		}
    }
    
	public void testWorkflowRelationDAO() throws Exception {
		
		CmsUUID uuid = new CmsUUID();
		CmsUUID uuid2 = new CmsUUID();		
		final CmsResource resourceWithBoth = new CmsResource(null, uuid,
				"unittest", 1, false, 0, null, null, 0,
				null, 0, null, 0, 0, 0, 0, 0, 0);
		final CmsResource resourceWithSameBoth = new CmsResource(null, uuid,
				"unittest", 1, false, 0, null, null, 0,
				null, 0, null, 0, 0, 0, 0, 0, 0);
		final CmsResource resourceWithSamePath = new CmsResource(null, uuid2,
				"unittest", 1, false, 0, null, null, 0,
				null, 0, null, 0, 0, 0, 0, 0, 0);
		final CmsResource resourceWithSameUUID = new CmsResource(null, uuid,
				"unittest2", 1, false, 0, null, null, 0,
				null, 0, null, 0, 0, 0, 0, 0, 0);
		
		final String WFID1 = "1", WFID2 = "2", WFID3 = "3";
		                
        SpringManager.reloadBeanFactory(getCmsObject());		
        I_WorkflowRelationDAO workflowRelationDAO = (I_WorkflowRelationDAO) SpringManager.getBean("workflow.workflowRelationDAO");
		assertNotNull(workflowRelationDAO);
		
		ResourceIdentifier resIDWithBoth = new ResourceIdentifier(resourceWithBoth);
		ResourceIdentifier resIDWithSameBoth = new ResourceIdentifier(resourceWithSameBoth);
		ResourceIdentifier resIDWithSamePath = new ResourceIdentifier(resourceWithSamePath);
		ResourceIdentifier resIDWithSameUUID = new ResourceIdentifier(resourceWithSameUUID);
					
		
		//CASE1: compare resource identifiers:
		assertTrue(resIDWithBoth.equals(resIDWithBoth));
		assertTrue(resIDWithBoth.equals(resIDWithSameBoth));
		assertTrue(resIDWithBoth.equals(resIDWithSamePath));
		assertTrue(resIDWithBoth.equals(resIDWithSameUUID));
		assertFalse(resIDWithSamePath.equals(resIDWithSameUUID));
		assertFalse(resIDWithBoth.equals(null));
		
		
		//CASE2: create a relation with path and UUID
		WorkflowRelation wr = new WorkflowRelation();
		wr.setResourceIdentifier(resIDWithBoth);
		wr.setWorkflowId(WFID1);
		workflowRelationDAO.storeWorkflowRelation(wr);
		
		//CASE3: create a relation with the same path as 
		//		 the previous one but different UUID
		try {
			WorkflowRelation wr2 = new WorkflowRelation();
			wr2.setResourceIdentifier(resIDWithSamePath);
			wr2.setWorkflowId(WFID2);
			workflowRelationDAO.storeWorkflowRelation(wr2);
			fail();
		}
		catch(RelationAlreadyDefinedException e) {			
			//this exception has to be thrown...
		}

		//CASE4: create a relation with the same UUID as
		// 		 the previous one but different path
		try {		
			WorkflowRelation wr3 = new WorkflowRelation();
			wr3.setResourceIdentifier(resIDWithSameUUID);
			wr3.setWorkflowId(WFID3);
			workflowRelationDAO.storeWorkflowRelation(wr3);
			fail();
		}
		catch(RelationAlreadyDefinedException e) {
			//this exception has to be thrown...
		}
		
		//CASE5: read relation by workflow-id / check if identifier equals
		wr = workflowRelationDAO.getWorkflowRelation(WFID1);
		assertEquals(wr.getResourceIdentifier(), resIDWithBoth);
		assertEquals(wr.getResourceIdentifier(), resIDWithSamePath);
		assertEquals(wr.getResourceIdentifier(), resIDWithSameUUID);

		//CASE6: read relation by resource-path
		wr = workflowRelationDAO.getWorkflowRelationByResourcePath(resIDWithSamePath.getResourcePath());
		assertEquals(wr.getResourceIdentifier(), resIDWithBoth);

		//CASE7: read relation by resource UUID
		wr = workflowRelationDAO.getWorkflowRelationByResourceUUID(resIDWithSameUUID.getResourceUUID());
		assertEquals(wr.getResourceIdentifier(), resIDWithBoth);
		
		//CASE7.5: read relation by a different resource UUID, which doesn't exist.
		//         the path is the same as the previous ones
		try {
			wr = workflowRelationDAO.getWorkflowRelationByResourceUUID(resIDWithSamePath.getResourceUUID());
			fail();
		}
		catch (EntityNotFoundException e) {
			//correct exception thrown
		}
		
		//CASE8: remove relation
		workflowRelationDAO.removeWorkflowRelationByResourcePath(resIDWithBoth.getResourcePath());
		
		//CASE9: remove a relation which should not exist anymore
		try {
			workflowRelationDAO.removeWorkflowRelationByResourcePath(resIDWithBoth.getResourcePath());
			fail();
		}
		catch (EntityNotFoundException e) {
			//correct exception thrown
		}
		
		//CASE10: check if the relations have been removed correctly by retrieving them via workflow id:
		try {
			wr = workflowRelationDAO.getWorkflowRelation(WFID1);
			fail();
		}
		catch (EntityNotFoundException e) {
			//this exception has to be thrown..
		}

	}

	public void testRelationManager() throws Exception {
		
		final String WFID = "1";
		
		//CASE1: get a valid relation manager object
        SpringManager.reloadBeanFactory(getCmsObject());		
        I_RelationManager relationManager = (I_RelationManager) SpringManager.getBean("workflow.relationManager");
		assertNotNull(relationManager);
		
		//CASE2: read a valid resource from CMS
		CmsResource res = getCmsObject().readResource("resource1.txt");
		assertNotNull(res);
		
		//CASE3: create a valid resource identifier
		ResourceIdentifier ri = new ResourceIdentifier(res);
		assertNotNull(ri);
		
		//CASE4: store a relation
		relationManager.addRelation(ri, WFID);
	
		//CASE5: check if the relation is available
		assertTrue(relationManager.hasRelation(ri));
		
		//CASE6: read the relation
		assertEquals(WFID, relationManager.resolve(ri));
		
		//CASE7: add listener & move resource; relation should be automatically 
		//       updated by the related listener
		I_CmsEventListener listener = new RelationIntegrityListener(relationManager);
		OpenCms.addCmsEventListener(listener, 
				new int[] {(I_CmsEventListener.EVENT_RESOURCE_MOVED)});
		getCmsObject().lockResource(getCmsObject().getRequestContext().removeSiteRoot(res.getRootPath()));
		getCmsObject().moveResource(getCmsObject().getRequestContext().removeSiteRoot(res.getRootPath()), 
				getCmsObject().getRequestContext().removeSiteRoot(res.getRootPath())+"MOVED");
		
		//CASE8: search for this resource with the old resource identifier
		//       --> should be still available cuz the UUID is the same
		relationManager.resolve(ri);
		
		//CASE9: delete relation
		relationManager.removeRelation(ri);
		
	}
}
