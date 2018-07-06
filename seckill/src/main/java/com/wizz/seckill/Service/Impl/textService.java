package com.wizz.seckill.Service.Impl;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;



/**
 * 基于http client 4.2.3叮咚云相关接口
 *
 * @author liangyang
 * @date 2015年5月5日 下午11:47:45
 * @version 1.0
 */
public class textService {

    // 查账户信息的http地址111
    private static String URL_GET_USER_INFO = "https://api.dingdongcloud.com/v1/user/get";

    // 单条短信发送url
    private static String URL_SEND_SINGLE = "https://api.dingdongcloud.com/v1/sms/notice/send";

    // 模版单条发送url
    private static String URL_SEND_TPL_SINGLE = "https://api.dingdongcloud.com/v1/sms/tpl_single_send";

    /**
     * 发送单条短信
     *
     * @param apikey
     *            apikey
     * @param mobile
     *            手机号码(唯一，不许多个)
     * @param content
     *            短信发送内容（必须经过utf-8格式编码)
     * @return json格式字符串
     */
    public static String sendSingle(String apikey, String mobile, String content) {

        if (!content.isEmpty()) {
            try {
                content = URLEncoder.encode(content, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        NameValuePair[] data = { new NameValuePair("apikey", apikey),

                new NameValuePair("mobile", mobile),

                new NameValuePair("content", content) };

        return doPost(URL_SEND_SINGLE, data);
    }

    /**
     * 基于HttpClient的post函数
     * PH
     * @param url
     *            提交的URL
     *
     * @param data
     *            提交NameValuePair参数
     * @return 提交响应
     */
    private static String doPost(String url, NameValuePair[] data) {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        method.setRequestBody(data);
        client.getParams().setConnectionManagerTimeout(10000);
        try {
            client.executeMethod(method);
            return method.getResponseBodyAsString();
        } catch (Exception e) {
            return e.toString();
        }
    }
}
