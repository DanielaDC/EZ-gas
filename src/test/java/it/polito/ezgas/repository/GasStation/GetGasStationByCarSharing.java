package it.polito.ezgas.repository.GasStation;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.entity.GasStation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetGasStationByCarSharing extends InteractionDB {

	private void initRepositoryWithItems() {
		GasStation gs1 = new GasStation("gasStationName1", "gasStationAddress1", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		GasStation gs2 = new GasStation("gasStationName2", "gasStationAddress2", false, true, false, true, false, false, "car2go", 1.0, 1.0, 1.2, 2.3, 3.4, 4.5, 5.6, 6.6, 0, "timestamp", 0.0);
		GasStation gs3 = new GasStation("gasStationName3", "gasStationAddress3", true, false, true, false, true, false, "car2go", 1.0, 1.0, 1.3, 2.4, 3.5, 4.6, 5.7,6.6,  0, "timestamp", 0.0);
		GasStation gs4 = new GasStation("gasStationName4", "gasStationAddress4", false, true, false, true, false, false, "enjoy", 1.0, 1.0, 1.4, 2.5, 3.6, 4.7, 5.8, 6.6, 0, "timestamp", 0.0);
		this.getGasRepo().save(gs1);
		this.getGasRepo().save(gs2);
		this.getGasRepo().save(gs3);
		this.getGasRepo().save(gs4);
	}
	
	@Test
	public void testGetGasStationByCarSharing() {
		initRepositoryWithItems();
		List<GasStation> gsList = this.getGasRepo().findGasStationByCarSharing("enjoy");
		if(gsList.size() == 2) {
			if(gsList.get(0).getCarSharing().contentEquals("enjoy") && gsList.get(1).getCarSharing().contentEquals("enjoy")) {
				assert(true);
			}
			else
				fail("The items have not the expected car sharing");
		}
		else
			fail("The list has not the expected number of item");
	}

}
