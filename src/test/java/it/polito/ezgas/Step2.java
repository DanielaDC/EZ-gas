package it.polito.ezgas;

import org.junit.Test;
import org.junit.runner.RunWith; 
import org.junit.runners.Suite; 
import org.springframework.boot.test.context.SpringBootTest;

import it.polito.ezgas.converter.GasStationConverterTest;
import it.polito.ezgas.converter.UserConverterTest;
import it.polito.ezgas.dto.GasStationDtoTest;
import it.polito.ezgas.dto.IdPwTest;
import it.polito.ezgas.dto.LoginDtoTest;
import it.polito.ezgas.dto.PriceReportDtoTest;
import it.polito.ezgas.dto.UserDtoTest;
import it.polito.ezgas.entity.GasStationTest;
import it.polito.ezgas.entity.PriceReportTest;
import it.polito.ezgas.entity.UserTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({GasStationDtoTest.class, IdPwTest.class, LoginDtoTest.class, PriceReportDtoTest.class,
	UserDtoTest.class, GasStationTest.class, PriceReportTest.class, UserTest.class,
	GasStationConverterTest.class, UserConverterTest.class})
@SpringBootTest
public class Step2{
	
	@Test
	public void contextLoads() {
		
	}

}
