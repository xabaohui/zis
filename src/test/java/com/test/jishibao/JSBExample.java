package com.test.jishibao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.jsb.rest.client.JSBClient;
import com.jsb.rest.comm.JSBRestException;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Product;
import com.taobao.api.domain.Refund;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.AreasGetRequest;
import com.taobao.api.request.ItemAddRequest;
import com.taobao.api.request.ItemQuantityUpdateRequest;
import com.taobao.api.request.ItemSellerGetRequest;
import com.taobao.api.request.ItemUpdateListingRequest;
import com.taobao.api.request.ItemcatsAuthorizeGetRequest;
import com.taobao.api.request.ItemcatsGetRequest;
import com.taobao.api.request.ItemsCustomGetRequest;
import com.taobao.api.request.LogisticsCompaniesGetRequest;
import com.taobao.api.request.LogisticsDummySendRequest;
import com.taobao.api.request.LogisticsOrdersDetailGetRequest;
import com.taobao.api.request.ProductsSearchRequest;
import com.taobao.api.request.RefundsApplyGetRequest;
import com.taobao.api.request.RefundsReceiveGetRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.request.TradeMemoAddRequest;
import com.taobao.api.request.TradeMemoUpdateRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.request.UserSellerGetRequest;
import com.taobao.api.response.AreasGetResponse;
import com.taobao.api.response.ItemAddResponse;
import com.taobao.api.response.ItemQuantityUpdateResponse;
import com.taobao.api.response.ItemSellerGetResponse;
import com.taobao.api.response.ItemUpdateListingResponse;
import com.taobao.api.response.ItemcatsAuthorizeGetResponse;
import com.taobao.api.response.ItemcatsGetResponse;
import com.taobao.api.response.ItemsCustomGetResponse;
import com.taobao.api.response.LogisticsCompaniesGetResponse;
import com.taobao.api.response.LogisticsDummySendResponse;
import com.taobao.api.response.LogisticsOrdersDetailGetResponse;
import com.taobao.api.response.ProductsSearchResponse;
import com.taobao.api.response.RefundsApplyGetResponse;
import com.taobao.api.response.RefundsReceiveGetResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradeMemoAddResponse;
import com.taobao.api.response.TradeMemoUpdateResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.api.response.TradesSoldIncrementGetResponse;
import com.taobao.api.response.UserSellerGetResponse;
import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.service.BookService;
import com.zis.common.util.TextClearUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring/spring.xml", "classpath:spring/shiro.xml" })
// @TransactionConfiguration(defaultRollback = true, transactionManager =
// "TransactionConfiguration")
public class JSBExample {

	private static final String AK = "b77d78765c9cd21d4b1d6c222150923b3f74dac22222a3afb3ca329cb362eb09";

	private static final String SK = "578696961bfd5087886c220709de641c8305d5abfa9d13e4b4677eb0ca229f71";

	@Autowired
	private BookService bookService;

	public static void testLogisticsCompaniesGetRequest() throws JSBRestException {
		LogisticsCompaniesGetRequest req = new LogisticsCompaniesGetRequest();
		req.setFields("id,code,name,reg_mail_no");
		JSBClient c = new JSBClient(AK, SK);
		LogisticsCompaniesGetResponse resp = c.execute(req);
		System.out.println(resp.getBody());
	}

	public static void testAreasGetRequest() throws JSBRestException {
		AreasGetRequest areaReq = new AreasGetRequest();
		areaReq.setFields("id");
		JSBClient c = new JSBClient(AK, SK);
		AreasGetResponse resp = c.execute(areaReq);
		System.out.println(resp.getBody());
	}

	public static void testItemSellerGetRequest() throws JSBRestException {
		ItemSellerGetRequest req = new ItemSellerGetRequest();
		req.setFields("num_iid,title,props,price,approve_status,sku");
		req.setNumIid(2200783011403L);
		JSBClient c = new JSBClient(AK, SK);
		ItemSellerGetResponse rsp = c.execute(req);
		Item item = rsp.getItem();
		System.out.println(item.getTitle());
		System.out.println(rsp.getBody());
	}

