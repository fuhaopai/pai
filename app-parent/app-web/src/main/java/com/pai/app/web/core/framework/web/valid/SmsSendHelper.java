package com.pai.app.web.core.framework.web.valid;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HttpContext;

import com.pai.app.web.core.framework.util.Base64;
import com.pai.base.core.util.ProxyUtil;
import com.pai.base.core.util.ConfigHelper;
/**
 * 短信发送帮助类
 * @author Administrator
 *
 */
public class SmsSendHelper {

	/**
	 * 螺丝帽发送短信（备用）
	 * @param mobile
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation"})
	public static String sendMsgByNut(String mobile, String msg) throws Exception {

        DefaultHttpClient client = new DefaultHttpClient();

        client.addRequestInterceptor(new HttpRequestInterceptor() {
            @Override
            public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
                request.addHeader("Accept-Encoding", "gzip");
                request.addHeader("Authorization", "Basic " + Base64.encode("api:key-230179949282315615cabd2e59ccf048".getBytes("utf-8")));
            }
        });

        client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
        client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 6000);

        HttpPost request = new HttpPost("https://sms-api.luosimao.com/v1/send.json");

        ByteArrayOutputStream bos = null;
        InputStream bis = null;
        byte[] buf = new byte[10240];

        String content = null;
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mobile", mobile));
            params.add(new BasicNameValuePair("message", msg));
            request.setEntity(new UrlEncodedFormEntity(params, "utf-8"));


            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                bis = response.getEntity().getContent();
                Header[] gzip = response.getHeaders("Content-Encoding");

                bos = new ByteArrayOutputStream();
                int count;
                while ((count = bis.read(buf)) != -1) {
                    bos.write(buf, 0, count);
                }
                bis.close();

                if (gzip.length > 0 && gzip[0].getValue().equalsIgnoreCase("gzip")) {
                    GZIPInputStream gzin = new GZIPInputStream(new ByteArrayInputStream(bos.toByteArray()));
                    StringBuffer sb = new StringBuffer();
                    int size;
                    while ((size = gzin.read(buf)) != -1) {
                        sb.append(new String(buf, 0, size, "utf-8"));
                    }
                    gzin.close();
                    bos.close();

                    content = sb.toString();
                } else {
                    content = bos.toString();
                }

                System.out.println(content);
            } else {
                System.out.println("error code is " + response.getStatusLine().getStatusCode());
            }
            return "[螺丝帽]"+content;

        } finally {
            if (bis != null) {
                try {
                    bis.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                }
            }
        }
    }

	/**
	 * 梦网 短信平台  单独发
	 * @param mobile
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public static String sendMsgByCat(String mobile, String msg) throws Exception {
		String result = "";
		String host = ConfigHelper.getInstance().getParamValue("sms.host");
		String userId = ConfigHelper.getInstance().getParamValue("sms.userId");
		String pwd = ConfigHelper.getInstance().getParamValue("sms.password");
		if(host == null || userId == null || pwd == null){
			result = "获取不到配置文件参数";
		}else{
			String content = "*|"+mobile+"|"+Base64.encode(msg.getBytes("GBK"))+"|SvrType|P1|P2|P3|P4|0|01234|0|0|1";
			result = ProxyUtil.httpRequest(host+"?userId="+userId+"&password="+pwd+"&multixmt="+content, "GET", null);
		}
		
		return "[梦网]"+result;
	}
	
	/**
	 * 梦网 短信平台  群发
	 * @param mobile
	 * @param tempMsg
	 * @return
	 * @throws Exception
	 */
	public static String sendMsgByCatMultiple(String[] mobile, String[] tempMsg) throws Exception {
		String result = "";
		String host = ConfigHelper.getInstance().getParamValue("sms.host");
		String userId = ConfigHelper.getInstance().getParamValue("sms.userId");
		String pwd = ConfigHelper.getInstance().getParamValue("sms.password");
		if(host == null || userId == null || pwd == null){
			result = "获取不到配置文件参数";
		}else{
			if (tempMsg.length > 0 && mobile.length > 0) {
				String msg = "";
				for (int i = 0; i < tempMsg.length; i++) {														
					msg += "*|"+mobile[i]+"|"+Base64.encode(tempMsg[i].getBytes("GBK"))+"|SvrType|||||||||1,";
				}
				msg = msg.substring(0, msg.length()-1);
				result = ProxyUtil.httpRequest(host, "POST", "userId="+userId+"&password="+pwd+"&multixmt="+msg);
			}else {
				result = "没有可发送手机或没有可发送信息";
			}
		}
		
		return "[梦网]"+result;
	}
	
