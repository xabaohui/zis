package com.zis.bookinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.bookinfo.service.BookService;

@Controller
@RequestMapping(value = "/bookInfo")
public class AddYouLuDataController {

	@Autowired
	private BookService bookService;

	// TODO 缺少springMVC验证框架jar包，及其配置
	// @Validations(intRangeFields = {
	// @IntRangeFieldValidator(fieldName = "startId", min = "1", key =
	// "开始ID不能小于1"),
	// @IntRangeFieldValidator(fieldName = "finalId", min = "1", key =
	// "结束ID不能小于1") }, requiredFields = {
	// @RequiredFieldValidator(fieldName = "startId", key = "开始ID不能为空"),
	// @RequiredFieldValidator(fieldName = "finalId", key = "结束ID不能为空") })
	@RequestMapping(value = "/addYouLuData")
	public String addYouLuData(Integer startId, Integer finalId,
			String operateType, ModelMap context) {
//		System.out.println(startId+" "+finalId+" "+operateType);
		if (startId != null && finalId != null) {
			if (startId <= finalId) {
				Integer activeTaskCount = bookService
						.asynchronousCaptureBookInfoFromYouLuNet(startId,
								finalId, operateType);
				if (activeTaskCount <= 1)
					activeTaskCount = 1;
				context.put("activeTaskCount", activeTaskCount + "");
			}
			return "bookinfo/addYouLuData";
		} else {
			return "error";
		}
	}
}
