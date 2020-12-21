package com.taotao.server.service;

import com.taotao.dataresult.R;

public interface ItemService {
	
	R getItemInfoByItemId(Long itemId);
	R getItemDescByItemId(Long itemId);
	R getItemParamByItemId(Long itemId);

}
