package it.polito.ezgas.repository.GasStation;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.entity.GasStation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetGasStationById extends InteractionDB {
	
	private void initRepositoryWithItems() {
		GasStation gs1 = new GasStation("gasStationName1", "gasStationAddress1", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		GasStation gs2 = new GasStation("gasStationName2", "gasStationAddress2", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		GasStation gs3 = new GasStation("gasStationName3", "gasStationAddress3", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		this.getGasRepo().save(gs1);
		this.getGasRepo().save(gs2);
		this.getGasRepo().save(gs3);
	}

	@Test
	public void testGetGasStationById() {
		initRepositoryWithItems();
		List<Integer> gsIdList = this.getGasRepo().findAll().stream().map(item -> item.getGasStationId()).collect(Collectors.toList());
		int extraId = getExtraId(gsIdList);
		if(gsIdList.size() == 3) {
			GasStation gs1 = this.getGasRepo().findOne(gsIdList.get(0));
			GasStation gs2 = this.getGasRepo().findOne(extraId);
			if(gs1 != null && gs2 == null)
				assert(true);
			else
				fail("Unexpected error");
		}
		else
			fail("List of GasStation has not the expected size");
	}
	
	private int getExtraId(List<Integer> idList) {
		int counter = 0;
		boolean trovato = false;
		while(!trovato) {
			if(!idList.contains(counter))
				trovato = true;
			else
				counter++;
		}
		return counter;
	}

}
