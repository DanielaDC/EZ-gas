package it.polito.ezgas.repository.User;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetAllUser extends InteractionDB {
	
	
	private void initRepositoryWithItems() {
		User us1 = new  User( "UserName1", "UserPassword1", "UserEmail1",-2);
		User us2 = new  User( "UserName2", "UserPassword2", "UserEmail2", 3);
		User us3 = new  User( "UserName3", "UserPassword3", "UserEmail3", 4);
		this.getUserRepo().save(us1);
		this.getUserRepo().save(us2);
		this.getUserRepo().save(us3);
	}
	
	@Test
	public void testGetAllUser() {
		initRepositoryWithItems();
		List<User> usList = this.getUserRepo().findAll();
		if(usList.size() != 3)
			fail("List of User has not the expected size");
		else {
			Boolean test1 = usList.get(0).getUserName().contentEquals("UserName1");
			Boolean test2 = usList.get(1).getUserName().contentEquals("UserName2");
			Boolean test3 = usList.get(2).getUserName().contentEquals("UserName3");
			if(test1 && test2 && test3){
				assert(true);
			}
			else {
				fail("List of User has not the expected items");
			}
		}
	}

}
