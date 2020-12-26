package com.taotao.sso.service;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbUser;

public interface UserService {
	
	R userCheckParam(String param,Integer type);
	R addUser(TbUser tbUser);
	R getUserByUsernameAndPassword(String username,String password);
	R getToken(String token);

}