	public static void testTradeFullGet() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
		req.setFields("tid,type,status,payment,orders");
		req.setTid(1988482578932334L);
		TradeFullinfoGetResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
		Trade trade = rsp.getTrade();
		System.out.println(trade.getTid());
		List<Order> orders = trade.getOrders();
		for (Order order : orders) {
			System.out.println(order.getTitle());
		}

	}

	public static void testTradeSoldGet() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		TradesSoldGetRequest req = new TradesSoldGetRequest();
		req.setFields("tid,type,status,payment,created");
		// req.setStartCreated(StringUtils.parseDateTime("2016-06-10 22:00:00"));
		// req.setEndCreated(StringUtils.parseDateTime("2016-06-20 22:59:59"));
		req.setPageNo(1L);
		req.setPageSize(5L);
		req.setUseHasNext(true);
		TradesSoldGetResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
		System.out.println(JSON.toJSON(rsp.getTrades()));
		List<Trade> list = rsp.getTrades();
		for (Trade t : list) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日HH-mm-ss-ssss");
			String date = dateFormat.format(t.getCreated());
			System.out.println(date);
		}
		// {
		//     "trades_sold_get_response":{
		//         "total_results":100,
		//         "has_next":true,
		//         "trades":{
		//             "trade":[
		//                 {
		//                     "seller_nick":"我在测试",
		//                     "pic_path":"http:\/\/img08.taobao.net\/bao\/uploaded\/i8\/T1jVXXXePbXXaoPB6a_091917.jpg",
		//                     "payment":"200.07",
		//                     "seller_rate":true,
		//                     "post_fee":"200.07",
		//                     "receiver_name":"东方不败",
		//                     "receiver_state":"浙江省",
		//                     "receiver_address":"淘宝城911号",
		//                     "receiver_zip":"223700",
		//                     "receiver_mobile":"13512501826",
		//                     "receiver_phone":"13819175372",
		//                     "consign_time":"2000-01-01 00:00:00",
		//                     "received_payment":"200.07",
		//                     "receiver_country":"中国",
		//                     "receiver_town":"三墎镇",
		//                     "order_tax_fee":"0",
		//                     "shop_pick":"1",
		//                     "tid":2231958349,
		//                     "num":1,
		//                     "num_iid":3424234,
		//                     "status":"TRADE_NO_CREATE_PAY",
		//                     "title":"麦包包",
		//                     "type":"fixed(一口价)",
		//                     "price":"200.07",
		//                     "discount_fee":"200.07",
		//                     "total_fee":"200.07",
		//                     "created":"2000-01-01 00:00:00",
		//                     "pay_time":"2000-01-01 00:00:00",
		//                     "modified":"2000-01-01 00:00:00",
		//                     "end_time":"2000-01-01 00:00:00",
		//                     "seller_flag":1,
		//                     "buyer_nick":"我在测试",
		//                     "has_buyer_message":true,
		//                     "credit_card_fee":"30.5",
		//                     "step_trade_status":"FRONT_NOPAID_FINAL_NOPAID",
		//                     "step_paid_fee":"525.70",
		//                     "mark_desc":"该订单需要延长收货时间",
		//                     "shipping_type":"free",
		//                     "adjust_fee":"200.07",
		//                     "trade_from":"WAP,JHS",
		//                     "service_orders":{
		//                         "service_order":[
		//                             {
		//                             }
		//                         ]
		//                     },
		//                     "buyer_rate":true,
		//                     "receiver_city":"杭州市",
		//                     "receiver_district":"西湖区",
		//                     "o2o":"crm",
		//                     "o2o_guide_id":"123456",
		//                     "o2o_shop_id":"123456",
		//                     "o2o_guide_name":"西湖门店导购员1",
		//                     "o2o_shop_name":"西湖门店",
		//                     "o2o_delivery":"inshop",
		//                     "orders":{
		//                         "order":[
		//                             {
		//                             }
		//                         ]
		//                     },
		//                     "rx_audit_status":"0",
		//                     "post_gate_declare":true,
		//                     "cross_bonded_declare":false,
		//                     "order_tax_promotion_fee":"0"
		//                 }
		//             ]
		//         }
		//     }
		// }
	}

	public static void testTradesSoldIncrementGet() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		TradesSoldIncrementGetRequest req = new TradesSoldIncrementGetRequest();
		req.setStatus("WAIT_BUYER_CONFIRM_GOODS");
		req.setFields("tid,type,status,payment,orders,rx_audit_status");
		req.setStartModified(StringUtils.parseDateTime("2016-06-20 00:00:00"));
		req.setEndModified(StringUtils.parseDateTime("2016-06-20 23:59:59"));
		req.setPageNo(1L);
		req.setPageSize(40L);
		req.setUseHasNext(true);
		TradesSoldIncrementGetResponse rsp = client.execute(req);
		List<Trade> trades = rsp.getTrades();
		for (Trade trade : trades) {
			System.out.println(trade.getTid());
			List<Order> orders = trade.getOrders();
			for (Order order : orders) {
				System.out.println(order.getTitle());
			}
		}
	}

	public static void testLogisticsOrdersDetailGetRequest() throws JSBRestException {
		LogisticsOrdersDetailGetRequest req = new LogisticsOrdersDetailGetRequest();
		req.setFields("receiver_mobile,tid,order_code,seller_nick,buyer_nick,item_title,receiver_location,status,type,company_name,created,is_quick_cod_order,sub_tids,is_split");
		req.setType("express");
		req.setPageNo(1L);
		req.setPageSize(40L);
		JSBClient client = new JSBClient(AK, SK);
		LogisticsOrdersDetailGetResponse resp = client.execute(req);
		System.out.println(resp.getBody());
	}

	public static void testTradeGet() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		TradeGetRequest req = new TradeGetRequest();
		req.setFields("tid,type,status,payment,seller_memo");
		req.setTid(3087643617106814L);
		// req.setTid(3087643614L);
		TradeGetResponse rsp = client.execute(req);
		if (rsp.isSuccess()) {
			System.out.println(rsp.getTrade().getTid());
		} else {
			// System.out.println(rsp.getErrorCode());
			System.out.println(rsp.getMsg());
			// System.out.println(rsp.getSubCode());
			System.out.println(rsp.getSubMsg());
		}
		// System.out.println(rsp.getBody());
	}

	public static void testTradeMemoAdd() throws JSBRestException {
		TradeMemoAddRequest req = new TradeMemoAddRequest();
		req.setTid(1988482578932334L);
		req.setMemo("交易备注");
		req.setFlag(1L);
		JSBClient client = new JSBClient(AK, SK);
		TradeMemoAddResponse rsp = client.execute(req);
		System.out.println(rsp.isSuccess() + " " + rsp.getBody());
	}

	public static void testTradeMemoUpdate() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		TradeMemoUpdateRequest req = new TradeMemoUpdateRequest();
		req.setTid(1988482578932334L);
		req.setMemo("交易备注new");
		req.setFlag(1L);
		TradeMemoUpdateResponse rsp = client.execute(req);
		System.out.println(rsp.isSuccess() + " " + rsp.getBody());
	}

	public static void testLogisticsDummySend() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);

		LogisticsDummySendRequest req = new LogisticsDummySendRequest();
		req.setTid(2003142416134634L);
		LogisticsDummySendResponse rsp = client.execute(req);
		// System.out.println(rsp.isSuccess() + " " + rsp.getBody());
	}

	public static void testGetCid() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		ItemcatsGetRequest req = new ItemcatsGetRequest();
		req.setParentCid(50012676L);
		// req.setCids("50018004");
		req.setFields("cid,parent_cid,name,is_parent");
		ItemcatsGetResponse rsp = client.execute(req);
		boolean a = rsp.isSuccess();
		if (a) {
			System.out.println(JSON.toJSON(rsp.getItemCats()));
		} else {
			System.out.println(rsp.getMsg());
		}
	}

	public static void testGetItemcatsAuthorizeGetRequest() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		ItemcatsAuthorizeGetRequest req = new ItemcatsAuthorizeGetRequest();
		req.setFields("item_cat.cid, item_cat.name, item_cat.status");
		ItemcatsAuthorizeGetResponse rsp = client.execute(req);
		boolean a = rsp.isSuccess();
		if (a) {
			System.out.println(JSON.toJSON(rsp.getSellerAuthorize()));
		} else {
			System.out.println(rsp.getMsg());
		}
	}

	public static Product testGetProductsSearchRequest(String isbn) throws JSBRestException {
		Product p = null;
		JSBClient client = new JSBClient(AK, SK);
		ProductsSearchRequest req = new ProductsSearchRequest();
		req.setFields("product_id,name,pic_url,cid,props,price,tsc");
		req.setQ(isbn);
		ProductsSearchResponse rsp = client.execute(req);
		boolean a = rsp.isSuccess();
		if (a) {
			System.out.println(JSON.toJSON(rsp.getProducts()));
			System.out.println(isbn);
			p = rsp.getProducts().get(0);
		} else {
			System.out.println(rsp.getMsg());
		}
		return p;
	}

	// public static Product testGetProductsSearchRequest(String isbn) throws
	// JSBRestException {
	// Product p = null;
	// JSBClient client = new JSBClient(AK, SK);
	// ItemsSearchRequest req = new ProductsSearchRequest();
	// req.setFields("product_id,name,pic_url,cid,props,price,tsc");
	// req.setQ(isbn);
	// ProductsSearchResponse rsp = client.execute(req);
	// boolean a = rsp.isSuccess();
	// if (a) {
	// System.out.println(JSON.toJSON(rsp.getProducts()));
	// System.out.println(isbn);
	// p = rsp.getProducts().get(0);
	// } else {
	// System.out.println(rsp.getMsg());
	// }
	// return p;
	// }

	public static void testAdd(Product p, Bookinfo bookinfo, BookinfoDetail bd, Long tid) throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		ItemAddRequest req = new ItemAddRequest();
		req.setStuffStatus("second");
		req.setTitle(TextClearUtils.buildTaobaoTitle(bookinfo));
		StringBuilder sb = new StringBuilder();
		sb.append(bd.getCatalog());
		sb.append(bd.getSummary());
		req.setDesc("这是一个好商品");
		// InputStream input = getInputStream(bd.getImageUrl());
		// FileItem image = new FileItem(bookinfo.getIsbn() + "-" +
		// bookinfo.getId() + ".jpg", input);
		// req.setImage(image);
		req.setCid(p.getCid());
		req.setNum(20L);
		req.setOuterId(bookinfo.getIsbn() + "-" + bookinfo.getId());
		req.setType("fixed");
		req.setPrice("1000");
		req.setLocationState("陕西");
		req.setLocationCity("西安");
		ItemAddResponse rsp = client.execute(req);
		if (rsp.isSuccess()) {
			System.out.println(JSON.toJSON(rsp.getItem()));
		} else {
			System.out.println(rsp.getErrorCode());
			System.out.println(rsp.getMsg());
			System.out.println(rsp.getSubCode());
			System.out.println(rsp.getSubMsg());
		}
	}

	@Test
	public void test() throws JSBRestException {
		String isbn = "9787802210905";
		Long tId = 7917680670L;// 运费模板
		List<Bookinfo> list = this.bookService.findBookByISBN(isbn);
		Bookinfo book = list.get(0);
		BookinfoDetail bd = this.bookService.findBookInfoDetailByBookId(book.getId());
		Product p = testGetProductsSearchRequest(isbn);
		if (p != null) {
			testAdd(p, book, bd, tId);
		} else {
			System.out.println("error");
		}

	}

	private static byte[] inputTobyte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
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

	public static void testGet() {
		JSBClient client = new JSBClient(AK, SK);
		ItemsCustomGetRequest req = new ItemsCustomGetRequest();
		req.setOuterId("9787561928851");
		// req.setFields("num_iid,2cied");
		ItemsCustomGetResponse rsp = null;
		try {
			rsp = client.execute(req);
		} catch (JSBRestException e) {
			System.out.println(rsp);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println(JSON.toJSON(rsp.getItems()));
		// boolean a = rsp.isSuccess();
		// if (a) {
		// System.out.println(JSON.toJSON(rsp.getItems()));
		// } else {
		// System.out.println(rsp.getMsg());
		// }
	}

	public static void testGet1() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		ItemsCustomGetRequest req = new ItemsCustomGetRequest();
		req.setOuterId("9787561928851");
		// req.setFields("num_iid,cid");
		ItemsCustomGetResponse rsp = client.execute(req);
		System.out.println(JSON.toJSON(rsp.getItems()));
		boolean a = rsp.isSuccess();
		if (a) {
			System.out.println(JSON.toJSON(rsp.getItems()));
		} else {
			System.out.println(rsp.getMsg());
			System.out.println(rsp.getErrorCode());
		}
	}

	public static void testUpload() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		ItemUpdateListingRequest req = new ItemUpdateListingRequest();
		req.setNumIid(546523522195L);
		req.setNum(100L);
		ItemUpdateListingResponse rsp = client.execute(req);
		// System.out.println(JSON.toJSON(rsp.getItem()));
		boolean a = rsp.isSuccess();
		if (a) {
			System.out.println(JSON.toJSON(rsp.getItem()));
		} else {
			System.out.println(rsp.getMsg());
			System.out.println(rsp.getErrorCode());
		}
	}

	public static void testUpdate() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		ItemQuantityUpdateRequest req = new ItemQuantityUpdateRequest();
		req.setNumIid(546331672462L);
		req.setQuantity(1L);
		ItemQuantityUpdateResponse rsp = client.execute(req);
		if (rsp.isSuccess()) {
			System.out.println(JSON.toJSON(rsp.getItem()));
			System.out.println(rsp.getItem().getApproveStatus());
		} else {
			System.out.println(rsp.getSubMsg());
			System.out.println(rsp.getBody());
			System.out.println(rsp.getErrorCode());
			System.out.println(rsp.getMsg());
		}
	}

	private static void testUserSellerGet() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		UserSellerGetRequest req = new UserSellerGetRequest();
		req.setFields("user_id");
		UserSellerGetResponse rsp = client.execute(req);
		System.out.println(JSON.toJSON(rsp.getUser()));
	}

	private static void testTradesSoldIncrementGeta() throws JSBRestException {
		try {
			JSBClient client = new JSBClient(AK, SK);
			TradesSoldIncrementGetRequest req = new TradesSoldIncrementGetRequest();
			req.setFields("tid,type,status,payment,orders");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = sdf.parse("2017-04-16 00:00:00");
			Date end = sdf.parse("2017-04-17 00:00:00");
			req.setStartModified(start);
			req.setEndModified(end);
			req.setUseHasNext(true);
			TradesSoldIncrementGetResponse rsp = client.execute(req);
			List<Trade> list = rsp.getTrades();
			for (Trade trade : list) {
				System.out.println(JSON.toJSON(trade));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void testRefundsApplyGet() throws JSBRestException {
		JSBClient client = new JSBClient(AK, SK);
		RefundsApplyGetRequest req = new RefundsApplyGetRequest();
		req.setFields("refund_id, tid, title, buyer_nick, seller_nick, total_fee, status, created, refund_fee");
		req.setPageNo(1L);
		req.setPageSize(40L);
		RefundsApplyGetResponse rsp = client.execute(req);
		List<Refund> list = rsp.getRefunds();
		System.out.println(rsp.getBody());
		System.out.println(rsp.getRefunds());
		for (Refund refund : list) {
			System.out.println(JSON.toJSON(refund));
		}
}
	private static void testRefundsReceiveGetRequest() throws Exception {
		JSBClient client = new JSBClient(AK, SK);
		RefundsReceiveGetRequest req = new RefundsReceiveGetRequest();
		req.setFields("refund_id, tid, title,buyer_nick, seller_nick, total_fee, status, created, refund_fee,refund_phase");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = sdf.parse("2017-04-16 00:00:00");
		Date end = sdf.parse("2017-04-17 00:00:00");
		req.setStartModified(start);
		req.setEndModified(end);
		req.setUseHasNext(true);
		RefundsReceiveGetResponse rsp = client.execute(req);
		List<Refund> list = rsp.getRefunds();
		for (Refund refund : list) {
			System.out.println(JSON.toJSON(refund));
		}
	}

	private static void test111() {

	}

	public static void main(String args[]) {
		// taobao.trades.sold.get
		// testTradeSoldGet();
		// testTradesSoldIncrementGet();
		// testTradeFullGet();
		// testTradeMemoAdd();
		// testTradeMemoUpdate();
		// testGetCid();
		// testGetProductsSearchRequest();
		// testTradeGet();
		// String s = "92929222";
		// String[]s1 =s.split("-");
		// System.out.println(s1.length);

		try {
			// // testUpload();
			// testTradeSoldGet();
			// testUserSellerGet();
//			testTradesSoldIncrementGeta();
			for (int i = 0; i < 100; i++) {
				testRefundsApplyGet();
//				testRefundsReceiveGetRequest();
			}
			// testUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
		// testLogisticsDummySend();
	}
}
