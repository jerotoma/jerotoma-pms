package com.jerotoma.common.constants;

public class RoleConstant {
	public static final String ROLE = "role";
	public static final String ROLES = "roles";
	public static final String ROLE_COUNT = "role";
	
	public static enum EROLE {
		ROLE_ADMIN(100,"ROLE_ADMIN"),
		ROLE_USER(102,"ROLE_USER"),
		ROLE_SUPER_USER(103,"ROLE_ADMIN");
		
		private Integer roleCode;
		private String roleName;
		
		EROLE(Integer roleCode, String roleName){
			this.roleCode = roleCode;
			this.roleName = roleName;
		}

		public Integer getRoleCode() {
			return roleCode;
		}

		public String getRoleName() {
			return roleName;
		}
	}	
}
