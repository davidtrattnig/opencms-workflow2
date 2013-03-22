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

import org.opencms.i18n.A_CmsMessageBundle;
import org.opencms.i18n.I_CmsMessageBundle;

/**
 * Convenience class to access the localized messages of this OpenCms package.<p> 
 * 
 * @author Michael Moossen 
 * 
 * @version $Revision$ 
 * 
 * @since 6.0.0 
 */
public final class Messages extends A_CmsMessageBundle {

	public static final String WORKFLOW_RUNTIME_EXCEPTION = "An error within the workflow controller occured";
	
	public static final String GUI_WORKFLOW_PROJECT_LIST_NAME = "Workflow Projects";
	
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_LIST_NAME = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_LIST_NAME";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_LIST_HELPTEXT = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_LIST_HELPTEXT";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_EDIT_NAME = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_EDIT_NAME";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_EDIT_HELPTEXT = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_EDIT_HELPTEXT";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_NAME = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_NAME";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_HELPTEXT = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_HELPTEXT";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_CONFIRMATION = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_CONFIRMATION";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_SHOWDETAILS_NAME = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_SHOWDETAILS_NAME";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_SHOWDETAILS_HELPTEXT = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_SHOWDETAILS_HELPTEXT";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_HIDEDETAILS_NAME = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_HIDEDETAILS_NAME";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_HIDEDETAILS_HELPTEXT = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_HIDEDETAILS_HELPTEXT";

	public static final String GUI_EXPLORER_CONTEXT_MOVETOPROJECT_0 = "GUI_EXPLORER_CONTEXT_MOVETOPROJECT_0";
    
    /** Name of the used resource bundle. */
    private static final String BUNDLE_NAME = "com.bearingpoint.opencms.workflow2";

    /** Static instance member. */
    private static final I_CmsMessageBundle INSTANCE = new Messages();

    /**
     * Hides the public constructor for this utility class.<p>
     */
    private Messages() {

        // hide the constructor
    }

    /**
     * Returns an instance of this localized message accessor.<p>
     * 
     * @return an instance of this localized message accessor
     */
    public static I_CmsMessageBundle get() {

        return INSTANCE;
    }

    /**
     * Returns the bundle name for this OpenCms package.<p>
     * 
     * @return the bundle name for this OpenCms package
     */
    public String getBundleName() {

        return BUNDLE_NAME;
    }

}