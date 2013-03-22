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

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsProject;
import org.opencms.file.CmsResource;
import org.opencms.file.CmsResourceFilter;
import org.opencms.file.CmsUser;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.lock.CmsLock;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;
import org.opencms.util.CmsUUID;
import org.opencms.workplace.CmsWorkplaceSettings;
import org.opencms.workplace.commons.CmsTouch;

import com.bearingpoint.opencms.workflow2.I_WorkflowController;
import com.bearingpoint.opencms.workflow2.WorkflowController;
import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.WorkflowPublishNotPermittedException;
import com.bearingpoint.opencms.workflow2.cms.CmsUtil;
import com.bearingpoint.opencms.workflow2.stage.ProjectWrapper;

/**
 * Adaptation of the original OpenCms 7 CmsTouch class -
 * Copies/Publishes an existing resource of the current project
 * to any target project provided by the user interface.
 * <p> 
 * 
 * Used by following files:
 * <ul>
 * <li>/ui/movetoproject.jsp
 * </ul>
 * <p>
 *
 * @author David Trattnig  
 *  
 */
public class WorkflowMoveToProject extends CmsTouch {

    /** Value for the action: copy the resource to current project. */
    public static final int ACTION_COPYTOPROJECT = 100;

    /** The dialog type. */
    public static final String DIALOG_TYPE = "copytoproject";

    /** The log object for this class. */
    private static final Log LOG = CmsLog.getLog(WorkflowMoveToProject.class);

    /** Contains the target project to copy the chosen resource(s) **/
    public static final String PARAM_TARGETPROJECT = "project";
    private String m_paramTargetProject;
    private I_WorkflowController wc;
    
    /**
     * Public constructor with JSP action element.<p>
     * 
     * @param jsp an initialized JSP action element
     */
    public WorkflowMoveToProject(CmsJspActionElement jsp) {
    	
        super(jsp);
        try {
        	wc = new WorkflowController(getCms());
        }
        catch (WorkflowException e) {
        	LOG.fatal("fatal error initializing the workflow controller!", e);
        	throw new RuntimeException (e);
        }
    }

    /**
     * Public constructor with JSP variables.<p>
     * 
     * @param context the JSP page context
     * @param req the JSP request
     * @param res the JSP response
     */
    public WorkflowMoveToProject(PageContext context, HttpServletRequest req, HttpServletResponse res) {

        this(new CmsJspActionElement(context, req, res));
    }

    public void actionPublish() throws JspException {
    
    	CmsProject targetProject = null;
		try {
			targetProject = getCms().readProject(new CmsUUID(getParamProject()));
	        // start workflow
	        if (targetProject != null) {
	        	CmsResource resource = getCms().readResource(getParamResource());            	
	        	try {
					wc.actionApprove(resource, targetProject);
					actionMoveToProject();
				} catch (WorkflowPublishNotPermittedException e) {
		            includeErrorpage(this, e);					
				}
	        }   
		} catch (NumberFormatException e) {
            includeErrorpage(this, e);
		} catch (CmsException e) {
            includeErrorpage(this, e);
		} catch (WorkflowException e) {
            includeErrorpage(this, e);
		}
    	
    	
    	        
        
        LOG.info("WF2| started workflow for resource <"+getParamResource()+">");
    }
    
    
    /**
     * Performs the copy to project action, will be called by the JSP page.<p>
     * 
     * @throws JspException if problems including sub-elements occur
     */
    public void actionMoveToProject() throws JspException {

        // save initialized instance of this class in request attribute for included sub-elements
        getJsp().getRequest().setAttribute(SESSION_WORKPLACE_CLASS, this);
        try {
        	//TODO check this for coming move-folder-integration..
        	boolean recursive = false;
        	
        	CmsResource resource = getCms().readResource(getParamResource());
        	CmsObject adminCms = CmsUtil.getAdminCmsObject();        	
        	
        	CmsProject targetProject = adminCms.readProject(new CmsUUID(getParamProject()));
        	LOG.info("WF2| target project uuid: "+targetProject.getUuid().toString());
        	        	
        	//backup current project for admin cms (to switch back after the process)      	
        	CmsProject currentProject = adminCms.getRequestContext().currentProject();
        	LOG.info("WF2| current project: "+currentProject.getName());
        	
        	//move to current user project:
        	CmsProject currentUserProject = getCms().getRequestContext().currentProject();
        	adminCms.getRequestContext().setCurrentProject(currentUserProject);        	
        	
        	//touch resource before moving to ensure
        	//correct project assignment:    
        	adminCms.lockResource(resource.getRootPath());
        	adminCms.setDateLastModified(resource.getRootPath(), (new Date()).getTime(), recursive);
        	adminCms.unlockResource(resource.getRootPath());
        	
        	// switch to target project
        	adminCms.getRequestContext().setCurrentProject(targetProject);
        	LOG.info("WF2| set current project to target project: "+targetProject.getName());
        	                    	
            // copy the resource to the current project                	
        	adminCms.copyResourceToProject(resource.getRootPath());
        	adminCms.lockResource(resource.getRootPath());
        	adminCms.setDateLastModified(resource.getRootPath(), (new Date()).getTime(), recursive);                	
            adminCms.unlockResource(resource.getRootPath());
            LOG.info("WF2| copied resource '"+getParamResource()+"' to current project: "+targetProject.getName());        
            
            // switch back to previous project            
        	adminCms.getRequestContext().setCurrentProject(currentProject);
            LOG.info("WF2| reset current project to: "+currentProject.getName());
            
            // close the dialog
            actionCloseDialog();
        } catch (Throwable e) {
            // error copying resource to project, include error page
            includeErrorpage(this, e);
        }
    }
        
