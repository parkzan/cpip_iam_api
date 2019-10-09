package co.prior.iam;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.TimeZone;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IAMApplicationTests {

	@Test
	public void contextLoads() {

		System.out.print(TimeZone.getDefault());
	}
	
}
