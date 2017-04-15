package com.test.youzan;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSON;
import com.youzan.open.sdk.client.auth.Sign;
import com.youzan.open.sdk.client.core.DefaultKDTClient;
import com.youzan.open.sdk.client.core.KDTClient;
import com.youzan.open.sdk.gen.v1_0_0.api.KdtItemAdd;
import com.youzan.open.sdk.gen.v1_0_0.api.KdtItemUpdate;
import com.youzan.open.sdk.gen.v1_0_0.api.KdtItemsCustomGet;
import com.youzan.open.sdk.gen.v1_0_0.api.KdtTradesSoldGet;
import com.youzan.open.sdk.gen.v1_0_0.model.KdtItemAddParams;
import com.youzan.open.sdk.gen.v1_0_0.model.KdtItemAddResult;
import com.youzan.open.sdk.gen.v1_0_0.model.KdtItemUpdateParams;
import com.youzan.open.sdk.gen.v1_0_0.model.KdtItemUpdateResult;
import com.youzan.open.sdk.gen.v1_0_0.model.KdtItemsCustomGetParams;
import com.youzan.open.sdk.gen.v1_0_0.model.KdtItemsCustomGetResult;
import com.youzan.open.sdk.gen.v1_0_0.model.KdtItemsInventoryGetParams;
import com.youzan.open.sdk.gen.v1_0_0.model.KdtTradesSoldGetParams;
import com.youzan.open.sdk.gen.v1_0_0.model.KdtTradesSoldGetResult;
import com.youzan.open.sdk.model.ByteWrapper;
import com.zis.shiro.test;
import com.zis.youzan.response.KdtItemsInventoryGetNew;
import com.zis.youzan.response.KdtItemsInventoryGetResultNew;
import com.zis.youzan.response.KdtTradesSoldGetNew;
import com.zis.youzan.response.KdtTradesSoldGetResultNew;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(value = { "classpath:spring/spring.xml" })
//@TransactionConfiguration(defaultRollback = true, transactionManager = "TransactionConfiguration")
public class Test {
	private static final String APP_ID = "2371a1f04e67552040"; // 这里换成你的app_id
	private static final String APP_SECRET = "f9a4ae815439fb683a94b6dfe8b60504"; // 这里换成你的app_secret

	static String sss = "ErrorResponse{code='50000', msg='商品不存在'}";

	public static void main(String[] args) {
//		kdt.trades.sold.get
		Test t = new Test();
//		t.add("http://img3.youlu.net/pic/book/l/9787531329831072F77C.jpg");
		// get();
		// try {
		// update();
		// } catch (Exception e) {
		// System.out.println(e.getMessage().equals(sss));
		// }
		// get1();
		t.soldGet();
//		t.InventoryGet();
	}
	
