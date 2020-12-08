package com.taotao.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.dataresult.R;
import com.taotao.service.TaotaoFilesService;

/**
 * @author hanzeyu
 * @date 2020年12月8日
 * taotaoManage工程中操作文件的controller
 */
@RestController
public class TaotaoFilesController {
	
	@Resource
	private TaotaoFilesService taotaoFilesService;
	
	@Value("${UPLOAD_PATH_PREFIX}")
	private String UPLOAD_PATH_PREFIX;
	
	@RequestMapping("/pic/upload")
	public R fileUpload(MultipartFile uploadFile) {
	
		R r = null;
		
		//fastdfs配置文件路径
		String clientConfPath = "client.conf";
		
		String url = taotaoFilesService.fileUpload(uploadFile, clientConfPath);
		
		if(url==null && url=="") {
			r = R.error("上传失败");
			r.put("error", 1);
			r.put("message", "上传失败");
			return r;
		}
		
		r = R.ok("上传成功");
		r.put("error", 0);
		r.put("url",UPLOAD_PATH_PREFIX+url);
		return r;
	}

}
