package com.hxts.unicorn.platform.interfaces;

public final class RequestAuthority {
	public boolean needAuthorize = true; //是否需要权限，如为false表示无需验证
	public boolean needLogon = true; //是否必须已登录用户
	public String authKey; //权限关键字
	public int orgnizId = 0; //0表示和组织无关
}
