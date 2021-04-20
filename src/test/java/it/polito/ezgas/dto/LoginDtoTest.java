package it.polito.ezgas.dto;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginDtoTest{

	private LoginDto log;
	
	@Before
	public void clearLog(){
		log = new LoginDto();
	}
	
	@Test
	public void emptyConstructor() {
		// Empty constructor
		log = new LoginDto();
		assert(log != null);
	}
	
	@Test
	public void constructor() {
		// Constructor with parameters
		log = new LoginDto(11, "name", "tk", "mail", 11);
		assert(11 == log.getUserId());
		assert("name" == log.getUserName());
		assert("tk" == log.getToken());
		assert("mail" == log.getEmail());
		assert(11 == log.getReputation());
	}

	@Test
	public void userId() {
		log.setUserId(1);
		assert(1 == log.getUserId());
	}

	@Test
	public void userName() {
		log.setUserName("user");
		assert("user" == log.getUserName());
	}

	@Test
	public void token() {
		log.setToken("token");
		assert("token" == log.getToken());
	}

	@Test
	public void email() {
		log.setEmail("email");
		assert("email" == log.getEmail());
	}

	@Test
	public void reputation() {
		log.setReputation(1);
		assert(1 == log.getReputation());
	}

	@Test
	public void Admin() {
		log.setAdmin(true);
		assert(true == log.getAdmin());
	}

}