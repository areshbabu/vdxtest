package com.vdx.ConfigManageServer;

import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
//@ContextConfiguration(classes = {TestWebConfig.class, TestBackEndConfiguration.class})
@TestInstance(Lifecycle.PER_CLASS)
class ConfigManageServerApplicationTests {
	
	@Test
	public void contextLoads() {
	}

}
