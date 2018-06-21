package com.base.mchtApi.util.repayment.util.crpyter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.HashMap;

import javax.crypto.Cipher;

import com.base.mchtApi.util.repayment.util.util.Base64;
import com.base.mchtApi.util.repayment.util.util.Strings;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xie
 */
@Slf4j
public class Rsa {

	private static final String ENCODING = "UTF-8";

	/**
	 * 使用rsa进行签名，算法支持MD5withRSA\SHA256withRSA
	 *
	 * @param data
	 * @param pfx_path
	 * @param key_pass
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static String sign(String data, String pfx_path, String key_pass, String algorithm) throws Exception {

		try {
			RSAPrivateKey pbk = getPrivateKey(pfx_path, key_pass);

			// Class<?> c = Class.forName("sun.security.rsa.SunRsaSign");
			// Provider provider = (Provider)c.newInstance();
			// Signature signet = Signature.getInstance(algorithm,provider);
			Signature signet = Signature.getInstance(algorithm);
			signet.initSign(pbk);
			signet.update(data.getBytes(ENCODING));
			byte[] signed = signet.sign();

			return Base64.encode(signed);

		}
		catch (Exception e) {
			log.error(Strings.toString(e));
			return "";
		}
	}

	/**
	 * 使用rsa进行验签，算法支持MD5withRSA\SHA256withRSA
	 *
	 * @param signMsg
	 * @param publicKey
	 * @param data
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(String signMsg, String publicKeyPath, String data, String algorithm) throws Exception {

		try {
			byte[] btsData = Base64.decode(signMsg);

			RSAPublicKey pbk = getPublicKey(publicKeyPath);

			// Class<?> c2 = Class.forName("sun.security.rsa.SunRsaSign");
			// Provider provider2 = (Provider)c2.newInstance();
			// Signature signetcheck = Signature.getInstance(algorithm,provider2);
			Signature signetcheck = Signature.getInstance(algorithm);
			signetcheck.initVerify(pbk);
			signetcheck.update(data.getBytes(ENCODING));

			return signetcheck.verify(btsData);

		}
		catch (Exception e) {
			log.error(Strings.toString(e));
			return false;
		}
	}

	/**
	 * 使用rsa进行加密，算法支持MD5withRSA\SHA256withRSA
	 *
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String publicKeyPath) throws Exception {
		RSAPublicKey pbk = getPublicKey(publicKeyPath);
		// Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING","SunJCE");
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, pbk);

		byte[] encDate = cipher.doFinal(data.getBytes(ENCODING));

		return Base64.encode(encDate);
	}

	/**
	 * 使用rsa进行加密，算法支持MD5withRSA\SHA256withRSA
	 *
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptBase64(String data, String publicKeyPath) throws Exception {

		RSAPublicKey pbk = getPublicKey(publicKeyPath);

		// Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING","SunJCE");
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, pbk);

		byte[] encDate = cipher.doFinal(data.getBytes(ENCODING));

		return Base64.encode(encDate);
	}

	/**
	 * 使用rsa进行解密，算法支持MD5withRSA\SHA256withRSA
	 *
	 * @param data
	 * @param pfx_path
	 * @param pfx_pass
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data, String pfx_path, String pfx_pass) throws Exception {

		try {

			RSAPrivateKey pbk = getPrivateKey(pfx_path, pfx_pass);

			// Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING","SunJCE");
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
			cipher.init(Cipher.DECRYPT_MODE, pbk);

			byte[] btSrc = cipher.doFinal(Base64.decode(data));

			return new String(btSrc, ENCODING);

		}
		catch (Exception e) {
			log.error(Strings.toString(e));
			return "";
		}
	}

	private static HashMap<String, PrivateKey> privateKeys = new HashMap<String, PrivateKey>();

	/**
	 * 获取文件的私钥
	 *
	 * @param keyPath
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey getPrivateKey(String keyPath, String passwd) throws Exception {

		// Class<?> c = Class.forName("com.sun.net.ssl.internal.ssl.Provider");
		// Provider provider = (Provider)c.newInstance();
		// KeyStore ks = KeyStore.getInstance("PKCS12",provider);
		PrivateKey prikey = privateKeys.get(keyPath);
		if (prikey == null) {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			// FileInputStream fis = new FileInputStream(keyPath);
			InputStream fis = Rsa.class.getResourceAsStream("/" + keyPath);

			char[] nPassword = null;
			if ((passwd == null) || passwd.trim().equals("")) {
				nPassword = null;
			}
			else {
				nPassword = passwd.toCharArray();
			}
			ks.load(fis, nPassword);
			fis.close();

			Enumeration<String> enumq = ks.aliases();
			String keyAlias = null;
			if (enumq.hasMoreElements()) {
				keyAlias = enumq.nextElement();
			}

			prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);

			privateKeys.put(keyPath, prikey);
		}

		return (RSAPrivateKey) prikey;
	}

	private static HashMap<String, RSAPublicKey> publicKeys = new HashMap<String, RSAPublicKey>();

	private static RSAPublicKey getPublicKey(String keyPath) {
		RSAPublicKey pbk = publicKeys.get(keyPath);
		if (null != pbk) {
			return pbk;
		}
		String publicKey = readFormatKey(keyPath);
		try {
			KeyFactory rsaKeyFac = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKey));
			pbk = (RSAPublicKey) rsaKeyFac.generatePublic(keySpec);
		}
		catch (Exception e) {
			e.printStackTrace();
			pbk = null;
		}
		if (null != pbk) {
			publicKeys.put(keyPath, pbk);
		}
		return pbk;
	}

	/**
	 * 读取密钥信息
	 *
	 * @param keyPath
	 * @return
	 * @throws IOException
	 */
	public static String readFormatKey(String keyPath) {
		if (null == keyPath || keyPath.length() == 0) {
			return null;
		}
		InputStream in = null;
		BufferedReader br = null;
		String data;
		try {
			int count = 0;
			in = Rsa.class.getResourceAsStream("/" + keyPath);
			br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			final StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (null == readLine || readLine.length() <= 0) {
					continue;
				}
				if (readLine.charAt(0) == '-') {
					count++;
					if (count < 2) {
						continue;
					}
					else {
						break;
					}
				}
				else {
					sb.append(readLine);
				}
			}

			data = sb.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			data = null;
		}
		finally {
			if (null != in) {
				try {
					in.close();
				}
				catch (Exception closeE) {
					// TODO: handle exception
					closeE.printStackTrace();
				}
			}
		}
		if (null == data || data.length() <= 0) {
			return null;
		}
		String keyReal = data.replace(" ", "").replace("\t", "");
		return null == keyReal || keyReal.length() <= 0 ? null : keyReal;
	}

}
