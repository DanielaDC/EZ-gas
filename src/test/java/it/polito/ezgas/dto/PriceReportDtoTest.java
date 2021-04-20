package it.polito.ezgas.dto;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.PriceReportDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceReportDtoTest{
	
	private PriceReportDto pr;
	private User u;
	@Before
	public void clearPriceRep(){
		u = new User();
		u.setUserId(1);
		pr = new PriceReportDto(0,0.0,0.0,0.0,0.0,0.0,0.0, u.getUserId());
	}
	
	@Test
	public void constructor() {
		// Constructor with parameters
		u = new User();
		u.setUserId(1);
		pr = new PriceReportDto(11, 1.30, 1.40, 1.45, 0.80, 1.1, 1.1, u.getUserId());
		assert(11 == pr.getGasStationId());
		assert(1 == pr.getUserId());
		assert(1.30 == pr.getDieselPrice());
		assert(1.40 == pr.getSuperPrice());
		assert(1.45 == pr.getSuperPlusPrice());
		assert(0.80 == pr.getGasPrice());
		assert(1.1 == pr.getMethanePrice());
		assert(1.1 == pr.getPremiumDieselPrice());
	}

	@Test
	public void PriceReportId() {
		pr.setGasStationId(1);
		assert(1 == pr.getGasStationId());
	}

	@Test
	public void userName() {
		pr.setUserId(1);
		assert(1 == pr.getUserId());
	}

	@Test
	public void dieselPrice() {
		pr.setDieselPrice(1.30);
		assert(1.30 == pr.getDieselPrice());
	}

	@Test
	public void superPrice() {
		pr.setSuperPrice(1.40);
		assert(1.40 == pr.getSuperPrice());
	}

	@Test
	public void superPlusPrice() {
		pr.setSuperPlusPrice(1.45);
		assert(1.45 == pr.getSuperPlusPrice());
	}

	@Test
	public void gasPrice() {
		pr.setGasPrice(0.70);
		assert(0.70 == pr.getGasPrice());
	}

	@Test
	public void methanePrice() {
		pr.setMethanePrice(0.70);
		assert(0.70 == pr.getMethanePrice());
	}

	@Test
	public void premiumDieselPrice() {
		pr.setPremiumDieselPrice(0.70);
		assert(0.70 == pr.getPremiumDieselPrice());
	}
}