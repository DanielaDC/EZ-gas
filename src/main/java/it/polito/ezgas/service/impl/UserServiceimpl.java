package it.polito.ezgas.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.UserService;

/**
 * Created by softeng on 27/4/2020.
 */
@Service
public class UserServiceimpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDto getUserById(Integer userId) throws InvalidUserException {
		
		if(userId < 0) throw new InvalidUserException("There is no User with the given Id");
		User user = userRepository.findOne(userId);
		if (user != null) {
			 return UserConverter.toUserDto(user);
		}
		return null;
		
	}
	
	@Override
	public UserDto saveUser(UserDto userDto) {
		User convItem = UserConverter.toUser(userDto);
		User user = userRepository.save(convItem);
		return UserConverter.toUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<UserDto> userlist = new ArrayList<>();
		userlist.addAll(UserConverter.toUserDtoList(userRepository.findAll()));
		return userlist;
	}

	@Override
	public Boolean deleteUser(Integer userId) throws InvalidUserException {
		if (userId <0) throw new InvalidUserException("The given User Id is not valid");
		if(userRepository.findOne(userId) == null)
			return false;
		userRepository.delete(userId);
		return true;
	}

	@Override
	public LoginDto login(IdPw credentials) throws InvalidLoginDataException {
		User user = userRepository.findUserByEmail(credentials.getUser());
		if (user == null) throw new InvalidLoginDataException("User not found. Check again your credentials");
		if (!(user.getPassword().equals(credentials.getPw()))) throw new InvalidLoginDataException("Password not valid. Check again your credentials"); 
		LoginDto loginDto = new LoginDto(user.getUserId(), user.getUserName(), "token", user.getEmail(), user.getReputation());
		loginDto.setAdmin(user.getAdmin());
		
		return loginDto;
	}

	
	@Override
	public Integer increaseUserReputation(Integer userId) throws InvalidUserException {
		User user = userRepository.findOne(userId);
		if (user != null) {
			if(user.getReputation() == 5)
				return user.getReputation();
			user.setReputation(user.getReputation()+1);
			userRepository.save(user);
			return user.getReputation();
		}
		throw new InvalidUserException("There is no User with the given Id");
	
	}

	@Override
	public Integer decreaseUserReputation(Integer userId) throws InvalidUserException {
		User user = userRepository.findOne(userId);
		if (user != null) {
			if(user.getReputation() == -5)
				return user.getReputation();
			user.setReputation(user.getReputation()-1);
			userRepository.save(user);
			return user.getReputation();
		}
	 throw new InvalidUserException("There is no User with the given Id");
	}
	
}
