package com.zis.youzan.test;


public class TestUtil {
//	public static void getConstantName(String name){
//		String n1 =  name.toUpperCase();
//		String[] n2 = n1.split("\\.");
//		StringBuffer b = new StringBuffer();
//		for (int i = 0; i < n2.length; i++) {
//			b.append(n2[i]);
//			if((n2.length-1)!=i){
//				b.append("_");
//			}
//		}
//		System.out.println(b.toString());
//	}
	public static void main(String[] args) {
		String str="cid:8000017,promotion_cid:0,tag_ids:93797305,detail_url:https://h5.koudaitong.com/v2/showcase" +
				"/goods?alias=2xcsysgsmsp14&from=wsc&kdtfrom=wsc,share_url:https://h5.koudaitong.com/v2/showcase/" +
				"goods?alias=2xcsysgsmsp14&from=wsc&kdtfrom=wsc,skus:[],pic_url:https://img.yzcdn.cn/upload_files/2016/12/13/148161479971383024.gif" +
				",pic_thumb_url:https://img.yzcdn.cn/upload_files/2016/12/13/148161479971383024.gif!120x120.jpg,num:117,sold_num:0,price:11.60,post_type:2,post_fee:0.00,delivery_template_fee:6.00,10.00,delivery_template_id:379054,delivery_template_name:u9ed8u8ba4u8fd0u8d39u6a21u7248" +
				",item_imgs:[{url:https://img.yzcdn.cn/upload_files/2016/12/13/148161479971383024.gif" +
				",thumbnail:https://img.yzcdn.cn/upload_files/2016/12/13/148161479971383024.gif?imageView2/2/w/290/h/290/q/75/format/gif" +
				",medium:https://img.yzcdn.cn/upload_files/2016/12/13/148161479971383024.gif?imageView2/2/w/600/h/0/q/75/format/gif,combine:https://img.yzcdn.cn/upload_files/2016/12/13/148161479971383024.gif?imageView2/2/w/600/h/0/q/75/format/gif,id:703569586,created:2016-12-24 14:34:29}],item_tags:[{id:93797305,item_num:7,tag_url:http://shop18572000.koudaitong.com/v2/showcase/tag?alias=1ee40gchs,share_url:http://shop18572000.koudaitong.com/v2/showcase/tag?alias=1ee40gchs,type:0,created:2016-12-21 11:58:51,desc:,name:u7ecfu5178u6559u6750}],item_type:0,is_supplier_item:false,is_virtual:false,virtual_type:0,item_validity_start:0,item_validity_end:0,effective_type:0,effective_delay_hours:0,holidays_available:0,is_listing:true,is_lock:false,is_used:false,product_type:0,auto_listing_time:0,has_component:false,template_id:45892742,template_title:u8be6u60c5u9875u6a21u677f,join_level_discount:0,messages:[],order:0,item_qrcodes:[],purchase_right:0,ump_tags:[],ump_level:[],ump_tags_text:null,ump_level_text:null,num_iid:321540209,alias:2xcsysgsmsp14,title:u4e8cu624bu9a6cu514bu601du4e3bu4e49u57fau672cu539fu7406u6982u8bba 2015u4feeu8ba2u7248 u9ad8u7b49u6559u80b2 9787040431971,desc:<p><font color=blue>u5185u5bb9u63d0u8981</font></p><p><span>u9a6cu514bu601du4e3bu4e49u57fau672cu539fu7406u6982u8bba2015u7248 u9ad8u7b49u6559u80b2u51fau7248u793e u4e24u8bfeu6559u6750 u8003u7814u7528u4e66_u672cu4e66u7f16u5199u7ec4_u9ad8u7b49u6559u80b2u51fau7248u793e_</span></p><p><font color=blue>u76eeu5f55</font></p><p><span></span></p>,origin_price:,outer_id:9787040431971,outer_buy_url:http://img.cdn.sb.hongware.com/1481614798927217.gif,buy_quota:0,created:2016-12-22 15:04:02}}}";
		String [] t1 = str.split(",");
		for (int i = 0; i < t1.length; i++) {
			System.out.println(t1[i]);
		}
		System.out.println(t1.length);
	}
}
//ump_tags	String	否	[121,122]	可购买该商品的用户标签
//ump_level	String	否	[222,223]	可购买该商品的会员等级
//title	String	否	Nokia N97全新行货	商品标题
//template_id	String	否	0	商品页模板
//tag_ids	String	否	12,13	商品标签id串，结构如 1234,1342,...，可参考API kdt.itemcategories.tags.get
//skus_with_json	String	否		商品Sku信息的Json字符串
//sku_quantities	String	否	2,3	Sku的数量串
//sku_properties	String	否	颜色:黄色;尺寸:M;重量:1KG,颜色:黄色;尺寸:S;重量:1KG	Sku的属性串
//sku_prices	String	否	10.00,5.00	Sku的价格串。结构如：10.00,5.00,... 精确到2位小数
//sku_outer_ids	String	否	1234,1342	Sku的商家编码（商家为Sku设置的外部编号）串。结构如：1234,1342,...
//quantity	String	否	10	商品总库存。当商品没有Sku的时候有效，商品有Sku时，总库存会自动按所有Sku库存之和计算
//purchase_right	Number	否	1	是否设置购买权限
//promotion_cid	Number	否	123	商品推广栏目id，可参考API kdt.itemcategories.promotions.get
//price	Price	否	100.00	商品价格。取值范围0.01-100000000.精确到2位小数.单位 元
//post_fee	Price	否	10.00	运费
//outer_id	String	否	B1222	商品货号（商家为商品设置的外部编号）
//origin_price	String	否	淘价：120	显示在“原价”一栏中的信息
//num_iid	Number	是	1212	商品数字编号
//messages	String	否		商品留言
//keep_item_img_ids	String	否	1222,1224	编辑商品时保留商品已有图片
//join_level_discount	Number	否	0	是否参加会员折扣。默认null，不改变原值，设置为1：参加会员折扣，若设置为0：不参加会员折扣。修改此字段时需要对传入参数components_extra_id设值，否则无法更新
//is_used	Boolean	否	0	是否为二手商品
//images[]	byte[]	否		商品主图文件列表，可一次上传多张。最大支持 1M，支持的文件类型 gif,jpg,jpeg,png
//注：1.图片参数不参与通讯协议签名，参数名中的中括号。
//2.images[]和image_ids两者至少传一个
//image_ids	String	否	1223,1225	添加的商品图片附件ID
//holidays_available	Number	否		节假日是否可用，值是 0 or 1
//hide_quantity	Number	否	1	是否隐藏商品库存。在商品展示时不显示商品的库存，默认0：显示库存，设置为1：不显示库存
//fields	String	否	num_iid,name	需要返回的商品对象字段
//effective_type	Number	否		电子凭证生效类型，0 立即生效， 1 自定义推迟时间， 2 隔天生效
//effective_delay_hours	Number	否		电子凭证自定义推迟时间
//desc	String	否	Nokia N97全新行货	商品描述
//delivery_template_id	Number	否	0	运费模板id
//cid	Number	否	8	商品分类的叶子类目id，可参考API kdt.itemcategories.get
//buy_url	String	否	http://item.taobao.com/item.htm?id=36963096	该商品的外部购买地址。当用户购买环境不支持微信或微博支付时会跳转到此地址
//buy_quota	Number	否	1	每人限购多少件。0代表无限购，默认为0
//auto_listing_time	Number	否	0	开始出售时间。默认null，不改变原值，设置为0：立即出售

