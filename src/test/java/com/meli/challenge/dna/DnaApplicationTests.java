package com.meli.challenge.dna;

import com.meli.challenge.dna.controller.DnaRestController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@TestPropertySource(properties = {"spring.data.mongodb.uri=mongodb+srv://test:test@melichallengecluster.5eppaxs.mongodb.net/dnaTestDatabase?retryWrites=true&w=majority"})
@RunWith(SpringRunner.class)
@SpringBootTest
class DnaApplicationTests {

	@Autowired
	private DnaRestController dnaRestController;

	@Test
	void contextLoads() {

	}

}
