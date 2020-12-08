package com.taotao.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.dataresult.R;
import com.taotao.service.TaotaoFilesService;
import com.taotao.utils.FastDFSUtil;

/**
 * @author hanzeyu
 * @date 2020年12月8日
 */
@Service
public class TaotaoFilesServiceImpl implements TaotaoFilesService{

	@Override
	public String fileUpload(MultipartFile uploadFile,String clientConfPath) {
		
		try {
			FastDFSUtil fastDFSUtil = new FastDFSUtil(clientConfPath);
			
			//文件扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			
			String url = fastDFSUtil.uploadFile(uploadFile.getBytes(),extName);
			return url;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
