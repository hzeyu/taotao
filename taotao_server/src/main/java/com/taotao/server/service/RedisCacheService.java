package com.taotao.server.service;

import com.taotao.dataresult.R;

public interface RedisCacheService {
	//缓存同步
	R synchro(Long contentCid);

}
