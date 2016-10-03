package com.zis.purchase.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.ShopItemInfo;
import com.zis.bookinfo.bean.ShopItemInfoShopName;
import com.zis.bookinfo.bean.ShopItemInfoStatus;
import com.zis.bookinfo.service.BookService;
import com.zis.common.controllertemplate.CommonExcelExportController;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.bean.InwarehouseDetail;
import com.zis.purchase.biz.DoPurchaseService;

/**
 * 导出卖空清单-根据入库数据
 * @author yz
 *
 */
@Controller
@RequestMapping(value="/purchase")
public class SoldOutItemExportByInwarehouseController extends CommonExcelExportController<InwarehouseDetail>{

	@Autowired
	private BookService bookService;
	@Autowired
	private DoPurchaseService doPurchaseService;
	
	@RequestMapping(value="/exportSoldOutItem")
	public String export(HttpServletRequest request,HttpServletResponse response){
		return super.export(request, response);
	}
	
	@Override
	protected String[] getTableHeaders() {
		return new String[]{"商家编码","书名","数量"};
	}

	@Override
	protected String[] getRowDatas(InwarehouseDetail record) {
		Bookinfo book = this.bookService.findBookById(record.getBookId());
		String artNo = book.getRepeatIsbn() ? book.getIsbn() + "-" + book.getId() : book.getIsbn();
		return new String[]{artNo, book.getBookName(), record.getAmount() + ""};
	}

	@Override
	protected List<InwarehouseDetail> queryExportData(HttpServletRequest request) {
		String[] batchSelectedItemStr=request.getParameterValues("batchSelectedItem");
		DetachedCriteria criteria = DetachedCriteria.forClass(InwarehouseDetail.class);
		if(batchSelectedItemStr!=null){
			Integer []batchSelectedItem=new Integer[batchSelectedItemStr.length];
			for(int i=0;i<batchSelectedItemStr.length;i++){
				batchSelectedItem[i]=Integer.parseInt(batchSelectedItemStr[i]);
			}
			criteria.add(Restrictions.in("inwarehouseId", batchSelectedItem));
		}else{
			throw new IllegalArgumentException("batchSelectedItem 数组为空");
		}
		return this.doPurchaseService.findInwarehouseDetailByCriteria(criteria);
	}
	
	@Override
	protected Collection<InwarehouseDetail> TransformResultList(
			List<InwarehouseDetail> list) {
		Map<String, InwarehouseDetail> resultMap = new HashMap<String, InwarehouseDetail>();
		for (InwarehouseDetail curDetail : list) {
			// 过滤在售商品和网店没有的商品
			ShopItemInfo item = this.bookService.findShopItemByBookIdAndShopName(ShopItemInfoShopName.TB_ZAIJIAN, curDetail.getBookId());
			if(item == null || ShopItemInfoStatus.ON_SALES.equals(item.getShopStatus())) {
				continue;
			}
			// 如果有重复记录，自动合并
			String key = curDetail.getBookId() + "_" +curDetail.getPositionLabel();
			if(resultMap.containsKey(key)) {
				InwarehouseDetail existDetail = resultMap.get(key);
				existDetail.setAmount(existDetail.getAmount() + curDetail.getAmount());
			} else {
				resultMap.put(key, curDetail);
			}
		}
		return resultMap.values();
	}
	@Override
	protected String getSuccessPage() {
		return "success";
	}
	@Override
	protected String setExportFileName() {
		return "卖空商品-" + ZisUtils.getDateString("yyyy-MM-dd") + ".xls";
	}	
}
