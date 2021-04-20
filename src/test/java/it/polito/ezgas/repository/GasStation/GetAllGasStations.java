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
public class GetAllGasStations extends InteractionDB {
	
	private void initRepositoryWithItems() {
		GasStation gs1 = new GasStation("gasStationName1", "gasStationAddress1", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		GasStation gs2 = new GasStation("gasStationName2", "gasStationAddress2", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		GasStation gs3 = new GasStation("gasStationName3", "gasStationAddress3", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		this.getGasRepo().save(gs1);
		this.getGasRepo().save(gs2);
		this.getGasRepo().save(gs3);
	}

	@Test
	public void testGetAllGasStations() {
		initRepositoryWithItems();
		List<GasStation> gsList = this.getGasRepo().findAll();
		if(gsList.size() != 3)
			fail("List of GasStation has not the expected size");
		else {
			Boolean test1 = gsList.get(0).getGasStationName().contentEquals("gasStationName1");
			Boolean test2 = gsList.get(1).getGasStationName().contentEquals("gasStationName2");
			Boolean test3 = gsList.get(2).getGasStationName().contentEquals("gasStationName3");
			if(test1 && test2 && test3){
				assert(true);
			}
			else {
				fail("List of GasStation has not the expected items");
			}
		}
	}

}
