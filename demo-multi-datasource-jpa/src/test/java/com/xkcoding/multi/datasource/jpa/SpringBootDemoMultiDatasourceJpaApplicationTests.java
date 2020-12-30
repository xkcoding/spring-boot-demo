package com.xkcoding.multi.datasource.jpa;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import com.xkcoding.multi.datasource.jpa.entity.primary.PrimaryMultiTable;
import com.xkcoding.multi.datasource.jpa.entity.second.SecondMultiTable;
import com.xkcoding.multi.datasource.jpa.repository.primary.PrimaryMultiTableRepository;
import com.xkcoding.multi.datasource.jpa.repository.second.SecondMultiTableRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBootDemoMultiDatasourceJpaApplicationTests {
    @Autowired
    private PrimaryMultiTableRepository primaryRepo;
    @Autowired
    private SecondMultiTableRepository secondRepo;
    @Autowired
    private Snowflake snowflake;

    @Test
    public void testInsert() {
        PrimaryMultiTable primary = new PrimaryMultiTable(snowflake.nextId(), "测试名称-1");
        primaryRepo.save(primary);

        SecondMultiTable second = new SecondMultiTable();
        BeanUtil.copyProperties(primary, second);
        secondRepo.save(second);
    }

    @Test
    public void testUpdate() {
        primaryRepo.findAll().forEach(primary -> {
            primary.setName("修改后的" + primary.getName());
            primaryRepo.save(primary);

            SecondMultiTable second = new SecondMultiTable();
            BeanUtil.copyProperties(primary, second);
            secondRepo.save(second);
        });
    }

    @Test
    public void testDelete() {
        primaryRepo.deleteAll();

        secondRepo.deleteAll();
    }

    @Test
    public void testSelect() {
        List<PrimaryMultiTable> primary = primaryRepo.findAll();
        log.info("【primary】= {}", primary);

        List<SecondMultiTable> second = secondRepo.findAll();
        log.info("【second】= {}", second);
    }

}

