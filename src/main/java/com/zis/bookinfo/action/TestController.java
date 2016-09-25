package com.zis.bookinfo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="start")
public class TestController {
	
	@RequestMapping(value = "page")
	public String startPage(String name, HttpServletRequest request,
			HttpServletResponse respose, ModelMap model) {
		model.put("msg", "this is start page ," + name);
		return "page";
	}

}
