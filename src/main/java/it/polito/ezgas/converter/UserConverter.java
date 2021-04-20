package it.polito.ezgas.converter;

import it.polito.ezgas.entity.User;

import java.util.List;
import java.util.stream.Collectors;

import it.polito.ezgas.dto.UserDto;


public class UserConverter {
	
	public static User toUser (UserDto userDto) {
		User item = new User();
		item.setUserId(userDto.getUserId());
		item.setUserName(userDto.getUserName());
		item.setPassword(userDto.getPassword());
		item.setEmail(userDto.getEmail());
		item.setReputation(userDto.getReputation());
		item.setAdmin(userDto.getAdmin());
		
		return item;
	}
	
	public static UserDto toUserDto (User user) {
		UserDto item= new UserDto();
		item.setUserId(user.getUserId());
		item.setUserName(user.getUserName());
		item.setPassword(user.getPassword());
		item.setEmail(user.getEmail());
		item.setReputation(user.getReputation());
		item.setAdmin(user.getAdmin());
		return item;
		
	}
	public static List<UserDto> toUserDtoList(List<User> userList){
		return userList.stream().map(item -> toUserDto(item)).collect(Collectors.toList());
	}
	
	public static List<User> toUserList(List<UserDto> userDtoList){
		return userDtoList.stream().map(item -> toUser(item)).collect(Collectors.toList());
	}
}

