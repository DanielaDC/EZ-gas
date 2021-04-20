package it.polito.ezgas.repository.User;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetUserbyEmail extends InteractionDB{

	
	private void initRepositoryWithItems() {
		User us1 = new  User( "UserName1", "UserPassword1", "UserEmail1", 2);
		User us2 = new  User( "UserName2", "UserPassword2", "UserEmail2", -3);
		User us3 = new  User( "UserName3", "UserPassword3", "UserEmail3", 5);
		this.getUserRepo().save(us1);
		this.getUserRepo().save(us2);
		this.getUserRepo().save(us3);
	}
	
	
	@Test
	public void testGetUserByEmail(){
		initRepositoryWithItems();
		User user = this.getUserRepo().findUserByEmail("UserEmail2");
		if( user.getEmail().contentEquals("UserEmail2")) {
			assert(true);
			}
			else 
				fail("The item is not found");
		}
		
	
}


