package it.polito.ezgas.service.impl.GasStation;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import exception.InvalidGasTypeException;
import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.service.GasStationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetGasStationsByGasolineType extends InteractionDB {
	@Autowired
	GasStationService gasStationService;

	private void simpleGSList() {
		//<gasolineType>price = 4, <gasolineType>flag
		GasStation g1 = new GasStation("G1","addr", true, true, true, true, true, false, "Enjoy", 1.0, 1.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 1, "2019-03-25 16:30:30", 1.0);
		//<gasolineType>price = 1, <gasolineType>flag = true
		GasStation g2 = new GasStation("G2","addr", true, true, true, true, true, false, "Enjoy", 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1, "2019-03-25 16:30:30", 1.0);
		//<gasolineType>price = 3, <gasolineType>flag = true
		GasStation g3 = new GasStation("G3","addr", true, true, true, true, true, false, "Enjoy", 1.0, 1.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 1, "2019-03-25 16:30:30", 1.0);
		//<gasolineType>price = 2, <gasolineType>flag = false
		GasStation g4 = new GasStation("G4","addr", false, false, false, false, false, false, "Enjoy", 1.0, 1.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 1, "2019-03-25 16:30:30", 1.0);
		
		
		this.localGasStationList = new ArrayList<GasStation>(Arrays.asList(g1,g2,g3, g4));
		this.getGasRepo().save(this.localGasStationList);

		this.localGasStationList = this.getGasRepo().findAll().stream().filter((GasStation g)->{
			 return g.getGasStationName() == "G1" ||
					g.getGasStationName() == "G2" ||
					g.getGasStationName() == "G3";
		 }).collect(Collectors.toList());
	}
	
	@Test
	public void invalidGasType() {
		this.getGasRepo().save(this.localGasStationList);
		
		//All the prices are the same, so the order is the same
		Collections.sort(this.localGasStationList, (e1,e2)->{
			return (int) (100*(e2.getDieselPrice()-e1.getDieselPrice())); 
					});
		
		List<GasStationDto> list = new ArrayList<GasStationDto>();

		try {
			list = gasStationService.getGasStationsByGasolineType("invalidString");
		} catch (InvalidGasTypeException e) {
			assert(true);
			return;
		}
	}
	
	@Test
	public void emptyDB() {
		this.getGasRepo().save(this.localGasStationList);
		this.localGasStationList = new ArrayList<GasStation>();
		
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		String fuels[] = {"diesel", "super", "superplus", "gas", "methane"};
		for(String fuelType : fuels ) {

			try {
				list = gasStationService.getGasStationsByGasolineType(fuelType);
			} catch (Exception e) {
				fail("Unexpected exception " + e);
			}

			assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		}
	}
	
	@Test
	public void oneElement() {
		GasStation g1 = new GasStation("G1","addr", true, true, true, true, true, false, "Enjoy", 1.0, 1.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 1, "2019-03-25 16:30:30", 1.0);
		this.localGasStationList.add(g1);
		this.getGasRepo().save(this.localGasStationList);

		this.localGasStationList = this.getGasRepo().findAll().stream().filter((GasStation g)->{
			 return g.getGasStationName() == "G1" ||
					g.getGasStationName() == "G2" ||
					g.getGasStationName() == "G3";
		 	}).collect(Collectors.toList());
		
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		String fuels[] = {"diesel", "super", "superplus", "gas", "methane"};
		for(String fuelType : fuels ) {

			try {
				list = gasStationService.getGasStationsByGasolineType(fuelType);
			} catch (Exception e) {
				fail("Unexpected exception " + e);
			}

			assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		}
	}
	
	@Test
	public void moreElements() {
		simpleGSList();
		
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		String fuels[] = {"diesel", "super", "superplus", "gas", "methane"};
		for(String fuelType : fuels ) {

			try {
				list = gasStationService.getGasStationsByGasolineType(fuelType);
			} catch (Exception e) {
				fail("Unexpected exception " + e);
			}

			assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		}
	}
}