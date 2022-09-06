package com.xkcoding.task.xxl.job.config;

import com.xkcoding.task.xxl.job.config.props.XxlJobProps;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * xxl-job 自动装配
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-08-07 10:20
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(XxlJobProps.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class XxlJobConfig {
    private final XxlJobProps xxlJobProps;

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProps.getAdmin().getAddress());
        xxlJobSpringExecutor.setAccessToken(xxlJobProps.getAccessToken());
        xxlJobSpringExecutor.setAppName(xxlJobProps.getExecutor().getAppName());
        xxlJobSpringExecutor.setIp(xxlJobProps.getExecutor().getIp());
        xxlJobSpringExecutor.setPort(xxlJobProps.getExecutor().getPort());
        xxlJobSpringExecutor.setLogPath(xxlJobProps.getExecutor().getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProps.getExecutor().getLogRetentionDays());

        return xxlJobSpringExecutor;
    }

}
