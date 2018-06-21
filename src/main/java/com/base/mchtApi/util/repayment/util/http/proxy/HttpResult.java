package com.base.mchtApi.util.repayment.util.http.proxy;

import java.net.HttpURLConnection;

import com.base.mchtApi.util.repayment.util.util.Strings;


public class HttpResult {
	private int httpStatus = HttpURLConnection.HTTP_GATEWAY_TIMEOUT;
	private String httpMessage = "";
	private String lastRequestUrl = "";
	private Object data;
	private String trace = "";
	
	public String getLastRequestUrl() {
		return lastRequestUrl;
	}
	public void setLastRequestUrl(String lastRequestUrl) {
		this.lastRequestUrl = lastRequestUrl;
	}
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getHttpMessage() {
		return httpMessage;
	}
	public void setHttpMessage(String httpMessage) {
		this.httpMessage = httpMessage;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getTrace() {
		return trace;
	}
	public void setTrace(String trace) {
		this.trace = trace;
	}
	public void addTrace(String trace) {
		if(!Strings.isNullOrEmpty(trace))
			this.trace += trace + "\n";
	}
	
	@Override
	public String toString(){
		String str = httpStatus + " " + httpMessage + "\n";
		if(null != data){
			if(data instanceof byte[]){
				str += new String((byte[])data) + "\n";
			} else {
				str += data + "\n";
			}
		}
		str += trace;
		return str;
	}
}
