package com.jerotoma.common.constants;

public class RoleConstant {
	public static final String ROLE = "role";
	public static final String ROLES = "roles";
	public static final String ROLE_COUNT = "role";
	public static final Object ROLE_IDS = "roleIDs";
	
	public static enum USER_ROLES {
		ROLE_ADMIN(1,"ROLE_ADMIN", "ROLE ADMIN"),
		ROLE_USER(2,"ROLE_USER", "ROLE USER"),
		ROLE_SUPER_ADMIN(3,"ROLE_SUPER_ADMIN", "ROLE SUPER ADMIN"),
		ROLE_TEACHER(4,"ROLE_TEACHER", "ROLE TEACHER"),
		ROLE_STAFF(5,"ROLE_STAFF", "ROLE STAFF"),
		ROLE_PARENT(6,"ROLE_PARENT", "ROLE PARENT"),
		ROLE_STUDENT(7,"ROLE_STUDENT", "ROLE STUDENT"),
		ROLE_PRINCIPAL(8,"ROLE_PRINCIPAL", "ROLE PRINCIPAL"),
		ROLE_MANAGER(9,"ROLE_MANAGER", "ROLE MANAGER"),
		ROLE_DIRECTOR(10,"ROLE_DIRECTOR", "ROLE DIRECTOR"),
		ROLE_LIBRARIAN(11,"ROLE_LIBRARIAN", "ROLE LIBRARIAN"),
		ROLE_RECEPTIONIST(12,"ROLE_RECEPTIONIST", "ROLE RECEPTIONIST"),
		ROLE_OFFICE_ADMINISTRATOR(13,"ROLE_OFFICE_ADMINISTRATOR", "ROLE OFFICE ADMINISTRATOR"),		
		ROLE_ANANYMOUS(14,"ROLE_ANANYMOUS", "ROLE ANANYMOUS"),
		;
		
		
		private Integer roleCode;
		private String roleName;
		private String displayName;
		
		USER_ROLES(Integer roleCode, String roleName, String displayName){
			this.roleCode = roleCode;
			this.roleName = roleName;
			this.displayName = displayName;
		}

		public Integer getRoleCode() {
			return roleCode;
		}

		public String getRoleName() {
			return roleName;
		}

		public String getDisplayName() {
			return displayName;
		}
		
		public static USER_ROLES getRole(Integer roleId) {			
			for(USER_ROLES userRole: values()) {
				if(userRole.getRoleCode().equals(roleId)) {
					return userRole;
				}
			}			
			return null;
			
		}
		public static USER_ROLES getRole(String roleName) {			
			for(USER_ROLES userRole: values()) {
				if(userRole.getRoleName().equals(roleName)) {
					return userRole;
				}
			}			
			return null;
			
		}
	}	
}
