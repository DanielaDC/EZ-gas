package it.polito.ezgas.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.GPSDataException;
import exception.InvalidCarSharingException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.GasStationService;

/**
 * Created by softeng on 27/4/2020.
 */
@Service
public class GasStationServiceimpl implements GasStationService {
	
	@Autowired
	private GasStationRepository gasStationRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public GasStationDto getGasStationById(Integer gasStationId) throws InvalidGasStationException {
		if(gasStationId < 0) throw new InvalidGasStationException("There is no Gas Station with the given Id");
		GasStation gs = gasStationRepository.findOne(gasStationId);
		if(gs != null) {
			GasStationDto gsDto = GasStationConverter.toGasStationDto(gs);
			gsDto = setTimeDependability(gsDto);
			return gsDto;
		}
		else
			return null;
	}

	@Override
	public GasStationDto saveGasStation(GasStationDto gasStationDto) throws PriceException, GPSDataException {
		//coordinate invalide se fuori dal loro raggio
		if((gasStationDto.getLat() < -90.0 || gasStationDto.getLat() > 90.0) || (gasStationDto.getLon() < -180.0 || gasStationDto.getLon() > 180.0)){
			throw new GPSDataException("Wrong GPS coordinates");
		}
		
		//prezzi invalidi se la gasStation fornisce quel tipo e il prezzo è negativo e diverso da null (code for missing/not setted price)
		if(gasStationDto.getHasDiesel() && gasStationDto.getDieselPrice() != null && gasStationDto.getDieselPrice() < 0.0) {
			throw new PriceException("The Diesel price is not valid");
		}
		if(gasStationDto.getHasSuper() && gasStationDto.getSuperPrice() != null && gasStationDto.getSuperPrice() < 0.0) {
			throw new PriceException("The Super price is not valid");
		}
		if(gasStationDto.getHasSuperPlus() && gasStationDto.getSuperPlusPrice() != null && gasStationDto.getSuperPlusPrice() < 0.0) {
			throw new PriceException("The SuperPlus price is not valid");
		}
		if(gasStationDto.getHasGas() && gasStationDto.getGasPrice() != null && gasStationDto.getGasPrice() < 0.0) {
			throw new PriceException("The Gas price is not valid");
		}
		if(gasStationDto.getHasMethane() && gasStationDto.getMethanePrice() != null && gasStationDto.getMethanePrice() < 0.0) {
			throw new PriceException("The Methane price is not valid");
		}
		if(gasStationDto.getHasPremiumDiesel() && gasStationDto.getPremiumDieselPrice() != null && gasStationDto.getPremiumDieselPrice() < 0.0) {
			throw new PriceException("The Premium Diesel price is not valid");
		}
		
		if(gasStationDto.getCarSharing().contentEquals("null")) {
			gasStationDto.setCarSharing(null);
		}
		
		GasStation convertedItem = GasStationConverter.toGasStation(gasStationDto);
		GasStation gs = gasStationRepository.save(convertedItem);
		
		return GasStationConverter.toGasStationDto(gs);
	}

	@Override
	public List<GasStationDto> getAllGasStations() {
		List<GasStationDto> gasStationList = new ArrayList<>();
		gasStationList.addAll(GasStationConverter.toGasStationDtoList(gasStationRepository.findAll()));
		if(gasStationList.size() > 0)
			gasStationList = gasStationList.stream().map(gsDto -> gsDto = setTimeDependability(gsDto)).collect(Collectors.toList());
		return gasStationList;
	}

	@Override
	public Boolean deleteGasStation(Integer gasStationId) throws InvalidGasStationException {
		if(gasStationId < 0 ) throw new InvalidGasStationException("The indicated Gas Station Id is not valid");
		if(gasStationRepository.findOne(gasStationId) == null)
			return false;
		gasStationRepository.delete(gasStationId);
		return true;
	}

	@Override
	public List<GasStationDto> getGasStationsByGasolineType(String gasolinetype) throws InvalidGasTypeException {
		List<GasStationDto> gasStationList = new ArrayList<>();
		switch(gasolinetype.toLowerCase()) {
		case "diesel":
			gasStationList.addAll(GasStationConverter.toGasStationDtoList(gasStationRepository.findGasStationByHasDieselOrderByDieselPriceAsc(true)));
			break;
		case "super":
			gasStationList.addAll(GasStationConverter.toGasStationDtoList(gasStationRepository.findGasStationByHasSuperOrderBySuperPriceAsc(true)));
			break;
		case "superplus":
			gasStationList.addAll(GasStationConverter.toGasStationDtoList(gasStationRepository.findGasStationByHasSuperPlusOrderBySuperPlusPriceAsc(true)));
			break;
		case "gas":
			gasStationList.addAll(GasStationConverter.toGasStationDtoList(gasStationRepository.findGasStationByHasGasOrderByGasPriceAsc(true)));
			break;
		case "methane":
			gasStationList.addAll(GasStationConverter.toGasStationDtoList(gasStationRepository.findGasStationByHasMethaneOrderByMethanePriceAsc(true)));
			break;
		case "premiumdiesel":
			gasStationList.addAll(GasStationConverter.toGasStationDtoList(gasStationRepository.findGasStationByHasPremiumDieselOrderByPremiumDieselPriceAsc(true)));
			break;
		default:
			throw new InvalidGasTypeException("The indicated gas type is not valid");
		}

		if(gasStationList.size() > 0)
			gasStationList = gasStationList.stream().map(gsDto -> gsDto = setTimeDependability(gsDto)).collect(Collectors.toList());
		
		return gasStationList;
	}

