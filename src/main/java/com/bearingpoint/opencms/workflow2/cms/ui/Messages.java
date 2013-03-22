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
package com.bearingpoint.opencms.workflow2.cms.ui;

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

	public static final String WORKFLOW_RUNTIME_EXCEPTION_0 = "WORKFLOW_RUNTIME_EXCEPTION_0";
	
	public static final String GUI_WORKFLOW_PROJECT_LIST_NAME_0 = "GUI_WORKFLOW_PROJECT_LIST_NAME_0";
	
	public static final String GUI_WORKFLOW_COL_PROJECTS_0 = "GUI_WORKFLOW_COL_PROJECTS_0";
	public static final String GUI_WORKFLOW_COL_EDIT_0 = "GUI_WORKFLOW_COL_EDIT_0";
	public static final String GUI_WORKFLOW_COL_DELETE_0 = "GUI_WORKFLOW_COL_DELETE_0";
	
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_LIST_NAME_0 = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_LIST_NAME_0";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_LIST_HELPTEXT_0 = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_LIST_HELPTEXT_0";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_EDIT_NAME_0 = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_EDIT_NAME_0";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_EDIT_HELPTEXT_0 = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_EDIT_HELPTEXT_0";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_NAME_0 = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_NAME_0";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_HELPTEXT_0 = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_HELPTEXT_0";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_CONFIRMATION_0 = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_CONFIRMATION_0";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_SHOWDETAILS_NAME_0 = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_SHOWDETAILS_NAME_0";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_SHOWDETAILS_HELPTEXT_0 = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_SHOWDETAILS_HELPTEXT_0";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_HIDEDETAILS_NAME_0 = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_HIDEDETAILS_NAME_0";
	public static final String GUI_WORKFLOW_PROJECT_LIST_COLUMN_HIDEDETAILS_HELPTEXT_0 = "GUI_WORKFLOW_PROJECT_LIST_COLUMN_HIDEDETAILS_HELPTEXT_0";

	public static final String GUI_WORKFLOW_PUBLISH_TO_PROJECT_TITLE_0 = "GUI_WORKFLOW_PUBLISH_TO_PROJECT_TITLE_0";
	public static final String GUI_WORKFLOW_PUBLISH_TO_PROJECT_BLOCKHEADER_0 = "GUI_WORKFLOW_PUBLISH_TO_PROJECT_BLOCKHEADER_0";
	public static final String GUI_WORKFLOW_PUBLISH_TO_PROJECT_CONFIRMATION_2 = "GUI_WORKFLOW_PUBLISH_TO_PROJECT_CONFIRMATION_2";
	
	public static final String GUI_WORKFLOW_PROJECT_0 = "GUI_WORKFLOW_PROJECT_0";
	public static final String GUI_WORKFLOW_STAGE_0 = "GUI_WORKFLOW_STAGE_0";
    
    /** Name of the used resource bundle. */
    private static final String BUNDLE_NAME = "com.bearingpoint.opencms.workflow2.cms.ui.messages";

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