/*
 * File   : $Source$
 * Date   : $Date: 2006-05-31 19:43:43 +0200 (Mi, 31 Mai 2006) $
 * Version: $Revision: 1326 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (c) 2005 Alkacon Software GmbH (http://www.alkacon.com)
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
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.bearingpoint.opencms.workflow2;


import junit.framework.Test;
import junit.framework.TestSuite;

import org.opencms.test.OpenCmsTestProperties;

/**
 * Main test suite for the package <code>{@link org.opencms.configuration}</code>.<p>
 * 
 * @author Alexander Kandzior 
 * @version $Revision: 1326 $
 * 
 * @since 6.0
 */
public final class AllTests {

    /**
     * Hide constructor to prevent generation of class instances.<p>
     */
    private AllTests() {

        // empty
    }

    /**
     * Returns the JUnit test suite for this package.<p>
     * 
     * @return the JUnit test suite for this package
     */
    public static Test suite() {

        TestSuite suite = new TestSuite("Tests for package " + AllTests.class.getPackage().getName());
        OpenCmsTestProperties.initialize(org.opencms.test.AllTests.TEST_PROPERTIES_PATH);
        //$JUnit-BEGIN$
        suite.addTest(TestInitCms.suite());
        suite.addTest(new TestSuite(TestWorkflowRelation.class));
        suite.addTest(new TestSuite(TestProjectManager.class));
        suite.addTest(new TestSuite(TestWorkflowController.class));
        //$JUnit-END$
        return suite;
    }
}
