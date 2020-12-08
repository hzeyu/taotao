package com.taotao.service;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbItem;

public interface ItemService {
	
	TbItem findItemById(Long id);
	
	R getItemList(Integer page,Integer rows);

	R saveItem(TbItem item);
}
