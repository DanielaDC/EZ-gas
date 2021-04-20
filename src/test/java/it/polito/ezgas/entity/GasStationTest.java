package it.polito.ezgas.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GasStationTest{

	private GasStation gs;
	
	@Before
	public void clearGs(){
		gs = new GasStation();
	}
	
	@Test
	public void emptyConstructor() {
		// Empty constructor
		gs = new GasStation();
		assert(gs != null);
	}

	@Test
	public void constructor() {
		// Constructor with parameters
		gs = new GasStation("name", "addr", true, true, true, true, true, true, "cs", 
				11.0, 11.0, 11.0, 11.0, 11.0, 11.0, 11.0, 11.0, 11, "time", 11.0);
		assert(gs.getGasStationName() == "name");
		assert(gs.getGasStationAddress() == "addr");
		assert(gs.getHasDiesel()== true);
		assert(gs.getHasSuper() == true);
		assert(gs.getHasSuperPlus() == true);
		assert(gs.getHasGas() == true);
		assert(gs.getHasMethane() == true);
		assert(gs.getHasPremiumDiesel() == true);
		assert(gs.getCarSharing() == "cs");
		assert(gs.getLat() == 11.0);
		assert(gs.getLon() == 11.0);
		assert(gs.getDieselPrice() == 11.0);
		assert(gs.getSuperPrice() == 11.0);
		assert(gs.getSuperPlusPrice() == 11.0);
		assert(gs.getGasPrice() == 11.0);
		assert(gs.getMethanePrice() == 11.0);
		assert(gs.getPremiumDieselPrice() == 11.0);
		assert(gs.getReportUser() == 11);
		assert(gs.getReportTimestamp() == "time");
		assert(gs.getReportDependability() == 11.0);
	}
	
	@Test
	public void GasStationId() {
		gs.setGasStationId(1);
		assert(1 == gs.getGasStationId());
	}

	@Test
	public void gasStationName() {
		gs.setGasStationName("name");
		assert("name" == gs.getGasStationName());
	}
	
	@Test
	public void gasStationAddress() {
		gs.setGasStationAddress("addr");
		assert("addr" == gs.getGasStationAddress());
	}
	
	@Test
	public void hasDiesel() {
		gs.setHasDiesel(true);
		assert(true == gs.getHasDiesel());
	}

	@Test
	public void hasSuper() {
		gs.setHasSuper(true);
		assert(true == gs.getHasSuper());
	}
	
	@Test
	public void hasSuperPlus() {
		gs.setHasSuperPlus(true);
		assert(true == gs.getHasSuperPlus());
	}
	
	@Test
	public void hasGas() {
		gs.setHasGas(true);
		assert(true == gs.getHasGas());
	}

	@Test
	public void hasMethane() {
		gs.setHasMethane(true);
		assert(true == gs.getHasMethane());
	}

	@Test
	public void hasPremiumDiesel() {
		gs.setHasPremiumDiesel(true);
		assert(true == gs.getHasPremiumDiesel());
	}
	
	@Test
	public void carSharing() {
		gs.setCarSharing("cs");
		assert("cs" == gs.getCarSharing());
	}
	
	@Test
	public void lat() {
		gs.setLat(1.0);
		assert(1.0 == gs.getLat());
	}

	@Test
	public void lon() {
		gs.setLon(1.0);
		assert(1.0 == gs.getLon());
	}
	
	@Test
	public void dieselPrice() {
		gs.setDieselPrice(1.0);
		assert(1.0 == gs.getDieselPrice());
	}

	@Test
	public void superPrice() {
		gs.setSuperPrice(1.0);
		assert(1.0 == gs.getSuperPrice());
	}

	@Test
	public void superPlusPrice() {
		gs.setSuperPlusPrice(1.0);
		assert(1 == gs.getSuperPlusPrice());
	}
	
	@Test
	public void gasPrice() {
		gs.setGasPrice(1.0);
		assert(1.0 == gs.getGasPrice());
	}
	
	@Test
	public void methanePrice() {
		gs.setMethanePrice(1.0);
		assert(1.0 == gs.getMethanePrice());
	}

	@Test
	public void premiumDieselPrice() {
		gs.setPremiumDieselPrice(1.0);
		assert(1.0 == gs.getPremiumDieselPrice());
	}

	@Test
	public void reportUser() {
		gs.setReportUser(1);
		assert(1 == gs.getReportUser());
	}
	
	@Test
	public void reportTimestamp() {
		gs.setReportTimestamp("time");
		assert("time" == gs.getReportTimestamp());
	}

	@Test
	public void reportDependability() {
		gs.setReportDependability(1.0);
		assert(1.0 == gs.getReportDependability());
	}

	@Test
	public void user() {
		User u = new User();
		gs.setUser(u);
		assert(u == gs.getUser());
	}
}