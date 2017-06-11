package com.test.xls.input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.common.excel.ExcelImporter;
import com.zis.common.excel.FileImporter;
import com.zis.storage.service.StorageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring/spring.xml", "classpath:spring/shiro.xml" })
@TransactionConfiguration(defaultRollback = true, transactionManager = "TransactionConfiguration")
public class XlsUtil {

	private static final String FAIL_URL = "E:/360data/重要数据/桌面/zis相关/zis/fail.csv";
	private static final String FILE_NAME = "E:/360data/重要数据/桌面/zis相关/zis/库位商品明细.xls";// 表格文件路径

	private List<FailDto> failList = new ArrayList<FailDto>();

	@Autowired
	private BookService bookService;

	@Autowired
	private StorageService storageService;

	@Test
	public void ssss() {
		for (int i = 1; i < 6; i++) {
			String fileName = "E:/360data/重要数据/桌面/zis相关/zis/库位商品明细" + i + ".xls";
			testPosInput(fileName);
		}
	}

	private void testPosInput(String fileName) {
		// 设置模板文件，用于检验导入文件是否合法
		Integer headerRownums = 1;
		File file = new File(fileName);
		// if (sheetCount == null) {
		// sheetCount = 0;
		// }
		try {
			InputStream fileInputStream = new FileInputStream(file);
			// 初始化导入器
			FileImporter<ItemDTO> im = new ExcelImporter<ItemDTO>(fileInputStream, null);
			im.setHeaderRowNums(headerRownums);

			// 检验导入文件是否合法
			String errMsg = im.validate();
			if (StringUtils.isNotBlank(errMsg)) {
				// 失败
				System.out.println("失败1");
			}
			// 解析文件并入库
			Map<String, Integer> propMapping = initPropMapping();
			ItemDTO instance = new ItemDTO();
			List<ItemDTO> list = im.parse(instance, propMapping);
			if (list.isEmpty()) {
				System.out.println(list);
			}
			for (ItemDTO dto : list) {
				ifList();
				try {
					Bookinfo book = buildBookInfoByItemOutNum(dto.getOutNum());
					dto.setSkuId(book.getId());
					if (dto.getAmount() == 0) {
						throw new RuntimeException("库存数量不足");
					}
					this.storageService.directInStorage(dto.getRepoId(), dto.getSkuId(), dto.getAmount(),
							dto.getPosLable(), dto.getOperator());
				} catch (Exception e) {
					FailDto f = getFailDto(dto.getOutNum(), dto.getAmount(), dto.getPosLable(), e.getMessage());
					failList.add(f);
				}
			}
			// int a = im.getActiveSheetIndex();
			// if (a > sheetCount) {
			// sheetCount++;
			// testPosInput();
			// return;
			// }
			addFailFile(failList, FAIL_URL);
		} catch (Exception e) {

		}
	}

	/**
	 * 通过商家编码获取book信息
	 * 
	 * @param skuInfo
	 * @return
	 */
	private Bookinfo buildBookInfoByItemOutNum(String outNum) {
		if (StringUtils.isBlank(outNum)) {
			throw new RuntimeException("订单编码:" + outNum + " 商家编码为空");
		}
		String[] itemOutNum = outNum.split("-");
		if (itemOutNum.length == 1) {
			List<Bookinfo> list = this.bookService.findBookByISBN(itemOutNum[0]);
			if (list.isEmpty()) {
				throw new RuntimeException("订单编码:" + outNum + "商家编码:" + itemOutNum[0] + "在zis中无商品");
			}
			if (list.size() > 1) {
				throw new RuntimeException("订单编码:" + outNum + "商家编码:" + itemOutNum[0] + "一码多书，请修改商家编码");
			}
			return list.get(0);
		} else if (itemOutNum.length == 2) {
			Integer bookId;
			try {
				bookId = Integer.parseInt(itemOutNum[1]);
			} catch (NumberFormatException e) {
				throw new RuntimeException("订单编码:" + outNum + "商家编码有误");
			}
			Bookinfo book = this.bookService.findByIdAndIsbn(bookId, itemOutNum[0]);
			if (book == null) {
				throw new RuntimeException("订单编码:" + outNum + "商家编码:" + itemOutNum[0] + "在zis中无商品");
			}
			return book;
		} else {
			throw new RuntimeException("订单编码:" + outNum + "商家编码有误");
		}
	}

	private Map<String, Integer> initPropMapping() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("posLable", 1);
		map.put("outNum", 3);
		map.put("amount", 11);
		return map;
	}

	private FailDto getFailDto(String outNum, Integer amount, String posLable, String failDesc) {
		FailDto f = new FailDto();
		f.setOutNum(outNum);
		f.setAmount(amount);
		f.setPosLable(posLable);
		f.setFailDesc(failDesc);
		return f;
	}

	private void ifList() {
		System.out.println(failList.size());
		if (failList.size() > 500) {
			addFailFile(failList, FAIL_URL);
			failList.clear();
		}
	}

	public static void addFailFile(List<FailDto> failList, String url) {
		File file = new File(url);
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp).append("\n");
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(file));
			bw.append(sb.toString());
			for (FailDto dto : failList) {
				bw.append(dto.getPosLable() + "," + dto.getOutNum() + "," + dto.getAmount() + "," + dto.getFailDesc()
						+ "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭输入流
				// in.close();
				// ou.close();
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}