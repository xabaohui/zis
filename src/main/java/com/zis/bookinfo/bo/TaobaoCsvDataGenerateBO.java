package com.zis.bookinfo.bo;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.dto.BookInfoAndDetailDTO;
import com.zis.common.mail.MailSenderFactory;
import com.zis.common.mail.SimpleMailSender;
import com.zis.common.util.ImageUtils;
import com.zis.common.util.TextClearUtils;
import com.zis.common.util.ZipUtils;
import com.zis.common.util.ZisUtils;

/**
 * 淘宝助理CSV数据包生成类
 * 
 * @author lvbin
 * 
 */
public class TaobaoCsvDataGenerateBO {

	private static final Logger logger = LoggerFactory.getLogger(TaobaoCsvDataGenerateBO.class);

	private static final String T_HEAD1 = "version 1.00\ntitle\tcid\tseller_cids\tstuff_status\tlocation_state\tlocation_city\titem_type\tprice\tauction_increment\tnum\tvalid_thru\tfreight_payer\tpost_fee\tems_fee\texpress_fee\thas_invoice\thas_warranty\tapprove_status\thas_showcase\tlist_time\tdescription\tcateProps\tpostage_id\thas_discount\tmodified\tupload_fail_msg\tpicture_status\tauction_point\tpicture\tvideo\tskuProps\tinputPids\tinputValues\touter_id\tpropAlias\tauto_fill\tnum_id\tlocal_cid\tnavigation_type\tuser_name\tsyncStatus\tis_lighting_consigment\tis_xinpin\tfoodparame\tfeatures\tbuyareatype\tglobal_stock_type\tglobal_stock_country\tsub_stock_type\titem_size\titem_weight\tsell_promise\tcustom_design_flag\twireless_desc\tbarcode\tsku_barcode\tnewprepay\tsubtitle\tcpv_memo\tinput_custom_cpv\tqualification\tadd_qualification\to2o_bind_service\n";
	private static final String T_HEAD2 = "宝贝名称\t宝贝类目\t店铺类目\t新旧程度\t省\t城市\t出售方式\t宝贝价格\t加价幅度\t宝贝数量\t有效期\t运费承担\t平邮\tEMS\t快递\t发票\t保修\t放入仓库\t橱窗推荐\t开始时间\t宝贝描述\t宝贝属性\t邮费模版ID\t会员打折\t修改时间\t上传状态\t图片状态\t返点比例\t新图片\t视频\t销售属性组合\t用户输入ID串\t用户输入名-值对\t商家编码\t销售属性别名\t代充类型\t数字ID\t本地ID\t宝贝分类\t用户名称\t宝贝状态\t闪电发货\t新品\t食品专项\t尺码库\t采购地\t库存类型\t国家地区\t库存计数\t物流体积\t物流重量\t退换货承诺\t定制工具\t无线详情\t商品条形码\tsku 条形码\t7天退货\t宝贝卖点\t属性值备注\t自定义属性值\t商品资质\t增加商品资质\t关联线下服务\n";
	/**
	 * %1$s 宝贝名称<br/>
	 * %2$s 宝贝类目ID<br/>
	 * %3$s 出售方式：1新品 %4$s 宝贝价格<br/>
	 * %5$s 宝贝数量<br/>
	 * %6$s 商品状态，1-放入仓库；<br/>
	 * %7$s 宝贝描述<br/>
	 * %8$s 邮费模板ID<br/>
	 * %9$s 新图片<br/>
	 * %10$s 商家编码<br/>
	 * %11$s 书名<br/>
	 * %12$s 定价<br/>
	 * %13$s 作者<br/>
	 * %14$s 出版社<br/>
	 * %15$s 条形码<br/>
	 * %16$s 出版日期<br/>
	 */
	private static final String T_ROW = "\"%1$s\"\t%2$s\t\",,\"\t1\t\"陕西\"\t\"西安\"\t%3$s\t%4$s\t\"\"\t%5$s\t7\t2\t0\t1.21555e-14\t2.24932e-18\t0\t1\t%6$s\t0\t\"\"\t\"%7$s\"\t\"2043183:2147483647;1636953:2147483647;2045745:4052146;\"\t%8$s\t0\t\"\"\t\"200\"\t\"2;\"\t5\t\"%9$s:1:0:|;\"\t\"\"\t\"\"\t\"46602357,46398806,2043189,2043183,122216620,1636953,2043193\"\t\"%11$s,%12$s,%13$s,%11$s,%14$s,%15$s,%16$s\"\t\"%10$s\"\t\"\"\t0\t0\t-1\t1\t\t1\t40\t249\t\"\"\t\t0\t-1\t\t2\t\t\t1\t\t\"\"\t\t\t\t\"\"\t\"\"\t\"\"\t\"\"\t1\t\n";
	private static final String DESCRIPTION_FMT = "<P><font color=\"blue\">内容提要（在见书城·高质量二手书）</font></P><P><span>%1$s</span></P><P><font color=\"blue\">目录（在见书城·高质量二手书）</font></P><P><span>%2$s</span></P>";

