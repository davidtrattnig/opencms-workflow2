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

	private static CmsObject cms;
	
	public static void setCmsObject(CmsObject cms) {
		CmsUtil.cms = cms;
	}
	
	public static CmsObject getCmsObject() {
		return cms;
	}
}
