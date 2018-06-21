package com.base.mchtApi.util.repayment.util.http;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * stream读取类
 * @author xie
 *
 */
public class StreamReader {

	/**
	 * 读取
	 * @param is
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static byte[] read(InputStream is,int length) throws Exception {

		byte[] result = new byte[length];
		int rNum = 0;
		while(rNum < result.length) {

			byte[] tt = new byte[result.length-rNum];

			int tmp = is.read(tt);

			if(tmp < 0) {
				break;
			}

			System.arraycopy(tt, 0, result, rNum, tmp);

			rNum += tmp;
		}

		return result;
	}

	/**
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static byte[] readSocket(InputStream in) throws Exception {

		LinkedList<StreamBuffer> bufList = new LinkedList<StreamBuffer>();
		int size = 0;
		int retry = 5;

		for(int i = 0;i < 5;i++) {
			if(in.available() <= 0) {
				Thread.sleep((i+1)*100);
			} else {
				break;
			}
		}

		byte[] buf;
		do {
			buf = new byte[128];
			int num = in.read(buf);
			if(-1 == num){
				break;
			}

			size += num;
			bufList.add(new StreamBuffer(buf, num));

			int unanum = 0;
			for(int i = 0; i<retry; i++) {
				int able = in.available();
				if(able <= 0){
					unanum++;
					Thread.sleep((i+1)*100);
				} else {
					break;
				}
			}
			if(unanum >= retry){
				break;
			}

		} while (true);
		
		if(size > 0){
			buf = new byte[size];
			int pos = 0;
			for (ListIterator<StreamBuffer> p = bufList.listIterator(); p.hasNext();) {

				StreamBuffer b = p.next();
				for (int i = 0; i < b.getSize();) {
					buf[pos] = b.getBuffer()[i];
					i++;
					pos++;
				}

			}
		}
		return buf;
	}

	/**
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static byte[] readHttp(InputStream in) throws Exception {

		LinkedList<StreamBuffer> bufList = new LinkedList<StreamBuffer>();
		int size = 0;
		byte[] buf;

		do {
			buf = new byte[128];
			int num = in.read(buf);
			if (num == -1) {
				break;
			}
			size += num;
			bufList.add(new StreamBuffer(buf, num));
		} while (true);

		buf = new byte[size];
		int pos = 0;
		for (ListIterator<StreamBuffer> p = bufList.listIterator(); p.hasNext();) {

			StreamBuffer b = p.next();
			for (int i = 0; i < b.getSize();) {
				buf[pos] = b.getBuffer()[i];
				i++;
				pos++;
			}

		}

		return buf;
	}
}