/*
 * File   : $Source$
 * Date   : $Date: 2006-05-18 17:48:18 +0200 (Do, 18 Mai 2006) $
 * Version: $Revision: 406 $
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

package com.bearingpoint.opencms.workflow2.cms.widgets;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsProject;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;
import org.opencms.util.CmsUUID;
import org.opencms.widgets.CmsSelectWidget;
import org.opencms.widgets.CmsSelectWidgetOption;
import org.opencms.widgets.I_CmsWidget;
import org.opencms.widgets.I_CmsWidgetDialog;
import org.opencms.widgets.I_CmsWidgetParameter;

import com.bearingpoint.opencms.workflow2.WorkflowController;
import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.stage.I_ProjectManager;

/**
 * Provides a widget for a standard HTML form multi select list or a group of check boxes.<p>
 * 
 * Please see the documentation of <code>{@link org.opencms.widgets.CmsSelectWidgetOption}</code> for a description 
 * about the configuration String syntax for the select options.<p>
 *
 * The multi select widget does use the following select options:<ul>
 * <li><code>{@link org.opencms.widgets.CmsSelectWidgetOption#getValue()}</code> for the value of the option
 * <li><code>{@link org.opencms.widgets.CmsSelectWidgetOption#isDefault()}</code> for pre-selecting a specific value 
 * <li><code>{@link org.opencms.widgets.CmsSelectWidgetOption#getOption()}</code> for the display name of the option
 * </ul>
 * <p>
 *
 * @author Michael Moossen 
 * 
 * @version $Revision: 406 $ 
 * 
 * @since 6.0.0 
 */
public class ProjectSelectWidget extends CmsSelectWidget {

    private static final Log LOG = CmsLog.getLog(ProjectSelectWidget.class);
    
    /**
     * Creates a new select widget.<p>
     */
    public ProjectSelectWidget() {
        super();
    }

    /**
     * Creates a select widget with the select options specified in the given configuration List.<p>
     * 
     * The list elements must be of type <code>{@link CmsSelectWidgetOption}</code>.<p>
     * 
     * @param configuration the configuration (possible options) for the select widget
     * 
     * @see CmsSelectWidgetOption
     */
    public ProjectSelectWidget(List configuration) {

        super(configuration);
    }

    /**
     * Creates a select widget with the specified select options.<p>
     * 
     * @param configuration the configuration (possible options) for the select box
     */
    public ProjectSelectWidget(String configuration) {

        super(configuration);
    }


    /**
     * @see org.opencms.widgets.I_CmsWidget#newInstance()
     */
    @Override
    public I_CmsWidget newInstance() {

        return new ProjectSelectWidget(getConfiguration());
    }
    
    

    /**
     * Returns the list of configured select options, parsing the configuration String if required.<p>
     * 
     * The list elements are of type <code>{@link CmsSelectWidgetOption}</code>.
     * The configuration String is parsed only once and then stored internally.<p>
     * 
     * @param cms the current users OpenCms context
     * @param widgetDialog the dialog of this widget
     * @param param the widget parameter of this dialog
     * 
     * @return the list of select options
     * 
     * @see CmsSelectWidgetOption
     */
    @Override
    protected List parseSelectOptions(CmsObject cms, I_CmsWidgetDialog widgetDialog, I_CmsWidgetParameter param) {

        if (getSelectOptions() == null) {
            List<CmsSelectWidgetOption> result = new ArrayList<CmsSelectWidgetOption>();
            
            WorkflowController wc;
            List<CmsProject> projects;
			try {
				wc = new WorkflowController(cms);
				String projectID = param.getStringValue(cms);
				
				I_ProjectManager pm = wc.getProjectManager();
				if (projectID != null && projectID.length()>0) {
					
					projects = new ArrayList<CmsProject>();
					try {
						projects.add(cms.readProject(new CmsUUID(projectID)));
					} catch (CmsException e) {
						LOG.error("Error while retrieving project "+projectID+" from cms");
					}
				}
				else {
					projects = pm.getUnassignedProjects();
				}
					               
                for (CmsProject p : projects){
                    result.add(new CmsSelectWidgetOption(p.getUuid().toString(), false, p.getName(), null));
                }
            } catch (WorkflowException e) {
                LOG.error("ERROR: " + e.getMessage(), e);
                result.add(new CmsSelectWidgetOption("", false, "ERROR: " + e.getMessage(), null));
            }
            
            setSelectOptions(result);
        }
        
        return getSelectOptions();
    }
    
}