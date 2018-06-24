/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 下午2:40:03
 */
package com.newpay.webauth.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.newpay.webauth.config.AppConfig;
import com.newpay.webauth.dal.response.ResultFactory;
import com.ruomm.base.tools.FileUtils;
import com.ruomm.base.tools.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("app/fileCore")
public class FileCoreController {
	@RequestMapping("uploadFile")
	public Object uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest httpServletRequest) throws Exception {
		String userId = httpServletRequest.getHeader("userId");
		if (StringUtils.isEmpty(userId)) {
			userId = "common";
		}
		String fileExt = FileUtils.getFileExtension(file.getOriginalFilename());
		if (StringUtils.isEmpty(fileExt)) {
			fileExt = ".unknow";
		}
		else {
			fileExt = "." + fileExt;
		}
		String fileName = AppConfig.SDF_DB_TIME.format(new Date()) + fileExt;
		boolean flag = FileUtils.writeFile(
				FileUtils.createFile(AppConfig.FileCoreUploadFilePath() + userId + File.separator + fileName),
				file.getInputStream());
		if (flag) {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("filePath", "app/fileCore/getFile?userId=" + userId + "&file=" + fileName);
			return ResultFactory.toAck(resultMap);
		}
		else {
			return ResultFactory.toNackCORE();
		}
	}

	@GetMapping("getFile")
	public void getFile(@RequestParam Map<String, String> requestParam, HttpServletResponse response) throws Exception {
		String userId = requestParam.get("userId");
		String fileName = requestParam.get("file");
		File file = new File(AppConfig.FileCoreUploadFilePath() + userId + File.separator + fileName);
		if (file.exists()) {
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				response.setContentLength((int) file.length());
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName);// 设置在下载框默认显示的文件名
				response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
				FileInputStream fileInputStream = new FileInputStream(file);
				bis = new BufferedInputStream(fileInputStream);
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
				bos.flush();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			}

		}
		else {
			response.sendError(404);
		}
	}
}
