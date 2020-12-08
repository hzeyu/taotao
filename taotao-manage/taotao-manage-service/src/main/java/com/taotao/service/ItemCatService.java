package com.taotao.service;

import java.util.List;

import com.taotao.dataresult.EUITreeDate;
import com.taotao.pojo.TbItemCat;

public interface ItemCatService {
	
	List<EUITreeDate> findItemCatsByParentId(Long parentId);

}
