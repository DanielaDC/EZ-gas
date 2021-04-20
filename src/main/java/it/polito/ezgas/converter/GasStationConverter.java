package it.polito.ezgas.converter;

import java.util.List;
import java.util.stream.Collectors;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;

public class GasStationConverter {

	public static GasStationDto toGasStationDto(GasStation gasStation) {
		GasStationDto item = new GasStationDto();
		item.setGasStationId(gasStation.getGasStationId());
		item.setGasStationName(gasStation.getGasStationName());
		item.setGasStationAddress(gasStation.getGasStationAddress());
		item.setHasDiesel(gasStation.getHasDiesel());
		item.setHasSuper(gasStation.getHasSuper());
		item.setHasSuperPlus(gasStation.getHasSuperPlus());
		item.setHasGas(gasStation.getHasGas());
		item.setHasMethane(gasStation.getHasMethane());	
		item.setHasPremiumDiesel(gasStation.getHasPremiumDiesel());
		item.setCarSharing(gasStation.getCarSharing());
		item.setLat(gasStation.getLat());
		item.setLon(gasStation.getLon());
		item.setDieselPrice(gasStation.getDieselPrice());
		item.setSuperPrice(gasStation.getSuperPrice());
		item.setSuperPlusPrice(gasStation.getSuperPlusPrice());
		item.setGasPrice(gasStation.getGasPrice());
		item.setMethanePrice(gasStation.getMethanePrice());
		item.setPremiumDieselPrice(gasStation.getPremiumDieselPrice());
		item.setReportUser(gasStation.getReportUser());
		
		if(gasStation.getUser() != null) {
			item.setUserDto(UserConverter.toUserDto(gasStation.getUser()));
		}
		
		item.setReportTimestamp(gasStation.getReportTimestamp());
		item.setReportDependability(gasStation.getReportDependability());
		return item;
	}
	
	public static GasStation toGasStation(GasStationDto gasStationDto) {
		GasStation item = new GasStation();
		item.setGasStationId(gasStationDto.getGasStationId());
		item.setGasStationName(gasStationDto.getGasStationName());
		item.setGasStationAddress(gasStationDto.getGasStationAddress());
		item.setHasDiesel(gasStationDto.getHasDiesel());
		item.setHasSuper(gasStationDto.getHasSuper());
		item.setHasSuperPlus(gasStationDto.getHasSuperPlus());
		item.setHasGas(gasStationDto.getHasGas());
		item.setHasMethane(gasStationDto.getHasMethane());	
		item.setHasPremiumDiesel(gasStationDto.getHasPremiumDiesel());
		item.setCarSharing(gasStationDto.getCarSharing());
		item.setLat(gasStationDto.getLat());
		item.setLon(gasStationDto.getLon());
		item.setDieselPrice(gasStationDto.getDieselPrice());
		item.setSuperPrice(gasStationDto.getSuperPrice());
		item.setSuperPlusPrice(gasStationDto.getSuperPlusPrice());
		item.setGasPrice(gasStationDto.getGasPrice());
		item.setMethanePrice(gasStationDto.getMethanePrice());
		item.setPremiumDieselPrice(gasStationDto.getPremiumDieselPrice());
		item.setReportUser(gasStationDto.getReportUser());
		
		if(gasStationDto.getUserDto() != null) {
			item.setUser(UserConverter.toUser(gasStationDto.getUserDto()));
		}
		
		item.setReportTimestamp(gasStationDto.getReportTimestamp());
		item.setReportDependability(gasStationDto.getReportDependability());
		return item;
	}
	
	public static List<GasStationDto> toGasStationDtoList(List<GasStation> gasStationList){
		return gasStationList.stream().map(item -> toGasStationDto(item)).collect(Collectors.toList());
	}
	
	public static List<GasStation> toGasStationList(List<GasStationDto> gasStationDtoList){
		return gasStationDtoList.stream().map(item -> toGasStation(item)).collect(Collectors.toList());
	}
}
