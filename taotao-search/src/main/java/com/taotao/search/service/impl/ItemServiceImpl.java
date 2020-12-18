package com.taotao.search.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dataresult.R;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Resource
	private ItemMapper itemMapper;
	@Resource
	private SolrServer solrServer;

	@Override
	public R importAllItems() {
		//查库
		List<Item> items = itemMapper.getAllItems();
		
		//导入solr搜索库
		try {
			for (Item item : items) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", item.getId());
				document.addField("item_category_name", item.getItem_category_name());
				document.addField("item_desc", item.getItem_desc());
				document.addField("item_image", item.getItem_image());
				document.addField("item_price", item.getItem_price());
				document.addField("item_sell_point", item.getItem_sell_point());
				document.addField("item_title", item.getItem_title());
				
				solrServer.add(document);
			}
			solrServer.commit();
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(e.getMessage());
		}
	}

}
