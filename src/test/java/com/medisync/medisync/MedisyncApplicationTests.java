package com.medisync.medisync;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MedisyncApplicationTests extends AbstractIntegrationTest {  // ← EXTENDER LA CLASE BASE

	@Test
	void contextLoads() {
		System.out.println("Context loaded successfully with PostgreSQL");
	}
}