	/**
	 * 玄武 短信平台  单独发
	 * @param mobile
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public static String sendMsgByXW(String mobile, String msg, String fromType) throws Exception{
		String result = "";
		String username = "";
		String password = "";
		try {
			
			String host = ConfigHelper.getInstance().getParamValue("xw.sms.host");
			if("pcmall".equals(fromType)){
				//商城PC
				username = ConfigHelper.getInstance().getParamValue("xw.sms.pcmall.username");
				password = ConfigHelper.getInstance().getParamValue("xw.sms.pcmall.password");
			}
			
			result = ProxyUtil.httpRequest(host+"?username="+username+"&password="+password+"&to="+mobile+"&text="+URLEncoder.encode(msg, "GB2312")+"&subid=&msgtype=4", "GET", null);
		} catch (Exception e) {
			result = e.getMessage();
		}
		
		return "[玄武]"+result;
	}
	
	/**
	 * 玄武 短信平台  群发
	 * @param mobile
	 * @param tempMsg
	 * @return
	 * @throws Exception
	 */
	/*public static String sendMsgByXWMultiple(String[] mobile, String[] tempMsg, String fromType) throws Exception {
		String result = "";
		List<MessageData> msgs = new ArrayList<MessageData>();
		String username = "";
		String password = "";
		String host = ConfigHelper.getInstance().getParamValue("xw.sms.host");
		String send_port = ConfigHelper.getInstance().getParamValue("xw.sms.send.port");
		String report_port = ConfigHelper.getInstance().getParamValue("xw.sms.report.port");
		if("skg".equals(fromType)){
			//SKG官方商城
			username = ConfigHelper.getInstance().getParamValue("xw.sms.skg.username");
			password = ConfigHelper.getInstance().getParamValue("xw.sms.skg.password");
		}else if("tttt".equals(fromType)){
			//她他头条验证码
			username = ConfigHelper.getInstance().getParamValue("xw.sms.tttt.username");
			password = ConfigHelper.getInstance().getParamValue("xw.sms.tttt.password");
		}else if("tttt1".equals(fromType)){
			//她他头条手机绑定修改
			username = ConfigHelper.getInstance().getParamValue("xw.sms.tttt1.username");
			password = ConfigHelper.getInstance().getParamValue("xw.sms.tttt1.password");
		}else if("shwsh".equals(fromType)){
			//S海外生活社
			username = ConfigHelper.getInstance().getParamValue("xw.sms.shwsh.username");
			password = ConfigHelper.getInstance().getParamValue("xw.sms.shwsh.password");
		}else{
			//默认SKG官方商城
			username = ConfigHelper.getInstance().getParamValue("xw.sms.skg.username");
			password = ConfigHelper.getInstance().getParamValue("xw.sms.skg.password");
		}
		
		if(username == null || password == null || host == null ||
				send_port == null || report_port == null){
			result = "获取不到配置文件参数";
		}else{
			if (tempMsg.length > 0 && mobile.length > 0) {
				try {
					MTPack pack = new MTPack();
					pack.setBatchID(UUID.randomUUID());
					pack.setBatchName("");
					pack.setMsgType(MTPack.MsgType.SMS);
					pack.setBizType(0);
					pack.setDistinctFlag(false);
					pack.setSendType(MTPack.SendType.MASS);
					for (int i = 0; i < tempMsg.length; i++) {														
						MessageData messageData = new MessageData(mobile[i], tempMsg[i]);
						msgs.add(messageData);
					}
					pack.setMsgs(msgs);
					Account ac = new Account(username, password);
					PostMsg pm = new PostMsg();
					pm.getCmHost().setHost(host, new Integer(send_port));//设置网关的IP和port，用于发送信息
					pm.getWsHost().setHost(host, new Integer(report_port));//设置网关的IP和port，用于获取账号信息、上行、状态报告等等
					GsmsResponse resp = pm.post(ac, pack);
					result = String.valueOf(resp.getResult());
				} catch (Exception e) {
					result = e.getMessage();
				}
			}else {
				result = "没有可发送手机或没有可发送信息";
			}
		}
		
		return "[玄武]"+result;
	}*/
	
	public static void main(String[] args) throws Exception {
		String[] mobile = {"13533435610"};
		String[] tempMsg = {"13533435610"};
//		String result = sendMsgByXWMultiple(mobile, tempMsg, "");
//		System.out.println(result);
	}
	
}
