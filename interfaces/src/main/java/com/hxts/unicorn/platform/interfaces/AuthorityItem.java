package com.hxts.unicorn.platform.interfaces;

/**
 * 权限名称及描述
 * @author LENOVO
 *
 */
public final class AuthorityItem {
	public String authKey;
	public String authName;
	public String authDescription;
	
	public AuthorityItem(){}
	
	public AuthorityItem(String authKey, String authName, String authDescription){
		this.authKey = authKey;
		this.authName = authName;
		this.authDescription = authDescription;
	}
	
	public boolean equals(Object obj)
	{
	    if (obj instanceof AuthorityItem)
	    {
	        AuthorityItem item = (AuthorityItem) obj;
	        return this.authKey.equals(item.authKey);
	    };
	    return super.equals(obj);
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getAuthDescription() {
		return authDescription;
	}

	public void setAuthDescription(String authDescription) {
		this.authDescription = authDescription;
	}
	
	
}
