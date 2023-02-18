package com.xprodcda.spring.xprodcda.enumeration;
import static com.xprodcda.spring.xprodcda.constant.Authority.*;
public enum Role {
	ROLE_USER(USER_AUTHORITIES),
	ROLE_HR(HR_AUTHORITIES),
	ROLE_MANAGER(MANAGER_AUTHORITIES),
	ROLE_ADMIN(ADMIN_AUTHORITIES),
	ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES); 	//SUPER_ADMIN_AUTHORITIES = {"user:read","user:create","user:update","user:delete"}
	
	private String[] authorities;
	
	Role(String... authorities){
		this.authorities=authorities;
	}

	public String[] getAuthorities() {
		return authorities;
	}
}
