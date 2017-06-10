package com.test.xls.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zis.common.excel.ExcelImporter;
import com.zis.common.excel.FileImporter;
import com.zis.storage.service.StorageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring/spring.xml", "classpath:spring/shiro.xml" })
@TransactionConfiguration(defaultRollback = true, transactionManager = "TransactionConfiguration")
public class TestUtil {

	private static final String FILE_NAME = "E:/360data/重要数据/桌面/zis相关/zis/库位明细5.xls";// 表格文件路径
	
	@Autowired
	private StorageService storageService;
	
	private Set<String> set = new HashSet<>();

	private Integer sheetCount = 0;

	private void testPosInput() {
		// 设置模板文件，用于检验导入文件是否合法
		Integer headerRownums = 1;
		File file = new File(FILE_NAME);
		if (sheetCount == null) {
			sheetCount = 0;
		}
		try {
			InputStream fileInputStream = new FileInputStream(file);
			// 初始化导入器
//			ExcelImporterV2<PosDTO> im = new ExcelImporterV2<PosDTO>(fileInputStream, null, sheetCount);
			FileImporter<PosDTO> im = new ExcelImporter<PosDTO>(fileInputStream, null);
			im.setHeaderRowNums(headerRownums);

			// 检验导入文件是否合法
			String errMsg = im.validate();
			if (StringUtils.isNotBlank(errMsg)) {
				// 失败
				System.out.println("失败1");
			}
			// 解析文件并入库
			Map<String, Integer> propMapping = initPropMapping();
			PosDTO instance = new PosDTO();
			List<PosDTO> list = im.parse(instance, propMapping);
			if (list.isEmpty()) {
				System.out.println(list);
			}
			System.out.println(list.size());
			for (PosDTO p : list) {
				set.add(p.getPosLable());
			}
//			int a = im.getActiveSheetIndex();
//			System.out.println(a);
//			if (a > sheetCount) {
//				sheetCount++;
//				testPosInput();
//				return;
//			}
			 List<String> list1 = new ArrayList<String>(set);   
             
             /*将list有序排列*/    
             Collections.sort(list1, new Comparator<String>() {    
                 public int compare(String arg0, String arg1) {    
                     return arg0.compareTo(arg1);   
                 }    
             });
             for (String s : list1) {
				this.storageService.savePosition(s, 2);
			}
			System.out.println(set.size());
		} catch (Exception e) {
			
		}
	}

	@Test
	public void ssss() {
		testPosInput();
	}
	
	@Test
	public void sdd(){
		this.storageService.savePosition("H-003-07-01-01", 2);
	}

	private Map<String, Integer> initPropMapping() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("posLable", 1);
		return map;
	}
}