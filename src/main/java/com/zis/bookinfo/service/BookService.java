package com.zis.bookinfo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;
import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoStatus;
import com.zis.bookinfo.bean.ShopItemInfo;
import com.zis.bookinfo.bean.YouluSales;
import com.zis.bookinfo.bo.RepeatIsbnAnalysisBO;
import com.zis.bookinfo.bo.SameBookAnalysisBO;
import com.zis.bookinfo.bo.SimilarityBookAnalysisBO;
import com.zis.bookinfo.dao.BookinfoDao;
import com.zis.bookinfo.dao.ShopItemInfoDao;
import com.zis.bookinfo.dao.YouluSalesDao;
import com.zis.bookinfo.dto.ShopItemInfoDTO;
import com.zis.bookinfo.util.BookMetadata;
import com.zis.bookinfo.util.ConstantString;
import com.zis.bookinfo.util.YouLuNetDetailCapture;
import com.zis.common.util.ZisUtils;
import com.zis.requirement.bean.Bookamount;
import com.zis.requirement.dao.BookAmountDao;

public class BookService {
	private static Logger logger = Logger.getLogger(BookService.class);

	private BookinfoDao bookinfoDao;
	private YouluSalesDao youluSalesDao;
	private YouLuNetDetailCapture youLuNetDetailCapture;
	private ThreadPoolTaskExecutor taskExecutor;
	private BookAmountDao bookAmountDao;
	private ShopItemInfoDao shopItemInfoDao;

	private SimilarityBookAnalysisBO similarityBookAnalysisBO;
	private SameBookAnalysisBO sameBookAnalysisBO;
	private RepeatIsbnAnalysisBO repeatIsbnAnalysisBO;

	/**
	 * dwr获得书的信息，只显示前20条记录
	 * 
	 * @param bookName
	 * @param bookAuthor
	 * @param ISBN
	 * @return
	 */
	public String getBookInfo(String bookName, String bookAuthor, String ISBN) {
		DetachedCriteria criteria = this.buildBookInfoCriteria(bookName,
				bookAuthor, ISBN);
		List<Bookinfo> list = bookinfoDao
				.findByCriteriaLimitCount(criteria, 20);
		// FIXME fastjson
		return JSON.toJSONString(list);
		// String str = JSONSerializer.toJSON(list).toString();
		// return str;
	}

	private DetachedCriteria buildBookInfoCriteria(String bookName,
			String bookAuthor, String ISBN) {
		DetachedCriteria dc = DetachedCriteria.forClass(Bookinfo.class);
		if (!StringUtils.isBlank(bookName)) {
			dc.add(Restrictions.like("bookName", "%" + bookName + "%"));
		}
		if (!StringUtils.isBlank(bookAuthor)) {
			dc.add(Restrictions.like("bookAuthor", "%" + bookAuthor + "%"));
		}
		if (!StringUtils.isBlank(ISBN)) {
			dc.add(Restrictions.eq("isbn", ISBN));
		}
		dc.add(Restrictions.eq("bookStatus", ConstantString.USEFUL));
		return dc;
	}
	

