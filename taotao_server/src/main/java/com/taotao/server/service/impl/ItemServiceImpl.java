package com.taotao.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dataresult.R;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.server.jedis.TaotaoJedisClient;
import com.taotao.server.service.ItemService;
import com.taotao.utils.JsonUtils;

/**
 * 商品展示服务
 * 
 * @author 00
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Resource
	private TbItemMapper tbItemMapper;
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;
	@Resource
	private TaotaoJedisClient jedisClient;
	@Value("${ITEM_REDIS_KEY}")
	private String ITEM_REDIS_KEY;
	@Value("${ITEM_REDIS_EXPIRE}")
	private Integer ITEM_REDIS_EXPIRE;

	@Override
	public R getItemInfoByItemId(Long itemId) {

		// 查缓存,缓存业务不能印象正常业务逻辑
		try {
			String itemJson = jedisClient.get(ITEM_REDIS_KEY + ":" + itemId + ":INFO");

			if (itemJson != "" && itemJson != null) {
				TbItem tbItem = JsonUtils.jsonToPojo(itemJson, TbItem.class);

				R r = R.ok();
				r.put("status", 200);
				r.put("data", tbItem);

				return r;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 缓存没有数据，查库
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);

		// 写入缓存,缓存业务不能印象正常业务逻辑
		try {
			String itemJson = JsonUtils.objectToJson(tbItem);
			jedisClient.set(ITEM_REDIS_KEY + ":" + itemId + ":INFO", itemJson);
			// 设置缓存过期时间
			jedisClient.expire(ITEM_REDIS_KEY + ":" + itemId + ":INFO", ITEM_REDIS_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回数据
		R r = R.ok();
		r.put("status", 200);
		r.put("data", tbItem);

		return r;
	}

	@Override
	public R getItemDescByItemId(Long itemId) {
		// 查缓存,缓存业务不能印象正常业务逻辑
		try {
			String itemJson = jedisClient.get(ITEM_REDIS_KEY + ":" + itemId + ":DESC");

			if (itemJson != "" && itemJson != null) {
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(itemJson, TbItemDesc.class);

				R r = R.ok();
				r.put("status", 200);
				r.put("data", tbItemDesc);

				return r;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 缓存中没有数据，查库
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);

		// 写入缓存,缓存业务不能印象正常业务逻辑
		try {
			String itemJson = JsonUtils.objectToJson(tbItemDesc);
			jedisClient.set(ITEM_REDIS_KEY + ":" + itemId + ":DESC", itemJson);
			// 设置缓存过期时间
			jedisClient.expire(ITEM_REDIS_KEY + ":" + itemId + ":DESC", ITEM_REDIS_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回数据
		R r = R.ok();
		r.put("status", 200);
		r.put("data", tbItemDesc);

		return r;
	}

	@Override
	public R getItemParamByItemId(Long itemId) {
		// 查缓存,缓存业务不能印象正常业务逻辑
		try {
			String itemJson = jedisClient.get(ITEM_REDIS_KEY + ":" + itemId + ":PARAM");

			if (itemJson != "" && itemJson != null) {
				TbItemParamItem tbItemParamItem = JsonUtils.jsonToPojo(itemJson, TbItemParamItem.class);

				R r = R.ok();
				r.put("status", 200);
				r.put("data", tbItemParamItem);

				return r;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//缓存中没有数据，查库
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		
		//查到数据
		if(list!=null && list.size()>0) {
			TbItemParamItem tbItemParamItem = list.get(0);
			
			// 写入缓存,缓存业务不能印象正常业务逻辑
			try {
				String itemJson = JsonUtils.objectToJson(tbItemParamItem);
				jedisClient.set(ITEM_REDIS_KEY + ":" + itemId + ":PARAM", itemJson);
				// 设置缓存过期时间
				jedisClient.expire(ITEM_REDIS_KEY + ":" + itemId + ":PARAM", ITEM_REDIS_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 返回数据
			R r = R.ok();
			r.put("status", 200);
			r.put("data", tbItemParamItem);

			return r;
		}
		
		return null;
	}

}
