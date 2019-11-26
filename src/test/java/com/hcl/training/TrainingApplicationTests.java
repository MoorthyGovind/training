package com.hcl.training;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class TrainingApplicationTests {

	@Test
    public void shouldLoadApplicationContext() {
    }
	
	 @Test
     public void applicationTest() {
		 TrainingApplication.main(new String[] {});
     }

}
