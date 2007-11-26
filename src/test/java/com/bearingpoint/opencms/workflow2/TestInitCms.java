/* ***************************************************************************
 * $RCSfile: TestXMLContentTypesUtil.java,v $ $Revision: 1.3 $ $Date: 2006/10/10 18:01:02 $
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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.logging.Log;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsProject;
import org.opencms.file.CmsResource;
import org.opencms.file.types.I_CmsResourceType;
import org.opencms.main.CmsLog;
import org.opencms.main.OpenCms;
import org.opencms.site.CmsSite;
import org.opencms.test.OpenCmsTestCase;
import org.opencms.test.OpenCmsTestProperties;

/**
 * Tests the OpenCms system roles.
 * <p>
 */
public class TestInitCms extends OpenCmsTestCase {

    private static final Log LOG = CmsLog.getLog(TestInitCms.class);

    /**
     * Default JUnit constructor.
     * <p>
     * 
     * @param arg0
     *            JUnit parameters
     */
    public TestInitCms(String arg0) {
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
        suite.setName(TestInitCms.class.getName());

        suite.addTest(new TestInitCms("testModuleImport"));
        suite.addTest(new TestInitCms("testGetOpenCmsInfos"));
        

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


    @SuppressWarnings("unchecked")
    public void testGetOpenCmsInfos() throws Exception {

        echo("Testing testGetOpenCmsInfos");
        CmsObject cms = getCmsObject();
        cms.getRequestContext().setSiteRoot("/");

        // get resourceTypes
        List<I_CmsResourceType> resourceTypes = OpenCms.getResourceManager().getResourceTypes();
        for (I_CmsResourceType resType : resourceTypes) {
            LOG.debug("" + resType.getTypeName() + "=" + resType.getTypeId());
        }

        // get existing Sites
        Collection<CmsSite> sites = OpenCms.getSiteManager().getSites().values();
        for (CmsSite site : sites) {
            LOG.debug("site.siteRoot: " + site.getSiteRoot());
            LOG.debug("site.url: " + site.getUrl());
        }

        // get some Folders
        LOG.debug("####### FOLDERS ##########");
        printSubfolderRecursive(cms, "/", 0);
        LOG.debug("##########################");
        

        // get all Projects
        List<CmsProject> projects = cms.getAllManageableProjects();
        for (CmsProject project : projects) {
            LOG.debug("project: " + project.getName());
        }

    }
    
    @SuppressWarnings("unchecked")
    private void printSubfolderRecursive(CmsObject cms, String rootPath, int level) throws Exception{
        // get some Folders
        List<CmsResource> subfolders = cms.getSubFolders(rootPath);
        String indent = " ";
        for (int i = 0; i < level; i++) indent += " ";
        
        for (CmsResource subfolder : subfolders) {
            LOG.debug(indent + subfolder.getName());
            printSubfolderRecursive(cms, subfolder.getRootPath(), level + 1);
        }
    }

    
    private void printoutMap(Map<String, Object> contentMap, int depth){
        for (Map.Entry<String, Object> entry : contentMap.entrySet()){
            printValue(entry.getKey(), entry.getValue(), depth);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void printValue(String key, Object obj, int depth){
        if (obj instanceof Map){
            System.out.println(getDepth(depth) + key);
            Map<String, Object> supContentMap = (Map<String, Object>)obj;
            printoutMap(supContentMap, depth+1);
        } else if (obj instanceof List){
            List<Object> values =(List<Object>)obj;
            for (Object o : values){
                printValue(key, o, depth);
            }
        }else {
            System.out.println(getDepth(depth) + key + ": " + obj);
        }
    }
    
    private String getDepth(int depth){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < depth; i++){
            result.append("\t");
        }
        return result.toString();
    }
    
    @SuppressWarnings("unchecked")
    public void testModuleImport() throws Exception {
        echo("Testing testModuleImport");

        String bpModuleName = "com.bearingpoint.opencms.workflow2";

        // basic check if the module was imported correctly (during
        // configuration)
        if (!OpenCms.getModuleManager().hasModule(bpModuleName)) {
            
            Set<String> moduleNames = OpenCms.getModuleManager().getModuleNames();
            for (String moduleName : moduleNames){
                LOG.info("imported Module: " + moduleName);
            }
            fail("Module '" + bpModuleName + "' was not imported!");
        }
    }
}
