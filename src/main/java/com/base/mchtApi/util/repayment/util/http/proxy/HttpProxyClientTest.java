package com.base.mchtApi.util.repayment.util.http.proxy;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HttpProxyClientTest {
	public static void main(String[] args) throws UnsupportedEncodingException{
		//代理地址1;代理地址2;......|目标地址1;目标地址2......|权重1;权重2;权重3;权重4......|连接超时(单位毫秒)|读取等待超时(单位毫秒)|最大等待超时次数(超过后会暂停N个周期)|计算等待超时时间周期|暂停周期|备份次数
		String addresses = "|http://218.4.234.150:9600/merchant/numberPaid.action|1|5000|10000|5|20000|60000|3";
		byte[] data = "merno=168885&time=20170212154230&totalAmount=10&num=2&batchNo=1487820407717&content=开户名1|ABC|6226131223214231|1|5.0|20170210000001|302|备注#开户名2|ICBC|6226131223214541|2|5.0|20170210000002|303|备注&remark=批次备注&signature=h7Ny/LovIq7Z6i9gXRBkJt9fDjurS4Wg/sYFby/YiZ8rGjJrqcQsSbqrMD4vs8BJ8O29dC1urKjp9EoJBAx0GAHsFfWmBx5FwgZZqfmwAGOnU/54FvDqaNJarSM852RdnVcJwTCjgYrjbjxmH5IuyFF+YtH240r5rLI8htl9KYu0dmTA+rXbAJsSCTx+/mP6Gd9Wo3NQ/sFMxnZr2xnE+1BQWsMwRejpW0fiUMbnOouDuSHRqfJ0mHr6AU1XLG6NISlIORWADmg8iYAV4rlNONU3ZkOYIjWo707qtivKpsZdXKtFDmSbJVFpInkrcCsBLMATHPsxJSHE/OiNYqoOKQ==".getBytes("UTF-8");

		HttpResult result = HttpProxyClient.httpPost(addresses, data, null);
		byte[] resp = (byte[])result.getData();
		System.out.println(new String(resp, "UTF-8"));
	}
	public static void main1(String[] args) throws UnsupportedEncodingException{
		//代理地址1;代理地址2;......|目标地址1;目标地址2......|权重1;权重2;权重3;权重4......|连接超时(单位毫秒)|读取等待超时(单位毫秒)|最大等待超时次数(超过后会暂停N个周期)|计算等待超时时间周期|暂停周期|备份次数
		String addresses = "|https://www.95epay.cn/sslpayment|1|5000|10000|5|20000|60000|3";
		byte[] data = "MerNo=200147&PaymentType=CMB&Amount=1.00&MerRemark=102700000025&NotifyURL=http://www.soeasy-pay.com:8088/payment/epay95ResUrl?type=asyn&MD5info=EDE516A7736C5246CB2D30D286CB96AA&products=贸易|服务|留学费|&ReturnURL=http://response.zuwen.top:8088/payment/epay95ResUrl?type=syn&PayType=CSPAY&BillNo=032017032100002481".getBytes();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Referer", "http://payment.zuwen.top");
		HttpResult result = HttpProxyClient.httpPost(addresses, data, headers);
		byte[] resp = (byte[])result.getData();
		System.out.println(new String(resp, "UTF-8"));
	}
}
