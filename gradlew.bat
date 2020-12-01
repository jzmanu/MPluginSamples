package com.amt.utils.adv;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.text.TextUtils;
import com.amt.amtdata.AmtDataManager;
import com.amt.amtdata.IPTVData;
import com.amt.utils.ALOG;
import com.amt.utils.NetUtils.HttpUtils;
import com.amt.utils.NetUtils.NetCallback;
import com.amt.utils.Security;

/**
 * lyn 下载广告，上报功能(移动规范)
 * @author Administrator
 *
 */
public class ADLogoManageCMCC {

	ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(3);
	String url = AmtDataManager.getString("ADPlatformUrl", "");// 请求广告url
	String StartPIC2 = "StartPIC2";// 开机LOGO
	String AppLaunchPIC2 = "AppLaunchPIC2";// 开机图片-视频
	String AuthenPIC2 = "AuthenPIC2";// 认证图片
	String userid = AmtDataManager.getString(IPTVData.IPTV_Account,"");// iptv账号
	String terminaltype = android.os.Build.MODEL;// 标识机顶盒的类型"B860AV2.1";
	String terminalversion ="";//SystemProperties.get("persist.sys.versioninfo");//"V81011351.0055";
	long time = 5000;
	final static String SavePath = "/data/local";//下载广告图片等保存目录
	Map<String, String> mapRequest=null;
	String showsuccessurl="/mad_interface/rest/startuptv/showsuccess/submit";//开机广告成功展示通知接口
	String downloadsuccessUrl="/mad_interface/rest/startuptv/downloadsuccess/submit";//开机广告成功下载通知接口
	String submiturl=HttpUtils.getIP(url);
	public ADLogoManageCMCC() {
		ALOG.debug("ADLogoManageCMCC");
		if(submiturl.indexOf("http://")>-1){
			showsuccessurl=submiturl+showsuccessurl;
			downloadsuccessUrl=submiturl+downloadsuccessUrl;
		}
		newScheduledThreadPool.schedule(StartPIC2Task, time,TimeUnit.MILLISECONDS);
		newScheduledThreadPool.schedule(AppLa