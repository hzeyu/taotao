package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import com.taotao.dataresult.R;

/**
 * @author hanzeyu
 * @date 2020年12月8日
 */
public interface TaotaoFilesService {

	String fileUpload(MultipartFile uploadFile,String clientConfPath);
}
