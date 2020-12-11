package com.taotao.service;

import java.util.List;

import com.taotao.dataresult.EUITreeDate;
import com.taotao.dataresult.R;

public interface ContentCategoryService {
	
	List<EUITreeDate> findContentCategoryListByParentId(Long parentId);
	
	R createContentCategory(Long parentId,String name);
	
	R updateContentCategory(Long id,String name);
	
	R delContentCategory(Long parentId,Long id);

}
