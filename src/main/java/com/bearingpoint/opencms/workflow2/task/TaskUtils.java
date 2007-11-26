/**
 * 
 */
package com.bearingpoint.opencms.workflow2.task;

import org.opencms.file.CmsProject;
import org.opencms.file.CmsResource;

import com.bearingpoint.opencms.workflow2.WorkflowConfiguration;

/**
 * TaskUtils
 * <p>
 * Some static utils for task creation.
 * <p>
 * @author David.Trattnig
 *
 */
public class TaskUtils {

	public static String createDefaultTaskTitle(CmsProject project, CmsResource resource) {
		
		String title = WorkflowConfiguration.getDefaultTaskTitle();
		title = title.replace("%1", resource.getName());	
		title = title.replace("%2", project.getName());		
		return title;
	}
}
