package cn.djh.gmms.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

// 密码加密工具类
public class MD5Util {
	// 设置盐值
	public static final String SALT = "masterDJ";
	// 设置遍历次数
	public static final int HASHITERATIONS = 10;

	// 加密密码
	public static String createMD5Str(String password){
		SimpleHash hash = new SimpleHash("MD5",password,SALT,HASHITERATIONS);
		return hash.toString();
	}
}