	private Integer DEFAULT_SOLD_TYPE = 1; // 出售方式：1新品
	private Integer DEFAULT_SOLD_COUNT = 10; // 默认数量
	private Integer DEFAULT_ON_SALES = 1; // 商品状态，1-放入仓库
	private Integer DEFAULT_CATEGORY_ID = 50050324; // 默认分类：大学教材

	private String encoding = "GBK";
	private String baseDir;
	
	private SimpleMailSender mailSender = MailSenderFactory.getSender();

	/**
	 * 生成淘宝数据包，以邮件形式发送
	 * @param bookList
	 * @param emails
	 */
	public void generate(List<BookInfoAndDetailDTO> bookList, String[] emails) {
		if (bookList == null || bookList.isEmpty()) {
			logger.info("[生成文件-淘宝csv] 未生成任何文件，传入数据为空。");
			return;
		}
		// 生成器基础检查，如果不通过会报错
		basicCheck();
		// 创建临时文件夹
		try {
			String batchId = ZisUtils.getDateString("yyyy-MM-dd_HHmmss");
			String tmpDir = baseDir + batchId;
			String picDir = tmpDir + "/data/";
			FileUtils.forceMkdir(new File(tmpDir));
			FileUtils.forceMkdir(new File(picDir));
			logger.info("[生成文件-淘宝csv] 生成临时目录 {}", tmpDir);
			// 生成csv文件
			generateCSV(bookList, tmpDir + "/data.csv");
			logger.info("[生成文件-淘宝csv] 生成数据文件 {}", tmpDir + "/data.csv");
			// 生成(下载)图片文件
			downloadImage(bookList, picDir);
			logger.info("[生成文件-淘宝csv] 生成图片目录 {}", picDir);
			// 打包
			String destZipFile = tmpDir+".zip";
			ZipUtils.compress(tmpDir, destZipFile);
			logger.info("[生成文件-淘宝csv] 生成压缩包 {}", tmpDir);
			// 发送邮件
			mailSender.send(emails, "淘宝数据包-" + batchId, "请在附件中下载，解压缩后导入淘宝助理\n\n- - - - - - - - - - - - - - - - -\n本邮件由ZIS系统自动发送", destZipFile);
			logger.info("[生成文件-淘宝csv]  压缩包{} 发送邮件到邮箱列表 {}", destZipFile, ArrayUtils.toString(emails));
			// 清理临时文件
			FileUtils.deleteQuietly(new File(tmpDir));
			FileUtils.deleteQuietly(new File(destZipFile));
			logger.info("[生成文件-淘宝csv] 清理所有临时文件 {}, {}", tmpDir, destZipFile);
		} catch (Exception e) {
			logger.error("[生成文件-淘宝csv] 系统异常，原因为", e);
			try {
				mailSender.send(emails, "生成淘宝数据包失败", "生成淘宝数据包失败！错误原因如下，请联系管理员：\n" + e.getMessage());
			} catch (Exception mailEx) {
				logger.error("[生成文件-淘宝csv] 发送邮件失败，原因为" + mailEx.getMessage(), mailEx);
			}
			throw new RuntimeException(e);
		}
	}

	private void generateCSV(List<BookInfoAndDetailDTO> bookList,
			String filepath) throws Exception {
		FileWriterWithEncoding writer = new FileWriterWithEncoding(filepath,
				encoding);
		writer.write(T_HEAD1);
		writer.write(T_HEAD2);
		for (BookInfoAndDetailDTO book : bookList) {
			writer.write(generateOneLine(book));
			writer.flush();
			logger.debug("[生成文件-淘宝csv] 生成数据行 bookId={}, bookName={}", book.getId(), book.getBookName());
		}
		writer.close();
	}

