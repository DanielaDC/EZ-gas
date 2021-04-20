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
import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.service.GasStationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetGasStationsByProximity extends InteractionDB {
	@Autowired
	GasStationService gasStationService;

	private void wrongCase() {
		GasStation g1 = new GasStation();
		GasStation g2 = new GasStation();
		GasStation g3 = new GasStation();
		
		g1.setGasStationId(0);
		g2.setGasStationId(4);
		g3.setGasStationId(10);
		
		this.localGasStationList = new ArrayList<GasStation>(Arrays.asList(g1,g2,g3));
		this.getGasRepo().save(this.localGasStationList);
	}

	private void moreButFar() {
		GasStation g1 = new GasStation();
		GasStation g2 = new GasStation();
		GasStation g3 = new GasStation();
		g1.setLat(40.31);
		g1.setLon(60.8);
		g2.setLat(40.3);
		g2.setLon(60.82);
		g3.setLat(40.31);
		g3.setLon(60.82);
		
		this.localGasStationList = new ArrayList<GasStation>(Arrays.asList(g1,g2,g3));
		this.getGasRepo().save(this.localGasStationList);
		this.localGasStationList = new ArrayList<GasStation>();
	}

	private void moreAndNear() {
		GasStation g1 = new GasStation();
		GasStation g2 = new GasStation();
		GasStation g3 = new GasStation();
		g1.setGasStationName("G1");
		g2.setGasStationName("G2");
		g3.setGasStationName("G3");
		g1.setLat(40.305);
		g1.setLon(60.8);
		g2.setLat(40.309);
		g2.setLon(61.804);
		g3.setLat(40.3);
		g3.setLon(60.81);
		
		this.localGasStationList = new ArrayList<GasStation>(Arrays.asList(g1,g2,g3));
		this.getGasRepo().save(this.localGasStationList);
		this.localGasStationList = this.getGasRepo().findAll().stream().filter((GasStation g)->{
			 return g.getGasStationName() == "G1" ||
					g.getGasStationName() == "G3";
		 }).collect(Collectors.toList());
	}
	
	@Test
	public void wrongLat() {
		wrongCase();
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsByProximity(123.0, -10.3);
		} catch (GPSDataException e) {
			assert(true);
			return;
		}
		
		fail("Expected GPSDataException");
		
	}
	
	@Test
	public void wrongLong() {
		wrongCase();
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsByProximity(3.0, -210.3);
		} catch (GPSDataException e) {
			assert(true);
			return;
		}
		
		fail("Expected GPSDataException");
		
	}
	
	@Test
	public void wrongLatAndLong() {
		wrongCase();
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsByProximity(-123.0, 810.3);
		} catch (GPSDataException e) {
			assert(true);
			return;
		}
		
		fail("Expected GPSDataException");
		
	}
	
	@Test
	public void emptyDB() {
		this.getGasRepo().save(this.localGasStationList);
	
		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsByProximity(40.3, 60.8);
		} catch (Exception e) {
			fail("Unexpected exception " + e);
		}

		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		
	}
	
	@Test
	public void oneElementNoNear() {
		GasStation g1 = new GasStation();
		g1.setGasStationName("name");
		g1.setLat(40.31);
		g1.setLon(60.8);
		
		this.localGasStationList = new ArrayList<GasStation>(Arrays.asList(g1));
		this.getGasRepo().save(this.localGasStationList);
		this.localGasStationList = new ArrayList<GasStation>();

		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsByProximity(40.3, 60.8);
		} catch (Exception e) {
			fail("Unexpected exception " + e);
		}
		
		System.out.println(list.stream().map(gs -> gs.getGasStationName()).collect(Collectors.toList()));
		System.out.println(this.localGasStationList.stream().map(gs -> gs.getGasStationName()).collect(Collectors.toList()));

		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		
	}

	@Test
	public void oneElementYesNear() {
		GasStation g1 = new GasStation();
		g1.setGasStationName("G1");
		g1.setLat(40.305);
		g1.setLon(60.8);
		
		this.getGasRepo().save(this.localGasStationList);
		
		this.localGasStationList = this.getGasRepo().findAll();

		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsByProximity(40.3, 60.8);
		} catch (Exception e) {
			fail("Unexpected exception " + e);
		}

		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		
	}

	@Test
	public void moreElementsNoNear() {
		moreButFar();

		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsByProximity(40.3, 60.8);
		} catch (Exception e) {
			fail("Unexpected exception " + e);
		}

		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		
	}

	@Test
	public void moreElementsYesNear() {
		moreAndNear();

		List<GasStationDto> list = new ArrayList<GasStationDto>();
		
		try {
			list = gasStationService.getGasStationsByProximity(40.3, 60.8);
		} catch (Exception e) {
			fail("Unexpected exception " + e);
		}
		
//		System.out.println(list.stream().map(gs -> gs.getGasStationName()).collect(Collectors.toList()));
//		System.out.println(this.localGasStationList.stream().map(gs -> gs.getGasStationName()).collect(Collectors.toList()));
		
		assert(compareLists(GasStationConverter.toGasStationList(list), this.localGasStationList));
		
	}
}