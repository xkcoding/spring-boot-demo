package com.xkcoding.activiti;

import com.xkcoding.activiti.util.SecurityUtil;
import net.minidev.json.JSONValue;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoActivitiApplicationTests {

  @Autowired
  private ProcessRuntime processRuntime;
  @Autowired
  private SecurityUtil securityUtil;

  @Test
  public void contextLoads() {
    System.out.println("SpringBootDemoActivitiApplicationTests.contextLoads======================");
    securityUtil.logInAs("salaboy");
    Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
    processDefinitionPage.getContent().forEach(elt -> System.out.println(JSONValue.toJSONString(elt))
    );
    System.out.println("SpringBootDemoActivitiApplicationTests.contextLoads========================");
  }

}