	/**
	 * 检查图书是否存在
	 * 
	 * @return true if exist
	 */
	public boolean ifBookInfoExist(Bookinfo book) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
		criteria.add(Restrictions.eq("bookName", book.getBookName()));
		criteria.add(Restrictions.eq("bookAuthor", book.getBookAuthor()));
		criteria.add(Restrictions.eq("bookEdition", book.getBookEdition()));
		criteria.add(Restrictions.eq("bookPublisher", book.getBookPublisher()));
		criteria.add(Restrictions.eq("isbn", book.getIsbn()));
		criteria.add(Restrictions.eq("bookStatus", BookinfoStatus.NORMAL));
		List<Bookinfo> list = bookinfoDao.findByCriteria(criteria);
		return list != null && !list.isEmpty();
	}

	/**
	 * 新增图书
	 * @param book
	 * @return
	 */
	public int addBook(Bookinfo book) {
		checkForCreateBook(book);
		// 检查图书是否存在
		if (ifBookInfoExist(book)) {
			throw new RuntimeException("图书已存在");
		}

		// 新增图书
		book.setIsNewEdition(true);// 默认设置为最新版
		book.setRepeatIsbn(false);// 一码多书设置为false
		// book.setBookStatus(ConstantString.USEFUL);
		book.setGmtCreate(ZisUtils.getTS());
		book.setGmtModify(ZisUtils.getTS());
		book.setVersion(0);
		bookinfoDao.save(book);
		logger.info("bookinfo.action.checkBook--添加图书成功");
		
		// 处理一码多书和不同版本图书
		try {
			repeatIsbnAnalysisBO.processOne(book);
			sameBookAnalysisBO.processOne(book);
		} catch (Exception e) {//出错不影响主流程
			logger.error("处理一码多书过程出错,bookId=" + book.getId(), e);
		}
		return book.getId();
	}

	// 检查图书信息是否符合系统要求
	private void checkForCreateBook(Bookinfo book) {
		if(book==null) {
			throw new IllegalArgumentException("book is null");
		}
		if(StringUtils.isBlank(book.getBookName())) {
			throw new IllegalArgumentException("书名不能为空");
		}
		if(book.getBookName().length() > 128) {
			throw new IllegalArgumentException("书名长度不能超过128个字符");
		}
		if(StringUtils.isBlank(book.getBookAuthor())) {
			throw new IllegalArgumentException("作者不能为空");
		}
		if(book.getBookAuthor().length() > 50) {
			throw new IllegalArgumentException("作者长度不能超过50个字符");
		}
		if(StringUtils.isBlank(book.getBookPublisher())) {
			throw new IllegalArgumentException("出版社不能为空");
		}
		if(book.getBookPublisher().length() > 50) {
			throw new IllegalArgumentException("出版社长度不能超过50个字符");
		}
		if(StringUtils.isBlank(book.getBookEdition())) {
			throw new IllegalArgumentException("版次不能为空");
		}
		if(book.getBookEdition().length() > 100) {
			throw new IllegalArgumentException("版次长度不能超过100个字符");
		}
		if(StringUtils.isBlank(book.getIsbn())) {
			throw new IllegalArgumentException("条形码不能为空");
		}
		if(book.getIsbn().length() > 15) {
			throw new IllegalArgumentException("版次长度不能超过15个字符");
		}
		if(book.getBookPrice() == null || book.getBookPrice() <= 0) {
			throw new IllegalArgumentException("图书价格不能为空且必须大于0");
		}
	}

	/**
	 * ���ز�ѯ����ͼ��
	 * 
	 * @param criteria
	 * @return
	 */
	public List<Bookinfo> findBookByCriteria(DetachedCriteria criteria) {
		return bookinfoDao.findByCriteria(criteria);
	}

	/**
	 * 修改图书-审核通过
	 */
	public void updateBookForCheckOK(Bookinfo book) {
		if(book == null) {
			return;
		}
		book.setBookStatus(ConstantString.USEFUL);
		this.updateBook(book);
	}
	
	/**
	 * 修改图书-废弃
	 */
	public void updateBookForDisable(Integer bookId) {
		if(bookId == null) {
			return;
		}
		// 检查图书是否已被使用
		List<Bookamount> list = this.bookAmountDao.findByBookId(bookId);
		if(list != null && !list.isEmpty()) {
			throw new RuntimeException("该书已经被使用，请联系管理员");
		}
		Bookinfo book = this.bookinfoDao.findById(bookId);
		if(book == null || ConstantString.ABANDON.equals(book.getBookStatus())) {
			return; // 图书不存在或者已废弃，不做任何处理
		}
		// 更新图书状态
		book.setBookStatus(ConstantString.ABANDON);
		this.updateBook(book);
		// 如果图书存在不同版次且被删除的记录是最新版，则需要重新调整分组策略
		if(StringUtils.isNotBlank(book.getGroupId()) && book.getIsNewEdition()) {
			// List<Bookinfo> booksToBeDeal = new ArrayList<Bookinfo>();
			// booksToBeDeal.add(book);
			// sameBookAnalysisBO.relateToSameBooks(booksToBeDeal);
			DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
			criteria.add(Restrictions.eq("groupId", book.getGroupId()));
			criteria.add(Restrictions.eq("bookPublisher", book.getBookPublisher()));
			criteria.add(Restrictions.ne("bookStatus", BookinfoStatus.DISCARD));
			List<Bookinfo> bookList = bookinfoDao.findByCriteria(criteria);
			this.sameBookAnalysisBO.batchUpdateBooksIsNewEdition(bookList, book.getGroupId());
		}
	}
	
	/**
	 * 修改图书
	 */
	public void updateBook(Bookinfo book) {
		book.setGmtModify(ZisUtils.getTS());
		book.setVersion(book.getVersion() + 1);
		bookinfoDao.update(book);
	}

	/**
	 * 查询图书（包括所有状态，如果限定为“正式”状态，请使用findNormalBookById()
	 * 
	 * @param id
	 * @return
	 */
	public Bookinfo findBookById(int id) {
		return bookinfoDao.findById(id);
	}
	
	/**
	 * 查询正常状态的图书
	 * @param id
	 * @return
	 */
	public Bookinfo findNormalBookById(int id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.eq("bookStatus", BookinfoStatus.NORMAL));
		List<Bookinfo> list = this.bookinfoDao.findByCriteria(criteria);
		if(list == null || list.isEmpty()) {
			return null;
		}
		if(list.size() > 1) {
			throw new RuntimeException("数据错误，bookId="+id+"对应两条以上记录");
		}
		return list.get(0);
	}

	/**
	 * 一码多书处理，扫描数据库中的记录，更新一码多书标志位
	 */
	public void processOneISBNToMultiBooks() {
		repeatIsbnAnalysisBO.analysis();
	}

	/**
	 * 相同图书（书名、作者、出版社完全相同）关联到一起，并设置最新版标记
	 */
	public void processSameBooksToOneGroup() {
		sameBookAnalysisBO.analysis();
	}

	/**
	 * 相同图书（书名、作者、出版社完全相同）关联到一起，并设置最新版标记
	 */
	public void updateSameBooksToOneGroup(Integer ids[]) {
		if (ids == null || ids.length <= 1) {
			logger.error("BookService.addGroupRelation--id为空");
			return;
		}
		List<Bookinfo> booksToBeDeal = new ArrayList<Bookinfo>();
		// 所有相同图书都整理到一起
		for (Integer id : ids) {
			Bookinfo book = bookinfoDao.findById(id);
			if (book != null) {
				booksToBeDeal.add(book);
			}
		}
		sameBookAnalysisBO.relateToSameBooks(booksToBeDeal);
	}

	/**
	 * 把相关图书（配套教材或上下册）关联到一起
	 * 
	 * @param ids
	 */
	public void updateRelatedBooksToOneGroup(Integer ids[]) {
		if (ids == null || ids.length == 0) {
			return;
		}
		String relateId = "";
		Map<Integer, Bookinfo> booksToBeDeal = new HashMap<Integer, Bookinfo>();
		// 所有关联图书都整理到一起
		for (Integer id : ids) {
			Bookinfo book = bookinfoDao.findById(id);
			if (book == null) { // 跳过不存在的记录
				continue;
			}
			// 没有相关图书，该书ID直接加入到集合中等待处理
			if (book.getRelateId() == null)
				booksToBeDeal.put(book.getId(), book);
			else {
				// 如果有相关图书，则将所有相关图书的ID都加入到集合中等待处理
				relateId = book.getRelateId();
				@SuppressWarnings("unchecked")
				List<Bookinfo> list = bookinfoDao.findByRelateId(book
						.getRelateId());
				for (Bookinfo bookinfo : list) {
					booksToBeDeal.put(bookinfo.getId(), bookinfo);
				}
			}
		}
		// 所有相关图书都关联到一起
		if (StringUtils.isBlank(relateId))
			relateId = "r" + ZisUtils.getDateString("yyyyMMddHHmmss") + (int) (Math.random() * 1000);
		for (Bookinfo record : booksToBeDeal.values()) {
			record.setRelateId(relateId);
			bookinfoDao.update(record);
			logger.info("successfully combined related books, bookId="
					+ record.getId());
		}
	}
	
	/**
	 * 批量删除图书
	 * @param ids
	 */
	public void updateBookForBatchDelete(Integer ids[]) {
		if (ids == null || ids.length == 0) {
			return;
		}
		for (Integer bookId : ids) {
			this.updateBookForDisable(bookId);
		}
	}
	
	/**
	 * ͨ��groupId���� ͬ�鲻ͬ�����
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Bookinfo> getBooksByGroupId(String groupId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Bookinfo.class);
		dc.add(Restrictions.eq("groupId", groupId));
		dc.add(Restrictions.ne("bookStatus", ConstantString.ABANDON));
		return bookinfoDao.findByCriteria(dc);
	}

	/**
	 * ͨ��realteId���� ������ͼ��
	 * 
	 * @param relateId
	 * @return
	 */
	public List<Bookinfo> getBooksByRelateId(String relateId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Bookinfo.class);
		dc.add(Restrictions.eq("relateId", relateId));
		dc.add(Restrictions.ne("bookStatus", ConstantString.ABANDON));
		return bookinfoDao.findByCriteria(dc);
	}

	/**
	 * 从分组中移除某一条记录
	 * 
	 * @param id
	 */
	public void removeRelateId(int id, String pageType) {
		Bookinfo book = bookinfoDao.findById(id);
		// 不同版本
		if (ConstantString.PAGEGROUP.equals(pageType)) {
			boolean isNewEditionRemoved = book.getIsNewEdition();
			String groupId = book.getGroupId();
			// 从分组中移除该记录，并且标记为最新版
			book.setIsNewEdition(true);
			book.setGroupId(null);
			this.updateBook(book);
			// 如果移除的记录是最新版，那么所属分组需要重新标记最新版
			if(isNewEditionRemoved) {
				DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
				criteria.add(Restrictions.eq("groupId", groupId));
				criteria.add(Restrictions.eq("bookPublisher", book.getBookPublisher()));
				criteria.add(Restrictions.ne("bookStatus", BookinfoStatus.DISCARD));
				List<Bookinfo> bookList = bookinfoDao.findByCriteria(criteria);
				this.sameBookAnalysisBO.batchUpdateBooksIsNewEdition(bookList, groupId);
			}
		}
		// 关联图书
		if (ConstantString.PAGEGRELATE.equals(pageType)) {
			book.setRelateId(null);
			this.updateBook(book);
		}
	}
	
	/**
	 * 解除所有关联
	 * @param list
	 * @param operateType
	 */
	public void removeAllRelation(List<Bookinfo> list, String operateType) {
		for (Bookinfo bookinfo : list) {
			// 不同版本分组
			if (ConstantString.PAGEGROUP.equals(operateType)) {
				bookinfo.setGroupId(null);
				bookinfo.setIsNewEdition(true);
				this.updateBook(bookinfo);
			}
			// 相关性分组
			else if (ConstantString.PAGEGRELATE.equals(operateType)) {
				bookinfo.setRelateId(null);
				this.updateBook(bookinfo);
			}
		}
	}

	/**
	 * 从有路网获取图书基本信息
	 * 
	 * @param id
	 * @return
	 */
	public Bookinfo saveBookinfoByCaptureFromYouluNet(int id) {
		// 检查系统中是否存在outId=id的记录，如果有，则直接返回
		@SuppressWarnings("unchecked")
		List<Bookinfo> list = this.bookinfoDao.findByOutId(id);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		// 从有路网抓取数据
		BookMetadata bi = youLuNetDetailCapture.getBookInfo(id + "");
		if (bi == null) {
			return null;
		}
		Bookinfo book = new Bookinfo();
		book.setOutId(id);
		book.setBookName(bi.getName());
		book.setBookAuthor(bi.getAuthor());
		book.setIsbn(bi.getIsbnCode());
		book.setBookPrice(Float.parseFloat(bi.getPrice().toString()));
		book.setBookEdition(bi.getEdition());
		book.setBookStatus(ConstantString.USEFUL);
		book.setBookPublisher(bi.getPublisher());
		book.setPublishDate(ZisUtils.youluNet(bi.getPublishDate(), logger));
		// 保存到数据库
		addBook(book);
		return book;
	}

	/**
	 * 保存有路网销售数据
	 * 
	 * @param id
	 */
	private void saveYouluBookSales(int id) {
		BookMetadata bi = youLuNetDetailCapture.getBookInfo(id + "");
		if (bi == null) {
			return;
		}
		// 检查系统中是否已经录入该记录
		@SuppressWarnings("unchecked")
		List<YouluSales> uSales = this.youluSalesDao.findByOutId(id);
		if (uSales != null && !uSales.isEmpty()) {
			return;
		}
		YouluSales sales = new YouluSales();
		sales.setOutId(id);
		@SuppressWarnings("unchecked")
		List<Bookinfo> list = this.bookinfoDao.findByOutId(id);
		if (!list.isEmpty()) {
			sales.setBookId(list.get(0).getId());
		}
		sales.setBookPrice(bi.getSalesPrice());
		sales.setStockBalance(bi.getStock());
		sales.setGmtCreate(ZisUtils.getTS());
		sales.setGmtModify(ZisUtils.getTS());
		sales.setVersion(0);
		this.youluSalesDao.save(sales);
	}

	/**
	 * 异步批量抓取有路网图书数据
	 * 
	 * @param idStart
	 *            起始ID，包含
	 * @param idEnd
	 *            结束ID，不包含
	 * @param operateType
	 *            操作类型，抓取图书信息：addBookInfo，抓取销售信息：addSales
	 * @return 活动线程数
	 */
	public int asynchronousCaptureBookInfoFromYouLuNet(final int idStart,
			final int idEnd, final String operateType) {
		Thread task = new Thread(new Runnable() {

			public void run() {
				for (int i = idStart; i < idEnd; i++) {
					try {
						if (operateType.equals("addBookInfo")) {
							saveBookinfoByCaptureFromYouluNet(i);
						} else {
							saveYouluBookSales(i);
						}
					} catch (Exception e) {
						String msg = String.format(
								"有路网添加图书信息失败, youluId=%s, errorDetail=%s", i,
								e.getMessage());
						logger.error(msg, e);
					}
				}
			}
		});
		taskExecutor.execute(task);
		return taskExecutor.getActiveCount();
	}

	public List<Bookinfo> findBookByISBN(String isbn) {
		if (StringUtils.isBlank(isbn)) {
			throw new RuntimeException("isbn不能为空");
		}
		DetachedCriteria dc = DetachedCriteria.forClass(Bookinfo.class);
		dc.add(Restrictions.eq("isbn", isbn));
		dc.add(Restrictions.ne("bookStatus", ConstantString.ABANDON));// FIXME 中文会产生乱码
		return bookinfoDao.findByCriteria(dc);
	}

	// public void update(Bookinfo book) {
	// bookinfoDao.update(book);
	// }

	public List<Bookinfo> getWaitCheckList() {
		List<Bookinfo> list = bookinfoDao
				.findByBookStatus(ConstantString.WAITCHECK);
		return list;
	}

	/**
	 * 分析哪些图书相同
	 */
	public void analysisSimilarityBook() {
		similarityBookAnalysisBO.analysis();
		similarityBookAnalysisBO.afterAnalysis();
	}
	
	/**
	 * 添加/更新网店商品
	 */
	public void saveShopItem(ShopItemInfoDTO shopItemInfo) {
		if(shopItemInfo == null) {
			throw new IllegalArgumentException("shopItemInfo不能为空");
		}
		Integer bookId = shopItemInfo.getBookId();
		if(bookId == null) {
			throw new IllegalArgumentException("图书ID不能为空");
		}
		Bookinfo book = this.findNormalBookById(bookId);
		if(book == null) {
			throw new IllegalArgumentException("无此图书, bookId=" + bookId);
		}
		ShopItemInfo existItem = this.findShopItemByBookIdAndShopName(shopItemInfo.getShopName(), bookId);
		if(existItem == null) {
			// 如果不存在记录，则新增
			ShopItemInfo item = new ShopItemInfo();
			item.setBookId(bookId);
			item.setIsbn(book.getIsbn());
			item.setShopName(shopItemInfo.getShopName());
			item.setShopStatus(shopItemInfo.getShopStatus());
			item.setGmtCreate(ZisUtils.getTS());
			item.setGmtModified(ZisUtils.getTS());
			item.setVersion(0);
			this.shopItemInfoDao.save(item);
			logger.info("create new shopInfoItem, bookId=" + bookId);
		} else {
			if(!existItem.getShopStatus().equals(shopItemInfo.getShopStatus())) {
				// 如果存在且状态发生变化，则进行更新
				existItem.setShopStatus(shopItemInfo.getShopStatus());
				existItem.setGmtModified(ZisUtils.getTS());
				existItem.setVersion(existItem.getVersion() + 1);
				this.shopItemInfoDao.update(existItem);
				logger.info("update exist shopInfoItem, bookId=" + bookId);
			}
		}
	}
	
	/**
	 * 批量添加/更新网店商品
	 */
	public void saveShopItemForBatch(List<ShopItemInfoDTO> list) {
		for (ShopItemInfoDTO shopItemInfoDTO : list) {
			saveShopItem(shopItemInfoDTO);
		}
	}
	
	/**
	 * 查询网店商品-按照bookId和网店名称
	 * @param shopName
	 * @param bookId
	 * @return
	 */
	public ShopItemInfo findShopItemByBookIdAndShopName(String shopName, Integer bookId) {
		if(StringUtils.isBlank(shopName)) {
			throw new IllegalArgumentException("参数非法，shopName不能为空");
		}
		if(bookId == null) {
			throw new IllegalArgumentException("参数非法，bookId不能为空");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(ShopItemInfo.class);
		criteria.add(Restrictions.eq("shopName", shopName));
		criteria.add(Restrictions.eq("bookId", bookId));
		List<ShopItemInfo> list = this.shopItemInfoDao.findbyCriteria(criteria);
		if(list == null || list.isEmpty()) {
			return null;
		}
		if(list.size() > 1) {
			throw new RuntimeException("数据非法，存在重复记录，bookId="+bookId+"shopName="+shopName);
		}
		return list.get(0);
	}

	public void setBookinfoDao(BookinfoDao bookinfoDao) {
		this.bookinfoDao = bookinfoDao;
	}

	public void setYouluSalesDao(YouluSalesDao youluSalesDao) {
		this.youluSalesDao = youluSalesDao;
	}

	public void setYouLuNetDetailCapture(
			YouLuNetDetailCapture youLuNetDetailCapture) {
		this.youLuNetDetailCapture = youLuNetDetailCapture;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setSimilarityBookAnalysisBO(
			SimilarityBookAnalysisBO similarityBookAnalysisBO) {
		this.similarityBookAnalysisBO = similarityBookAnalysisBO;
	}

	public void setSameBookAnalysisBO(SameBookAnalysisBO sameBookAnalysisBO) {
		this.sameBookAnalysisBO = sameBookAnalysisBO;
	}

	public void setRepeatIsbnAnalysisBO(
			RepeatIsbnAnalysisBO repeatIsbnAnalysisBO) {
		this.repeatIsbnAnalysisBO = repeatIsbnAnalysisBO;
	}
	
	public void setBookAmountDao(BookAmountDao bookAmountDao) {
		this.bookAmountDao = bookAmountDao;
	}
	
	public void setShopItemInfoDao(ShopItemInfoDao shopItemInfoDao) {
		this.shopItemInfoDao = shopItemInfoDao;
	}
}