//cid:8000017
//promotion_cid:0
//tag_ids:93797305
//detail_url:https://h5.koudaitong.com/v2/showcase/goods?alias=2xcsysgsmsp14&from=wsc&kdtfrom=wsc
//share_url:https://h5.koudaitong.com/v2/showcase/goods?alias=2xcsysgsmsp14&from=wsc&kdtfrom=wsc
//skus:[]
//pic_url:https://img.yzcdn.cn/upload_files/2016/12/13/148161479971383024.gif
//pic_thumb_url:https://img.yzcdn.cn/upload_files/2016/12/13/148161479971383024.gif!120x120.jpg
//num:117
//sold_num:0
//price:11.60
//post_type:2
//post_fee:0.00
//delivery_template_fee:6.00
//10.00
//delivery_template_id:379054
//delivery_template_name:u9ed8u8ba4u8fd0u8d39u6a21u7248
//item_imgs:[{url:https://img.yzcdn.cn/upload_files/2016/12/13/148161479971383024.gif
//thumbnail:https://img.yzcdn.cn/upload_files/2016/12/13/148161479971383024.gif?imageView2/2/w/290/h/290/q/75/format/gif
//medium:https://img.yzcdn.cn/upload_files/2016/12/13/148161479971383024.gif?imageView2/2/w/600/h/0/q/75/format/gif
//combine:https://img.yzcdn.cn/upload_files/2016/12/13/148161479971383024.gif?imageView2/2/w/600/h/0/q/75/format/gif
//id:703569586
//created:2016-12-24 14:34:29}]
//item_tags:[{id:93797305
//item_num:7
//tag_url:http://shop18572000.koudaitong.com/v2/showcase/tag?alias=1ee40gchs
//share_url:http://shop18572000.koudaitong.com/v2/showcase/tag?alias=1ee40gchs
//type:0
//created:2016-12-21 11:58:51
//desc:
//name:u7ecfu5178u6559u6750}]
//item_type:0
//is_supplier_item:false
//is_virtual:false
//virtual_type:0
//item_validity_start:0
//item_validity_end:0
//effective_type:0
//effective_delay_hours:0
//holidays_available:0
//is_listing:true
//is_lock:false
//is_used:false
//product_type:0
//auto_listing_time:0
//has_component:false
//template_id:45892742
//template_title:u8be6u60c5u9875u6a21u677f
//join_level_discount:0
//messages:[]
//order:0
//item_qrcodes:[]
//purchase_right:0
//ump_tags:[]
//ump_level:[]
//ump_tags_text:null
//ump_level_text:null
//num_iid:321540209
//alias:2xcsysgsmsp14
//title:u4e8cu624bu9a6cu514bu601du4e3bu4e49u57fau672cu539fu7406u6982u8bba 2015u4feeu8ba2u7248 u9ad8u7b49u6559u80b2 9787040431971
//desc:<p><font color=blue>u5185u5bb9u63d0u8981</font></p><p><span>u9a6cu514bu601du4e3bu4e49u57fau672cu539fu7406u6982u8bba2015u7248 u9ad8u7b49u6559u80b2u51fau7248u793e u4e24u8bfeu6559u6750 u8003u7814u7528u4e66_u672cu4e66u7f16u5199u7ec4_u9ad8u7b49u6559u80b2u51fau7248u793e_</span></p><p><font color=blue>u76eeu5f55</font></p><p><span></span></p>
//origin_price:
//outer_id:9787040431971
//outer_buy_url:http://img.cdn.sb.hongware.com/1481614798927217.gif
//buy_quota:0
//created:2016-12-22 15:04:02}}}