package com.example.disconf.conifg;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.stereotype.Service;

@Service
@DisconfFile(filename = "common.properties")
public class DemoBean {

    private String base_url;
    private String tenantId;

    @DisconfFileItem(name="uyun.baseurl",associateField = "base_url")
    public String getBase_url() {
        return base_url;
    }
    @DisconfFileItem(name="default.tenant.id",associateField = "tenantId")
    public String   getTenantId() {
        return tenantId;
    }

}
