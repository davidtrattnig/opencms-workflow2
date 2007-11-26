/* ***************************************************************************
* $RCSfile$ $Revision$ $Date$
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