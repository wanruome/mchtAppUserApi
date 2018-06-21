package com.base.mchtApi.util.repayment.util.http.proxy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * 
 * @author luoyunqiu
 * @time 2016-09-12 20:54
 */
public class HttpProxyClient {
	
	private static Map<String, HttpProxyRouter> routers = Collections.synchronizedMap(new HashMap<String, HttpProxyRouter>());
	
	private static HttpProxyRouter getRouter(String clientName){
		synchronized(routers){
			HttpProxyRouter router = routers.get(clientName);
			if(null == router){
				router = new HttpProxyRouter();
				routers.put(clientName, router);
			} else {
				router.setLastUserTime(System.currentTimeMillis());
			}
			clearUnUseRouter();
			return router;
		}
	}
	private static void clearUnUseRouter(){
		Iterator<Map.Entry<String, HttpProxyRouter>> iterator = routers.entrySet().iterator();
		long max = 1000L * 60 * 60;
		while(iterator.hasNext()){
			Map.Entry<String, HttpProxyRouter> entity = iterator.next();
			long dis = System.currentTimeMillis() - entity.getValue().getLastUserTime();
			if(dis > max){
				iterator.remove();
			}
		}
	}
	/**
	 * 
	 * @param addresses 地址格式：
	 *  代理地址1;代理地址2;......|目标地址1;目标地址2......|权重1;权重2;权重3;权重4......|连接超时(单位毫秒)|读取等待超时(单位毫秒)|最大等待超时次数(超过后会暂停N个周期)|计算等待超时时间周期|暂停周期
	 *  权重1=代理地址1--目标地址1
		权重2=代理地址1--目标地址2
		权重3=代理地址2--目标地址1
		权重4=代理地址2--目标地址2
		如果不配置权重填空，则默认都为1:1:1:1......
	 * @param data 数据
	 * @param headers 特殊要求的http请求头
	 * @return
	 */
	public static HttpResult httpPost(String addresses, byte[] data, Map<String, String> headers){
		return getRouter(addresses).httpPost(addresses, data, headers);
	}
	public static HttpResult httpPost(String method, String addresses, String extraparams, byte[] data, Map<String, String> headers){
		return getRouter(addresses).httpPost(addresses, extraparams, data, headers);
	}
	
	public static HttpResult httpGet(String addresses, String data, Map<String, String> headers){
		return getRouter(addresses).httpGet(addresses, data, headers);
	}
}