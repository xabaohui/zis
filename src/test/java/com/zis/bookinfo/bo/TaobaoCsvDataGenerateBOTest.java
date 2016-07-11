package com.zis.bookinfo.bo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zis.bookinfo.dto.BookInfoAndDetailDTO;
import com.zis.common.util.ZisUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext_bookinfo.xml", "classpath:applicationContext_requirement.xml"})
public class TaobaoCsvDataGenerateBOTest {
	
	@Resource
	TaobaoCsvDataGenerateBO gen;

	@Test
	public void testGenerate() {
		List<BookInfoAndDetailDTO> bookList = buildTestData();
		String[] emails = {"290479238@qq.com", "lvbin0502@126.com"};
		gen.generate(bookList, emails);
	}

	private List<BookInfoAndDetailDTO> buildTestData() {
		BookInfoAndDetailDTO book1 = buildBookInfoAndDetailDTO(1, "小李飞刀传奇", "9787123121312");
		BookInfoAndDetailDTO book2 = buildBookInfoAndDetailDTO(2, "小李飞刀他妈的传奇", "97871231749801");
		book2.setRepeatIsbn(true);
		BookInfoAndDetailDTO book3 = buildBookInfoAndDetailDTO(3, "小李飞刀TMD传奇", "97871231749801");
		book3.setRepeatIsbn(true);
		List<BookInfoAndDetailDTO> list = new ArrayList<BookInfoAndDetailDTO>();
		list.add(book1);
		list.add(book2);
		list.add(book3);
		return list;
	}

	private BookInfoAndDetailDTO buildBookInfoAndDetailDTO(Integer bookId, String bookName, String isbn) {
		BookInfoAndDetailDTO book = new BookInfoAndDetailDTO();
		book.setId(bookId);
		book.setBookName(bookName);
		book.setBookEdition("第一版");
		book.setBookAuthor("张三 李四 王二麻子 赵六");
		book.setBookPublisher("高等教育出版社");
		book.setPublishDate(ZisUtils.getTS());
		book.setIsbn(isbn);
		book.setBookPrice(99.0);
		book.setCatalog("目录<br/>Class One<p/>Class Two<p/>");
		book.setSummary("这本书主要讲述了。。。");
		book.setImageUrl("http://img3x4.ddimg.cn/9/5/8976834-1_w.jpg");
		book.setTaobaoCatagoryId(9527);
		book.setIsNewEdition(true);
		book.setRepeatIsbn(false);
		return book;
	}
}