	private String generateOneLine(BookInfoAndDetailDTO book) {
		// 标题使用 书名 (版次)+ 作者 + 条形码
		String title = book.getTaobaoTitle();
		if(StringUtils.isBlank(title)) {
			title = TextClearUtils.buildTaobaoTitle(book);
		}
		Integer categoryId = book.getTaobaoCatagoryId();
		if(categoryId == null) {
			categoryId = DEFAULT_CATEGORY_ID;// 默认分类：大学教材
		}
		String merchantNo = genUniqueIsbn(book);// 一码多书的，采用"条形码+bookId"作为唯一标识，正常的图书直接使用条形码
		String description = genDescription(book);
		String deliveryFeeId = "7838769160";// XXX 运费模板ID可以考虑和店铺关联
		String imageName = genImageName(book);
		String publishDateStr = ZisUtils.getDateString("yyyy-MM", book.getPublishDate());
		// 使用系统中的库存量，如果不存在/为零，则使用默认值
		Integer stockBalance = (book.getStockBalance() != null && book.getStockBalance() > 0) ? book.getStockBalance() : DEFAULT_SOLD_COUNT;
		return String.format(T_ROW,
				title, categoryId, DEFAULT_SOLD_TYPE, book.getBookPrice(), stockBalance,
				DEFAULT_ON_SALES, description, deliveryFeeId, imageName, merchantNo,
				book.getBookName(), book.getBookPrice(), book.getBookAuthor(), book.getBookPublisher(), book.getIsbn(),
				publishDateStr);
	}

	private String genDescription(BookInfoAndDetailDTO book) {
		if(StringUtils.isBlank(book.getSummary()) && StringUtils.isBlank(book.getCatalog())) {
			StringBuilder builder = new StringBuilder();
			builder.append("ISBN：").append(book.getIsbn()).append("<br/>");
			builder.append("书名：").append(book.getBookName()).append("<br/>");
			builder.append("作者：").append(book.getBookAuthor()).append("<br/>");
			builder.append("版次：").append(book.getBookEdition()).append("<br/>");
			builder.append("出版社：").append(book.getBookPublisher()).append("<br/>");
			builder.append("出版日期：").append(ZisUtils.getDateString("yyyy年MM月", book.getPublishDate())).append("<br/>");
			return builder.toString();
		}
		String summary = formatContent(book.getSummary());
		String catalog = formatContent(book.getCatalog());
		return String.format(DESCRIPTION_FMT, summary, catalog);
	}

	/**
	 * 格式化文本内容<p/>
	 * 1. 替换回车\r、换行\n<br/>
	 * 2. 替换制表符\t<br/>
	 * 3. 替换双引号"
	 * @param content
	 * @return
	 */
	private String formatContent(String content) {
		if(StringUtils.isBlank(content)) {
			return ""; 
		}
		content = content.replaceAll("\\r", "<br/>");
		content = content.replaceAll("\\n", "<br/>");
		content = content.replaceAll("\\t", "&nbsp;");
		content = content.replaceAll("\"", "&quot;");
		return content;
	}

	private String genUniqueIsbn(Bookinfo book) {
		// 一码多书的，采用"条形码-bookId"作为唯一标识，正常的图书直接使用条形码
		return book.getRepeatIsbn() ? book.getIsbn() + "-" + book.getId()
				: book.getIsbn();
	}

	// 图片名称：条形码_bookId
	private String genImageName(BookInfoAndDetailDTO book) {
		return String.format("%s_%s", book.getIsbn(), book.getId());
	}

	// 下载图片到临时目录
	private void downloadImage(List<BookInfoAndDetailDTO> bookList,
			String picDir) {
		for (BookInfoAndDetailDTO book : bookList) {
			if(StringUtils.isBlank(book.getImageUrl())) {
				continue;//跳过无图片的记录
			}
			String imageFileName = genImageName(book) + ".tbi";
			try {
				ImageUtils.downloadImg(book.getImageUrl(), picDir, imageFileName);
				// 休眠100毫秒，防止被对方系统拉黑
				ZisUtils.sleepQuietly(100);
				logger.debug("[生成文件-淘宝csv] 成功下载图片 {}，保存到 {}", book.getImageUrl(), picDir);
			} catch (Exception e) {
				logger.error("[生成文件-淘宝csv] 下载图片过程出错", e);
			}
		}
	}
	
	private void basicCheck() {
		if(StringUtils.isBlank(baseDir)) {
			throw new RuntimeException("[生成文件-淘宝csv] 生成器未完成初始化，baseDir未设置，请检查配置文件");
		}
	}
	
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
