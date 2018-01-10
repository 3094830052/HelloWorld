package com.hxts.unicorn.modules.resident.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.jarentry.resident.ServiceModuleFactory;
import com.hxts.unicorn.modules.resident.dto.ResidentTempDto;
import com.hxts.unicorn.modules.resident.service.ISrvResidentScore;
import com.hxts.unicorn.modules.resident.service.ISrvResidentTempInfo;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.CurrentUser;
import com.hxts.unicorn.platform.interfaces.DataDictionaryItem;
import com.hxts.unicorn.platform.interfaces.IAppPlugin;
import com.hxts.unicorn.platform.interfaces.IServiceModule;
import com.hxts.unicorn.platform.interfaces.IUnicornFrame;
import com.hxts.unicorn.platform.interfaces.ModuleProperty;
import com.hxts.unicorn.platform.interfaces.basic.IActionLogService;
import com.hxts.unicorn.platform.interfaces.biz.ResidentScoreInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentTemp;
import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/g/resident")
public class ResidentTempController {
	@Autowired
	private ISrvResidentTempInfo srvResidentTempInfo;
	@Autowired
	private ISrvResidentScore srvResidentScore;

	/**
	 * 微信主菜单入口，判断用户是否已绑定，如已绑定则跳转到正常入口，否则跳转到绑定页面
	 * 或者如果绑定信息待审核，跳转到提示页：您的绑定信息在审核中，请等待审核结果
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value="home/*", method = RequestMethod.GET)
	public String weiChatMain(@RequestParam(value="code", required=true) String token, HttpServletRequest request,
			HttpServletResponse response) {
		String uri = request.getRequestURI();
		String reqType = uri.substring(uri.lastIndexOf('/') + 1);
		
//		//访问微信服务器，取得openid，
//		String requestUrl = String.format(
//				"https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
//				ServiceModuleFactory.getFramework().getSetting(IUnicornFrame.WEI_APPID),
//				ServiceModuleFactory.getFramework().getSetting(IUnicornFrame.WEI_SECRET), token);
//		
//		javax.net.ssl.TrustManager[] tm = { new MyX509TrustManager() };
//
//		URL url;
//		JSONObject jsonObject = null;
//		try {
//			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//			sslContext.init(null, tm, new java.security.SecureRandom());
//			// 从上述SSLContext对象中得到SSLSocketFactory对象
//			SSLSocketFactory ssf = sslContext.getSocketFactory();
//			
//			url = new URL(requestUrl);
//			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//			conn.setSSLSocketFactory(ssf);
//			conn.setRequestMethod("GET");
//			InputStream is = conn.getInputStream();
//			InputStreamReader isr = new InputStreamReader(is, "utf-8");
//			StringBuffer sb = new StringBuffer();
//			char[] buf = new char[1024];
//			int size = 0;
//			while ((size = isr.read(buf)) > 0) {
//				sb.append(buf, 0, size);
//			}
//			jsonObject = new JSONObject(sb.toString());
//			// 释放资源
//			isr.close();
//			is.close();
//			conn.disconnect();
//		} catch (MalformedURLException e) {			
//			e.printStackTrace();
//			throw new AppModuleErrorException(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new AppModuleErrorException(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new AppModuleErrorException(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
//		} catch (NoSuchProviderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new AppModuleErrorException(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
//		} catch (KeyManagementException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new AppModuleErrorException(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
//		}
//		
//		String openId = jsonObject.getString("openid");
		int isSub = 1;//jsonObject.getInt("subscribe");		
		String openId="hxts2018";
		if (openId == null || isSub == 0) {
			throw new AppModuleErrorException(HttpServletResponse.SC_FORBIDDEN, "非法的用户访问");
		}
		//然后查找本地绑定表，取得绑定信息
		ResidentScoreInfo residentScoreInfo=srvResidentScore.selectByOpenId(openId);
		int personId =0; //
		int status = -1; //-1:未绑定，0待审核，1审核通过，2审核不通过
		if(residentScoreInfo!=null){
			status=residentScoreInfo.getStatus();
			personId=residentScoreInfo.getResidentBaseId();
		}
		
		switch (status) {
		case -1:
			//存储绑定表:人员id，身份证，电话，openid，状态(待审核)
			//..................
			return "redirect:/s/wechat/personCard.html?token=" + openId;
		case 0:
			return "redirect:/s/wechat/wait.html"; //绑定信息在审核中，请等待审核结果
		
		case 1: //生成会话信息
			ServiceModuleFactory.getFramework().getCurrentSession()
				.setCurrentSysUser(String.valueOf(personId), CurrentUser.USER_GUEST, 120, response);
			
			JSONObject json = new JSONObject();
			IActionLogService logService = (IActionLogService) ServiceModuleFactory.getFramework().getModuleManager()
					.getModule(IActionLogService.APP_ID);
			logService.writeGuestUserLog(IUnicornFrame.ACT_LOGIN[1], json);
			return "redirect:/s/wechat/" +reqType + ".html";
		case 2:
			return "redirect:/s/wechat/personCard.html?token=" + openId + "&idno="+residentScoreInfo.getIdNo()+"&name="+residentScoreInfo.getName()+"&contact="+residentScoreInfo.getContact(); //此处添加保存的绑定信息以便用户修改后再提交
		default:
			return null;
		}		
	}

	/**
	 * <微信页面新增人口基础信息>
	 * 
	 * @param ResidentTemp，isfozuling，file，picBaseStr
	 *            人口信息，国籍，户籍地是否佛祖岭，添加图片
	 * @return
	 */
	@PostMapping("add")
	@ResponseBody
	public int add(ResidentTemp ResidentTemp, boolean isfozuling,
			@RequestParam(value = "picBaseStr", required = false) String picBaseStr,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		System.out.println(ResidentTemp.getContact());
		if (ResidentTemp.getBirthDate() == "" || ResidentTemp.getIdNo() == "" || ResidentTemp.getName() == ""
				|| ResidentTemp.getSex() == "" || ResidentTemp.getContact() == ""
				|| ResidentTemp.getCurrentResidence() == "" || ResidentTemp.getCurrentResidenceAddress() == ""
				|| ResidentTemp.getNationality() == "" || ResidentTemp.getBirthDate() == null
				|| ResidentTemp.getIdNo() == null || ResidentTemp.getName() == null || ResidentTemp.getSex() == null
				|| ResidentTemp.getContact() == null || ResidentTemp.getCurrentResidence() == null
				|| ResidentTemp.getCurrentResidenceAddress() == null || ResidentTemp.getNationality() == null) {
			throw new AppModuleErrorException("信息填写不完整！");
		}

		if (ResidentTemp != null) {
			// 图片剪切、压缩、上传
			if (picBaseStr != null && !picBaseStr.isEmpty()) {
				picBaseStr = picBaseStr.substring(22);// 去除开头data:image/jpg;base64,声明数据协议及类型名称及编码形式
				byte[] bytes = Base64.decodeBase64(picBaseStr);
				InputStream sbs = new ByteArrayInputStream(bytes);
				// 新的图片文件名 = 获取时间戳+"."图片扩展名
				String newFileName = String.valueOf(System.currentTimeMillis());
				int hashcode = Math.abs(newFileName.hashCode());
				String residentPictureUrl = ServiceModuleFactory.getFramework().getSetting("upload.private");
				String imgDir = residentPictureUrl + "resident/idno/" + hashcode % 256 + "/";
				File imgFile = new File(imgDir);
				if (!imgFile.exists()) {// 如果不存在此目录就新建目录
					imgFile.mkdirs();
				}
				String imgPath = imgDir + newFileName;
				try {
					Thumbnails.of(sbs).scale(1f).outputQuality(1.0).outputFormat("jpg").toFile(imgPath);
					// }
				} catch (IOException ioex) {
					ioex.printStackTrace();
				}
				ResidentTemp.setPicture(imgPath + ".jpg");
			}
			if (file != null && file.getSize() > 0) {
				String newFileName = String.valueOf(System.currentTimeMillis());
				int hashcode = Math.abs(newFileName.hashCode());
				String residentPictureUrl = ServiceModuleFactory.getFramework().getSetting("upload.private");
				String imgDir = residentPictureUrl + "resident/idno/" + hashcode % 256 + "/";
				File imgFile = new File(imgDir);
				if (!imgFile.exists()) {// 如果不存在此目录就新建目录
					imgFile.mkdirs();
				}
				String imgPath = imgDir + newFileName;
				try {
					if (file.getSize() > 200 * 1024) {
						Thumbnails.of(file.getInputStream()).scale(1f).outputQuality((200 * 1024f) / file.getSize())
								.outputFormat("jpg").toFile(imgPath);
					} else {
						Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(1.0).outputFormat("jpg")
								.toFile(imgPath);
					}
				} catch (IOException ioex) {
					ioex.printStackTrace();
				}
				ResidentTemp.setPicture(imgPath + ".jpg");
			}
		}
		if (isfozuling = true) {// 若是佛祖岭地区户籍地 420115为武汉江夏区代码
			ResidentTemp.setRegisteredResidence("420115");
		}
		if(ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser()==null){
			throw new AppModuleErrorException("未绑定！");
		}
		int residentBaseId=ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
		String openId=srvResidentScore.selectByResidentBaseId(residentBaseId).getOpenId();
		if(openId==null){
			throw new AppModuleErrorException("未绑定！");
		}
		srvResidentTempInfo.add(ResidentTemp, openId);
		
		return srvResidentTempInfo.selectByIdNo(ResidentTemp.getIdNo(), 0).getResidentBaseId();
	}

