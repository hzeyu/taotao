package com.taotao.utils;
/**
 * @ClassName:  StringUtil   
 * @Description:操作字符串工具类
 * @author: hanzeyu
 * @date:   2020年12月26日 下午8:36:13      
 * @Copyright:
 */
public class StringUtil {
	
	/**
	 * @Title: isNull   
	 * @Description: 判断空串
	 * @author:hanzeyu
	 * @param: @param str
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public static boolean isNull(String str) {
		
		if(null == str) {
			return true;
		}
		str = str.trim();
		if("" == str || 0 == str.length()) {
			return true;
		}
		return false;
	}
	/**
	 * @Title: isNotNull   
	 * @Description: 判断非空字符串
	 * @author:hanzeyu
	 * @param: @param str
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public static boolean isNotNull(String str) {
		return !isNull(str);
	}

}
