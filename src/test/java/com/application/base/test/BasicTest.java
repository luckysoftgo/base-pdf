package com.application.base.test;

import com.application.base.PdfApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 孤狼
 * @NAME: BasicTest
 * @DESC: BasicTest类设计
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PdfApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicTest {

}
