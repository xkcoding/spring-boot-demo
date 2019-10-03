package com.xkcoding.orm.jpa.repository;

import com.xkcoding.orm.jpa.SpringBootDemoOrmJpaApplicationTests;
import com.xkcoding.orm.jpa.entity.Department;
import com.xkcoding.orm.jpa.entity.User;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * jpa 测试类
 * </p>
 *
 * @package: com.xkcoding.orm.jpa.repository
 * @description: jpa 测试类
 * @author: 76peter
 * @date: Created in 2018/11/7 14:09
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: 76peter
 */
@Slf4j
public class DepartmentDaoTest extends SpringBootDemoOrmJpaApplicationTests {
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private UserDao userDao;

    /**
     * 测试保存 ,根节点
     */
    @Test
    @Transactional
    public void testSave() {
        Collection<Department> departmentList = departmentDao.findDepartmentsByLevels(0);
        if(departmentList.size()==0){
            Department testSave1 = Department.builder().name("testSave1").orderno(0).levels(0).superior(null).build();
            Department testSave1_1 = Department.builder().name("testSave1_1").orderno(0).levels(1).superior(testSave1).build();
            Department testSave1_2 = Department.builder().name("testSave1_2").orderno(0).levels(1).superior(testSave1).build();
            Department testSave1_1_1 = Department.builder().name("testSave1_1_1").orderno(0).levels(2).superior(testSave1_1).build();
            departmentList.add(testSave1);
            departmentList.add(testSave1_1);
            departmentList.add(testSave1_2);
            departmentList.add(testSave1_1_1);
            departmentDao.saveAll(departmentList);

            Collection<Department> deptall = departmentDao.findAll();
            log.debug("【部门】= {}", JSONArray.toJSONString((List)deptall));
        }


        userDao.findById(1L).ifPresent(user -> {
            user.setName("添加部门");
            Department dept = departmentDao.findById(2L).get();
            user.setDepartmentList(departmentList);
            userDao.save(user);
        });
        User users = userDao.findById(1L).get();
        log.debug("用户部门={}", JSONArray.toJSONString((List)userDao.findById(1L).get().getDepartmentList()));


        departmentDao.findById(2L).ifPresent(dept -> {
            Collection<User> userlist = dept.getUserList();
            //关联关系由user维护中间表，department userlist不会发生变化，可以增加查询方法来处理
            log.debug("部门下用户={}", JSONArray.toJSONString((List)userlist));
        });


        userDao.findById(1L).ifPresent(user -> {
            user.setName("清空部门");
            user.setDepartmentList(null);
            userDao.save(user);
        });
        log.debug("用户部门={}", userDao.findById(1L).get().getDepartmentList());


    }


}
