# Integration and API Test Documentation

Authors:

Andrea Bruno (s269705)  
Daniela Di Canio (276062)  
Gaetano Prudente (276096)  
Ruben Rinaldi (278179)

Date:

11/06/2020  

Version:  
1.0
2.0 (V2.0)

# Contents

- [Dependency graph](#dependency-graph)

- [Integration approach](#integration-approach)

- [Tests](#tests)

- [Scenarios](#scenarios)

- [Coverage of scenarios and FR](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Dependency graph

```plantuml
package "Backend" {
   package Controller{
      class GasStationController
      class HomeController
      class UserController
   }
   package Service{
      class GasStationService
      class UserService
   }
   package Repository{
      class GasStationRepository
      class UserRepository
   }
   package Converter{
      class UserConverter
      class GasStationConverter
   }
   package Entity{
      class GasStation
      class PriceReport
      class User
   }
   package Dto{
      class  GasStationDto
      class IdPw
      class LoginDto
      class PriceReportDto
      class UserDto
   }
UserConverter -down-> UserDto
UserConverter -down-> IdPw
UserConverter -down-> LoginDto
GasStationConverter -down-> GasStationDto

UserConverter -down-> User
GasStationConverter -down-> GasStation
GasStationConverter -left-> UserConverter

UserRepository -down-> User
GasStationRepository -down-> GasStation
GasStationRepository -down-> PriceReport

UserService -down-> UserRepository
GasStationService -down-> GasStationRepository

GasStationService -down-> UserRepository
UserService -down-> UserConverter
GasStationService -down-> GasStationConverter

GasStationController -down-> GasStationService
UserController -down-> UserService
}
```

# Integration approach

The approach used for these tests is **bottom-up**, and the sequence of integration is:  

- Step 1: Entity, Dto
- Step 2: Entity, Dto, Converter
- Step 3: Entity, Dto, Converter, Repository
- Step 4: Entity, Dto, Converter, Repository, Service


#  Tests

Each step relies also on the tests defined in previous steps.

## Step 1
Test defined in UnitTestReport.md

## Step 2
Tests defined in src/test/java/it.polito.ezgas.converter  
Test suite in src/test/java/it.polito.ezgas/Step2

| Classes  | JUnit test cases |
|--|--|
|GasStationConverter|toGasStation|
||toGasStationDto|
||toGasStationList|
||toGasStationDtoList|
|UserConverter|toUser|
||toUserDto|
||toUserList|
||toUserList|

## Step 3
Tests defined in src/test/java/it.polito.ezgas.repository.GasStation and src/test/java/it.polito.ezgas.repository.User  
Test suite in src/test/java/it.polito.ezgas/Step3

| Classes  | JUnit test cases |
|--|--|
|findGasStationByHasDieselOrderByDieselPriceAsc|GetGasStationByDiesel.testGetGasStationByDiesel|
|findGasStationByHasSuperOrderBySuperPriceAsc|GetGasStationBySuper.testGetGasStationBySuper|
|findGasStationByHasSuperPlusOrderBySuperPlusPriceAsc|GetGasStationBySuperPlus.testGetGasStationBySuperPlus|
|findGasStationByHasGasOrderByGasPriceAsc|GetGasStationByGas.testGetGasStationByGas|
|findGasStationByHasMethaneOrderByMethanePriceAsc|GetGasStationByMethane.testGetGasStationByMethane|
|findGasStationByCarSharing|GetGasStationByCarSharing.testGetGasStationByCarSharing|
|findGasStationByLatBetweenAndLonBetween|GetGasStationByProximity.testGetGasStationByProximity|
|findAll|GetAllGasStations.testGetAllGasStations|
|findOne|GetGasStationById.testGetGasStationById|
|findUserByAdmin|GetUserByAdmin.testGetUserByAdmin|
|findAll|GetAllUser.testGetAllUser|
|findUserByEmail|GetUserByEmail.testGetUserByEmail|


## Step 4 API Tests
Tests defined in src/test/java/it.polito.ezgas.service.impl.GasStation and src/test/java/it.polito.ezgas.service.impl.User  
Test suite in src/test/java/it.polito.ezgas/Step4

| Classes  | JUnit test cases |
|--|--|
|DecreaseUserReputation|DecreaseUserReputation.existentId|
||DecreaseUserReputation.existentIdMinReputation|
||DecreaseUserReputation.insertNull|
||DecreaseUserReputation.invalidId|
||DecreaseUserReputation.nonExistentId|
|DeleteUser|DeleteUser.existentId|
||DeleteUser.invalidId|
||DeleteUser.nonExistentId|
|GetAllUsers|GetAllUsers.emptyDb|
||GetAllUsers.moreElements|
||GetAllUsers.oneElement|
|GetUserById|GetUserById.existentId|
||GetUserById.invalidId|
||GetUserById.nonExistentId|
|IncreaseUserReputation|IncreaseUserReputation.existentId|
||IncreaseUserReputation.existentIdMaxReputation|
||IncreaseUserReputation.insertNull|
||IncreaseUserReputation.invalidId|
||IncreaseUserReputation.nonExistentId|
|Login|Login.existentRightPass|
||Login.existentWrongPass|
||Login.invalidId|
|SaveUser|SaveUser.insertNull|
||SaveUser.insertOne|

Since GasStationService functions had more complexity in terms of possible inputs, we defined formally their tests.  
### getGasStationById

**Criteria**:  
Id validity  
Id existence  
**Boundary**: minint, minint+1, -1, 0, 1, maxint-1, maxint  

**Conditions**:
|  |  |
|--|--|
|Id validity	|Non valid (id < 0)|
|				|Valid (id >= 0)|
|Id existence	|Id non existent|
|				|Id existent|

**Combine**:  
**Starting database: G1{id:0}, G2{id:4}, G3{id:10}**  
|Id validity|	Id existence|	Valid/Invalid	|Test cases|Junit method|
|-|-|-|-|-|
|No|	-	|I	|{ -5  -> throw InvalidGasStationException }|invalidId|
||||Boundary: minint, minint+1, -1 (they all throw the same exception)||
|Yes|	No|	I	|{ 5 -> null}|nonExistentId|
||||Boundary: 1, maxint-1, maxint (they all return null)||
|	|Yes|	V	|{ 4 -> G2 }|existentId|
||||Boundary: 0 (returns G1)||

### saveGasStation
**Criteria**:  
Price range (negative/positive)  
Latitude/ Longitude correctness  
Id already existent  

**Conditions**:  
|  |  |
|--|--|
|Price range|	Negative|
|			|Positive|
|Latitude/longitude|	Correct ( -90 < lat < +90, -180 < lon < +180)|
|	|Incorrect|
|Id existence|	Yes|
|	|No|

**Combine**:
**Starting database: G1{id:0}, G2{id:4}, G3{id:10}**
|Price range|	Latitude/Longitude	|Id existence	|Valid/Invalid|	Test cases|Junit method|
|-|-|-|-|-|-|
|-|	-|	-|	I (null)|{null -> null<br> Check NO insertion in the db}|insertNull|
|Negative|	Correct|	-	|I|	{{priceA=-1.0, latitude=3.0, longitude = -10.3} -> throw PriceException<br>Check it’s NOT inserted}|negativePriceCorrectLat|
||	Incorrect	|-|	I|	{{priceA=-1.0, latitude=123.0, longitude = -10.3} -> throw PriceException, GPSDataException<br>Check it’s NOT inserted}|negativePriceWrongLat|
|Positive|	Incorrect (latitude)|	-|	I	|{{priceA=1.0, latitude=123.0, longitude = -10.3} -> throw GPSDataException<br>Check it’s NOT inserted}|positivePriceWrongLat|
|||||Boundary: mindouble, mindouble+0.1, -90.1, 90.1, maxint-0.1, maxint|
||	Incorrect (longitude)|	-	|I|{{priceA=1.0, latitude=3.0, longitude = -210.3} -> throw GPSDataException<br>Check it’s NOT inserted}|positivePriceWrongLon|
|||||Boundary: mindouble, mindouble+0.1, -180.1, 180.1, maxint-0.1, maxint|
||	Incorrect (both)|	-	|I|	{{priceA=1.0, latitude=-123.0, longitude = 810.3} -> throw GPSDataException<br>Check it’s NOT inserted}|positivePriceWrongLatAndLon|
||	Correct|	Yes	|I	|{{priceA= 1.0, latitude=60, longitude=20, id=4} -> null<br>Check it’s NOT inserted}|PositivePriceCorrectLonExistentId|
|||		No|	V	|{G4 = {priceA= 1.0, latitude=60, longitude=20, id=5} -> G4<br>Check it’s inserted}|PositivePriceCorrectLonNewId|

### getAllGasStations
**Criteria**:  
Number of gas stations in the database  

**Conditions**:  
|||
|-|-|
|Number of gas stations|	0|
||	1|
||	>=2|

**Combine**:
**Starting database:   
Case 0) empty  
Case 1) G1{id:0}  
Case more) G1{id:0}, G2{id:4}, G3{id:10}**  

|Number of gas stations	|Valid/Invalid|	Test cases|Junit method|
|-|-|-|-|
|0|	V|	{ ()  -> empty arrayList }|emptyDB|
|1|	V|	{ () -> G1} |oneElement|
|>=2|	V|	{ () -> G1, G2, G3 } (any order?)|moreElements|

### deleteGasStation
**Criteria**:  
Id validity  
Id existence  
**Boundary**: minint, minint+1, -1, 0, 1, maxint-1, maxint  
**Conditions**:
|||
|-|-|
|Id validity|	Non valid (id < 0)|
|	|Valid (id >= 0)|
|Id existence	|Id non existent|
|	|Id existent|

**Combine**:
**Starting database: G1{id:0}, G2{id:4}, G3{id:10}**
|Id validity|	Id existence|	Valid/Invalid	|Test cases|Junit method|
|-|-|-|-|-|
|No|	-|	I|	{ -5  -> throw InvalidGasStationException<br>Check it’s NOT inserted}|invalidId|
||||Boundary: minint, minint+1, -1 (they all throw the same exception)||
|Yes|	No|	I|	{5 -> throw EmptyResultDataAccessException<br>Check it’s NOT inserted}|nonExistentId|
||||Boundary: 1, maxint-1, maxint (they all return null)||
||	Yes|	V|	{ 4 -> true<br>Check it’s inserted}|existentId|
||||Boundary: 0 (returns G1)||


### getGasStationsByGasolineType
**Criteria**:  
String validity  
Number of gas stations in db  
**Conditions**:  
|||
|-|-|
|String validity|	Non valid|
||	Valid (diesel, super, superPlus, gas, methane)|
|Number of gas stations	|0|
||	1|
||	>=2|

**Combine**:  
**Starting database (each gasolineType may have different values to have different orders):   
Case 0) empty  
Case 1) G1{id:0, <gasolineType>price = 1, <gasolineType>flag = true}  
Case more)   
G1{id:0, <gasolineType>price = 4, <gasolineType>flag = true},   
G2{id:1, <gasolineType>price = 1, <gasolineType>flag = true},   
G3{id:2, <gasolineType>price = 3, <gasolineType>flag = true} ,   
G4{id:3, <gasolineType>price = 2, <gasolineType>flag = false}**  
|String	|Number of gas stations|	Valid/Invalid	|Test cases|Junit method|
|-|-|-|-|-|
|Invalid|	-|	I|	{ “invalidString” -> throw InvalidGasTypeException}|invalidGasType|
||||Boundary: empty string||
|Valid<br>(repeat for every valid string)|	0|	V|	{ \<gasolineType\>  -> empty arrayList }|emptyDB|
||	1|	V|	{ \<gasolineType\> -> G1}|oneElement|
||	>=2|	V|	{ \<gasolineType\> -> G2, G3, G1 }(increasing price for that gasoline type)|moreElements|

