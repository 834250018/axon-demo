/*
package com.ywy.learn.web.util;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import push.AndroidNotification;
import push.PushClient;
import push.android.AndroidCustomizedcast;
import push.ios.IOSCustomizedcast;

import java.util.Map;

*/
/**
 * ve
 * <p>
 * 安卓自定义播
 *
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * iOS自定义播(正式环境)
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * 安卓自定义播
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * iOS自定义播(正式环境)
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * 安卓自定义播
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * iOS自定义播(正式环境)
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * 安卓自定义播
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * iOS自定义播(正式环境)
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * 安卓自定义播
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * iOS自定义播(正式环境)
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * 安卓自定义播
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * iOS自定义播(正式环境)
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * 安卓自定义播
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 * <p>
 * iOS自定义播(正式环境)
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 *//*

@Component
public class UMengUtils {

    private static String umeng_android_appkey;
    private static String umeng_android_app_master_secret;
    private static String umeng_android_type;
    private static String umeng_ios_appkey;
    private static String umeng_ios_app_master_secret;
    private static String umeng_ios_type;
    private static String timestamp = null;

    */
/**
 * 安卓自定义播
 *
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 *//*

    public static void sendAndroidCustomizedcast(String token, String title, String content, Map<String, String> dataMap) throws Exception {
        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(umeng_android_appkey, umeng_android_app_master_secret);
        customizedcast.setAlias(token, umeng_android_type);
        customizedcast.setTicker("安卓自定义播,token=" + token);
        customizedcast.setTitle(title);
        customizedcast.setText(content);
        // 传递单一数据
//        customizedcast.setCustomField(pushType.toString());
        for (String s : dataMap.keySet()) {
            customizedcast.setExtraField(s, dataMap.get(s));
        }
        // 传递多个数据
//        customizedcast.setExtraField("type", pushType.toString());
//        customizedcast.goCustomAfterOpen(pushType.toString());
        customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        customizedcast.setProductionMode();
        new PushClient().send(customizedcast);
    }

    */
/**
 * iOS自定义播(正式环境)
 *
 * @param token    用户id
 * @param title    消息标题
 * @param content  消息内容
 * @param pushType 推送消息类型,告知app跳转目标
 * @param dataMap  数据map
 * @throws Exception
 *//*

    public static void sendIOSCustomizedcast(String token, String title, String content, Integer badge, Map<String, String> dataMap) throws Exception {
        IOSCustomizedcast customizedcast = new IOSCustomizedcast(umeng_ios_appkey, umeng_ios_app_master_secret);
        customizedcast.setAlias(token, umeng_ios_type);
        JSONObject alertJson = new JSONObject();
        alertJson.put("title", title);
        alertJson.put("body", content);
        customizedcast.setPredefinedKeyValue("alert", alertJson);
        // 如果没有二级标题,可以使用下面这种写法
//        customizedcast.setAlert("标题");
        customizedcast.setBadge(badge);
        customizedcast.setSound("default");
        // 传递多个数据
//        customizedcast.setCustomizedField("type", pushType.toString());
        for (String s : dataMap.keySet()) {
            customizedcast.setCustomizedField(s, dataMap.get(s));
        }
        customizedcast.setProductionMode();
        new PushClient().send(customizedcast);
    }

    @Value("${umeng.android.appkey}")
    public void setUmeng_android_appkey(String umeng_android_appkey) {
        UMengUtils.umeng_android_appkey = umeng_android_appkey;
    }

    @Value("${umeng.android.app-master-secret}")
    public void setUmeng_android_app_master_secret(String umeng_android_app_master_secret) {
        UMengUtils.umeng_android_app_master_secret = umeng_android_app_master_secret;
    }

    @Value("${umeng.android.type}")
    public void setUmeng_android_type(String umeng_android_type) {
        UMengUtils.umeng_android_type = umeng_android_type;
    }

    @Value("${umeng.ios.appkey}")
    public void setUmeng_ios_appkey(String umeng_ios_appkey) {
        UMengUtils.umeng_ios_appkey = umeng_ios_appkey;
    }

    @Value("${umeng.ios.app-master-secret}")
    public void setUmeng_ios_app_master_secret(String umeng_ios_app_master_secret) {
        UMengUtils.umeng_ios_app_master_secret = umeng_ios_app_master_secret;
    }

    @Value("${umeng.ios.type}")
    public void setUmeng_ios_type(String umeng_ios_type) {
        UMengUtils.umeng_ios_type = umeng_ios_type;
    }

}
*/
