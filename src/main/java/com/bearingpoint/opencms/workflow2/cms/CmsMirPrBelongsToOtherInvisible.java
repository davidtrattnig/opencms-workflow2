/*
 * File   : $Source: /usr/local/cvs/opencms/src/org/opencms/workplace/explorer/menu/CmsMirPrOtherInvisible.java,v $
 * Date   : $Date: 2007-07-04 16:56:53 $
 * Version: $Revision: 1.2 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (C) 2005 Alkacon Software GmbH (http://www.alkacon.com)
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

package com.bearingpoint.opencms.workflow2.cms;

import org.opencms.file.CmsObject;
import org.opencms.workplace.explorer.CmsResourceUtil;
import org.opencms.workplace.explorer.menu.A_CmsMenuItemRule;
import org.opencms.workplace.explorer.menu.CmsMenuItemVisibilityMode;

/**
 * Defines a menu item rule that sets the visibility to invisible if the resource doesn't belong to the current project.<p>
 * 
 * @author David Trattnig  
 * 
 * @since 0.5.0
 */
public class CmsMirPrBelongsToOtherInvisible extends A_CmsMenuItemRule {

	private static final String DEFAULT_PR_ID = "00000000-0000-0000-0000-000000000000";
	
    /**
     * @see org.opencms.workplace.explorer.menu.I_CmsMenuItemRule#getVisibility(org.opencms.file.CmsObject, CmsResourceUtil[])
     */
    public CmsMenuItemVisibilityMode getVisibility(CmsObject cms, CmsResourceUtil[] resourceUtil) {

        return CmsMenuItemVisibilityMode.VISIBILITY_INVISIBLE;
    }

    /**
     * @see org.opencms.workplace.explorer.menu.I_CmsMenuItemRule#matches(org.opencms.file.CmsObject, CmsResourceUtil[])
     */
    public boolean matches(CmsObject cms, CmsResourceUtil[] resourceUtil) {

    	String currentProject = cms.getRequestContext().currentProject().getUuid().toString();
    	
    	return 
    		(!resourceUtil[0].getProjectId().toString().equals(currentProject))        	
    		&& (!DEFAULT_PR_ID.equals(resourceUtil[0].getProjectId().toString()));
    	
    }

}