### getGasStationsByProximity
**Criteria**:  
Latitude/ Longitude correctness  
Number of gas stations in db  
Near gas stations in db  
**Conditions**:  
|||
|-|-|
|Latitude/Longitude|	Correct ( -90 < lat < +90, -180 < lon < +180)|
|	|Incorrect|
|Number of gas stations	|0|
|	|1|
|	|>=2|
|Near gas stations| 	Yes|
|	|No|  
**Combine**:  
**Starting database:  
(Invalid cases) G1{id:0}, G2{id:4}, G3{id:10}  
(1) empty  
(2) G1{lat=40.31, lon=60.8}  
(3) G1{lat=40.305, lon=60.8}  
(4) G1{lat=40.31, lon=60.8}, G2{lat=40.3, lon=60.82}, G3{lat=40.31, lon=60.82}  
(5) G1{lat=40.305, lon=60.8}, G2{lat=40.309, lon=61.804}, G3{lat=40.3, lon=60.81}**  
|Latitude/Longitude	|Number of gas stations|	Near gas stations|	Valid/Invalid	|Test cases|Junit method|
|-|-|-|-|-|-|
|Incorrect (latitude)|	-|	-|	I|	{ (123.0, -10.3) -> throw GPSDataException }|wrongLat|
|||||Boundary: mindouble, mindouble+0.1, -90.1, 90.1, maxint-0.1, maxint||
|Incorrect (longitude)|	-|	-|	I|	{ (3.0,  -210.3) -> throw GPSDataException }|wrongLong|
|||||Boundary: mindouble, mindouble+0.1, -180.1, 180.1, maxint-0.1, maxint||
|Incorrect (both)|	-|	-|	I|	{ (123.0, 810.3)  -> throw GPSDataException }|wrongLatAndLong|
|Correct|	0|	-|	V	(1)| { (40.3, 60.8) ->empty arrayList }|emptyDB|
||	1|	No|	V	(2)| { (40.3, 60.8) ->empty arrayList }|oneElementNoNear|
|||		Yes|	V	(3)| { (40.3, 60.8) ->G1 }|oneElementYesNear|
||	>=2|	No	|V	(4)| { (40.3, 60.8) ->empty arrayList }|moreElementsNoNear|
|||		Yes|	V	(5) |{ (40.3, 60.8) ->G1, G3 } (any order?)|moreElementsYesNear|

