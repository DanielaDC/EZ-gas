package it.polito.ezgas.service.impl.GasStation;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
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
public class GetGasStationByCarSharing extends InteractionDB {
	@Autowired
	GasStationService gasStationService;

	private void simpleGSList(String result) {
		//G1{id:0, lat=40.306, lon=60.8, <fuelType>price = 1, <gasolineType>flag = true, carsharing = “Enjoy”},
		GasStation g1 = new GasStation("G1","addr", true, true, true, true, true, false, "Enjoy", 40.306, 60.8, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1, "ts", 1.0);
		//G2{id:1, lat=40.3, lon=60.803, <fuelType>price = 3, <gasolineType>flag = false, carsharing = “Car2Go”},
		GasStation g2 = new GasStation("G2","addr", false, false, false, false, false, false, "Car2Go", 40.3, 60.803, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 1, "ts", 1.0);
		//G3{id:2, lat=40.304, lon=60.8, <fuelType>price = 6, <gasolineType>flag = false, carsharing = “Enjoy”},
		GasStation g3 = new GasStation("G3","addr", false, false, false, false, false, false, "Enjoy", 40.304, 60.8, 6.0, 6.0, 6.0, 6.0, 6.0, 6.0, 1, "ts", 1.0);
		//G4{id:3, lat=40.305, lon=60.8, <fuelType>price = 5, <gasolineType>flag = true, carsharing = “Enjoy”},
		GasStation g4 = new GasStation("G4","addr", true, true, true, true, true, false, "Enjoy", 40.305, 60.8, 5.0, 5.0, 5.0, 5.0, 5.0, 6.0, 1, "ts", 1.0);
		//G5{id:4, lat=40.3, lon=60.804, <fuelType>price = 4, <gasolineType>flag = false, carsharing =“Car2Go”},
		GasStation g5 = new GasStation("G5","addr", false, false, false, false, false, false, "Car2Go", 40.3, 60.804, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 1, "ts", 1.0);
		//G6{id:5, lat=40.3, lon=61.81, <fuelType>price = 2, <gasolineType>flag = false, carsharing = “Car2Go”}
		GasStation g6 = new GasStation("G6","addr", false, false, false, false, false, false, "Car2Go", 40.3, 61.81, 2.0, 2.0, 2.0, 2.0, 2.0, 4.0, 1, "ts", 1.0);
		
		
		this.localGasStationList = new ArrayList<GasStation>(Arrays.asList(g1,g2,g3,g4,g5,g6));
		this.getGasRepo().save(this.localGasStationList);
		
		switch (result){
			case "nullCarSharing":
				this.localGasStationList = this.getGasRepo().findAll();
				break;
			case "correctCarSharing1":
				this.localGasStationList = this.getGasRepo().findAll().stream().filter((GasStation g)->{
					 return g.getGasStationName() == "G1" ||
							g.getGasStationName() == "G3" ||
							g.getGasStationName() == "G4";
				 }).collect(Collectors.toList());
				break;
			case "correctCarSharing2":
				this.localGasStationList = this.getGasRepo().findAll().stream().filter((GasStation g)->{
					 return g.getGasStationName() == "G2" ||
							g.getGasStationName() == "G5" ||
							g.getGasStationName() == "G6";
				 }).collect(Collectors.toList());
				break;
			default:
				this.localGasStationList = new ArrayList<GasStation>();
				break;
		}
	}
	
	@Test
	public void incorrectCarSharing() {
		simpleGSList("");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationByCarSharing("CSGo");
			assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		
	}
	
	@Test
	public void nullCarSharing() {
		simpleGSList("nullCarSharing");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationByCarSharing(null);
			assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		
	}

	
	@Test
	public void correctCarSharing1() {
		simpleGSList("correctCarSharing1");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationByCarSharing("Enjoy");
			assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		} catch (Exception e) {
			fail("Unexpected exception " + e);
		}
		
	}

	
	@Test
	public void correctCarSharing2() {
		simpleGSList("correctCarSharing2");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationByCarSharing("Car2Go");
			assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		
	}
}