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
public class GetUserByAdmin extends InteractionDB {
	
	private void initRepositoryWithItems() {
		User us1 = new  User( "UserName1", "UserPassword1", "UserEmail1", 5);
		User us2 = new  User( "UserName2", "UserPassword2", "UserEmail2", -3);
		User us3 = new  User( "UserName3", "UserPassword3", "UserEmail3", 5);
		us1.setAdmin(true);
		us2.setAdmin(false);
		us3.setAdmin(true);

		this.getUserRepo().save(us1);
		this.getUserRepo().save(us2);
		this.getUserRepo().save(us3);
	}
	
	
	@Test
	public void testGetUserByAdmin(){
		initRepositoryWithItems();
		List<User> usList = this.getUserRepo().findUserByAdmin(true);
		if( usList.size()== 2) {
			if(usList.get(0).getAdmin() && usList.get(1).getAdmin()) {
				assert(true);
			}
			else 
				fail("The items are not admins");
		}
		else 
			fail("The list has not the correct number of admins");
	}
	
	
}