### getGasStationsWithCoordinates
**Criteria**:  
Latitude/ Longitude correctness  
FuelType string value  
CarSharing string value  
Value of radius  
**Conditions**:  
|||
|-|-|
|Latitude /Longitude|	Correct ( -90 < lat < +90, -180 < lon < +180)|
|	|Incorrect|
|FuelType string value|	Non valid|
||null|
|	|Valid (diesel, super, superPlus, gas, methane)|
|CarSharing string value	|Non valid|
||null|
|	|Valid|
|Value of radius | < 0|
| | >= 0|
**Combine**:
**Starting database:    
G1{id:0, lat=40.306, lon=60.8, \<fuelType>price = 1, \<gasolineType>flag = true, carsharing = “Enjoy”},   
G2{id:1, lat=40.3, lon=60.803, \<fuelType>price = 3, \<gasolineType>flag = true, carsharing = “Car2Go”},   
G3{id:2, lat=40.304, lon=60.8, \<fuelType>price = 6, \<gasolineType>flag = false, carsharing = “Enjoy”},   
G4{id:3, lat=40.305, lon=60.8, \<fuelType>price = 5, \<gasolineType>flag = true, carsharing = “Enjoy”},   
G5{id:4, lat=40.3, lon=60.804, \<fuelType>price = 4, \<gasolineType>flag = false,  carsharing =“Car2Go”},   
G6{id:5, lat=40.3, lon=65.81, \<fuelType>price = 2, \<gasolineType>flag = false, carsharing = “Car2Go”}**  
|Latitude/Longitude	|Value of radius|FuelType|	CarSharing|	Valid/Invalid|	Test cases|Junit method|
|-|-|-|-|-|-|-|
|Incorrect (latitude)|	- |-|(correct to test a single exception)	-(correct to test a single exception)|	I	|{ (-95, -95, 10, “diesel”, “Car2Go”) -> throw GPSDataException}|wrongLat|
||||||Boundary: mindouble, mindouble+0.1, -90.1, 90.1, 10, maxint-0.1, maxint||
|Incorrect (longitude)|	- |-|	-|	I|	{ (5, -195, 10, “diesel”, “Car2Go”) -> throw GPSDataException}|wrongLon|
||||||Boundary: mindouble, mindouble+0.1, -180.1, 180.1, maxint-0.1, maxint||
|Incorrect (both)|	- |-|	-|	I	|{ (185, -7095, 10, “diesel”, “Car2Go”) -> throw GPSDataException}|wrongLatAndLon|
|-(correct to test a single exception)|-|Incorrect|	-|	I|	{ (5, -95, “abcd”, “Car2Go”) -> throw InvalidGasTypeException}|wrongFuelType|
||||||Boundary: empty string||
|-|	-|-	|Incorrect	|I	|{ (40.3, 60.8, 10, “diesel”, “CSGo”) -> empty arrayList }|wrongCarSharing|
||||||Boundary: empty string||
|Incorrect|-|	Incorrect|	Incorrect|	I|	{ (185, -7095, 10, “fmab”, “CSGo”) -> throw GPSDataException, InvalidGasTypeException }|wrongAll|
|Correct|<0|Null|Null|V|{ (40.3, 60.8, 10, null, null) -> G2, G5, G3, G4, G1 } (uses default radius of 1km)|latLonDefault|
||	>=0||	|	V|	{ (40.3, 60.8, 10, null, null) -> G2, G5, G3, G4, G1 } (any order?)|latLon|
|	||	|	|V (no matches)|	{ (50.3, 30.8, 10, null, null) -> empty arrayList }|latLonNoMatches|
|	||	|\<carsharing>|	V|	{ (40.3, 60.8, 10, null, “Enjoy”) -> G3, G4, G1 }  (any order?)|carSharing|
||	|	| |	V (no matches)|	{ (40.3, 61.8, 10, null, “Enjoy”) -> empty arrayList }|carSharingNoMatches|
|	||	\<fuelType>|	Null|	V	|{ (40.3, 60.8, 10, \<fuelType>, null) -> G2, G4, G1 } (any order?)|fuelType|
|	||	| |	V (no matches)|	{ (40.3, 61.8, 10, \<fuelType>, null) ->empty arrayList }|fuelTypeNoMatches|
|	||	|\<carsharing>	|V|	{ (40.3, 60.8, 10, \<fuelType>, “Car2Go”) ->G2 } (any order?)|fuelTypeAndCarSharing|
|||||			V (no matches)|	{ (40.3, 61.8, 10, \<fuelType>, “Car2Go”) ->empty arrayList }|fuelTypeAndCarSharingNoMatches|

