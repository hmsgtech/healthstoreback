package com.hmsgtech.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

@Controller
public class UploadImage {
	@RequestMapping("/uploadimage")
	public @ResponseBody
	String getImage(@RequestParam("imagefile") MultipartFile file)
			throws Exception {
		Upload upload = new Upload();
		File filei = convert(file);
		//压缩图片
		Thumbnails.of(filei).size(100, 100).outputFormat("jpg")
				.toFiles(Rename.NO_CHANGE);
		upload.upload(filei);
		String imageurl = "{\"image\":\"http://7xs8w3.com1.z0.glb.clouddn.com/"
				+ upload.key + "\"}";
		return imageurl;
	}
	
	
	@RequestMapping("/gettoken")
	public @ResponseBody
	String gettoken(){
		Upload upload = new Upload();
		String uptoken = upload.getUpToken();
		return "{\"uptoken\":\"" + uptoken + "\"}";
	}

	private File convert(MultipartFile multipartFile) throws IOException {
		File convFile = new File(multipartFile.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(multipartFile.getBytes());
		fos.close();
		return convFile;
	}

	public class Upload {
		// 设置好账号的ACCESS_KEY和SECRET_KEY
		String ACCESS_KEY = "XKOKACjcZ0XXFoM-Itu-D2z60edkSEh_yd0yReJd";
		String SECRET_KEY = "9Ih7lgbjl8TG5hOypAG0zDGN40-zq8fQvFF8oUkC";
		// 要上传的空间
		String bucketname = "client-image";
		// 上传到七牛后保存的文件名
		String key = "java" + System.currentTimeMillis() + ".png";
		String result = "";

		// 密钥配置
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		// 创建上传对象
		UploadManager uploadManager = new UploadManager();

		// 简单上传，使用默认策略，只需要设置上传的空间名就可以了
		public String getUpToken() {
			return auth.uploadToken(bucketname);
		}

		public void upload(File file) throws IOException {
			try {
				// 调用put方法上传
				Response res = uploadManager.put(file, key, getUpToken());
				// 打印返回的信息
				result = res.toString();
			} catch (QiniuException e) {
				Response r = e.response;
				// 请求失败时打印的异常的信息
				System.out.println(r.toString());
				try {
					// 响应的文本信息
					System.out.println(r.bodyString());
				} catch (QiniuException e1) {
					// ignore
				}
			}
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}
	}
}