	@Override
	public List<GasStationDto> getGasStationsByProximity(double lat, double lon) throws GPSDataException {
		List<GasStationDto> gasStationList = getGasStationsByProximity(lat, lon, 1);
		return gasStationList;
	}

	@Override
	public List<GasStationDto> getGasStationsByProximity(double lat, double lon, int myRadius) throws GPSDataException {
		if((lat < -90.0 || lat > 90.0) || (lon < -180.0 || lon > 180.0)) {
			throw new GPSDataException("Given latitude/longitude are not valid");
		}
		List<GasStationDto> gasStationList = GasStationConverter.toGasStationDtoList(gasStationRepository.findAll());
		
		//Pulizia delle GasStation per ottenere quelle nel raggio "pulito"
		int rad = (myRadius <= 0) ? 1 : myRadius;
		gasStationList.removeIf(item -> !geoPointDistance(rad, lat, lon, item.getLat(), item.getLon()));

		if(gasStationList.size() > 0)
			gasStationList = gasStationList.stream().map(gsDto -> gsDto = setTimeDependability(gsDto)).collect(Collectors.toList());
		
		return gasStationList;
	}

	@Override
	public List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, int myRadius, String gasolinetype,
			String carsharing) throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		List<GasStationDto> gasStationList1 = getGasStationsByProximity(lat, lon, myRadius);
		if(gasolinetype != null && !gasolinetype.contentEquals("null")) {
			List<Integer> gasStationList2 = getGasStationsByGasolineType(gasolinetype).stream()
					.map(item -> item.getGasStationId()).collect(Collectors.toList());
			gasStationList1.removeIf(item -> !gasStationList2.contains(item.getGasStationId()));
		}
		if(carsharing == null || carsharing.contentEquals("")) {
			throw new InvalidCarSharingException("The indicated car sharing company is not valid");
		}
		else if(!carsharing.contentEquals("null")) {
			List<Integer> gasStationList3 = getGasStationByCarSharing(carsharing).stream()
					.map(item -> item.getGasStationId()).collect(Collectors.toList());;
			gasStationList1.removeIf(item -> !gasStationList3.contains(item.getGasStationId()));
		}

		return gasStationList1;
	}

	@Override
	public List<GasStationDto> getGasStationsWithoutCoordinates(String gasolinetype, String carsharing)
			throws InvalidGasTypeException, InvalidCarSharingException {
		// TODO Auto-generated method stub
		List<GasStationDto> gasStationList = GasStationConverter.toGasStationDtoList(gasStationRepository.findAll());
		if(gasolinetype != null && !gasolinetype.contentEquals("null")) {
			List<Integer> gasStationList2 = getGasStationsByGasolineType(gasolinetype).stream()
					.map(itemGS -> itemGS.getGasStationId()).collect(Collectors.toList());
			gasStationList.removeIf(item -> !gasStationList2.contains(item.getGasStationId()));
		}
		if(carsharing == null || carsharing.contentEquals("")) {
			throw new InvalidCarSharingException("The indicated car sharing company is not valid");
		}
		else if(!carsharing.contentEquals("null")) {
			List<Integer> gasStationList3 = getGasStationByCarSharing(carsharing).stream()
					.map(itemGS -> itemGS.getGasStationId()).collect(Collectors.toList());
			gasStationList.removeIf(item -> !gasStationList3.contains(item.getGasStationId()));
		}
		
		return gasStationList;
	}

	@Override
	public void setReport(Integer gasStationId, Double dieselPrice, Double superPrice, Double superPlusPrice, 
			Double gasPrice, Double methanePrice, Double premiumDieselPrice, Integer userId) 
			throws InvalidGasStationException, PriceException, InvalidUserException	{
		System.out.println(userId);
		if(userId < 0) 
			throw new InvalidUserException("The indicated Id for the user is not valid");
		else {
			User user = userRepository.findOne(userId);
			if(user == null) {
				throw new InvalidUserException("The indicated Id has not a corresponding user");
			}
			else {
				if(gasStationId < 0) {
					throw new InvalidGasStationException("The Id indicated is not valid");
				}
				else {
					GasStation gasStation = gasStationRepository.findOne(gasStationId);
					
					if(gasStation == null) {
						throw new InvalidGasStationException("The Id indicated is not valid");
					}
					else {							
						Timestamp today = new Timestamp(System.currentTimeMillis());
						DateFormat formatter = new SimpleDateFormat("MM-dd-YYYY");
						String oldTimestamp = gasStation.getReportTimestamp();
						if(oldTimestamp != null && oldTimestamp.contentEquals("")) {
							oldTimestamp = null;
						}
						Long days = 0L;
						try {
							Timestamp oldTime = new Timestamp( formatter.parse(
									(oldTimestamp != null) ? oldTimestamp : "01-01-1970" ).getTime());
							days = TimeUnit.DAYS.convert((today.getTime() - oldTime.getTime()), TimeUnit.MILLISECONDS);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
						if(gasStation.getUser() == null || user.getReputation() >= gasStation.getUser().getReputation()
								|| days > 4) {

							if(gasStation.getHasDiesel() && dieselPrice < 0.0) {
								throw new PriceException("The Diesel price is not valid");
							}
							if(gasStation.getHasSuper() && superPrice < 0.0) {
								throw new PriceException("The Super price is not valid");
							}
							if(gasStation.getHasSuperPlus() && superPlusPrice < 0.0) {
								throw new PriceException("The SuperPlus price is not valid");
							}
							if(gasStation.getHasGas() && gasPrice < 0.0) {
								throw new PriceException("The Gas price is not valid");
							}
							if(gasStation.getHasMethane() && methanePrice < 0.0) {
								throw new PriceException("The Methane price is not valid");
							}
							if(gasStation.getHasPremiumDiesel() && premiumDieselPrice < 0.0) {
								throw new PriceException("The Premium Diesel price is not valid");
							}
						
							//aggiorno i prezzi
							if(gasStation.getHasDiesel()) {
								gasStation.setDieselPrice(dieselPrice);
							}
							if(gasStation.getHasSuper()) {
								gasStation.setSuperPrice(superPrice);
							}
							if(gasStation.getHasSuperPlus()) {
								gasStation.setSuperPlusPrice(superPlusPrice);
							}
							if(gasStation.getHasGas()) {
								gasStation.setGasPrice(gasPrice);
							}
							if(gasStation.getHasMethane()) {
								gasStation.setMethanePrice(methanePrice);
							}
							if(gasStation.getHasPremiumDiesel()) {
								gasStation.setPremiumDieselPrice(premiumDieselPrice);
							}
	
							gasStation.setReportUser(userId);
							gasStation.setUser(user);
							
							
							double dependability = (50 * (user.getReputation() + 5)/10);
							gasStation.setReportDependability(dependability);
							
							gasStation.setReportTimestamp(formatter.format(today));
							
							gasStationRepository.save(gasStation);
						}
					}
				}
			}
		}
	}

	@Override
	public List<GasStationDto> getGasStationByCarSharing(String carSharing) {
		List<GasStationDto> gasStationList = new ArrayList<>();
		gasStationList.addAll(GasStationConverter.toGasStationDtoList(gasStationRepository.findGasStationByCarSharing(carSharing)));

		if(gasStationList.size() > 0)
			gasStationList = gasStationList.stream().map(gsDto -> gsDto = setTimeDependability(gsDto)).collect(Collectors.toList());
		
		return gasStationList;
	}
	
	private Boolean geoPointDistance(int myRadius, Double lat1, Double lon1, Double lat2, Double lon2) {
		int R = 6371; // Radius of the earth in km
		double dLat = deg2rad(lat2-lat1);  // deg2rad below
		double dLon = deg2rad(lon2-lon1); 
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
			    Math.sin(dLon/2) * Math.sin(dLon/2); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double d = R * c; // Distance in km
		double rad = new Double(myRadius);
		boolean result = d <= rad;
		return (result);
	}
	
	private Double deg2rad(Double deg) {
		return deg * (Math.PI/180);
	}

	private GasStationDto setTimeDependability(GasStationDto gs) {
		Timestamp today = new Timestamp(System.currentTimeMillis());
		if(gs.getReportTimestamp() == null)
			return gs;

		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		try {
			Timestamp temp = new Timestamp( df.parse(gs.getReportTimestamp() ).getTime());
			System.out.println("temp " + temp);
			Long days = TimeUnit.DAYS.convert((today.getTime() - temp.getTime()), TimeUnit.MILLISECONDS);
			
			double obsolescence;
			if(days > 7) {
				obsolescence = 0.0;
			}
			else {
				obsolescence = 1.0 - (days/7);
			}
			gs.setReportDependability(gs.getReportDependability() + (50*obsolescence));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gs;
	}
}