### getGasStationsWithoutCoordinates
**Criteria**:  
FuelType string value  
CarSharing string value  
**Conditions**:  
|||
|-|-|
|FuelType string value	|Non valid|
||	null|
||	Valid (diesel, super, superPlus, gas, methane)|
|CarSharing string value	|Non valid|
||	null|
||	Valid|
**Combine**:
**Starting database:  
G1{id:0, lat=40.306, lon=60.8, <fuelType>price = 1, \<fuelType>flag = true, carsharing = “Enjoy”},  
G2{id:1, lat=40.3, lon=60.803, <fuelType>price = 3, \<fuelType>flag = false, carsharing = “Car2Go”},  
G3{id:2, lat=40.304, lon=60.8, <fuelType>price = 6, \<fuelType>flag = false, carsharing = “Enjoy”},  
G4{id:3, lat=40.305, lon=60.8, <fuelType>price = 5, \<fuelType>flag = true, carsharing = “Enjoy”},  
G5{id:4, lat=40.3, lon=60.804, <fuelType>price = 4, \<fuelType>flag = false,  carsharing =“Car2Go”},  
G6{id:5, lat=40.3, lon=61.81, <fuelType>price = 2, \<fuelType>flag = false, carsharing = “Car2Go”}**  
|FuelType|	CarSharing|	Valid/Invalid|	Test cases|Junit method|
|-|-|-|-|-|
|Incorrect|	-|	I|	{ (“abcd”, “Car2Go”) -> throw invalidGasTypeException}|wrongFuelType|
||||Boundary: empty string||
|-	|Incorrect|	I	|{ (“diesel”, “CSGo”) -> empty arrayList }|wrongCarSharing|
||||Boundary: empty string||
|Incorrect|	Incorrect	|I|	{ (“fmab”, “CSGo”) -> throw InvalidGasTypeException }|wrongAll|
|Null	|Null	|V|	{ (null, null) -> G2, G5, G3, G4, G1, G6 } (any order?)|noFilters|
||	\<carsharing>	|V|	{ (null, “Enjoy”) -> G3, G4, G1 }  (any order?)|carSharing|
|\<fuelType>|	Null|	V|	{ (\<fuelType>, null) -> G4, G1 } (any order?)|fuelType|
||	\<carsharing>|	V	|{ (\<fuelType>, “Enjoy”) ->G4, G1 } (any order?)|fuelTypeAndCarSharing|
|||		V (no matches)	|{ (\<fuelType>, “Car2Go”) ->empty arrayList }|fuelTypeAndCarSharingNoMatches|

