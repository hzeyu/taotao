package com.taotao.service;

import java.util.List;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbContent;

public interface ContentService {

	R getContentListByContentCategoryId(Long categoryId,Integer page,Integer rows);
	
	R saveContentWithCategoryId(Long categoryId,TbContent content);
	
	R deleteContentdById(Long[] ids);
}
