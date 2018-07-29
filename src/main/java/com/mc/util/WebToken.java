package com.mc.util;

import org.springframework.stereotype.Component;

/**
 * 加载jwt配置文件类
 * @version 1.0
 * @date 2017年1月22日上午11:44:33
 */
@Component
public class WebToken { 
	private String salt;	//base64位签名
	private String name;			//发行人名称
	private int expiresSecond;		//保存时间
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getExpiresSecond() {
		return expiresSecond;
	}
	public void setExpiresSecond(int expiresSecond) {
		this.expiresSecond = expiresSecond;
	}
}