### setReport
**Criteria**:  
User Id validity  
User Id existence  
Gas station Id validity  
Gas station Id existence  
User trust level  
Interval of time since last report  

**Boundary**: minint, minint+1, -1, 0, 1, maxint-1, maxint  
Fuel validity  
**Boundary**: mindouble, mindouble+0.1, -0.1, 0, 0-1, maxdouble-0.1, maxdouble  
Fuel existence (if the gas station handles that fuel type)  
**Conditions**:
|||
|-|-|
|User Id validity|	Non valid (id < 0)|
|	|Valid (id >= 0)|
|User Id existence	|Id non existent|
|	|Id existent|
|Gas Station Id validity	|Non valid (id < 0)|
|	|Valid (id >= 0)|
|Gas Station Id existence	|Id non existent|
|	|Id existent|
|Fuel validity	|Non valid (fuelPrice < 0) if \<fuelType>flag = true|
|	|Valid (fuelPrice >= 0) if \<fuelType>flag = true|
| User trust level | Lower (newUser < oldUser) |
|  | Higher (newUser >= oldUser) |
| Interval of time since last report | <= 4 days |
|  | > 4 days |

**Combine**:  
**Starting database: G1{id:2,
hasDiesel: false,
hasSuper: true,
hasSuperPlus: true,
hasGas: false,
hasMethane: true,
dieselPrice: -3,
superPrice: 3,
superPlusPrice: 3,
gasPrice: -3,
methanePrice: 3,
(last report linked to U1)
name:”G1”},  
U1{id:2, name:”U1”}, U2{id:4, name:”U2”}**  
|Validity User|	Existence User|	Validity Gas Station|	Existence Gas Station|	Validity Fuel|User trust level| Interval of time |	Valid /Invalid|	Test cases|Junit method|
|-|-|-|-|-|-|-|-|-|-|
|No|	-|	-|	-|	-|-|-|	I|	{ (30, 1, 1, 1, 1, 1, -5)  -> throw InvalidUserException <br>Check NO changes}|invalidUser|
|||||||||Boundary: minint, minint+1, -1 (they all throw the same exception)||
|Yes|	No|	-|	-|	-|-|-|	I|	{ (30, 1, 1, 1, 1, 1, 5) <br>Check NO changes}|nonExistentUser|
|||||||||Boundary: 0, 1, maxint-1, maxint(they all trigger no change)||
||	Yes	|No|	-|	-|-|-|	I|	{ (-30, 1, 1, 1, 1, 1, 4)  -> throw InvalidGasStationException<br>Check NO changes}|invalidGasStation|
|||||||||Boundary: minint, minint+1, -1 (they all throw the same exception)||
|||		Yes|	No|	-|-|-|	I|	{ (30, 1, 1, 1, 1, 1, 4) <br>Check NO changes}|nonExistentGasStation|
|||||||||Boundary: 0, 1, maxint-1, maxint(they all trigger no change)||
|||		|	Yes	|No	|-|-|I|	{ (2, 1, -1, -1, 1, -1, 4)  -> throw PriceException<br>Check NO changes}|invalidFuelPrice|
|||||||||Boundary: mindouble, mindouble+0.1, -0.1 (they all trigger NO change)||
|||||				Yes|Higher (newUser >= oldUser)|-|	V	|{ (2, -1, 1, 1, -1, 1, 4) <br>Check YES changes}|correctReport|
|||||||||Boundary (difference of trust levels): 0, 1, -1, 10, -10||
|||||				Yes|Lower (newUser < oldUser)|<= 4 days|	I	|{ (2, -1, 1, 1, -1, 1, 4) <br>Check NO changes}|correctReportLowerTrustShortTime|
|||||||||Boundary (difference of trust levels): 0, 1, -1, 10, -10<br>  Boundary (interval of time): 0, 1 second, 4 days-1second, 4 days, 4days+1second, maxDays||
|||||				Yes|Lower (newUser < oldUser)| > 4 days|	V	|{ (2, -1, 1, 1, -1, 1, 4) <br>Check YES changes}|correctReportLowerTrustLongTime|
|||||||||Boundary (difference of trust levels): 0, 1, -1, 10, -10<br>  Boundary (interval of time): 0, 1 second, 4 days-1second, 4 days, 4days+1second, maxDays||

