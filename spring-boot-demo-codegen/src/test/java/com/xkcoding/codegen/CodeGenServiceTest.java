package com.xkcoding.codegen;

import cn.hutool.core.io.IoUtil;
import cn.hutool.db.Entity;
import com.xkcoding.codegen.common.PageResult;
import com.xkcoding.codegen.entity.GenConfig;
import com.xkcoding.codegen.entity.TableRequest;
import com.xkcoding.codegen.service.CodeGenService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * <p>
 * 代码生成service测试
 * </p>
 *
 * @package: com.xkcoding.codegen
 * @description: 代码生成service测试
 * @author: yangkai.shen
 * @date: Created in 2019-03-22 10:34
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CodeGenServiceTest {
    @Autowired
    private CodeGenService codeGenService;

    @Test
    public void testTablePage() {
        TableRequest request = new TableRequest();
        request.setCurrentPage(1);
        request.setPageSize(10);
        request.setPrepend("jdbc:mysql://");
        request.setUrl("127.0.0.1:3306/spring-boot-demo");
        request.setUsername("root");
        request.setPassword("root");
        request.setTableName("sec_");
        PageResult<Entity> pageResult = codeGenService.listTables(request);
        log.info("【pageResult】= {}", pageResult);
    }

    @Test
    @SneakyThrows
    public void testGeneratorCode() {
        GenConfig config = new GenConfig();

        TableRequest request = new TableRequest();
        request.setPrepend("jdbc:mysql://");
        request.setUrl("127.0.0.1:3306/spring-boot-demo");
        request.setUsername("root");
        request.setPassword("root");
        request.setTableName("shiro_user");
        config.setRequest(request);

        config.setModuleName("shiro");
        config.setAuthor("Yangkai.Shen");
        config.setComments("用户角色信息");
        config.setPackageName("com.xkcoding");
        config.setTablePrefix("shiro_");

        byte[] zip = codeGenService.generatorCode(config);
        OutputStream outputStream = new FileOutputStream(new File("/Users/yangkai.shen/Desktop/" + request.getTableName() + ".zip"));
        IoUtil.write(outputStream, true, zip);
    }

}
