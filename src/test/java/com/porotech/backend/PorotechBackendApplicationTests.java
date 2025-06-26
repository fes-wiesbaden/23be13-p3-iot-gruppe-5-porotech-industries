package com.porotech.backend;

import com.porotech.backend.datatransfer.mqtt.PoroMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class PorotechBackendApplicationTests {

	@Test
	void contextLoads() {
	}
	@TestConfiguration
	static class MockMqttClientConfig {
		@Bean
		public PoroMqttClient poroMqttClient() {
            try {
                return new PoroMqttClient("tcp://localhost:1883") {
                };
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }
	}
	@SuppressWarnings("resource")
	@Container
	static PostgreSQLContainer<?> postgres =
			new PostgreSQLContainer<>("postgres:15-alpine")
					.withDatabaseName("porocar")
					.withUsername("porocar")
					.withPassword("secret")
					.withExposedPorts(5432);

	@DynamicPropertySource
	static void overrideDataSourceProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url",    postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

}
