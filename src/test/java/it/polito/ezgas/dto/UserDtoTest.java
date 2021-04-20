package it.polito.ezgas.dto;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.dto.UserDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDtoTest{

	private UserDto us;

	@Before
	public void clearUs(){
		us = new UserDto();
	}

	@Test
	public void emptyConstructor() {
		// Empty constructor
		us = new UserDto();
		assert(us != null);
	}

	@Test
	public void constructor1() {
		us = new UserDto(121, "name", "password","user@email.com",4);
		assert(us.getUserId() == 121);
		assert(us.getUserName() == "name");
		assert(us.getPassword() == "password");
		assert(us.getEmail() == "user@email.com");
		assert(us.getReputation() == 4);
		assert(us.getAdmin() == false);
	}

	@Test
	public void constructor2() {
		us = new UserDto(121, "name", "password","user@email.com",4,true);
		assert(us.getUserId() == 121);
		assert(us.getUserName() == "name");
		assert(us.getPassword() == "password");
		assert(us.getEmail() == "user@email.com");
		assert(us.getReputation() == 4);
		assert(us.getAdmin() == true);
	}

	@Test
	public void UserId() {
		us.setUserId(1);
		assert(1 == us.getUserId());
	}

	@Test
	public void UserName() {
		us.setUserName("name");
		assert("name" == us.getUserName());
	}

	@Test
	public void UserPassword() {
		us.setPassword("password");
		assert("password" == us.getPassword());
	}

	@Test
	public void UserEmail() {
		us.setEmail("email");
		assert("email" == us.getEmail());
	}

	@Test
	public void UserReputation() {
		us.setReputation(4);
		assert(4 == us.getReputation());
	}

	@Test
	public void isAdmin() {
		us.setAdmin(true);
		assert(true == us.getAdmin());
	}
}
