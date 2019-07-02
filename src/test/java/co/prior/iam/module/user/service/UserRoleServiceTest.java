package co.prior.iam.module.user.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.prior.iam.module.user.model.response.UserRoleObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleServiceTest {

	@Autowired
	private UserRoleService userRoleService;
	
	@Test
	public void testGetUserRoleObject() {
		long userId = 125;
		List<UserRoleObject> userRoleObject = this.userRoleService.getUserRoleObject(userId);
		System.out.println("userRoleObject: " + userRoleObject);
	}

}
