package cn.djh.gmms.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 文件上传工具类
 */
public class UploadUtil {
	/**
	 * 如果上传，就返回上传的文件名
	 * 如果不上传，返回null
	 * @param fileUpload 上传的文件
	 * @param realPath 上传位置的真实路径
	 * @return
	 */
	public static String uploadFile(MultipartFile fileUpload, String realPath){
		if(fileUpload==null||fileUpload.isEmpty()){//为空，表示不上传，返回null
			return null;
		}
		
		//处理文件名
		String prefix = UUID.randomUUID().toString().replaceAll("-", "");
		//使用UUID加前缀命名文件，防止名字重复被覆盖
		String fileName = prefix+"_"+fileUpload.getOriginalFilename();//先获取文件的原始名(含后缀),再加上随机的前缀
		String finalName = realPath+"\\"+fileName;//处理后的最终文件名,也是要存储在数据库中的文件名
		
		InputStream in = null;
		OutputStream out = null;
		File file = new File(realPath);
		if (!file.exists()){// 如果上传图片所在的父目录不存在,就创建父目录
			file.mkdirs();
		}
		try {
			in = fileUpload.getInputStream();//获取输入流
			out = new FileOutputStream(finalName);//指定输出流位置

					//开始上传文件
			IOUtils.copy(in, out);
			System.out.println("上传成功");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();//关闭流
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}
}