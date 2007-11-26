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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;
import org.opencms.main.CmsRuntimeException;
import org.opencms.workplace.CmsDialog;
import org.opencms.workplace.list.A_CmsListDialog;
import org.opencms.workplace.list.CmsListColumnAlignEnum;
import org.opencms.workplace.list.CmsListColumnDefinition;
import org.opencms.workplace.list.CmsListDefaultAction;
import org.opencms.workplace.list.CmsListDirectAction;
import org.opencms.workplace.list.CmsListItem;
import org.opencms.workplace.list.CmsListItemDetails;
import org.opencms.workplace.list.CmsListItemDetailsFormatter;
import org.opencms.workplace.list.CmsListMetadata;
import org.opencms.workplace.list.CmsListMultiAction;
import org.opencms.workplace.list.CmsListOrderEnum;
import org.opencms.workplace.list.CmsListSearchAction;

import com.bearingpoint.opencms.workflow2.WorkflowController;
import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.stage.I_ProjectManager;
import com.bearingpoint.opencms.workflow2.stage.ProjectWrapper;

public class ProjectList extends A_CmsListDialog {

    private static final Log LOG = CmsLog.getLog(ProjectList.class);

    /** list id constant. */
    public static final String LIST_ID = "WorkflowProjectList";

    //Default-Action
    public static final String LIST_DEFACTION_EDIT = "dae";
    
    //Columns
    public static final String LIST_COLUMN_DELETE = "cd";
    public static final String LIST_COLUMN_EDIT = "ce";
    public static final String LIST_COLUMN_NAME = "columnName";

    //actions
    public static final String LIST_ACTION_DELETE= "ad";
    public static final String LIST_ACTION_EDIT = "ae";

    //show-Details on Table
    public static final String LIST_PROJECT_DETAILS = "projectsDetails";

    //Multi-Actions (Delete selected Rules)
    public static final String LIST_MACTION_DELETE = "md";

    /** Path to the list buttons. */
    public static final String PATH_BUTTONS = "tools/accounts/buttons/";
    
    public static final String PARAM_SELECTED_PROJECT = "paramProjectid";
    public static final String PARAM_PROJECT_STAGE = "paramProjectstage";
    private static I_ProjectManager pm;
    

    /**
     * Public constructor.<p>
     * 
     * @param jsp an initialized JSP action element
     */
    public ProjectList(CmsJspActionElement jsp) {
        super(jsp,
                LIST_ID,
                Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_NAME_0),
                LIST_COLUMN_NAME,
                CmsListOrderEnum.ORDER_ASCENDING,
                null);        
        
