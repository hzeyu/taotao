package com.taotao.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author 韩泽宇
 *
 */
public class FastJsonUtil {

	/**
	 * 将一个按json格式组合的字符串转换成java对象集合
	 * 参数
	 * str  要转换的java字符串
	 * node   字符串中要转换的字段
	 * clazz   目标类型
	 */
	public static List<?> getJavaListForLikeJsonString(String str,String node,Class<?> clazz){
		
		//判断是否传来一个要转换的Java类型
		if(clazz == null) {
			throw new RuntimeException("不能转换的目标类型");
		}
		
		//判断是否有节点，没有节点可以直接转换
		if(node == null) {
			Object parse = JSON.parse(str);
			List<?> parseArray = JSONObject.parseArray(str, clazz);
			
			return parseArray;
		}
		
		//将字符串转换成json对象
		JSONObject jsonpObject = JSON.parseObject(str);
		//将字符串转换成json数组
		JSONArray jsonArray = jsonpObject.getJSONArray(node);
		
		List<?> parseArray = JSONObject.parseArray(jsonArray.toString(), clazz);
		
		return parseArray;
	}
	
	/**
	 * 传来的字符串没有节点，只有数组结构
	 * 参数
	 * str  要转换的java字符串
	 * clazz   目标类型
	 */
	public static List<?> getJavaListForLikeJsonString(String str,Class<?> clazz){
		return getJavaListForLikeJsonString(str, null, clazz);
	}
}
