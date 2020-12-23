package com.taotao.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taotao.mapper.TbItemDescMapper;
import com.taotao.pojo.TbItemDesc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-dao.xml",
		"classpath:applicationContext-redis.xml",
		"classpath:applicationContext-service.xml"})
public class TestDesc {
	
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	
	@Test
	public void testA() {
		//TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(160857416615146l);
		System.out.println(tbItemDescMapper);
	}

}
