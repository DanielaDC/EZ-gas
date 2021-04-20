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
import it.polito.ezgas.repository.GasStation.GetAllGasStations;
import it.polito.ezgas.repository.GasStation.GetGasStationByCarSharing;
import it.polito.ezgas.repository.GasStation.GetGasStationByDiesel;
import it.polito.ezgas.repository.GasStation.GetGasStationByGas;
import it.polito.ezgas.repository.GasStation.GetGasStationById;
import it.polito.ezgas.repository.GasStation.GetGasStationByMethane;
import it.polito.ezgas.repository.GasStation.GetGasStationByProximity;
import it.polito.ezgas.repository.GasStation.GetGasStationBySuper;
import it.polito.ezgas.repository.GasStation.GetGasStationBySuperPlus;
import it.polito.ezgas.repository.User.GetAllUser;
import it.polito.ezgas.repository.User.GetUserByAdmin;
import it.polito.ezgas.repository.User.GetUserbyEmail;
import it.polito.ezgas.service.impl.GasStation.DeleteGasStation;
import it.polito.ezgas.service.impl.GasStation.GetGasStationsByGasolineType;
import it.polito.ezgas.service.impl.GasStation.GetGasStationsByProximity;
import it.polito.ezgas.service.impl.GasStation.GetGasStationsWithCoordinates;
import it.polito.ezgas.service.impl.GasStation.GetGasStationsWithoutCoordinates;
import it.polito.ezgas.service.impl.GasStation.SaveGasStation;
import it.polito.ezgas.service.impl.GasStation.SetReport;
import it.polito.ezgas.service.impl.User.DecreaseUserReputation;
import it.polito.ezgas.service.impl.User.DeleteUser;
import it.polito.ezgas.service.impl.User.GetAllUsers;
import it.polito.ezgas.service.impl.User.GetUserById;
import it.polito.ezgas.service.impl.User.IncreaseUserReputation;
import it.polito.ezgas.service.impl.User.Login;
import it.polito.ezgas.service.impl.User.SaveUser;

@RunWith(Suite.class)
@Suite.SuiteClasses({GasStationDtoTest.class, IdPwTest.class, LoginDtoTest.class, PriceReportDtoTest.class,
	UserDtoTest.class, GasStationTest.class, PriceReportTest.class, UserTest.class,
	GasStationConverterTest.class, UserConverterTest.class,
	GetAllGasStations.class, GetGasStationByDiesel.class, GetGasStationByGas.class, GetGasStationById.class, GetGasStationByMethane.class,
	GetGasStationByProximity.class, GetGasStationBySuper.class, GetGasStationBySuperPlus.class, 
	GetAllUser.class, GetUserByAdmin.class, GetUserbyEmail.class,
	DeleteGasStation.class, GetAllGasStations.class, GetGasStationByCarSharing.class, GetGasStationById.class, GetGasStationsByGasolineType.class,
	GetGasStationsByProximity.class, GetGasStationsWithCoordinates.class, GetGasStationsWithoutCoordinates.class, SaveGasStation.class,
	SetReport.class, DecreaseUserReputation.class, DeleteUser.class, GetAllUsers.class, GetUserById.class, IncreaseUserReputation.class,
	Login.class, SaveUser.class})
@SpringBootTest
public class Step4{
	
	@Test
	public void contextLoads() {
		
	}

}