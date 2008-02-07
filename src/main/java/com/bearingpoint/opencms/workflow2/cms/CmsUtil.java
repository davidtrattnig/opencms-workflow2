/**
 * 
 */
package com.bearingpoint.opencms.workflow2.cms;

import org.opencms.file.CmsObject;

/**
 * @author David.Trattnig
 *
 */
public class CmsUtil {

	private static CmsObject adminCms;
	
	public static void setAdminCmsObject(CmsObject adminCms) {
		CmsUtil.adminCms = adminCms;
	}
	
	public static CmsObject getAdminCmsObject() {
		return adminCms;
	}
}
