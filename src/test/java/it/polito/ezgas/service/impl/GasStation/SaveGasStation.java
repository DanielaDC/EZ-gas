package it.polito.ezgas.service.impl.GasStation;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import exception.GPSDataException;
import exception.InvalidGasStationException;
import exception.PriceException;
import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.service.GasStationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaveGasStation extends InteractionDB {
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
	public void insertNull() {
		GasStationDto g = new GasStationDto();
		simpleGSList();
		try {
			g = gasStationService.saveGasStation(null);
		} catch (NullPointerException e) {
			assert(true);
		} catch (Exception e) {
			fail("Unexpected exception" + e);
		}
	}

	@Test
	public void negativePriceCorrectLat() {
		GasStationDto g = new GasStationDto();
		g.setHasDiesel(true);
		g.setDieselPrice(-1.0);
		g.setLat(3.0);
		g.setLon(-10.3);
		
		simpleGSList();
		try {
			g = gasStationService.saveGasStation(g);
		} catch (GPSDataException e) {
			fail("Unexpected GPSDataException");
		} catch (PriceException e) {
			assert(true);
			return;
		}
		fail("Expected PriceException");
	}

	@Test
	public void negativePriceWrongLat() {
		GasStationDto g = new GasStationDto();
		g.setHasDiesel(true);
		g.setDieselPrice(-1.0);
		g.setLat(123.0);
		g.setLon(-10.3);
		
		simpleGSList();
		try {
			g = gasStationService.saveGasStation(g);
		} catch (GPSDataException | PriceException e) {
			assert(true);
			return;
		}
		fail("Expected PriceException or GPSDataException");
	}

	@Test
	public void positivePriceWrongLat() {
		GasStationDto g = new GasStationDto();
		g.setHasDiesel(true);
		g.setDieselPrice(1.0);
		g.setLat(123.0);
		g.setLon(-10.3);
		
		simpleGSList();
		try {
			g = gasStationService.saveGasStation(g);
		} catch (GPSDataException e) {
			assert(true);
			return;
		} catch (PriceException e) {
			fail("Expected GPSDataException");
		}
		fail("Expected GPSDataException");
	}
	
	@Test
	public void positivePriceWrongLon() {
		GasStationDto g = new GasStationDto();
		g.setHasDiesel(true);
		g.setDieselPrice(1.0);
		g.setLat(3.0);
		g.setLon(-210.3);
		
		simpleGSList();
		try {
			g = gasStationService.saveGasStation(g);
		} catch (GPSDataException e) {
			assert(true);
			return;
		} catch (PriceException e) {
			fail("Expected GPSDataException");
		}
		fail("Expected GPSDataException");
	}
	
	@Test
	public void positivePriceWrongLatAndLon() {
		GasStationDto g = new GasStationDto();
		g.setHasDiesel(true);
		g.setDieselPrice(1.0);
		g.setLat(123.0);
		g.setLon(-210.3);
		
		simpleGSList();
		try {
			g = gasStationService.saveGasStation(g);
		} catch (GPSDataException e) {
			assert(true);
			return;
		} catch (PriceException e) {
			fail("Expected GPSDataException");
		}
		fail("Expected GPSDataException");
	}
	
	@Test
	public void PositivePriceCorrectLonExistentId() {
		GasStationDto g = new GasStationDto();
		g.setHasDiesel(true);
		g.setDieselPrice(1.0);
		g.setLat(3.0);
		g.setLon(-10.3);
		g.setGasStationId(4);
		g.setCarSharing("Enjoy");
		
		simpleGSList();
		try {
			g = gasStationService.saveGasStation(g);
			assert(true);
		} catch (GPSDataException | PriceException e) {
			fail("Unexpected exception " + e);
		}
	}
	
	@Test
	public void PositivePriceCorrectLonNewId() {
		GasStationDto g = new GasStationDto();
		GasStationDto newG  = new GasStationDto();
		g.setGasStationName("G");
		g.setHasDiesel(true);
		g.setDieselPrice(1.0);
		g.setLat(3.0);
		g.setLon(-10.3);
		g.setGasStationId(5);
		g.setCarSharing("Enjoy");
		
		simpleGSList();
		try {
			newG = gasStationService.saveGasStation(g);
		} catch (GPSDataException | PriceException e) {
			fail("Unexpected exception " + e);
		}

		assert(newG.getGasStationName()==g.getGasStationName());
	}
}