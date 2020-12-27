package com.taotao.portal.service;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbUser;

public interface UserService {
	
	R logout(String token);
	TbUser getUserByToken(String token);

}
