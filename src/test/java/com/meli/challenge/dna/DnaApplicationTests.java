package com.meli.challenge.dna;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {"spring.data.mongodb.uri=mongodb+srv://test:test@melichallengecluster.5eppaxs.mongodb.net/dnaTestDatabase?retryWrites=true&w=majority"})
@SpringBootTest
class DnaApplicationTests {

	@Test
	void contextLoads() {
	}

}