	/**
	 * <微信页面新增人口其他信息>
	 * 
	 * @param ResidentTemp
	 * @return
	 */
	@PostMapping("addother")
	@ResponseBody
	public int addother(ResidentTemp ResidentTemp) {
		// +100积分 新增其他信息获得
		if (ResidentTemp != null) {
			srvResidentTempInfo.update(ResidentTemp);

			return ResidentTemp.getResidentBaseId();
		} else
			return 0;
	}

	/**
	 * <微信页面新增房屋信息>
	 * 
	 * @param ResidentTemp
	 * @return
	 */
	@PostMapping("addhouse")
	@ResponseBody
	public int addhouse(@RequestParam(required = false) Integer unitNumber,
			@RequestParam(required = false) Integer floorNumber, @RequestParam(required = false) Integer houseNumber,
			@RequestParam(required = false) String street, @RequestParam(required = false) String community,
			@RequestParam(required = false) String buildingName, @RequestParam(required = false) String idCode,
			@RequestParam(required = false) String idNumber, @RequestParam(required = false) String houseType,
			@RequestParam(required = false) Long residentBaseId) {
		// +100积分 新增房屋信息获得
		return srvResidentTempInfo.addhouse(unitNumber, floorNumber, houseNumber, street, community, buildingName,
				idCode, idNumber, houseType, residentBaseId);
	}

