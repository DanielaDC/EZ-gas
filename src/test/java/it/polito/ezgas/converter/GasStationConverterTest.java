package it.polito.ezgas.converter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GasStationConverterTest {

	@Test
	public void toGasStation() {
		GasStationDto gsDto = new GasStationDto(10, "gasStationName", "gasStationAddress", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6,  0, "timestamp", 0.0);
		GasStation gs = GasStationConverter.toGasStation(gsDto);
		if((gs.getGasStationId() == gsDto.getGasStationId()) && (gs.getGasStationName().contentEquals(gsDto.getGasStationName())) &&
				(gs.getGasStationAddress().contentEquals(gsDto.getGasStationAddress())) && (gs.getHasDiesel() == gsDto.getHasDiesel()) &&
				(gs.getHasSuper() == gsDto.getHasSuper()) && (gs.getHasSuperPlus() == gsDto.getHasSuperPlus()) && (gs.getHasGas() == gsDto.getHasGas()) &&
				(gs.getHasMethane() == gsDto.getHasMethane()) && (gs.getHasPremiumDiesel() == gsDto.getHasPremiumDiesel()) &&(gs.getCarSharing().contentEquals(gsDto.getCarSharing())) && (gs.getLat() == gsDto.getLat()) &&
				(gs.getLon() == gsDto.getLon()) && (gs.getDieselPrice() == gsDto.getDieselPrice()) && (gs.getSuperPrice() == gsDto.getSuperPrice()) &&
				(gs.getSuperPlusPrice() == gsDto.getSuperPlusPrice()) && (gs.getGasPrice() == gsDto.getGasPrice()) && (gs.getMethanePrice() == gsDto.getMethanePrice()) && (gs.getPremiumDieselPrice() == gsDto.getPremiumDieselPrice()) &&
				(gs.getReportUser() == gsDto.getReportUser()) && (gs.getReportTimestamp().contentEquals(gsDto.getReportTimestamp())) && (gs.getReportDependability() == gsDto.getReportDependability())) {
			assert(true);
		}
		else
			fail("The Gas Station Entity and Dto are different");
	}
	
	@Test
	public void toGasStationDto() {
		GasStation gs = new GasStation("gasStationName", "gasStationAddress", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		GasStationDto gsDto = GasStationConverter.toGasStationDto(gs);
		if((gs.getGasStationId() == gsDto.getGasStationId()) && (gs.getGasStationName().contentEquals(gsDto.getGasStationName())) &&
				(gs.getGasStationAddress().contentEquals(gsDto.getGasStationAddress())) && (gs.getHasDiesel() == gsDto.getHasDiesel()) &&
				(gs.getHasSuper() == gsDto.getHasSuper()) && (gs.getHasSuperPlus() == gsDto.getHasSuperPlus()) && (gs.getHasGas() == gsDto.getHasGas()) &&
				(gs.getHasMethane() == gsDto.getHasMethane()) && (gs.getHasPremiumDiesel() == gsDto.getHasPremiumDiesel()) && (gs.getCarSharing().contentEquals(gsDto.getCarSharing())) && (gs.getLat() == gsDto.getLat()) &&
				(gs.getLon() == gsDto.getLon()) && (gs.getDieselPrice() == gsDto.getDieselPrice()) && (gs.getSuperPrice() == gsDto.getSuperPrice()) &&
				(gs.getSuperPlusPrice() == gsDto.getSuperPlusPrice()) && (gs.getGasPrice() == gsDto.getGasPrice()) && (gs.getMethanePrice() == gsDto.getMethanePrice()) && (gs.getPremiumDieselPrice() == gsDto.getPremiumDieselPrice()) &&
				(gs.getReportUser() == gsDto.getReportUser()) && (gs.getReportTimestamp().contentEquals(gsDto.getReportTimestamp())) && (gs.getReportDependability() == gsDto.getReportDependability())) {
			assert(true);
		}
		else
			fail("The Gas Station Entity and Dto are different");
	}
	
	@Test
	public void toGasStationList() {
		GasStationDto gsDto1 = new GasStationDto(10, "gasStationName1", "gasStationAddress1", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		GasStationDto gsDto2 = new GasStationDto(20, "gasStationName2", "gasStationAddress2", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		GasStationDto gsDto3 = new GasStationDto(30, "gasStationName3", "gasStationAddress3", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		List<GasStationDto> gsDtoList = new ArrayList<>();
		gsDtoList.add(gsDto1);
		gsDtoList.add(gsDto2);
		gsDtoList.add(gsDto3);
		List<GasStation> gsList = GasStationConverter.toGasStationList(gsDtoList);
		if(gsDtoList.size() == gsList.size()) {
			for(int i = 0; i < gsList.size(); i++) {
				if(gsList.get(i).getGasStationId() != gsDtoList.get(i).getGasStationId())
					fail("The Gas Station Entity and Dto Lists' items have different ids");
			}
			assert(true);
		}
		else
			fail("The Gas Station Entity and Dto Lists are different");
	}
	
	@Test
	public void toGasStationDtoList() {
		GasStation gs1 = new GasStation("gasStationName1", "gasStationAddress1", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		GasStation gs2 = new GasStation("gasStationName2", "gasStationAddress2", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		GasStation gs3 = new GasStation("gasStationName3", "gasStationAddress3", true, false, true, false, true, false, "enjoy", 1.0, 1.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 0, "timestamp", 0.0);
		List<GasStation> gsList = new ArrayList<>();
		gsList.add(gs1);
		gsList.add(gs2);
		gsList.add(gs3);
		List<GasStationDto> gsDtoList = GasStationConverter.toGasStationDtoList(gsList);
		if(gsDtoList.size() == gsList.size()) {
			for(int i = 0; i < gsList.size(); i++) {
				if(gsList.get(i).getGasStationId() != gsDtoList.get(i).getGasStationId())
					fail("The Gas Station Entity and Dto Lists' items have different ids");
			}
			assert(true);
		}
		else
			fail("The Gas Station Entity and Dto Lists are different");
	}
}
