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

import exception.InvalidUserException;
import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DecreaseUserReputation extends InteractionDB {
	@Autowired
	UserService userService;

	private void simpleUSList(String status) {
		User u1 = new User();
		User u2 = new User();
		User u3 = new User();
		
		u1.setUserName("U1");
		u2.setUserName("U2");
		u3.setUserName("U3");
		
		switch (status) {
		case "normal":
			u1.setReputation(3);
			break;
		case "edge":
			u1.setReputation(-5);
			break;
		default:
				break;
		}
		this.localUserList = new ArrayList<User>(Arrays.asList(u1,u2,u3));
		this.getUserRepo().save(this.localUserList);
	}
	
	@Test
	public void invalidId() {
		simpleUSList("");
		
		UserDto u = new UserDto();
		try {
			u = userService.getUserById(-5);
		} catch (Exception e) {
			assert(true);
			return;
		}
		fail("Unexpected absence of exception");
	}
	
	@Test
	public void insertNull() {
		simpleUSList("");
		
		UserDto u = new UserDto();
		try {
			u = userService.getUserById(null);
		} catch (Exception e) {
			assert(true);
			return;
		}
		fail("Unexpected absence of exception");
	}

	@Test
	public void nonExistentId() {
		simpleUSList("");
		
		UserDto u = new UserDto();
		try {
			u = userService.getUserById(this.nonExistentUserId());
		} catch (Exception e) {
			fail("Unexpected exception " + e);
		}
		assert(u==null);
	}

	@Test
	public void existentId() {
		simpleUSList("normal");

		User u = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U1")).collect(Collectors.toList()).get(0);
		
		Integer r = null;
		
		try {
			r = userService.decreaseUserReputation(u.getUserId());
		} catch (InvalidUserException e) {
			fail("Unexpected exception " + e);
		}
		
		assert(r == 2);
	}
	
	@Test
	public void existentIdMinReputation() {
		simpleUSList("edge");

		User u = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U1")).collect(Collectors.toList()).get(0);
		
		Integer r = null;
		try {
			r = userService.decreaseUserReputation(u.getUserId());
		} catch (InvalidUserException e) {
			fail("Unexpected exception " + e);
		}
		
		assert(r == -5);
	}
}