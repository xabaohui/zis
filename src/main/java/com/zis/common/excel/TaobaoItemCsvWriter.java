/**
 * 
 */
package com.zis.common.excel;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zis.bookinfo.util.BookMetadata;

/**
 * @author lvbin 2015-7-23
 */
public class TaobaoItemCsvWriter {

	private BufferedOutputStream bos = null;
	private String header = "version 1.00\r\ntitle	cid	seller_cids	stuff_status	location_state	location_city	item_type	price	auction_increment	num	valid_thru	freight_payer	post_fee	ems_fee	express_fee	has_invoice	has_warranty	approve_status	has_showcase	list_time	description	cateProps	postage_id	has_discount	modified	upload_fail_msg	picture_status	auction_point	picture	video	skuProps	inputPids	inputValues	outer_id	propAlias	auto_fill	num_id	local_cid	navigation_type	user_name	syncStatus	is_lighting_consigment	is_xinpin	foodparame	features	buyareatype	global_stock_type	global_stock_country	sub_stock_type	item_size	item_weight	sell_promise	custom_design_flag	wireless_desc	barcode	sku_barcode	newprepay	subtitle\r\n宝贝名称	宝贝类目	店铺类目	新旧程度	省	城市	出售方式	宝贝价格	加价幅度	宝贝数量	有效期	运费承担	平邮	EMS	快递	发票	保修	放入仓库	橱窗推荐	开始时间	宝贝描述	宝贝属性	邮费模版ID	会员打折	修改时间	上传状态	图片状态	返点比例	新图片	视频	销售属性组合	用户输入ID串	用户输入名-值对	商家编码	销售属性别名	代充类型	数字ID	本地ID	宝贝分类	用户名称	宝贝状态	闪电发货	新品	食品专项	尺码库	采购地	库存类型	国家地区	库存计数	物流体积	物流重量	退换货承诺	定制工具	无线详情	商品条形码	sku 条形码	7天退货	宝贝卖点\r\n";
	private String dataFmt = "\"%1$s\"	50050324	\"\"	3	\"陕西\"	\"西安\"	1	%3$s	\"0.00\"	1	7	1	0	0	0	0	0	2	0	\"\"	\"%8$s\"	\"46602357:311354894;46398806:10448865;122216620:16405245;2043193:10285019;2043189:129253040;2043183:311354894;1636953:126199908\"	10638168	0	\"2015-07-22 21:25:47\"	\"200\"	\"1;\"	0	\"6d0070032957e5af9ffeb5879ebaf8d6:1:0:|http://img.alicdn.com/imgextra/i1/543527737/TB2GBTfeVXXXXapXpXXXXXXXXXX_!!543527737.jpg;411cab5cd91f5cb45b1b780986ad37fa:1:1:|;\"	\"\"	\"\"	\"46602357,46398806,2043189,2043183,122216620,1636953,2043193\"	\"%2$s,%3$s,%4$s,%2$s,%5$s,%6$s,%7$s\"	\"%6$s\"	\"\"	0	520810592067	0	3	tech_天地	1	156	248		tags:32642	0	-1		2			1		\"\"	%6$s		1	\"%9$s\"\n";
	private String describFmt = "<P><font color=\"blue\">内容提要（在见书城·高质量二手书）</font></P><P><span>%1$s</span></P><P><font color=\"blue\">目录（在见书城·高质量二手书）</font></P><P><span>%2$s</span></P>";

	public TaobaoItemCsvWriter(String fileName) throws IOException{
		FileOutputStream fos = new FileOutputStream(fileName);
		this.bos = new BufferedOutputStream(fos);
		bos.write(header.getBytes());
		bos.flush();
	}

	public void close() throws IOException{
		this.bos.close();
	}
	
	public void writeTaobaoCsv(BookMetadata bi) throws IOException {
		bos.write(getItemData(bi));
		bos.flush();
	}

	public void write(List<BookMetadata> biList) throws IOException {
		for (BookMetadata bi : biList) {
			this.writeTaobaoCsv(bi);
		}
	}
	
	public void writeBookInfo(BookMetadata bi) throws IOException {
		bos.write(getBookInfo(bi));
		bos.flush();
	}
	
	public void writeBookInfos(List<BookMetadata> biList) throws IOException {
		for (BookMetadata bi : biList) {
			this.writeBookInfo(bi);
		}
	}

	/**
	 * @param bi
	 * @return
	 */
	private byte[] getBookInfo(BookMetadata bi) {
		// 标题使用 书名 + 作者 + 条形码 + 出版社
		String title = "二手" + bi.getName() + " " + bi.getAuthor();
		title = appendIfAllow(title, bi.getIsbnCode());
		title = appendIfAllow(title, bi.getPublisher());
		
		StringBuilder builder = new StringBuilder();
		builder.append(bi.getIsbnCode());
		builder.append(",\""+ bi.getName() + "\"");
		builder.append(",\""+ title + "\"");
		builder.append(",\""+ bi.getAuthor() + "\"");
		builder.append(",\""+ bi.getPublisher() + "\"");
		builder.append(",\""+ bi.getPrice() + "\"\r\n");
		return builder.toString().getBytes();
	}

	/**
	 * @param bi
	 * @return
	 */
	private byte[] getItemData(BookMetadata bi) {
		// 标题使用 书名 + 作者 + 条形码 + 出版社
		String title = "二手" + bi.getName() + " " + bi.getAuthor();
		title = appendIfAllow(title, bi.getIsbnCode());
		title = appendIfAllow(title, bi.getPublisher());
		String content = String.format(describFmt, bi.getSummary(), bi
				.getCatelog());
		StringBuilder builder = new StringBuilder();
		//\"46602357,46398806,2043189,2043183,122216620,1636953,2043193\"	
		//\"%2$s,%3$s,%4$s,%2$s,%5$s,%6$s,%7$s\"
		// 书名，price，作者，书名，出版社，isbn，出版日
		String author = StringUtils.isBlank(bi.getAuthor()) ? " " : bi.getAuthor();
		String publisher = StringUtils.isBlank(bi.getPublisher()) ? " " : bi.getPublisher();
		String pubDate = StringUtils.isBlank(bi.getPublishDate()) ? " " : bi.getPublishDate();
		
		String data = String.format(dataFmt, title, bi.getName(),
				bi.getPrice(), author, publisher, bi
						.getIsbnCode(), pubDate, content, bi.getBookIntro());
		builder.append(data);
		return builder.toString().getBytes();
	}

	/**
	 * @param title
	 * @param append
	 * @return
	 */
	private String appendIfAllow(String title, String append) {
		if((title + " " + append).getBytes().length <=60) {
			return title + " " + append;
		} else {
			return title;
		}
	}
}