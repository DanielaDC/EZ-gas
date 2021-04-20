package it.polito.ezgas.converter;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConverterTest {
	
	
	@Test
	public void toUser() {
		UserDto usDto = new UserDto(20, "UserName", "UserPassword", "UserEmail", 4, true);
		User us = UserConverter.toUser(usDto);
		if( (us.getAdmin()== usDto.getAdmin()) && 
				(us.getUserId() == usDto.getUserId()) &&
				(us.getUserName().contentEquals(usDto.getUserName())) &&
				(us.getPassword().contentEquals(usDto.getPassword()))&&
				(us.getEmail().contentEquals(usDto.getEmail()))&&
				(us.getReputation() == usDto.getReputation()) ) {
			assert(true);
		}
		else
			fail("The User Entity and Dto are different");
	}
	
	@Test
	public void toUserDto() {
		User us = new User( "UserName", "UserPassword", "UserEmail", 4);
		us.setAdmin(true);
		UserDto usDto = UserConverter.toUserDto(us);
		if( (us.getAdmin()== usDto.getAdmin()) && 
				(us.getUserId() == usDto.getUserId()) &&
				(us.getUserName().contentEquals(usDto.getUserName())) &&
				(us.getPassword().contentEquals(usDto.getPassword()))&&
				(us.getEmail().contentEquals(usDto.getEmail()))&&
				(us.getReputation() == usDto.getReputation()) ) {
			assert(true);
		}
		else
			fail("The User Entity and Dto are different");
	}
	
	
	
	@Test
	public void toUserDtoList() {
		User us1 = new  User( "UserName1", "UserPassword1", "UserEmail1",-2);
		User us2 = new  User( "UserName2", "UserPassword2", "UserEmail2", 3);
		User us3 = new  User( "UserName3", "UserPassword3", "UserEmail3", 4);
		List<User> usList = new ArrayList<>();
		usList.add(us1);
		usList.add(us2);
		usList.add(us3);
		List<UserDto> usDtoList = UserConverter.toUserDtoList(usList);
		if(usDtoList.size() == usList.size()) {
			for(int k = 0; k < usDtoList.size(); k++) {
				if(usList.get(k).getUserId() != usDtoList.get(k).getUserId())
					fail("The User Entity and Dto Lists' items have different ids");
		  }
		 assert(true);
	    }
	  else
		fail("The User Entity and Dto Lists are different");
	}
	
	
	
	@Test
	public void toUserList() {
		UserDto usDto1 = new  UserDto( 20, "UserName1", "UserPassword1", "UserEmail1",-2, true);
		UserDto usDto2 = new  UserDto( 30, "UserName2", "UserPassword2", "UserEmail2", 3, false);
		UserDto usDto3 = new  UserDto( 14, "UserName3", "UserPassword3", "UserEmail3", 4, true);
		List<UserDto> usDtoList = new ArrayList<>();
		usDtoList.add(usDto1);
		usDtoList.add(usDto2);
		usDtoList.add(usDto3);
		List<User> usList = UserConverter.toUserList(usDtoList);
		if(usDtoList.size() == usList.size()) {
			for(int k = 0; k < usList.size(); k++) {
				if(usList.get(k).getUserId() != usDtoList.get(k).getUserId())
					fail("The User Entity and Dto Lists' items have different ids");
		  }
		 assert(true);
	    }
	  else
		fail("The User Entity and Dto Lists are different");
	}
}