	private void soldGet(){
		KDTClient client = new DefaultKDTClient(getSign());
		KdtTradesSoldGetParams params = new KdtTradesSoldGetParams();
		params.setUseHasNext(true);
		params.setPageSize(1L);
		params.setPageNo(1L);
		KdtTradesSoldGetNew get = new KdtTradesSoldGetNew();
		get.setAPIParams(params);
		KdtTradesSoldGetResultNew result = null;
		String json = null;
		try {
			result = client.invoke(get);
//			json = client.execute(get);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result.getHasNext());
		System.out.println(result.getTotalResults());
		System.out.println(JSON.toJSON(result.getTrades()));
//		System.out.println(json);
	}

	private void add(String url) {
		KDTClient client = new DefaultKDTClient(getSign());
		KdtItemAddParams kdtItemAddParams = new KdtItemAddParams();
		kdtItemAddParams.setTitle("二手岛-I5land郭敬明春风文艺出版社9787531329831");
		kdtItemAddParams
				.setDesc("<b> 详情 <b/><p/><p/><p/>图书名称:岛-I5land<p/>ISBN:9787531329831<p/>作者:郭敬明<p/>出版时间:2006年05月<p/>版次:第一版<p/>出版社:春风文艺出版社<p/>原价:20.0<p/><b> 概要 <b/><p/><p/><p/>再比如我家原本住在一楼,带有一个小小的庭院。庭院在父王大人的改<br />造之下,鸟语花香,还有一个颇有深度的鱼池,供蚊子滋生。父王大人在鱼<br />池里养了许多锦鲤,买来时只是甚小的鱼苗,一养便是七八年,这些红白的<br />鲤鱼都成精一般,每条都超过尺长,躲在鱼池底,不轻易出现,就算不喂也<br />不会轻易死去。在父王大人陪我读书,迁家之前,父王大人将很多鱼儿都放<br />生在护城河里。可是还有几条,不知被楼上的邻居扔了什么在鱼池里给弄死<br />了,父王大人很是心疼。不过这些都是题外话了,回到正题上来。那时候鱼<br />还小,邻家养了只虎斑花纹的小猫,常常光顾我家,用爪子在鱼池里捞啊捞<br />的偷鱼吃。一日在作案现场被父王大人发现,但是小猫动作十分敏捷,很难<br />抓住。不过父王大人可不会轻易就放过这个罪犯,他首先细心观察了几天小<br />猫的行动路线,发现它总是走一条作案路线。于是,父王大人在小猫的必经<br />之路上放置了一块自制钉子板(就是一块板上钉满了钉子的那种,HO—H0,)<br />。不过没想到,小猫在路上发现了这块诡异的板就没走原路,绕了一圈,又<br />想偷鱼。可惜父王大人本领太大,在小猫想要偷鱼之际怒喝一声,小猫吓得<br />急忙逃窜,匆忙之际<p/><b> 目录 <b/><p/><p/><p/>影像<br /><br />时差<br />二重身<br />魔法快转<br />To be by your side<br />冰是睡着的水<br />远路云<br />晴朗<br />小王子<br />重拾<br />日子淡淡风吹<br /><br />长篇连载<br />绝杀<br />尘埃星球<br />N.世界");
		kdtItemAddParams.setPrice(8.0f);
		kdtItemAddParams.setQuantity("1");
		kdtItemAddParams.setPostFee(10f);
		kdtItemAddParams.setOuterId("9787531329831-3");
		kdtItemAddParams.setIsDisplay(0L);
		kdtItemAddParams.setDeliveryTemplateId((long) 379054);
		kdtItemAddParams.setTemplateId("45892742");
		ByteWrapper[] byteWrappers = new ByteWrapper[1];
		// 文件被包装成ByteWrapper
		ByteWrapper byteWrapper = new ByteWrapper("11102030333233-111page.jpg", getInputStream(url));
		byteWrappers[0] = byteWrapper;
		kdtItemAddParams.setImages(byteWrappers);
		KdtItemAdd kdtItemAdd = new KdtItemAdd();
		kdtItemAdd.setAPIParams(kdtItemAddParams);
		KdtItemAddResult result = null;
		try {
			result = client.invoke(kdtItemAdd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result.getItem().getDesc());
		System.out.println(result.getItem().getNumIid());
	}

	private static void update() {
		KDTClient client = new DefaultKDTClient(getSign()); // new Sign(appKey,
															// appSecret)
		KdtItemUpdateParams kdtItemUpdateParams = new KdtItemUpdateParams();
		KdtItemUpdate kdtItemUpdate = new KdtItemUpdate();
		kdtItemUpdate.setAPIParams(kdtItemUpdateParams);
		// kdtItemUpdateParams.setAutoListingTime(0L);
		kdtItemUpdateParams.setQuantity("12");
		kdtItemUpdateParams.setNumIid(11111111111111111L);

		KdtItemUpdateResult result = client.invoke(kdtItemUpdate);
		System.out.println(result.getItem().getTitle());
		System.out.println(result.getItem().getIsListing());
	}

	private static void get() {
		KDTClient client = new DefaultKDTClient(getSign()); // new Sign(appKey,
															// appSecret)
		KdtItemsCustomGetParams kdtItemCustomGetParams = new KdtItemsCustomGetParams();
		KdtItemsCustomGet kdtItemCustomGet = new KdtItemsCustomGet();
		kdtItemCustomGet.setAPIParams(kdtItemCustomGetParams);

		kdtItemCustomGetParams.setOuterId("1111-1111");
		// kdtItemCustomGetParams.setFields("title,desc");

		KdtItemsCustomGetResult result = client.invoke(kdtItemCustomGet);
		System.out.println(JSON.toJSON(result.getItems()[0]));
	}

	private void InventoryGet() {
		KDTClient client = new DefaultKDTClient(getSign()); // new Sign(appKey,
															// appSecret)
		KdtItemsInventoryGetParams kdtItemsInventoryGetParams = new KdtItemsInventoryGetParams();
		KdtItemsInventoryGetNew kdtItemsInventoryGet = new KdtItemsInventoryGetNew();
		kdtItemsInventoryGet.setAPIParams(kdtItemsInventoryGetParams);

		kdtItemsInventoryGetParams.setPageSize(10L);
		kdtItemsInventoryGetParams.setPageNo(1L);
		kdtItemsInventoryGetParams.setFields("title");
		kdtItemsInventoryGetParams.setOrderBy("desc");
		kdtItemsInventoryGetParams.setBanner("sold_out");
		KdtItemsInventoryGetResultNew result = client.invoke(kdtItemsInventoryGet);
		System.out.println(JSON.toJSON(result.getItems()));
		System.out.println(result.getTotalResults());
	}

	private static Sign getSign() {
		return new Sign(APP_ID, APP_SECRET);
	}

	private static InputStream getInputStream(String urlStr) {
		InputStream is = null;
		try {
			// 构造URL
			URL url = new URL(urlStr);
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
			// 输入流
			is = con.getInputStream();

			// 转换为byte 数组
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}
}
