package com.taotao.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.server.jedis.TaotaoJedisClient;
import com.taotao.server.pojo.CatNode;
import com.taotao.server.pojo.CatResult;
import com.taotao.server.service.ItemCatService;
import com.taotao.utils.JsonUtils;

/**
 * @author hanzeyu
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Resource
	private TbItemCatMapper itemCatMapper;
	@Resource
	private TaotaoJedisClient jedisClient;

	@Value("${INDEX_ITEMCAT_REDIS_KEY}")
	private String INDEX_ITEMCAT_REDIS_KEY;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	/**
	 * 首页商品分类数据
	 */
	@Override
	public CatResult findAllCatForCatResult() {
		
		//查询之前查看是否有缓存，有缓存直接返回结果,缓存不能影响程序正常执行，有异常要处理
		try {
			String hget = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, INDEX_ITEMCAT_REDIS_KEY);
			
			if(hget!="" && hget!=null) {
				List<CatNode> catNodes = JsonUtils.jsonToList(hget, CatNode.class);
				
				//将结果封装成CatResult类型
				CatResult catResult = new CatResult();
				catResult.setData(catNodes);
				return catResult;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//从第一层父节点开始查
		List<?> result = getCatResult(0l);
		
		//将结果封装成CatResult类型
		CatResult catResult = new CatResult();
		catResult.setData(result);
		
		//将查询结果添加到缓存中,缓存不能影响程序正常执行，有异常要处理
		String contensdJson = JsonUtils.objectToJson(result);
		try {
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, INDEX_ITEMCAT_REDIS_KEY, contensdJson);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return catResult;
	}

	private List<?> getCatResult(Long parentId) {
		//返回结果
		List result = new ArrayList();
		
		//查询条件，通过parentId查询，区分父节点和叶子节点
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> itemCats = itemCatMapper.selectByExample(example);
		
		//遍历得到的商品类型，判断得到的节点时否是父节点
		//父节点创建catNode节点并继续向下查询，叶子节点创建catNode节点
		int count = 0;//计数器 当前版面只能存放14条数据，向版面妥协，只返回14个0级父节点，待vision2.0前端升级版面
		for (TbItemCat tbItemCat : itemCats) {
			//判断是否为父节点
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				catNode.setUrl("/products/"+tbItemCat.getId()+".html");
				if (tbItemCat.getParentId() == 0) {
					//只有第一层添加a标签
					catNode.setName("\"<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>\"");
				}else {
					//父节点，但不是第一层
					catNode.setName(tbItemCat.getName());
				}
				//查询父节点下的分类
				List<?> list = getCatResult(tbItemCat.getId());
				catNode.setItem(list);	
				//存放到结果集合
				result.add(catNode);
				
				//当前版面只能存放14条数据，向版面妥协，只返回14个0级父节点，待vision2.0前端升级版面
				count++;
				if(tbItemCat.getParentId()==0 && count==14) {
					break;
				}
			}else {
				//叶子节点,直接存到结果集合
				result.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName()+"");
			}
		}
		
		return result;
	}

}
