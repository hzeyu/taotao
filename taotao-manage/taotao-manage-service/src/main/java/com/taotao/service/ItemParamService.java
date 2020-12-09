package com.taotao.service;

import java.util.List;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {
	
	R findItemParams(Integer page,Integer rows);
	
	R queryItemParamsByCid(Long itemcatid);
	
	R saveItemParam(Long itemCatId,String paramData);

}
