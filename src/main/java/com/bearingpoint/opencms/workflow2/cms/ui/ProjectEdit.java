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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.opencms.file.CmsProject;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;
import org.opencms.main.CmsRuntimeException;
import org.opencms.util.CmsUUID;
import org.opencms.workplace.CmsWidgetDialog;
import org.opencms.workplace.CmsWidgetDialogParameter;

import com.bearingpoint.opencms.commons.springmanager.tools.I_CmsDialogSessionWrapper;
import com.bearingpoint.opencms.workflow2.WorkflowController;
import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.cms.widgets.ProjectSelectWidget;
import com.bearingpoint.opencms.workflow2.cms.widgets.StageSelectWidget;
import com.bearingpoint.opencms.workflow2.stage.I_ProjectManager;

/**
 * ProjectEdit
 * <p>
 * @author David.Trattnig
 *
 */
public class ProjectEdit extends CmsWidgetDialog implements I_CmsDialogSessionWrapper {
	
    public static final String PARAM_PROJECT_ID = ProjectList.PARAM_SELECTED_PROJECT;
    public static final String PARAM_PROJECT_STAGE = ProjectList.PARAM_PROJECT_STAGE;
    
    /** Defines which pages are valid for this dialog. */
    public static final String[] PAGES = {"page1"};
    
    private ProjectBean project;
    private String paramProjectid;
    private String paramProjectstage;
    private I_ProjectManager pmngr;
    
    private static final Log LOG = CmsLog.getLog(ProjectEdit.class);
    
    public ProjectEdit(CmsJspActionElement jsp) {
        super(jsp);        
    }

    public ProjectEdit(PageContext context, HttpServletRequest req, HttpServletResponse res) {
        super(context, req, res);
    }

    @Override
    public void actionCommit() throws IOException, ServletException {
    	
    	//create new project:
        if (isNewProject()){
        	        	
        	CmsProject cmsProject=null;        	

			try { 
				cmsProject = getCms().readProject(new CmsUUID(paramProjectid));
			} catch (CmsException e1) {
				
				LOG.error("Error while reading project with ID: " + getProject().getId(), e1);
                throw new CmsRuntimeException(
                		Messages.get().container(
                        Messages.WORKFLOW_RUNTIME_EXCEPTION_0,
                        "Error while reading project with ID: " + getProject().getId(), e1));
			}
        	try {
				getPM().addProject(cmsProject, new Integer(paramProjectstage));
			} catch (NumberFormatException e) {
				
				LOG.error("Passed invalid parameter for project stage: " + paramProjectstage, e);
                throw new CmsRuntimeException(                		
                		Messages.get().container(
                        Messages.WORKFLOW_RUNTIME_EXCEPTION_0,
                        "Passed invalid parameter for project stage: " + paramProjectstage, e));

			} catch (WorkflowException e) {
				
				LOG.error("Error while updating project with ID: " + paramProjectid, e);
                throw new CmsRuntimeException(
                Messages.get().container(
                        Messages.WORKFLOW_RUNTIME_EXCEPTION_0,
                        "Error while updating project with ID: " + paramProjectid, e));
			}
		//update current project:
        }else{
        	try {
				getPM().updateProjectStage(getProject().getId(), new Integer(paramProjectstage));
			} catch (NumberFormatException e) {
				
				LOG.error("Passed invalid parameter for project stage: " + paramProjectid, e);
                throw new CmsRuntimeException(
                Messages.get().container(
                        Messages.WORKFLOW_RUNTIME_EXCEPTION_0,
                        "Passed invalid parameter for project stage: " + paramProjectstage, e));

			} catch (WorkflowException e) {
				
				LOG.error("Error while updating project with ID: " + paramProjectid, e);
                throw new CmsRuntimeException(
                Messages.get().container(
                        Messages.WORKFLOW_RUNTIME_EXCEPTION_0,
                        "Error while updating project with ID: " + paramProjectid, e));
                
			}
        }
    }

    @Override
    protected void defineWidgets() {
        // initialize the project object to use for the dialog
        initProject();

        addWidget(new CmsWidgetDialogParameter(this, PARAM_PROJECT_ID, PAGES[0], new ProjectSelectWidget()));
        addWidget(new CmsWidgetDialogParameter(this, PARAM_PROJECT_STAGE, PAGES[0], new StageSelectWidget()));
    }

    @Override
    protected String[] getPageArray() {
        return PAGES;
    }

    /**
     * Creates the dialog HTML for all defined widgets of the named dialog (page).<p>  
     * 
     * @param dialog the dialog (page) to get the HTML for
     * @return the dialog HTML for all defined widgets of the named dialog (page)
     */
    @Override
    protected String createDialogHtml(String dialog) {

        StringBuilder result = new StringBuilder(1024);

        // create table
        result.append(createWidgetTableStart());

        // show error header once if there were validation errors
        result.append(createWidgetErrorHeader());

        if (dialog.equals(PAGES[0])) {
            result.append(dialogBlockStart("Project"));
            result.append(createWidgetTableStart());
            result.append(createDialogRowsHtml(0, 1));
            result.append(createWidgetTableEnd());
            result.append(dialogBlockEnd());
        }

        // close table
        result.append(createWidgetTableEnd());

        return result.toString();
    }
    private void initProject(){
    	
    	//TODO init the selected project id -
    	//normally this should happen automatically:
    	try {
    		String[] pParam = (String[]) getParameterMap().get(PARAM_PROJECT_ID+".0");
	    	if (pParam[0] != null && pParam[0].length()>0) {
	    		setParamProjectid(pParam[0]);
	    	}
    	}    	
    	catch (NullPointerException e) {}
   	        	

        try {
        	
        	project = new ProjectBean(getPM().getProjectById(paramProjectid));            	
        	if (LOG.isDebugEnabled()) {
        		LOG.debug("project: " + project.getId());
        	}
        } catch (WorkflowException e) {
//            throw new CmsRuntimeException(
//                    Messages.get().container(
//                            Messages.WORKFLOW_RUNTIME_EXCEPTION_0,
//                            "Can't find project with ID: " + paramProjectid), e);
        	project = null;
        	if (LOG.isDebugEnabled()) {
        		LOG.debug("project: new project");
        	}
        }

    }


    /**
     * Checks if the Project overview has to be displayed.<p>
     * 
     * @return <code>true</code> if the project overview has to be displayed
     */
    private boolean isNewProject() {
        return project == null;        
    }
    
    public String getParamProjectid() {
        return paramProjectid;
    }

    public void setParamProjectid(String paramProjectid) {
        this.paramProjectid = paramProjectid;
    }

	/**
	 * @return the paramProjectstage
	 */
	public String getParamProjectstage() {
		return paramProjectstage;
	}

	/**
	 * @param paramProjectstage the paramProjectstage to set
	 */
	public void setParamProjectstage(String paramProjectstage) {
		this.paramProjectstage = paramProjectstage;
	}

    public ProjectBean getProject() {
        return project;
    }

    public void setProject(ProjectBean project) {
        this.project = project;
    }
    
    private I_ProjectManager getPM() {
    	
    	if (this.pmngr == null) {
	        try {
				this.pmngr = (new WorkflowController(getCms())).getProjectManager();
			} catch (WorkflowException e) {
	            throw new CmsRuntimeException(
	                    Messages.get().container(Messages.WORKFLOW_RUNTIME_EXCEPTION_0,
	                            "Error while instantiating project manager"), e);
	
			}
    	}
    	
    	return pmngr;
    }
}
