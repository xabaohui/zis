package com.zis.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring/spring.xml", "classpath:spring/shiro.xml" })
public class BaseTestUnit {

}
