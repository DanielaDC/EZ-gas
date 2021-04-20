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

import exception.GPSDataException;
import exception.InvalidGasTypeException;
import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.service.GasStationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetGasStationsWithCoordinates extends InteractionDB {
	@Autowired
	GasStationService gasStationService;

	private void simpleGSList(String result) {
		//G1{id:0, lat=40.306, lon=60.8, <fuelType>price = 1, <gasolineType>flag = true, carsharing = “Enjoy”},
		GasStation g1 = new GasStation("G1","addr", true, true, true, true, true, false, "Enjoy", 40.306, 60.8, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1, "2019-03-25 16:30:30", 1.0);
		//G2{id:1, lat=40.3, lon=60.803, <fuelType>price = 3, <gasolineType>flag = true, carsharing = “Car2Go”},
		GasStation g2 = new GasStation("G2","addr", true, true, true, true, true, false, "Car2Go", 40.3, 60.803, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 1, "2019-03-25 16:30:30", 1.0);
		//G3{id:2, lat=40.304, lon=60.8, <fuelType>price = 6, <gasolineType>flag = false, carsharing = “Enjoy”},
		GasStation g3 = new GasStation("G3","addr", false, false, false, false, false, false, "Enjoy", 40.304, 60.8, 6.0, 6.0, 6.0, 6.0, 6.0, 6.0, 1, "2019-03-25 16:30:30", 1.0);
		//G4{id:3, lat=40.305, lon=60.8, <fuelType>price = 5, <gasolineType>flag = true, carsharing = “Enjoy”},
		GasStation g4 = new GasStation("G4","addr", true, true, true, true, true, false, "Enjoy", 40.305, 60.8, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 1, "2019-03-25 16:30:30", 1.0);
		//G5{id:4, lat=40.3, lon=60.804, <fuelType>price = 4, <gasolineType>flag = false, carsharing =“Car2Go”},
		GasStation g5 = new GasStation("G5","addr", false, false, false, false, false, false, "Car2Go", 40.3, 60.804, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 1, "2019-03-25 16:30:30", 1.0);
		//G6{id:5, lat=40.3, lon=65.81, <fuelType>price = 2, <gasolineType>flag = false, carsharing = “Car2Go”}
		GasStation g6 = new GasStation("G6","addr", false, false, false, false, false, false, "Car2Go", 40.3, 65.81, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 1, "2019-03-25 16:30:30", 1.0);
		
		
		this.localGasStationList = new ArrayList<GasStation>(Arrays.asList(g1,g2,g3,g4,g5,g6));
		this.getGasRepo().save(this.localGasStationList);
		switch (result){
			case "latLon":
				this.localGasStationList = this.getGasRepo().findAll().stream().filter((GasStation g)->{
					 return g.getGasStationName() == "G2" ||
							g.getGasStationName() == "G5" ||
							g.getGasStationName() == "G3" ||
							g.getGasStationName() == "G4" ||
							g.getGasStationName() == "G1";
				 }).collect(Collectors.toList());
				break;
			case "carSharing":
				this.localGasStationList = this.getGasRepo().findAll().stream().filter((GasStation g)->{
					 return g.getGasStationName() == "G3" ||
							g.getGasStationName() == "G4" ||
							g.getGasStationName() == "G1";
				 }).collect(Collectors.toList());
				break;
			case "fuelType":
				this.localGasStationList = this.getGasRepo().findAll().stream().filter((GasStation g)->{
					 return g.getGasStationName() == "G2" ||
							g.getGasStationName() == "G4" ||
							g.getGasStationName() == "G1";
				 }).collect(Collectors.toList());
				break;
			case "fuelTypeAndCarSharing":
				this.localGasStationList = this.getGasRepo().findAll().stream().filter((GasStation g)->{
					 return g.getGasStationName() == "G2";
				 }).collect(Collectors.toList());
				break;
			default:
				this.localGasStationList = new ArrayList<GasStation>();
				break;
		}
	}
	
	@Test
	public void wrongLat() {
		simpleGSList("");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsWithCoordinates(-95.0, -95.0, 10, "diesel", "Car2Go");
		} catch (GPSDataException e) {
			assert(true);
			return;
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		
		fail("Expected GPSDataException");
		
	}
	
	@Test
	public void wrongLon() {
		simpleGSList("");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsWithCoordinates(5.0, -195.0, 10, "diesel", "Car2Go");
		} catch (GPSDataException e) {
			assert(true);
			return;
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		
		fail("Expected GPSDataException");
		
	}
	
	@Test
	public void wrongLatAndLon() {
		simpleGSList("");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsWithCoordinates(185.0, -7095.0, 10, "diesel", "Car2Go");
		} catch (GPSDataException e) {
			assert(true);
			return;
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		
		fail("Expected GPSDataException");
		
	}
	
	@Test
	public void wrongFuelType() {
		simpleGSList("");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsWithCoordinates(5.0, -95.0, 10, "abcd", "Car2Go");
		} catch (InvalidGasTypeException e) {
			assert(true);
			return;
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		
		fail("Expected InvalidGasTypeException");
		
	}
	
	@Test
	public void wrongCarSharing() {
		simpleGSList("");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsWithCoordinates(5.0, -95.0, 10, "diesel", "CSGo");
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}

		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		
	}
	
	@Test
	public void wrongAll() {
		simpleGSList("");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsWithCoordinates(185.0, -7095.0, 10, "fmab", "CSGo");
		} catch (InvalidGasTypeException | GPSDataException e) {
			assert(true);
			return;
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		
		fail("Expected InvalidGasTypeException or GPSDataException");
		
	}
	
	@Test
	public void latLon() {
		simpleGSList("latLon");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsWithCoordinates(40.3, 60.8, 10, "null", "null");
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));		
	}

	@Test
	public void latLonDefault() {
		simpleGSList("latLon");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsWithCoordinates(40.3, 60.8, -1, "null", "null");
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));		
	}
	
	@Test
	public void latLonNoMatches() {
		simpleGSList("");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsWithCoordinates(50.3, 30.8, 10, "null", "null");
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}

		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));		
	}
	
	@Test
	public void carSharing() {
		simpleGSList("carSharing");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsWithCoordinates(40.3, 60.8, 10, "null", "Enjoy");
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));		
		
	}
	
	@Test
	public void carSharingNoMatches() {
		simpleGSList("");
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsWithCoordinates(40.3, 61.8, 10, "null", "Enjoy");
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}

		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));	
	}
	
	@Test
	public void fuelType() {
		simpleGSList("fuelType");
		List<GasStationDto> list = new ArrayList<GasStationDto>();

		String fuels[] = {"diesel", "super", "superplus", "gas", "methane"};
		for(String fuelType : fuels ) {

			try {
				list = gasStationService.getGasStationsWithCoordinates(40.3, 60.8, 10, fuelType, "null");
			} catch (Exception e) {
				fail("Unexpected exception " + e);			
			}

			assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));	
		}
	}
	
	@Test
	public void fuelTypeNoMatches() {
		simpleGSList("");
		List<GasStationDto> list = new ArrayList<GasStationDto>();

		String fuels[] = {"diesel", "super", "superplus", "gas", "methane"};
		for(String fuelType : fuels ) {

			try {
				list = gasStationService.getGasStationsWithCoordinates(40.3, 61.8, 10, fuelType, "null");
			} catch (Exception e) {
				fail("Unexpected exception " + e);			
			}

			assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));	
		}
	}
	
	@Test
	public void fuelTypeAndCarSharing() {
		simpleGSList("fuelTypeAndCarSharing");
		List<GasStationDto> list = new ArrayList<GasStationDto>();

		String fuels[] = {"diesel", "super", "superplus", "gas", "methane"};
		for(String fuelType : fuels ) {

			try {
				list = gasStationService.getGasStationsWithCoordinates(40.3, 60.8, 10, fuelType, "Car2Go");
			} catch (Exception e) {
				fail("Unexpected exception " + e);			
			}

			assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));	
		}
	}
	
	@Test
	public void fuelTypeAndCarSharingNoMatches() {
		simpleGSList("");
		List<GasStationDto> list = new ArrayList<GasStationDto>();

		String fuels[] = {"diesel", "super", "superplus", "gas", "methane"};
		for(String fuelType : fuels ) {

			try {
				list = gasStationService.getGasStationsWithCoordinates(40.3, 61.8, 10, fuelType, "Car2Go");
			} catch (Exception e) {
				fail("Unexpected exception " + e);			
			}

			assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));	
		}
	}
}