    /**
     * Returns the value of the target project parameter, 
     * or null if this parameter was not provided.<p>
     * 
     * The project parameter contains the project to move
     * the resource.<p>
     * 
     * @return the value of the target project parameter
     */
    public String getParamProject() {

        if ((m_paramTargetProject != null) && !"null".equals(m_paramTargetProject)) {
            return m_paramTargetProject;
        } else {
            return null;
        }
    }
    
    /**
     * Set the value of the target project parameter.<p>
     * 
     * The project parameter contains the project to move
     * the resource.<p>
     * 
     * @param the value of the target project parameter
     */
    public void setParamProject(String targetProject) {

    	m_paramTargetProject = targetProject;
    }
    
    /**
     * Returns the HTML containing project information and confirmation question for the JSP.<p>
     * 
     * @return the HTML containing project information and confirmation question for the JSP
     */
    public String buildProjectInformation() {

        StringBuffer result = new StringBuffer(32);
        String resName = getParamResource();
        CmsResource res = null;
        try {
            res = getCms().readResource(getParamResource(), CmsResourceFilter.ALL);
            if (res.isFolder() && !resName.endsWith("/")) {
                resName += "/";
            }
        } catch (CmsException e) {
            // error reading resource, should not happen
            if (LOG.isInfoEnabled()) {
                LOG.info(e.getLocalizedMessage());
            }
        }
        
        try {        	
        	CmsUser user = getCms().getRequestContext().currentUser();
        	List<ProjectWrapper> validTargetProjects = wc.getProjectManager().getValidTargetProjects(user, res);
        	validTargetProjects.addAll(wc.getProjectManager().getValidRejectProjects(res));
        	
        	//TODO add localization!
            //String[] localizedObject = new String[] {getCms().getRequestContext().currentProject().getName()};

            result.append(dialogBlockStart(Messages.get().container(Messages.GUI_WORKFLOW_PUBLISH_TO_PROJECT_BLOCKHEADER_0).key()));
            
            result.append("<select id=\""+PARAM_TARGETPROJECT+"\" name=\""+PARAM_TARGETPROJECT+"\">");
            for (ProjectWrapper project : validTargetProjects) {
            	
            	result.append("<option value=\"");
            	result.append(project.getId());
            	result.append("\">");
            	result.append(project.getName());
            	result.append("</option>");            	
            }
            result.append("</select>");
            
            result.append(dialogBlockEnd());
            result.append(dialogSpacer());
        } catch (Exception e) {
            // error reading project resources, should not happen
            if (LOG.isInfoEnabled()) {
                LOG.info(e.getLocalizedMessage());
            }
        }

        // show confirmation question
        //String[] localizedObject = new String[] {resName, getCms().getRequestContext().currentProject().getName()};
        result.append(Messages.get().container(Messages.GUI_WORKFLOW_PUBLISH_TO_PROJECT_CONFIRMATION_2, res.getName(),"").key());
        return result.toString();
    }

    /**
     * @see org.opencms.workplace.CmsWorkplace#initWorkplaceRequestValues(org.opencms.workplace.CmsWorkplaceSettings, javax.servlet.http.HttpServletRequest)
     */
    protected void initWorkplaceRequestValues(CmsWorkplaceSettings settings, HttpServletRequest request) {

        // fill the parameter values in the get/set methods
        fillParamValues(request);
        // set the dialog type
        setParamDialogtype(DIALOG_TYPE);
        // set the action for the JSP switch 
        if (DIALOG_TYPE.equals(getParamAction())) {
            setAction(ACTION_COPYTOPROJECT);
        } else if (DIALOG_CANCEL.equals(getParamAction())) {
            setAction(ACTION_CANCEL);
        } else {
            setAction(ACTION_DEFAULT);
            // build title for copy to project dialog     
            setParamTitle(Messages.get().container(Messages.GUI_WORKFLOW_PUBLISH_TO_PROJECT_TITLE_0).key());
        }
    }
    
    /**
     * Unlocks the resource within the target project:
     * 	1. the lock has to be stolen from the previous user (which is the same but from another project ;-)
     *  2. the resource has to be unlocked
     * <p>
     * 
     * @param resName
     * @throws CmsException
     */
    protected void unlockResource(String resName) throws CmsException {
    	
    	//unlock resource if locked and it is locked by the current user:
    	CmsLock lock = getCms().getLock(resName);
    	if (!lock.isUnlocked()) {
            if (!lock.isNullLock() && lock.isOwnedBy(getCms().getRequestContext().currentUser())) {
            	getCms().changeLock(resName);
                getCms().unlockResource(resName);
            }
    	}
    }

}