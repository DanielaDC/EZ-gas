package it.polito.ezgas.service.impl.GasStation;

import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;
import it.polito.ezgas.InteractionDB;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.service.GasStationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SetReport extends InteractionDB {
	@Autowired
	GasStationService gasStationService;

	private void simpleGSList(Integer firstUserReputation, String time) {
		User u1 = new User();
		u1.setUserName("U1");
		u1.setReputation(firstUserReputation);
		User u2 = new User();
		u2.setUserName("U2");
		u2.setReputation(4);

		this.getUserRepo().save(Arrays.asList(u1, u2));
		
		User tmp = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U1")).findFirst().get();
		GasStation g1 = new GasStation("G1","addr", false, true, true, false, true, false, "Enjoy", 40.306, 60.8, -3.0, 3.0, 3.0, -3.0, 3.0, 3.0, tmp.getUserId(), time, 1.0);
		this.getGasRepo().save(g1);
		
	}
	
	@Test
	public void invalidUser() {
		simpleGSList(new Integer(1), "2019-03-25 16:30:30");
		GasStation g1 = this.getGasRepo().findAll().stream()
				.filter(gasStation -> gasStation.getGasStationName().contentEquals("G1"))
				.collect(Collectors.toList()).get(0);

		User u1 = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U1")).findFirst().get();
		User u2 = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U2")).findFirst().get();
		
		try {
			 gasStationService.setReport(g1.getGasStationId(), 1.0,1.0,1.0,1.0,1.0,1.0, -5);
		} catch ( InvalidUserException e) {
			assert(true);
			return;
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		
		fail("Expected InvalidUserException");
	}
	
	@Test
	public void nonExistentUser() {
		simpleGSList(new Integer(1), "2019-03-25 16:30:30");

		GasStation g1 = this.getGasRepo().findAll().stream()
				.filter(gasStation -> gasStation.getGasStationName().contentEquals("G1"))
				.collect(Collectors.toList()).get(0);
		
		try {
			 User u = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U1")).findFirst().get();
			 gasStationService.setReport(g1.getGasStationId(), 1.0,1.0,1.0,1.0,1.0, 1.0, this.nonExistentUserId());
		}catch (InvalidUserException e) {
			assert(true);			
		}catch (Exception e) {
			fail("Unexpected exception " + e);			
		}

	}
	
	@Test
	public void invalidGasStation() {
		simpleGSList(new Integer(1), "2019-03-25 16:30:30");

		GasStation g1 = this.getGasRepo().findAll().stream()
				.filter(gasStation -> gasStation.getGasStationName().contentEquals("G1"))
				.collect(Collectors.toList()).get(0);

		User u1 = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U1")).findFirst().get();
		User u2 = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U2")).findFirst().get();
		
		try {
			 gasStationService.setReport(-30, 1.0,1.0,1.0,1.0,1.0, 1.0, u2.getUserId());
		} catch ( InvalidGasStationException e) {
			assert(true);
			return;
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		
		fail("Expected InvalidGasStationException");
		
	}
	
	@Test
	public void nonExistentGasStation() {
		simpleGSList(new Integer(1), "2019-03-25 16:30:30");

		GasStation g1 = this.getGasRepo().findAll().stream()
				.filter(gasStation -> gasStation.getGasStationName().contentEquals("G1"))
				.collect(Collectors.toList()).get(0);

		User u1 = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U1")).findFirst().get();
		User u2 = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U2")).findFirst().get();
		
		try {
			 gasStationService.setReport( -1, 1.0,1.0,1.0,1.0,1.0, 1.0, u2.getUserId());
			 g1 = this.getGasRepo().findOne(g1.getGasStationId());
			 // No changes
			 assert(g1.getReportUser() == u1.getUserId());
			 assert(g1.getDieselPrice() == -3.0);
			 assert(g1.getSuperPrice() == 3.0);
			 assert(g1.getSuperPlusPrice() == 3.0);
			 assert(g1.getGasPrice() == -3.0);
			 assert(g1.getMethanePrice() == 3.0);
		}catch (InvalidGasStationException e) {
			assert(true);			
		}
		catch (Exception e) {
			fail("Unexpected exception " + e);			
		}

	}
	
	@Test
	public void invalidFuelPrice() {
		simpleGSList(new Integer(1), "2019-03-25 16:30:30");

		GasStation g1 = this.getGasRepo().findAll().stream()
				.filter(gasStation -> gasStation.getGasStationName().contentEquals("G1"))
				.collect(Collectors.toList()).get(0);
		User u1 = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U1")).findFirst().get();
		User u2 = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U2")).findFirst().get();
		
		try {
			 gasStationService.setReport(g1.getGasStationId(), 1.0,-1.0,-1.0,1.0,-1.0, 1.0, u2.getUserId());
		} catch ( PriceException e) {
			assert(true);
			return;
		} catch (Exception e) {
			fail("Unexpected exception " + e);			
		}
		
		fail("Expected PriceException");

	}
	
	@Test
	public void correctReport() {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY HH:mm:ss");
		Timestamp today = new Timestamp(System.currentTimeMillis());
		Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        // manipulate date
        c.add(Calendar.DATE, -5);
        Date timestampDate = c.getTime();
        
        
		simpleGSList(new Integer(1), dateFormat.format(timestampDate));
		try {
			GasStation g1 = this.getGasRepo().findAll().stream().filter(gs -> gs.getGasStationName().contentEquals("G1")).findFirst().get();
		
			User u2 = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U2")).findFirst().get();
			
			gasStationService.setReport(g1.getGasStationId(), -1.0, 1.0, 1.0, -1.0, 1.0, 1.0, u2.getUserId());

			//verifico aggiornamento di g1
			g1 = this.getGasRepo().findAll().stream().filter(gs -> gs.getGasStationName().contentEquals("G1")).findFirst().get();

			assert(g1.getReportUser().intValue() == u2.getUserId());
			 //Assert su diesel lo controllo inverso in quanto i prezzi negativi non devono aggiornare il valore
			assert(g1.getDieselPrice() != -1.0);
			assert(g1.getSuperPrice() == 1.0);
			assert(g1.getSuperPlusPrice() == 1.0);
			//Assert su gas lo controllo inverso in quanto i prezzi negativi non devono aggiornare il valore
			assert(g1.getGasPrice() != -1.0);
			assert(g1.getMethanePrice() == 1.0);
			assert(g1.getReportTimestamp() != dateFormat.format(timestampDate));
		}catch (Exception e) {
			fail("Unexpected exception " + e);			
		}

	}
	
	@Test
	public void correctReportLowerTrustShortTime() {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY HH:mm:ss");
		Timestamp today = new Timestamp(System.currentTimeMillis());
		Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        // manipulate date
        c.add(Calendar.DATE, -1);
        Date timestampDate = c.getTime();
        
		simpleGSList(new Integer(5), dateFormat.format(timestampDate));
		try {
			GasStation g1 = this.getGasRepo().findAll().stream().filter(gs -> gs.getGasStationName().contentEquals("G1")).findFirst().get();
		
			User u2 = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U2")).findFirst().get();
			
			gasStationService.setReport(g1.getGasStationId(), -1.0, 1.0, 1.0, -1.0, 1.0, 1.0, u2.getUserId());

			//verifico aggiornamento di g1
			g1 = this.getGasRepo().findAll().stream().filter(gs -> gs.getGasStationName().contentEquals("G1")).findFirst().get();

			assert(g1.getReportUser() != u2.getUserId());
		}catch (Exception e) {
			fail("Unexpected exception " + e);			
		}

	}

	@Test
	public void correctReportLowerTrustLongTime() {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY HH:mm:ss");
		Timestamp today = new Timestamp(System.currentTimeMillis());
		Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        // manipulate date
        c.add(Calendar.DATE, -5);
        Date timestampDate = c.getTime();
        
		simpleGSList(new Integer(5), dateFormat.format(timestampDate));
		try {
			GasStation g1 = this.getGasRepo().findAll().stream().filter(gs -> gs.getGasStationName().contentEquals("G1")).findFirst().get();
		
			User u2 = this.getUserRepo().findAll().stream().filter(user -> user.getUserName().contentEquals("U2")).findFirst().get();
			
			gasStationService.setReport(g1.getGasStationId(), -1.0, 1.0, 1.0, -1.0, 1.0, 1.0, u2.getUserId());

			//verifico aggiornamento di g1
			g1 = this.getGasRepo().findAll().stream().filter(gs -> gs.getGasStationName().contentEquals("G1")).findFirst().get();

			assert(g1.getReportUser().intValue() == u2.getUserId());
			 //Assert su diesel lo controllo inverso in quanto i prezzi negativi non devono aggiornare il valore
			assert(g1.getDieselPrice() != -1.0);
			assert(g1.getSuperPrice() == 1.0);
			assert(g1.getSuperPlusPrice() == 1.0);
			//Assert su gas lo controllo inverso in quanto i prezzi negativi non devono aggiornare il valore
			assert(g1.getGasPrice() != -1.0);
			assert(g1.getMethanePrice() == 1.0);
			assert(g1.getReportTimestamp() != dateFormat.format(timestampDate));
		}catch (Exception e) {
			fail("Unexpected exception " + e);			
		}

	}
}