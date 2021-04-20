package it.polito.ezgas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import it.polito.ezgas.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

		public List<User> findUserByAdmin(boolean admin);
		public User findUserByEmail(String email);
}