### getGasStationByCarSharing
**Criteria**:  
CarSharing string value  
**Conditions**:  
|||
|-|-|
|CarSharing string value|	Non valid|
||	null|
||	Valid|  
**Combine**:
**Starting database:  
G1{id:0, name:”G1”, lat=40.306, lon=60.8, <fuelType>price = 1, <gasolineType>flag = true, carsharing = “Enjoy”},  
G2{id:1, name:”G2”, lat=40.3, lon=60.803, <fuelType>price = 3, <gasolineType>flag = false, carsharing = “Car2Go”},  
G3{id:2, name:”G3”, lat=40.304, lon=60.8, <fuelType>price = 6, <gasolineType>flag = false, carsharing = “Enjoy”},  
G4{id:3, name:”G4”, lat=40.305, lon=60.8, <fuelType>price = 5, <gasolineType>flag = true, carsharing = “Enjoy”},  
G5{id:4, name:”G5”, lat=40.3, lon=60.804, <fuelType>price = 4, <gasolineType>flag = false,  carsharing =“Car2Go”},  
G6{id:5, name:”G6”, lat=40.3, lon=61.81, <fuelType>price = 2, <gasolineType>flag = false, carsharing = “Car2Go”}**  
|CarSharing|	Valid/Invalid|	Test cases|Junit method|
|-|-|-|-|
|Incorrect|	I|	{ (“CSGo”) -> empty arrayList }|incorrectCarSharing|
|||Boundary: empty string||
|Null	|V|	{ (null) -> G2, G5, G3, G4, G1, G6 } (any order?)|nullCarSharing|
|\<carsharing>|	V|	{ (“Enjoy”) -> G3, G4, G1 }  (any order?)|correctCarSharing1|
|||{ (“Car2Go”) -> G2, G5, G6 }  (any order?)|correctCarSharing2|

