package com.gyz.javasamples;


import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by gyzboy on 2017/7/14.
 */

public class Robot {

    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=05832f053c91e20bf894c12312fe7264ca8f0a1bf88dcb84d0127ffaf91087f3";

    public static void main(String args[]) throws Exception{

        HttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");

        String textMsg = "{\n"
            + "    \"msgtype\": \"text\", \n"
            + "    \"text\": {\n"
            + "        \"content\": \"吃饭吗！\"\n"
            + "    }, \n"
            + "    \"at\": {\n"
            //+ "        \"atMobiles\": [\n"
            //+ "            \"18500976686\", \n"//超
            //+ "            \"18612109226\"\n"//达
            //+ "            \"18210189485\"\n"//肥
            //+ "            \"15010510212\"\n"//明
            //+ "            \"18686489806\"\n"//岩
            //+ "        ], \n"
            + "        \"isAtAll\": true\n"
            + "    }\n"
            + "}";
        String textCardMsg = "{\n"
            + "    \"feedCard\": {\n"
            + "        \"links\": [\n"
            + "            {\n"
            + "                \"title\": \"时代的火车向前开\", \n"
            + "                \"messageURL\": \"https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI\", \n"
            + "                \"picURL\": \"https://www.dingtalk.com/\"\n"
            + "            },\n"
            + "            {\n"
            + "                \"title\": \"时代的火车向前开2\", \n"
            + "                \"messageURL\": \"https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI\", \n"
            + "                \"picURL\": \"https://www.dingtalk.com/\"\n"
            + "            }\n"
            + "        ]\n"
            + "    }, \n"
            + "    \"msgtype\": \"feedCard\"\n"
            + "}";
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);

        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String result= EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }
    }
}

