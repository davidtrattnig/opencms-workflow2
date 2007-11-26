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