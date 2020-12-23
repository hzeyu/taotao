package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${SERVER_BASE_URL}")
	private String SERVER_BASE_URL;
	@Value("${SERVER_QUERY_ITEMINFO_URL}")
	private String SERVER_QUERY_ITEMINFO_URL;
	@Value("${SERVER_QUERY_ITEMDESC_URL}")
	private String SERVER_QUERY_ITEMDESC_URL;
	@Value("${SERVER_QUERY_ITEMPARAM_URL}")
	private String SERVER_QUERY_ITEMPARAM_URL;

	@Override
	public ItemInfo getItemInfoByItemId(Long itemId) {

		try {
			String itemJson = HttpClientUtil.doGet(SERVER_BASE_URL + SERVER_QUERY_ITEMINFO_URL + itemId);

			if (itemJson != null && itemJson != "") {
				R r = JsonUtils.jsonToPojo(itemJson, R.class);
				Object object = r.get("data");
				String dataJson = JsonUtils.objectToJson(object);
				ItemInfo itemInfo = JsonUtils.jsonToPojo(dataJson, ItemInfo.class);

				return itemInfo;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	@Override
	public String getItemDescByItemId(Long itemId) {
		try {
			String itemJson = HttpClientUtil.doGet(SERVER_BASE_URL + SERVER_QUERY_ITEMDESC_URL + itemId);

			if (itemJson != null && itemJson != "") {
				R r = JsonUtils.jsonToPojo(itemJson, R.class);
				Object object = r.get("data");
				String dataJson = JsonUtils.objectToJson(object);
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(dataJson, TbItemDesc.class);
				String itemDesc = tbItemDesc.getItemDesc();
				if(itemDesc!="" && itemDesc!=null)
					return itemDesc;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	@Override
	public String getItemParamByItemId(Long itemId) {
		try {
			String itemJson = HttpClientUtil.doGet(SERVER_BASE_URL + SERVER_QUERY_ITEMPARAM_URL + itemId);

			if (itemJson != null && itemJson != "") {
				R r = JsonUtils.jsonToPojo(itemJson, R.class);
				Object object = r.get("data");

				// 把json转换成java对象
				if ((int) r.get("status") == 200) {
					String itemParamJson = JsonUtils.objectToJson(object);
					TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(itemParamJson, TbItemParamItem.class);
					String paramData = itemParamItem.getParamData();
					// 生成html
					// 把规格参数json数据转换成java对象
					List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
					StringBuffer sb = new StringBuffer();
					sb.append(
							"<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
					sb.append("    <tbody>\n");
					for (Map m1 : jsonList) {
						sb.append("        <tr>\n");
						sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
						sb.append("        </tr>\n");
						List<Map> list2 = (List<Map>) m1.get("params");
						for (Map m2 : list2) {
							sb.append("        <tr>\n");
							sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
							sb.append("            <td>" + m2.get("v") + "</td>\n");
							sb.append("        </tr>\n");
						}
					}
					sb.append("    </tbody>\n");
					sb.append("</table>");
					// 返回html片段
					return sb.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
