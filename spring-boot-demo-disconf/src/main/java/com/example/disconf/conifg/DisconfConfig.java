package com.example.disconf.conifg;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
@DisconfFile(filename = "common.properties")
public class DisconfConfig {

       private String  baseUrl;

       @DisconfFileItem(name ="config.service.url",associateField ="baseUrl")
       public String  getBaseUrl(){
          return   baseUrl;
        }

}
