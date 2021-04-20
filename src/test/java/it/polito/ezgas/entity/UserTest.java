package it.polito.ezgas.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest{

	private User us;

	@Before
	public void clearUs(){
		us = new User();
	}

	@Test
	public void emptyConstructor() {
		// Empty constructor
		us = new User();
		assert(us != null);
	}

	@Test
	public void constructor() {
		us = new User("name", "password","user@email.com",3);
		assert(us.getUserName() == "name");
		assert(us.getPassword() == "password");
		assert(us.getEmail() == "user@email.com");
		assert(us.getReputation() == 3);
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
		us.setReputation(3);
		assert(3 == us.getReputation());
	}

	@Test
	public void isAdmin() {
		us.setAdmin(true);
		assert(true == us.getAdmin());
	}
}
