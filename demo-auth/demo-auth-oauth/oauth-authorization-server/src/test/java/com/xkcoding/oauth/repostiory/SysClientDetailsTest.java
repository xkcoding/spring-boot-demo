package com.xkcoding.oauth.repostiory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020-01-06 13:10
 */
@DataJpaTest
public class SysClientDetailsTest {
    @Autowired
    private SysClientDetailsRepository sysClientDetailsRepository;

    @Test
    public void autowiredSuccessWhenPassed() {
        assertNotNull(sysClientDetailsRepository);
    }

}
