package com.base.mchtApi.util.repayment.util.http;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * http或https连接标准类
 * @author xie
 *
 */
public class SslConnection {

	/**
	 * 打开http连接
	 * @param strUrl
	 * @return
	 * @throws Exception
	 */
	public HttpURLConnection openConnection(String strUrl) throws Exception {

		trustAllCerts();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		URL url = new URL(strUrl);
		return (HttpURLConnection) url.openConnection();
	}

	/**
	 * 
	 * @param strUrl
	 * @return
	 * @throws Exception
	 */
	public String connect(String strUrl) throws Exception {

		trustAllCerts();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		URL url = new URL(strUrl);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		byte[] bts = new byte[100];
		urlConn.getInputStream().read(bts);
		String result = new String(bts).trim();
		return result;

	}
	private HostnameVerifier hv = new HostnameVerifier() {
		public boolean verify(String urlHostName, SSLSession session) {
			return true;
		}
	};

	private static void trustAllCerts() throws Exception {

		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];

		javax.net.ssl.TrustManager tm = new Mitm();

		trustAllCerts[0] = tm;

		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");

		sc.init(null, trustAllCerts, null);

		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	}

	public HttpURLConnection openConnection(Proxy proxy, String strUrl, String sslversion) throws Exception {
		trustAllHttpsCertificates(sslversion);
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		URL url = new URL(strUrl);
		if(null != proxy){
			return (HttpURLConnection) url.openConnection(proxy);
		} else {
			return (HttpURLConnection) url.openConnection();
		}
	}

	private static void trustAllHttpsCertificates(String sslversion) throws Exception {

		//  Create a trust manager that does not validate certificate chains:

		javax.net.ssl.TrustManager[] trustAllCerts =
				new javax.net.ssl.TrustManager[1];

		javax.net.ssl.TrustManager tm = new Mitm();

		trustAllCerts[0] = tm;

		javax.net.ssl.SSLContext sc =
				javax.net.ssl.SSLContext.getInstance(sslversion);

		sc.init(null, trustAllCerts, null);

		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(
				sc.getSocketFactory());

	}

	/**
	 * 
	 * @author xie
	 *
	 */
	public static class Mitm implements javax.net.ssl.TrustManager,javax.net.ssl.X509TrustManager {

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		/**
		 * 
		 * @param certs
		 * @return
		 */
		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		/**
		 * 
		 * @param certs
		 * @return
		 */
		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		/**
		 * 
		 */
		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
			return;
		}

		/**
		 * 
		 */
		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
			return;
		}
	}
}