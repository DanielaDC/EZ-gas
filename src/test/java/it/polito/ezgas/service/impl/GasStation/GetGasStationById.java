package it.polito.ezgas.service.impl.GasStation;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import exception.InvalidGasStationException;
import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.service.GasStationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetGasStationById extends InteractionDB {
	@Autowired
	GasStationService gasStationService;

	private void simpleGSList() {
		GasStation g1 = new GasStation();
		GasStation g2 = new GasStation();
		GasStation g3 = new GasStation();

		g1.setGasStationName("G1");
		g2.setGasStationName("G2");
		g3.setGasStationName("G3");
		
		this.localGasStationList = new ArrayList<GasStation>(Arrays.asList(g1,g2,g3));
		this.getGasRepo().save(this.localGasStationList);
	}
	
	@Test
	public void invalidId() {
		simpleGSList();
		
		GasStationDto g = new GasStationDto();
		try {
			g = gasStationService.getGasStationById(-5);
		} catch (Exception e) {
			assert(true);
			return;
		}
		fail("Unexpected absence of exception");
	}

	@Test
	public void nonExistentId() {
		simpleGSList();
		
		GasStationDto g = new GasStationDto();
		try {
			g = gasStationService.getGasStationById(this.nonExistentGasStationId());
		} catch (Exception e) {
			fail("Unexpected exception " + e);
		}
		assert(g==null);
	}

	@Test
	public void existentId() {
		simpleGSList();

		this.localGasStationList = this.getGasRepo().findAll().stream().filter((GasStation g)->{
			 return g.getGasStationName() == "G1";
		 }).collect(Collectors.toList());
		
		GasStationDto g = new GasStationDto();
		try {
			g = gasStationService.getGasStationById(this.localGasStationList.get(0).getGasStationId());
		} catch (Exception e) {
			fail("Unexpected exception " + e);
		}
		
		assert(g.getGasStationName() == this.localGasStationList.get(0).getGasStationName());
	}
}