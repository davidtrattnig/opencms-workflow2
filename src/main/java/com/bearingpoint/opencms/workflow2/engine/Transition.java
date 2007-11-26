/**
 * 
 */
package com.bearingpoint.opencms.workflow2.engine;

/**
 * Transition
 * 
 * Types of transitions the workflow process definition can contain.
 * 
 * @author David.Trattnig
 */
public enum Transition {
	
	/**
	 * Transition to publish a resource or set of resources
	 * to another project (next project of the projects the 
	 * current user belongs to)
	 */
	APPROVE,
	
	/**
	 * Transition to reject a resource to one of the previous
	 * projects (all previous projects of the project the resource
	 * belongs to).
	 */
	REJECT,
		
	/**
	 * Publishes a resource to the online project. Just available
	 * for workflow super-users or approvers (depending on the configuration). 
	 */
	PUBLISH,
	
	/**
	 * Moves a resource within the current project. 
	 */
	MOVE
}
