package com.example.awaysuse;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HutoolDateUtil {

    @Test
    public void getCurrentTime() {
        DateTime date = DateUtil.date();
        Assert.assertNotNull("当前时间:" + date);
        String dateStr = date.toString();
        Assert.assertNotNull("当前时间格式化:" + date);


    }
}
