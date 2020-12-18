package com.taotao.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;


public class TestSolr {
	

	
	@Test
	public void testSave() throws Exception {
	
			SolrServer server = new HttpSolrServer("http://192.168.106.106:8080/solr/taotao");
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", 1);
			document.addField("item_title", "nihao2");
			server.add(document);
			server.commit();
		
		
	}

}
