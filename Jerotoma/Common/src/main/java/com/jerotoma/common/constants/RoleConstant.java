package com.jerotoma.common.constants;

public class RoleConstant {
	public static final String ROLE = "role";
	public static final String ROLES = "roles";
	public static final String ROLE_COUNT = "role";
	
	public static enum USER_ROLES {
		ROLE_ADMIN(1,"ROLE_ADMIN", "ROLE ADMIN"),
		ROLE_USER(2,"ROLE_USER", "ROLE USER"),
		ROLE_SUPER_USER(3,"ROLE_SUPER_ADMIN", "ROLE SUPER ADMIN");
		
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
			for(USER_ROLES userRole: USER_ROLES.values()) {
				if(userRole.getRoleCode().equals(roleId)) {
					return userRole;
				}
			}			
			return null;
			
		}
		public static USER_ROLES getRole(String roleName) {			
			for(USER_ROLES userRole: USER_ROLES.values()) {
				if(userRole.getRoleName().equals(roleName)) {
					return userRole;
				}
			}			
			return null;
			
		}
	}	
}
