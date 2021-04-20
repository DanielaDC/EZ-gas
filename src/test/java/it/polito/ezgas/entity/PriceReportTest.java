package it.polito.ezgas.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.PriceReport;
import it.polito.ezgas.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceReportTest{

	private PriceReport pr;
	private User u;
	
	@Before
	public void clearPriceRep(){
		u = new User();
		pr = new PriceReport(u,0,0,0,0);
	}

	@Test
	public void constructor() {
		// Constructor with parameters
		u = new User();
		pr = new PriceReport(u, 1.30, 1.40, 1.45, 0.80);
		assert(u == pr.getUser());
		assert(1.30 == pr.getDieselPrice());
		assert(1.40 == pr.getSuperPrice());
		assert(1.45 == pr.getSuperPlusPrice());
		assert(0.80 == pr.getGasPrice());
	}

	@Test
	public void userName() {
		u = new User();
		pr.setUser(u);
		assert(u == pr.getUser());
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
	public void PriceReportId() {
		pr.setPriceReportId(1);
		assert(1 == pr.getPriceReportId());
	}
}