	/**
	 * <微信页面绑定人口基础信息>
	 * 
	 * @param ResidentTemp
	 * @return
	 */
	@PostMapping("binding")
	@ResponseBody
	public int binding(String name, String idNo,String contact,Integer verificationCode,String openId) {
		return srvResidentTempInfo.binding(name, idNo, contact, verificationCode, openId);
	}
	
	/**
	 * <获得微信用户积分>
	 * 
	 * @param ResidentTemp
	 * @return
	 */
	@GetMapping("score")
	@ResponseBody
	public Integer getScore(){
		int residentBaseId=ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
		return srvResidentScore.selectByResidentBaseId(residentBaseId).getScore();
	}
	
	
	/**
	 * <根据网格员状态查询基础人口信息>
	 * 
	 * @param 网格员确认状态status
	 * @return 
	 */
	@GetMapping("list")
	@ResponseBody
	public PageInfo<ResidentTempDto> view(@RequestParam(required = false) Integer status,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		if(ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser()==null){
			throw new AppModuleErrorException("未绑定！");
		}
		int residentBaseId=ServiceModuleFactory.getFramework().getCurrentSession().getCurrentSysUser().getUserId();
		String openId=srvResidentScore.selectByResidentBaseId(residentBaseId).getOpenId();
		if(openId==null){
			throw new AppModuleErrorException("未绑定！");
		}
		PageInfo<ResidentTempDto> r = new PageInfo<ResidentTempDto>(srvResidentTempInfo.getResidentTemp(status,openId));
		return r;
	}

	/**
	 * <网格员根据residentBaseId查询基础人口信息>
	 * 
	 * @param residentBaseId
	 * @return 
	 */
	@GetMapping("view/{residentBaseId}")
	@ResponseBody
	public ResidentTemp view(@PathVariable Integer residentBaseId) {
		if (srvResidentTempInfo.selectByResidentBaseId(residentBaseId).getStatus() == 1) {
			throw new AppModuleErrorException("信息已保存，无法查看！");
		} else
			return srvResidentTempInfo.selectByResidentBaseId(residentBaseId);
	}
	
	

	
}

/**
 * 信任ssl服务器
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年12月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
class MyX509TrustManager implements X509TrustManager {  
	  
    @Override  
    public void checkClientTrusted(X509Certificate[] chain, String authType)  
            throws CertificateException {  
    }  
  
    @Override  
    public void checkServerTrusted(X509Certificate[] chain, String authType)  
            throws CertificateException { 
  
    }  
  
    @Override  
    public X509Certificate[] getAcceptedIssuers() {  
        // TODO Auto-generated method stub  
        return null;  
    }
    
}
