package com.taotao.portal.service;

import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;

public interface ItemService {
	
	ItemInfo getItemInfoByItemId(Long itemId);
	String getItemDescByItemId(Long itemId);
	String getItemParamByItemId(Long itemId);

}
