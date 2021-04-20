package it.polito.ezgas.dto;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class IdPwTest{

	private IdPw ip;
	
	@Before
	public void clearIdPw(){
		ip = new IdPw();
	}
	
	@Test
	public void emptyConstructor() {
		// Empty constructor
		ip = new IdPw();
		assert(ip != null);
	}
	
	@Test
	public void constructor() {
		// Constructor with parameters
		ip = new IdPw("id", "pw");
		assert("id" == ip.getUser());
		assert("pw" == ip.getPw());
	}

	@Test
	public void user() {
		ip.setUser("user");
		assert("user" == ip.getUser());
	}

	@Test
	public void password() {
		ip.setPw("password");
		assert("password" == ip.getPw());
	}
}