# Scenarios

## Scenario 11 - Search a user

| Scenario |  search a user |
| ------------- |:-------------:|
|  Precondition     | Admin A in authenticated |
|  Post condition     |  - |
| Step#        | Description  |
|  1     |  Admin selects User id |  
|  2     | System searches in DB for that id |
|  3     | System return User |


## Scenario 12 - Verify rights of a user

| Scenario |  verify rights |
| ------------- |:-------------:|
|  Precondition     | Account exists |
|  Post condition     |  - |
| Step#        | Description  |
|  1     |  User sends request |  
|  2     | System searches in DB for rights of that id |
|  3     | System returns status |

## Scenario 13 - List all gas stations

| Scenario |  verify rights |
| ------------- |:-------------:|
|  Precondition     | - |
|  Post condition     |  - |
| Step#        | Description  |
|  1     |  User sends request |  
|  2     | System searches in DB for all gas stations |
|  3     | System returns list of gas stations |

# Coverage of Scenarios and FR


| Scenario ID | Functional Requirements covered | JUnit  Test(s) |
| ----------- | ------------------------------- | ----------- |
| 1, 2        | FR1.1                            |it.polito.ezgas.service.impl.User/SaveUser|
| 3         | FR1.2                            |it.polito.ezgas.service.impl.User/DeleteUser|
| 4, 5        | FR3.1                            |it.polito.ezgas.service.impl.GasStation/SaveGasStation|
| 6        | FR3.2                            |it.polito.ezgas.service.impl.GasStation/DeleteGasStation|
| 7        | FR5 |it.polito.ezgas.service.impl.GasStation/SetReport|
| 8        | FR4.1, FR4.2, FR4.3, FR4.4 |it.polito.ezgas.service.impl.GasStation/GetGasStationsByProximity|
| 8 (variant)        | FR4.5 |it.polito.ezgas.service.impl.GasStation/GetGasStationsWithCoordinates|
| 9        | FR5.2                            |it.polito.ezgas.service.impl.GasStation/SetReport|
| 10.1        | FR5.3                            |it.polito.ezgas.service.impl.GasStation/IncreaseUserReputation|
| 10.2        | FR5.3                            |it.polito.ezgas.service.impl.GasStation/DecreaseUserReputation|
| (not done by users) |FR1.3|it.polito.ezgas.service.impl.User/GetAllUsers|
|11|FR1.4|it.polito.ezgas.service.impl.User/GetUserById|
|12|FR2|it.polito.ezgas.service.impl.User/GetUserById|
|13|FR3.3|it.polito.ezgas.service.impl.GasStation/GetAllGasStations|




# Coverage of Non Functional Requirements

###

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
| NFR1                           |    Not testable       |
| NFR2                           |    Not testable at the level of Service package      |
| NFR3                           |    Not automatically testable, empiric tests on development machines        |
| NFR4                           |    Not testable at the level of Service package      |
| NFR5                           |    Not testable at the level of Service package      |
