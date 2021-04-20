package it.polito.ezgas.service.impl.User;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Login extends InteractionDB {
	@Autowired
	UserService userService;

	private void simpleUSList() {
		User u1 = new User();
		User u2 = new User();
		User u3 = new User();
		
		u1.setUserName("U1");
		u1.setEmail("email");
		u1.setPassword("password");
		u2.setUserName("U2");
		u3.setUserName("U3");
		
		this.localUserList = new ArrayList<User>(Arrays.asList(u1,u2,u3));
		this.getUserRepo().save(this.localUserList);
	}
	
	@Test
	public void invalidId() {
		simpleUSList();
		
		IdPw id = new IdPw("email2","pass");
		LoginDto log = new LoginDto();
		try {
			log = userService.login(id);
		} catch (InvalidLoginDataException e) {
			assert(true);
			return;
		}
		fail("Unexpected absence of exception");
	}

	
	@Test
	public void existentIdWrongPass() {
		simpleUSList();

		IdPw id = new IdPw("email","pass");
		LoginDto log = new LoginDto();
		
		try {
			log = userService.login(id);
		} catch (InvalidLoginDataException e) {
			assert(true);
			return;
		}
		fail("Unexpected absence of exception");
	}
	
	@Test
	public void existentIdRightPass() {
		simpleUSList();

		IdPw id = new IdPw("email","password");
		LoginDto log = new LoginDto();
		
		try {
			log = userService.login(id);
		} catch (Exception e) {
			assert(true);
			return;
		}
		assert(log.getUserName()=="U1");
	}
}