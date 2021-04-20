package it.polito.ezgas.service.impl.GasStation;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.service.GasStationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetAllGasStations extends InteractionDB {
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
	public void emptyDB() {
		this.getGasRepo().save(this.localGasStationList);
		
		List<GasStationDto> list = new ArrayList<GasStationDto>();

		list = gasStationService.getAllGasStations();
		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
	}
	
	@Test
	public void oneElement() {
		GasStation g3 = new GasStation();
		g3.setGasStationName("G3");
		this.localGasStationList.add(g3);
		this.getGasRepo().save(this.localGasStationList);
		
		List<GasStationDto> list = new ArrayList<GasStationDto>();

		list = gasStationService.getAllGasStations();
		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
	}
	
	@Test
	public void moreElements() {
		simpleGSList();
		List<GasStationDto> list = new ArrayList<GasStationDto>();

		list = gasStationService.getAllGasStations();
		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
	}
}