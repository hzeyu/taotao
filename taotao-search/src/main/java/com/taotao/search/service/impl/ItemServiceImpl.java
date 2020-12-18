package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
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
				document.addField("item_category_name", item.getCategory_name());
				document.addField("item_desc", item.getDesc());
				document.addField("item_image", item.getImage());
				document.addField("item_price", item.getPrice());
				document.addField("item_sell_point", item.getSell_point());
				document.addField("item_title", item.getTitle());
				
				solrServer.add(document);
			}
			solrServer.commit();
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(e.getMessage());
		}
	}

	@Override
	public R importItem(String itemId) {
		
		Item item = itemMapper.getItemById(itemId);
		
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", itemId);
		document.addField("item_category_name", item.getCategory_name());
		document.addField("item_desc", item.getDesc());
		document.addField("item_image", item.getImage());
		document.addField("item_price", item.getPrice());
		document.addField("item_sell_point", item.getSell_point());
		document.addField("item_title", item.getTitle());

		try {
			solrServer.add(document);
			solrServer.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return R.ok();
	}

}
