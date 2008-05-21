/* ***************************************************************************
* $RCSfile$ $Revision: 2129 $ $Date: 2007-07-04 18:44:03 +0200 (Mi, 04 Jul 2007) $
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.opencms.file.CmsObject;
import org.opencms.file.CmsUser;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.main.CmsLog;
import org.opencms.workplace.CmsDialog;
import org.opencms.workplace.CmsWorkplaceSettings;

import com.bearingpoint.opencms.workflow2.WorkflowController;
import com.bearingpoint.opencms.workflow2.WorkflowException;
import com.bearingpoint.opencms.workflow2.engine.NoEngineAttachedException;
import com.bearingpoint.opencms.workflow2.task.I_TaskManager;
import com.bearingpoint.opencms.workflow2.task.TaskException;


/**
 * TaskView
 * <p>
 * Dialog displaying task for the current user.
 * <p>
 * @author David.Trattnig
 */
public class WorkflowTaskView extends CmsDialog {
    
	/** Dialog initialization */
    
    
	/**
	 * ACTION_ACCEPT
	 */
	public static final int ACTION_ACCEPT = 101;
	/**
	 * ACTION_POOL
	 */
	public static final int ACTION_POOL = 102;
    /**
     * ACTION_DELETETASK
     */
    public static final int ACTION_DELETETASK = 103;
	/**
	 * ACTION_DETAIL
	 */
	public static final int ACTION_DETAIL=110;
	/**
	 * ACTION_DETAIL_ADDCOMMENT
	 */
	public static final int ACTION_DETAIL_ADDCOMMENT = 111;
	
	/**
	 * PARAM_TITLE
	 */
	public static String PARAM_TITLE = "Workflow View";
	/**
	 * PARAM_GROUP
	 */
	public static final String PARAM_GROUP = "group";
	/**
	 * PARAM_USER
	 */
	public static final String PARAM_USER = "user";
    
    /**
     * PARAM_DELETETASK
     */
    public static final String PARAM_DELETETASK = "deletetask";

    /** The dialog type. */
    public static final String DIALOG_TYPE = "taskview";
    /**
     * DIALOG_ACCEPT
     */
    public static final String DIALOG_ACCEPT = "accept";
    /**
     * DIALOG_POOL
     */
    public static final String DIALOG_POOL = "pool";
    /**
     * DIALOG_DETAIL_ADDCOMMENT
     */
    public static final String DIALOG_DETAIL_ADDCOMMENT = "addcomment";
    /**
     * DIALOG_DETAIL
     */
    public static final String DIALOG_DETAIL = "detail";
    
    /**
     * DIALOG_DELETETASK
     */
    public static final String DIALOG_DELETETASK = "deletetask";
    /**
     * PARAM_RESOURCE
     */
    public static final String PARAM_RESOURCE = "resource";
    /**
     * PARAM_TASKID
     */
    public static final String PARAM_TASKID = "taskid";
    
    
    /** The log object. */
    private static final Log LOG = CmsLog.getLog(WorkflowTaskView.class);
        
    private I_TaskManager taskManager=null;
    private CmsUser currentUser = null;
    private CmsObject cms;
    private TaskViewResultBean result;
    private String m_paramTask = null;
    private String m_paramComment = null;

    


    /**
     * JSP action element constructor.<p>
     * 
     * @param jsp an JSP action element
     */
    public WorkflowTaskView(CmsJspActionElement jsp) {
    	
    	super(jsp);
    	fillParamValues(jsp.getRequest());
    	cms = getCms();
    	WorkflowController wc=null;
    	currentUser = cms.getRequestContext().currentUser();
    	
    	try {
    		wc = new WorkflowController(getCms());    	
    		taskManager = wc.getTaskManager();
    	}
    	catch (WorkflowException e) {
    		LOG.fatal("Couldn't instantiate WorkflowController!", e);
    	}   
		catch (NoEngineAttachedException ee) {
			LOG.error("Couldn't retrieve taskManager (no engine attached)", ee);
		}
    	    	
    	try {
			result = new TaskViewResultBean(cms, wc);
		} catch (Exception e) {
			LOG.error("Error while retrieving task view results!", e);
		}
    }
    
    /**
     * constructor with JSP variables.<p>
     * 
     * @param context the JSP page context
     * @param req the JSP request
     * @param res the JSP response
     */
	public WorkflowTaskView(PageContext context, HttpServletRequest req, HttpServletResponse res) {	
		this(new CmsJspActionElement(context, req, res));		
	}
    	
	public TaskViewResultBean getResult() {
		return result;
	}
	
	/**
	 * actionAccept
	 * 
	 * accepts the given task
	 * 
	 * @return true if the process succeeded, else false
	 */
	public boolean actionAccept() {
	
		Long taskId = getTaskId();
		if (taskId==null) { return false; }		
		
		try {			
			taskManager.assignTask(taskId.toString(), currentUser);
			result.refresh();
		} catch (Exception e) {
			LOG.error("Error while assigning task "+taskId+" to user "+currentUser.getName());
		}
		
		return true;
	}
	
