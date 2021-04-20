package it.polito.ezgas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;

@SpringBootApplication
public class BootEZGasApplication {
	
	@Autowired
	private GasStationRepository gasStationRepository;
	@Autowired
	private UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BootEZGasApplication.class, args);
	}
	
	@PostConstruct
	public void setupDbWithData() throws SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:h2:./data/memo", "sa", "password");
		conn.close();
		
//		userRepository.deleteAll();
		if(userRepository.findUserByAdmin(true).isEmpty()) {
			System.out.println("user new"); 
			User u1= new User("admin", "admin", "admin@ezgas.com", 5);
			u1.setAdmin(true);
			userRepository.save(u1);
			User u2= new User("normalUser", "user", "user@ezgas.com", 3);
			u2.setAdmin(false);
			userRepository.save(u2);
		}
		
		User tmp = userRepository.findAll().get(1);
//		gasStationRepository.deleteAll();
		if(gasStationRepository.count() == 0) {			
			GasStation gs = new GasStation("name", "address", true, false, false, false, false, true, "Enjoy", 45.0677, 7.6824, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52, tmp.getUserId(), "03-25-2019", 0.0);
			gasStationRepository.save(gs);
			
			GasStation gs2 = new GasStation("name2", "address", false, true, false, false, false, true, "Car2Go", 45.06, 7.68, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52, tmp.getUserId(), "03-25-2019", 0.0);
			gasStationRepository.save(gs2);
			
			GasStation gs3 = new GasStation("name3", "address", true, false, true, false, false, true, "Car2Go", 49.0671, 7.6824, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52, tmp.getUserId(), "03-25-2019", 0.0);
			gasStationRepository.save(gs3);
	
			GasStation gs4 = new GasStation("name4", "address", false, true, false, true, false, true, "Enjoy", 45.0679, 7.6822, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52, tmp.getUserId(), "03-25-2019", 0.0);
			gasStationRepository.save(gs4);
			
			GasStation gs5 = new GasStation("name5", "address", false, false, false, false, true, true, "Enjoy", 49.0677, 7.6828, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52, tmp.getUserId(), "03-25-2019", 0.0);
			gasStationRepository.save(gs5);
		}
	}

}
