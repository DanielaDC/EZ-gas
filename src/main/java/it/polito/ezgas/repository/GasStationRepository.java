package it.polito.ezgas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.polito.ezgas.entity.GasStation;

public interface GasStationRepository extends JpaRepository<GasStation, Integer>{
	
	public List<GasStation> findGasStationByHasDieselOrderByDieselPriceAsc(boolean hasDiesel);
	
	public List<GasStation> findGasStationByHasSuperOrderBySuperPriceAsc(boolean hasSuper);
	
	public List<GasStation> findGasStationByHasSuperPlusOrderBySuperPlusPriceAsc(boolean hasSuperPlus);
	
	public List<GasStation> findGasStationByHasGasOrderByGasPriceAsc(boolean hasGas);
	
	public List<GasStation> findGasStationByHasMethaneOrderByMethanePriceAsc(boolean hasMethane);
	
	public List<GasStation> findGasStationByHasPremiumDieselOrderByPremiumDieselPriceAsc(boolean hasPremiumDiesel);
	
	public List<GasStation> findGasStationByCarSharing(String carSharing);
	
	public List<GasStation> findGasStationByLatBetweenAndLonBetween(double latStart, double latEnd, double lonStart, double lonEnd);

}