	/**
	 * Retrieves the task id passed via parameters.
	 * Returns null if an invalid id has been passed.
	 * <p>
	 * @return taskId
	 */
	private Long getTaskId() {
		
		String sTaskId=getParamTask();
		Long nTaskId=null;
		
		try {
			nTaskId=new Long(sTaskId);			
		}
		catch (NullPointerException e) {
			LOG.fatal("invalid taskID: "+sTaskId+" // ", e);			
		}
		
		return nTaskId;
	}
	
    /**
     * Finishes the workflow and its corresponding task
     * defined by the given resource id 
     * 
     * @return true if action succeeds
     */
    public boolean actionDeleteTask() {
        
    	Long taskId = getTaskId();
		if (taskId==null) { return false; }
        
        try {
			taskManager.closeTask(taskId.toString());
			result.refresh();
		} catch (Exception e) {
			LOG.error("error while closing task "+taskId, e);
			return false;
		}
		return true;
    }
    
	/**
	 * actionPool
	 * <p>
	 * pools the given task
	 * <p>
	 * @return true if the process succeeded, else false
	 */
	public boolean actionPool() {

		Long taskId = getTaskId();
		if (taskId==null) { return false; }
						
		try {
			taskManager.poolTask(taskId.toString());
			result.refresh();
		} catch (Exception e) {
			LOG.error("error while pooling task "+taskId.toString(), e);
			return false;
		}
				
		return true;
	}
	
	/**
	 * actionAddComment
	 * 
	 * Action-Handler which adds a comment
	 * to the current task<p>
	 * 
	 * @return true if the process succeeded, else false
	 */
	public boolean actionAddComment() {
		String sComment = getParamComment();
		
		if (sComment!=null && sComment!="") {
			String sTaskId=getParamTask();
            
			try {
				taskManager.addCommentToTask(sTaskId, currentUser, sComment);
			} catch (TaskException e) {
				LOG.error("Error while adding comment to task!", e);
				return false;
			}
			            

            LOG.info("comment successfully added!");            
			setParamComment(""); //reset comment
			return true;
		}
		
		return false;
	}
		
    /**
     * Returns the value of the next-group parameter, 
     * or null if this parameter was not provided.<p>
     * 
     * @return the value of the next-group parameter
     */
    public String getParamTask() {

        return m_paramTask;
    }

    /**
     * Sets the value of the next-group parameter.<p>
     * 
     * @param taskid
     */
    public void setParamTask(String taskid) {
    	m_paramTask = taskid;
    }
    
    /**
     * Returns the value of the next-group parameter, 
     * or null if this parameter was not provided.<p>
     * 
     * @return the value of the next-group parameter
     */
    public String getParamComment() {

        return m_paramComment;
    }

    /**
     * setParamComment
     * 
     * Sets the value of the next-group parameter.<p>
     * 
     * @param sComment
     */
    public void setParamComment(String sComment) {
    	m_paramComment = sComment;
        
        if (m_paramComment==null) {
            String[] comments = getJsp().getRequest().getParameterValues("commentarea");
            m_paramComment = comments[0];
        }
    }
	    
    /**
     * @see org.opencms.workplace.CmsWorkplace#initWorkplaceRequestValues(org.opencms.workplace.CmsWorkplaceSettings, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void initWorkplaceRequestValues(CmsWorkplaceSettings settings, HttpServletRequest request) {

        // fill the parameter values in the get/set methods
        fillParamValues(request);
                            
        // set the dialog type
        setParamDialogtype(DIALOG_TYPE);
        // set the action for the JSP switch 
        if (DIALOG_TYPE.equals(getParamAction())) {
            setAction(ACTION_DEFAULT);
        } else if (DIALOG_CANCEL.equals(getParamAction())) {
            setAction(ACTION_CANCEL);
        } else if (DIALOG_OK.equals(getParamAction())) {
            setAction(ACTION_OK);   
        } else if (DIALOG_ACCEPT.equals(getParamAction())) {
        	setAction(ACTION_ACCEPT);
        } else if (DIALOG_POOL.equals(getParamAction())) {
        	setAction(ACTION_POOL);   
        } else if (DIALOG_DELETETASK.equals(getParamAction())) {
            setAction(ACTION_DELETETASK);       
        } else if (DIALOG_DETAIL_ADDCOMMENT.equals(getParamAction())) {
        	setAction(ACTION_DETAIL_ADDCOMMENT);     	
        } else if (DIALOG_DETAIL.equals(getParamAction())) {
        	setAction(ACTION_DETAIL);
        } else {
            setAction(ACTION_DEFAULT);
        }
   
    }
        
}