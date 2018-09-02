package com.zhezhu;

/**
 * @author Liguiqing
 * @since V3.0
 */

import com.zhezhu.commons.spring.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TFKAppliction.class)
public class TFKApplictionTest {

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(SpringContextUtil.getBean("dataSource"));
    }

}