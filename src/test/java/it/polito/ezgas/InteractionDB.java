package it.polito.ezgas;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InteractionDB {

	@Autowired
	private GasStationRepository gasStationRepository;
	@Autowired
	private UserRepository userRepository;
	
	protected List<GasStation> permanentGasStationList;
	protected List<User> permanentUserList;
	protected List<GasStation> localGasStationList;
	protected List<User> localUserList;
	
	public InteractionDB() {
		this.permanentGasStationList = new ArrayList<GasStation>();
		this.permanentUserList = new ArrayList<User>();
		this.localGasStationList = new ArrayList<GasStation>();
		this.localUserList = new ArrayList<User>();
	}
	
	@Before
	public void saveDB() {
		// Save the actual state of the DB
		this.permanentGasStationList=this.gasStationRepository.findAll();
		this.permanentUserList=this.userRepository.findAll();
		
		// Clear the DB
		this.gasStationRepository.deleteAll();
		this.userRepository.deleteAll();
	}
	
	@After
	public void restoreDB() {
		// Clear the DB
		this.gasStationRepository.deleteAll();
		this.userRepository.deleteAll();
		
		// Restore the previous data in the DB
		this.gasStationRepository.save(this.permanentGasStationList);
		this.userRepository.save(this.permanentUserList);
	}	
	
	/*
	 * See if there's a better method 
	*/
	public GasStationRepository getGasRepo() {
		return this.gasStationRepository;
	}
	
	public UserRepository getUserRepo() {
		return this.userRepository;
	}
	
	public static boolean compareLists(List<GasStation> l1, List<GasStation> l2) {
		if(l1.size() != l2.size())
			return false;
		
		l1.sort((a,b)->{return b.getGasStationName().compareTo(a.getGasStationName()); });
		l2.sort((a,b)->{return b.getGasStationName().compareTo(a.getGasStationName()); });
		
		for (int i=0; i<l1.size(); i++) {
			if(l1.get(i).getGasStationName() != l2.get(i).getGasStationName() )
				return false;
		}
		return true;
	}
	
	public static boolean compareListsUser(List<User> l1, List<User> l2) {
		if(l1.size() != l2.size())
			return false;
		
		l1.sort((a,b)->{return b.getUserName().compareTo(a.getUserName()); });
		l2.sort((a,b)->{return b.getUserName().compareTo(a.getUserName()); });
		
		for (int i=0; i<l1.size(); i++) {
			if(l1.get(i).getUserName() != l2.get(i).getUserName() )
				return false;
		}
		return true;
	}
	
	public Integer nonExistentGasStationId() {
		if(this.gasStationRepository.findAll().size()==0)
			return 1;
		return this.gasStationRepository.findAll().stream().map((GasStation g)->{return g.getGasStationId();}).max(Integer::compare).get() + 1;
	}
	
	public Integer nonExistentUserId() {
		if(this.userRepository.findAll().size()==0)
			return 1;
		return this.userRepository.findAll().stream().map((User u)->{return u.getUserId();}).max(Integer::compare).get() + 1;
	}
}
