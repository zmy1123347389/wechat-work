package com.article.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * AES加,保密性高的数据使用此工具类
 * 
 * @author 张立伟
 * @date : 2016-1-29 上午9:37:49
 * @snice V1.0
 */
public class AESCipher {

	private final static String algorithm = "AES";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @param rawKey
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String rawKey) {
		byte[] key = rawKey.getBytes();
		// Instantiate the cipher
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			byte[] encrypted = cipher.doFinal(data.getBytes());
			return encryptBASE64(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}

	}

	/**
	 * 解密
	 * 
	 * @param encrypted
	 * @param rawKey
	 * @return
	 */
	public static String decrypt(String encrypted, String rawKey) {
		try {
			byte[] tmp = decryptBASE64(encrypted);
			byte[] key = rawKey.getBytes();

			SecretKeySpec skeySpec = new SecretKeySpec(key, algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			byte[] decrypted = cipher.doFinal(tmp);
			return new String(decrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return encrypted;
		}

	}

	public static void main(String[] args) throws Exception {
		String data = "captchacaptchacaptchaca在要工ptchacaptchacaptchacaptcha";
		String key = "1234560123456789";
		System.out.println("密钥为：" + key);
		long lStart = System.currentTimeMillis();
		// 加密
		String encrypted = encrypt(data, key);
		System.out.println("原文：" + data);
		System.out.println("加密后：" + encrypted);
		long lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("加密耗时：" + lUseTime + "毫秒");

		System.out.println();

		// 第一次创建编码器或解码器消耗较多时间，再次创建时所用时间明显减少

		lStart = System.currentTimeMillis();
		// 加密
		encrypted = encrypt(data, key);
		System.out.println("原文：" + data);
		System.out.println("加密后：" + encrypted);
		lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("加密耗时：" + lUseTime + "毫秒");

		System.out.println();

		System.out.println();

		lStart = System.currentTimeMillis();
		// 加密
		encrypted = encrypt(data, key);
		System.out.println("原文：" + data);
		System.out.println("加密后：" + encrypted);
		lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("加密耗时：" + lUseTime + "毫秒");

		System.out.println();

		// 解密
		lStart = System.currentTimeMillis();
		String decrypted = decrypt(encrypted, key);// 解密串
		System.out.println("解密后： " + decrypted);
		lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("解密耗时：" + lUseTime + "毫秒");

	}
}