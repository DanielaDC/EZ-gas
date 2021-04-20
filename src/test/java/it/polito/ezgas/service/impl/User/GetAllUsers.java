package it.polito.ezgas.service.impl.User;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetAllUsers extends InteractionDB {
	@Autowired
	UserService userService;

	private void simpleUSList() {
		User u1 = new User();
		User u2 = new User();
		User u3 = new User();
		
		u1.setUserName("U1");
		u2.setUserName("U2");
		u3.setUserName("U3");
		
		this.localUserList = new ArrayList<User>(Arrays.asList(u1,u2,u3));
		this.getUserRepo().save(this.localUserList);
	}
	
	@Test
	public void emptyDB() {
		this.getUserRepo().save(this.localUserList);
		
		List<UserDto> list = new ArrayList<UserDto>();

		list = userService.getAllUsers();
		assert(compareListsUser(UserConverter.toUserList(list), this.localUserList));
	}
	
	@Test
	public void oneElement() {
		User u3 = new User();
		u3.setUserName("U3");
		this.localUserList.add(u3);
		this.getUserRepo().save(this.localUserList);
		
		List<UserDto> list = new ArrayList<UserDto>();

		list = userService.getAllUsers();
		assert(compareListsUser(UserConverter.toUserList(list), this.localUserList));
	}
	
	@Test
	public void moreElements() {
		simpleUSList();
		List<UserDto> list = new ArrayList<UserDto>();

		list = userService.getAllUsers();
		assert(compareListsUser(UserConverter.toUserList(list), this.localUserList));
	}
}