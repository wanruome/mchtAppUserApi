package com.base.mchtApi.util.repayment.util.crpyter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.base.mchtApi.util.repayment.util.util.Base64;
import com.base.mchtApi.util.repayment.util.util.Strings;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xie
 */
@Slf4j
public class TripleDes {

	private static final String Algorithm = "DESede"; // 定义 加密算法,可用 DES,DESede,Blowfish
	private static final String ENCODING = "UTF-8";

	/**
	 * 加密，使用base64编码
	 *
	 * @param keybyte
	 * @param src
	 * @return
	 */
	public static String encrypt(String keybyte, String src) {

		try {

			SecretKey deskey = new SecretKeySpec(Base64.decode(keybyte), Algorithm);

			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] bts = c1.doFinal(src.getBytes(ENCODING));

			return Base64.encode(bts);

		}
		catch (java.lang.Exception e) {
			log.error(Strings.toString(e));
		}
		return "";
	}

	/**
	 * @param keybyte
	 * @param src
	 * @return
	 */
	public static String decrypt(String keybyte, String src) {

		try {

			SecretKey deskey = new SecretKeySpec(Base64.decode(keybyte), Algorithm); //

			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.DECRYPT_MODE, deskey);

			byte[] bts = c1.doFinal(Base64.decode(src));

			return new String(bts, ENCODING);
		}
		catch (java.lang.Exception e) {
			log.error(Strings.toString(e));
		}
		return null;
	}

	/**
	 * @param keybyte
	 * @param src
	 * @return
	 */
	public static byte[] encrypt(byte[] keybyte, byte[] src) {

		try {

			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] bts = c1.doFinal(src);

			return bts;

		}
		catch (java.lang.Exception e) {
			log.error(Strings.toString(e));
		}
		return null;
	}

	/**
	 * @param keybyte
	 * @param src
	 * @return
	 */
	public static byte[] decrypt(byte[] keybyte, byte[] src) {

		try {

			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.DECRYPT_MODE, deskey);

			byte[] bts = c1.doFinal(src);

			return bts;
		}
		catch (java.lang.Exception e) {
			log.error(Strings.toString(e));
		}
		return null;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String keybyte = Base64.encode("67d10dc16d8901600d57fd83".toUpperCase().getBytes());

		String enc = "hz7l9lpO3j1cTdxJ+S4L3wWHb+CXheG4brgnchO0kxA7AkNdeyNUsLA82tfc4Ds6WvOzNufh4JD1dXbu5m68/Q==";

		System.out.println(TripleDes.decrypt(keybyte, enc));

		// String src = "abc:[0200 190011 111 2016110657248621 06635595 0.1 156]";
		// System.out.println(TripleDes.encrypt(keybyte, src));

		/*
		 * System.out.println(Base64.encode(Rsa.getPublicKey("c:\\gdyilian_merchant_signature.pfx",
		 * "80115864").getEncoded())); if(1==1) return; //String easy_pub_key =
		 * "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqWSfUW3fSyoOYzOG8joy3xldpBanLVg8gEDcvm9KxVjqvA/qJI7y0Rmkc1I7l9vAfWtNzphMC+wlulpaAsa/4PbfVj+WhoNQyhG+m4sP27BA8xuevNT9/W7/2ZVk4324NSowwWkaqo1yuZe1wQMcVhROz2h+g7j/uZD0fiCokWwIDAQAB";
		 * String merchant_pub_key =
		 * "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8JiMYfBZx5+WGEnKbQOGB4D8BkkH/2VR5x3xKkD84HjeWhnoBtP+cSY9H2uB0tb8GTtPU/qM0mK3JcUDIboVbbZjmSgDZrnAtAM5OXW2oeUdDFQNvCqsM5/IZuKco6Fl8KNp0GNDla8gcw7YNWFeOyoIJbHVCfRDn5bibSNwJiQIDAQAB";
		 * String merchant_pfx_key = "c:\\merchant.pfx"; String a = "abcde";
		 * System.out.println("报文="+a); //签名 String b = Rsa.sign(a,merchant_pfx_key,"11111111");
		 * System.out.println("签名="+b); //验签 boolean c = Rsa.verify(b,merchant_pub_key,a);
		 * System.out.println("验签="+c); //加密 String d = "876578900988098765678899";
		 * System.out.println("key="+d); String e = TripleDes.encrypt(d, a);
		 * System.out.println("报文密文="+e); String e1 = TripleDes.encrypt(d, b);
		 * System.out.println("签名密文="+e1); String f = Rsa.encrypt(d, merchant_pub_key);
		 * System.out.println("key密文="+f); //解密 String g = Rsa.decrypt(f, merchant_pfx_key,
		 * "11111111"); System.out.println("key="+g); String h = TripleDes.decrypt(g, e);
		 * System.out.println("报文="+h); String h1 = TripleDes.decrypt(g, e1);
		 * System.out.println("签名="+h1); System.out.println(a.equals(h));
		 * System.out.println(b.equals(h1));
		 */
	}
}
