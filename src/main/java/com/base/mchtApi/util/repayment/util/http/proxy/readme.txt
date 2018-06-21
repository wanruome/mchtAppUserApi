http请求proxy或者直接访问目标地址使用说明：
1，统一访问出口HttpProxyClient，有三个方法：
1.1， POST提交数据 public static HttpResult httpPost(String addresses, byte[] data, Map<String, String> headers)
addresses 见3
data 请求数据
headers 特殊要求的http请求头

1.2， POST提交数据 public static HttpResult httpPost(String method, String addresses, String extraparams, byte[] data, Map<String, String> headers)
extraparams 如果目标地址需要附带数据，比如extraparams="a=b&c=d"，这两个附带数据 http://目标url?a=b&c=d
其余和1.1相同

1.3  GET提交数据 public static HttpResult httpGet(String addresses, String data, Map<String, String> headers)
addresses 同1.1
data get方式提交数据，比如data="a=b&c=d"，get方式访问 http://目标url?a=b&c=d
headers 同1.1

2，返回对象HttpResult说明：
httpStatus http状态码
httpMessage 状态码描述
data  如果状态码为200，则返回结果数据
trace 多个地址发送轨迹

3，addresses 地址格式：
代理地址P1;代理地址P2;......|目标地址M1;目标地址M2......|权重W1;权重W2;权重W3;权重W4......|连接超时(单位毫秒)C|读取等待超时(单位毫秒)R|最大等待超时次数(超过后会暂停N个周期)MRT|计算等待超时时间周期(单位毫秒)CR|暂停周期CS(单位毫秒)
权重W1=代理地址P1--目标地址M1
权重W2=代理地址P1--目标地址M2
权重W3=代理地址P2--目标地址M1
权重W4=代理地址P2--目标地址M2
如果不配置权重填空，则默认都为1:1:1:1......
3.1，代理地址格式：ip:port，测试环境 10.123.1.11:9899;10.123.1.57:9899；
3.2，目标地址，http://目标地址，如果不经过中转直接出去，可以将代理地址填空；
3.3，权重说明，根据权重配置代理地址-目标地址的占比权重；
3.4，在计算等待超时次数周期CR内，如果等待次数超过MRT，则停止调用此“代理地址-目标地址，休息了暂停周期CS后如果再出现超时则在休息2*CS以此类推休息N*CS；
3.5，如果出现连接超时或者接口地址以此，暂停调用，规则和3.4一致；
3.6，如果没有可用的地址，则会使用最长未被使用的地址。
