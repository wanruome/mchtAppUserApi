package com.base.mchtApi.util.repayment.util.http;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;

import com.base.mchtApi.util.repayment.util.util.Strings;

import lombok.extern.slf4j.Slf4j;

/**
 * http流收发工具，UTF-8编码
 *
 * @author xie
 */
@Slf4j
public class Httper {

	/**
	 * 获取http连接，支持自动备份（连接超时情况下）
	 *
	 * @param urls
	 * @param connTimeOut
	 * @param readTimeOut
	 * @return HttpURLConnection
	 */
	public static HttpURLConnection getHttpURLConnection(String[] urls, int connTimeOut, int readTimeOut,
			String content) {

		// 获取连接，只要exception，都可以获取下一个
		HttpURLConnection connect = null;
		for (int i = 0; i < urls.length; i++) {

			try {

				String url = urls[i];

				log.info("connect to " + url);
				url = url + "?request_text=" + content;
				connect = new SslConnection().openConnection(url);
				connect.setConnectTimeout(connTimeOut * 1000);
				connect.setReadTimeout(readTimeOut * 1000);

				connect.setRequestMethod("POST");
				connect.setDoInput(true);
				connect.setDoOutput(true);
				connect.connect();

				return connect;

			}
			catch (Exception e) {
				log.error(Strings.toString(e));
				if (connect != null) {
					try {
						connect.disconnect();
					}
					catch (Exception ex) {
						log.error(Strings.toString(ex));
					}
				}
			}
		}

		return null;
	}

	/**
	 * 获取http连接，支持自动备份（连接超时情况下）
	 *
	 * @param urls
	 * @param connTimeOut
	 * @param readTimeOut
	 * @return HttpURLConnection
	 */
	public static HttpURLConnection getHttpURLConnection(String[] urls, int connTimeOut, int readTimeOut) {

		// 获取连接，只要exception，都可以获取下一个
		HttpURLConnection connect = null;
		for (int i = 0; i < urls.length; i++) {

			try {

				String url = urls[i];

				// log.info("connect to "+url);
				// url=url+"?request_text="+content;
				connect = new SslConnection().openConnection(url);
				connect.setConnectTimeout(connTimeOut * 1000);
				connect.setReadTimeout(readTimeOut * 1000);

				connect.setRequestMethod("POST");
				connect.setDoInput(true);
				connect.setDoOutput(true);
				connect.connect();

				return connect;

			}
			catch (Exception e) {
				// log.error(Strings.toString(e));
				if (connect != null) {
					try {
						connect.disconnect();
					}
					catch (Exception ex) {
						log.error(Strings.toString(ex));
					}
				}
			}
		}

		return null;
	}

	/**
	 * 发送并同步接收数据，报文无长度
	 *
	 * @param urls
	 * @param connTimeOut
	 * @param readTimeOut
	 * @param content
	 * @return
	 */
	public static String sendAndGet(String[] urls, int connTimeOut, int readTimeOut, String content) {

		HttpURLConnection connect = null;

		try {
			// urls=urls+"?request_text="+content;
			connect = getHttpURLConnection(urls, connTimeOut, readTimeOut);

			byte[] put = content.getBytes("UTF-8");

			OutputStream out = connect.getOutputStream();
			out.write(put);
			out.flush();
			out.close();

			return new String(StreamReader.readHttp(connect.getInputStream()), "UTF-8");

		}
		catch (Exception e) {
			log.error(Strings.toString(e));
		}
		finally {
			if (connect != null) {
				try {
					connect.disconnect();
				}
				catch (Exception e) {
					log.error(Strings.toString(e));
				}
			}
		}

		return null;
	}

	/**
	 * 发送并同步接收数据，报文有长度位
	 *
	 * @param urls
	 * @param connTimeOut
	 * @param readTimeOut
	 * @param content
	 * @param msgLen
	 * @return
	 */
	public static String sendAndGet(String[] urls, int connTimeOut, int readTimeOut, String content, int msgLen) {

		HttpURLConnection connect = null;

		try {
			connect = getHttpURLConnection(urls, connTimeOut, readTimeOut);

			byte[] put = (Strings.padLeft(content.getBytes("UTF-8").length + "", '0', msgLen) + content)
					.getBytes("UTF-8");

			OutputStream out = connect.getOutputStream();
			out.write(put);
			out.flush();
			out.close();

			BufferedInputStream in = new BufferedInputStream(connect.getInputStream());
			byte[] lb = new byte[msgLen];
			int ll = 0;
			int lr = 0;
			while (ll < msgLen && (lr = in.read(lb, ll, msgLen - ll)) != -1) {
				ll += lr;
			}
			if (ll < msgLen) {
				log.warn("readLen invalid length:" + ll);
			}
			int tl = Integer.parseInt(new String(lb));
			log.info("readLen:" + tl);
			byte[] bts = new byte[tl];
			int totalLen = 0;
			int len = 0;
			while (totalLen < tl && (len = in.read(bts, totalLen, tl - totalLen)) != -1) {
				totalLen += len;
				log.info("readLen:" + tl);
			}
			if (totalLen < tl) {
				log.info("invalid request,read:" + totalLen + ",total:" + tl);
			}

			return new String(bts, "UTF-8");

		}
		catch (Exception e) {
			log.error(Strings.toString(e));
		}
		finally {
			if (connect != null) {
				try {
					connect.disconnect();
				}
				catch (Exception e) {
					log.error(Strings.toString(e));
				}
			}
		}

		return "";
	}

	/**
	 * 读取数据流的内容
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String read(HttpServletRequest request) throws Exception {

		return new String(StreamReader.readHttp(request.getInputStream()), "UTF-8");
	}
}
