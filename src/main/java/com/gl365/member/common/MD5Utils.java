package com.gl365.member.common;

import java.security.MessageDigest;

public class MD5Utils {  
    
	private static final String SALT = "GL_SALT_MD5_KEY";//C B 端密码加的盐
    /** 
     * md5加密方法 
     * @param password 
     * @return 
     */  
	public static String md5Password(String password) {
		try {
			password = SALT + password;
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte b[] = md.digest();

			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
			// System.out.println("32λ: " + buf.toString());// 32位
			// System.out.println("16λ: " + buf.toString().substring(8, 24));//
			// 16位
		} catch (Exception e) {
			return "";
		}
	}  
}