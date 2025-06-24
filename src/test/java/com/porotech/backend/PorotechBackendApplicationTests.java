package com.porotech.backend;

import com.porotech.backend.datatransfer.mqtt.PoroMqttClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PorotechBackendApplicationTests {

	@Test
	void contextLoads() {
	}
	@MockBean
	private PoroMqttClient poroMqttClient;

}