        try {
			pm = (new WorkflowController(jsp.getCmsObject())).getProjectManager();
		} catch (WorkflowException e) {
            throw new CmsRuntimeException(
                    Messages.get().container(Messages.WORKFLOW_RUNTIME_EXCEPTION_0,
                            "Error while instantiating project manager"), e);

		}
    }

    /**
     * Public constructor with JSP variables.<p>
     * 
     * @param context the JSP page context
     * @param req the JSP request
     * @param res the JSP response
     */
    public ProjectList(PageContext context, HttpServletRequest req, HttpServletResponse res) {

        this(new CmsJspActionElement(context, req, res));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeListMultiActions() throws IOException, ServletException, CmsRuntimeException {
    	
        if (getParamListAction().equals(LIST_MACTION_DELETE)) {
        	
            // execute the delete multiaction
            //List<String> removedItems = new ArrayList<String>();
            String projectID = null;
            
            try {
                List<CmsListItem> items = getSelectedItems();
                for (CmsListItem listItem : items){
                	projectID = listItem.getId();                	                                        
                    pm.deleteProject(projectID);
                    
                    //removedItems.add(projectID);
                }
            } catch (WorkflowException e) {
                throw new CmsRuntimeException(
                        Messages.get().container(Messages.WORKFLOW_RUNTIME_EXCEPTION_0,
                                "Error while deleting project <"+projectID+">"), e);
            } finally {
                //getList().removeAllItems(removedItems, getLocale());
            }
        } else {
            throwListUnsupportedActionException();
        }
        listSave();
    }

    @Override
    public void executeListSingleActions() throws IOException, ServletException,
            CmsRuntimeException {
    	    	
        String projectID = getSelectedItem().getId();
        Map<String, Object> params = new HashMap<String, Object>(); //value can be String[] or String

        // init selected project id
        params.put(PARAM_SELECTED_PROJECT, getSelectedItem().getId());
        
        // set action parameter to initial dialog call
        params.put(CmsDialog.PARAM_ACTION, CmsDialog.DIALOG_INITIAL);

        if (getParamListAction().equals(LIST_ACTION_EDIT)) {
            getToolManager().jspForwardTool(this, "/workflow2/projects/edit", params);
        } else if (getParamListAction().equals(LIST_DEFACTION_EDIT)) {
            getToolManager().jspForwardTool(this, "/workflow2/projects/edit", params);
        } else if (getParamListAction().equals(LIST_ACTION_DELETE)) {
            
            try {
            	pm.deleteProject(projectID);                
            } catch (WorkflowException e) {
                throw new CmsRuntimeException(
                        Messages.get().container(Messages.WORKFLOW_RUNTIME_EXCEPTION_0,
                                "Error while deleting project <"+projectID+">"), e);
            }
        }

        listSave();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void fillDetails(String detailId) {
        List<CmsListItem> groups = getList().getAllContent();
        String projectID = null;
        for (CmsListItem item : groups){
            try {
            	projectID = item.getId();
                
                if (detailId.equals(LIST_PROJECT_DETAILS)) {
                    String html = getProjectDetailsHTML(projectID);
                    item.set(LIST_PROJECT_DETAILS, html);
                }
            } catch (Exception e) {
                LOG.error("Unknown error: " + e.getMessage(), e);
            }
        }
    }

    @Override
    protected List getListItems() throws CmsException {
        String html;
        
        List<CmsListItem> result = new ArrayList<CmsListItem>();
        List<ProjectWrapper> projects;
        
        try{
        	projects = pm.getAllWorkflowProjects();
        } catch (WorkflowException e) {
            throw new CmsRuntimeException(
                            Messages.get().container(Messages.WORKFLOW_RUNTIME_EXCEPTION_0,
                                    "Error while retrieving projects"), e);
        }
        
        
        for (ProjectWrapper project : projects) {

            CmsListItem item = getList().newItem(project.getId().toString());
            item.set(LIST_COLUMN_NAME, project.getName());
            
            try{
                html = getProjectDetailsHTML(project.getId().toString());
                item.set(LIST_PROJECT_DETAILS, html);
            } catch (WorkflowException e) {
                throw new CmsRuntimeException(
                        Messages.get().container(Messages.WORKFLOW_RUNTIME_EXCEPTION_0,
                                "Error while retrieving project details"), e);
            }
            
            result.add(item);
        }
        
        return result;
    }

    @Override
    protected void setColumns(CmsListMetadata metadata) {

        // create column for editing
        CmsListColumnDefinition editCol = new CmsListColumnDefinition(LIST_COLUMN_EDIT);
        editCol.setName(Messages.get().container(Messages.GUI_WORKFLOW_COL_EDIT_0));
        editCol.setHelpText(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_EDIT_HELPTEXT_0));
        editCol.setWidth("20");
        editCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        editCol.setSorteable(false);
        
        // add edit action
        CmsListDirectAction editAction = new CmsListDirectAction(LIST_ACTION_EDIT);
        editAction.setName(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_EDIT_NAME_0)); //Message will not be shown
        editAction.setHelpText(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_NAME_0)); //Message will not be shown
        editAction.setIconPath(PATH_BUTTONS + "group.png");        
        editCol.addDirectAction(editAction);      
        
        metadata.addColumn(editCol);

        
        // create column for deletion
        CmsListColumnDefinition deleteCol = new CmsListColumnDefinition(LIST_COLUMN_DELETE);
        deleteCol.setName(Messages.get().container(Messages.GUI_WORKFLOW_COL_DELETE_0));
        deleteCol.setHelpText(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_HELPTEXT_0));
        deleteCol.setWidth("20");
        deleteCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        deleteCol.setSorteable(false);
        
        // add delete action
        CmsListDirectAction deleteAction = new CmsListDirectAction(LIST_ACTION_DELETE);
        deleteAction.setName(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_NAME_0));
        deleteAction.setHelpText(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_HELPTEXT_0));
        deleteAction.setConfirmationMessage(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_CONFIRMATION_0));
        deleteAction.setIconPath(ICON_DELETE);
        deleteCol.addDirectAction(deleteAction);
        
        metadata.addColumn(deleteCol);
                
        
        // create column for name
        CmsListColumnDefinition nameCol = new CmsListColumnDefinition(LIST_COLUMN_NAME);
        nameCol.setName(Messages.get().container(Messages.GUI_WORKFLOW_COL_PROJECTS_0));
        nameCol.setWidth("100%");
        
        // create default edit action
        CmsListDefaultAction defEditAction = new CmsListDefaultAction(LIST_DEFACTION_EDIT);
        defEditAction.setName(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_EDIT_NAME_0));
        defEditAction.setHelpText(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_EDIT_HELPTEXT_0));
        nameCol.addDefaultAction(defEditAction);
        
        // add it to the list definition
        metadata.addColumn(nameCol);
    }

    @Override
    protected void setIndependentActions(CmsListMetadata metadata) {
        
        // add Group-Articles details
        CmsListItemDetails projects = new CmsListItemDetails(LIST_PROJECT_DETAILS);
        projects.setAtColumn(LIST_COLUMN_NAME);
        projects.setVisible(false);
        projects.setShowActionName(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_SHOWDETAILS_NAME_0));
        projects.setShowActionHelpText(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_SHOWDETAILS_HELPTEXT_0));
        projects.setHideActionName(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_HIDEDETAILS_NAME_0));
        projects.setHideActionHelpText(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_HIDEDETAILS_HELPTEXT_0));
        projects.setName(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_NAME_0));
        projects.setFormatter(new CmsListItemDetailsFormatter(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_NAME_0)));
        metadata.addItemDetails(projects);
        
        // makes the list searchable
        CmsListSearchAction searchAction = new CmsListSearchAction(metadata.getColumnDefinition(LIST_COLUMN_NAME));
        //searchAction.addColumn(metadata.getColumnDefinition(LIST_COLUMN_DESCRIPTION));
        metadata.setSearchAction(searchAction);
    }

    @Override
    protected void setMultiActions(CmsListMetadata metadata) {
        
        // add delete multi action
        CmsListMultiAction deleteMultiAction = new CmsListMultiAction(LIST_MACTION_DELETE);
        deleteMultiAction.setName(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_NAME_0));
        deleteMultiAction.setHelpText(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_HELPTEXT_0));
        deleteMultiAction.setConfirmationMessage(Messages.get().container(Messages.GUI_WORKFLOW_PROJECT_LIST_COLUMN_DELETE_CONFIRMATION_0));
        deleteMultiAction.setIconPath(ICON_MULTI_DELETE);
        metadata.addMultiAction(deleteMultiAction);
    }

    private String getProjectDetailsHTML(String projectID) throws WorkflowException {
    	
        StringBuilder html = new StringBuilder(512);
        ProjectWrapper daProject = pm.getProjectById(projectID);
                
        html.append(daProject.getName());
        html.append("(STAGE: "+daProject.getStage()+")");
        html.append("<br/>");

        return html.toString();
    }
}
