# GUI  Testing Documentation

Authors:
Andrea Bruno (s269705)  
Daniela Di Canio (276062)  
Gaetano Prudente (276096)  
Ruben Rinaldi (278179)

Date: 01/06/2020

Version: 1.0

# GUI testing

This part of the document reports about testing at the GUI level. Tests are end to end, so they should cover the Use Cases, and corresponding scenarios.

## Coverage of Scenarios and FR

```
<Complete this table (from IntegrationApiTestReport.md) with the column on the right. In the GUI Test column, report the name of the .py  file with the test case you created.>
```

###

| Scenario ID | Functional Requirements covered   | GUI Test(s)          |
| ----------- | --------------------------------- | -------------------- |
| 1           | FR1.1                             | UC1                  |
| 2           | FR1.1                             | UC2                  |
| 3           | FR1.2                             | UC3 (only for admin) |
| 4           | FR3.1                             | UC4                  |
| 5           | FR3.1                             | UC5                  |
| 6           | FR3.2                             | UC6                  |
| 7           | FR5                               | UC7                  |
| 8           | FR4.1, FR4.2, FR4.3, FR4.4, FR4.5 | UC8                  |
| 9           | FR5.2                             | (not testable)       |
| 10.1        | FR5.3                             | UC10_1               |
| 10.2        | FR5.3                             | UC10_2               |


# REST  API  Testing

This part of the document reports about testing the REST APIs of the back end. The REST APIs are implemented by classes in the Controller package of the back end.
Tests should cover each function of classes in the Controller package

## Coverage of Controller methods


<Report in this table the test cases defined to cover all methods in Controller classes >

| class.method name            | Functional Requirements covered          | REST  API Test(s) |
| ---------------------------- | ---------------------------------------- | ----------------- |
| getAllUsers                  | FR1.3                                    | testGetAllUsers   |     
| getUserById                  | FR1.4                                    | testGetUserById_IdNotInDB, testGetUserById_IdInDB |             
| saveUser                     | FR1.1                                    | testSaveUser      |             
| login                        | FR1, FR2                                 | testLogin         |             
| increaseUserReputation       | FR1.1                                    | testIncreaseUserReputation |             
| decreaseUserReputation       | FR1.1                                    | testDecreaseUserReputation |     
| deleteUser                   | FR1.2                                    | testDeleteUser_IdNotInDB, testDeleteUser_IdInDB |             
| ---------------------------- | ---------------------------------------- | ----------------- |             
| getAllGasStations            | FR3.3, FR5.2                             | testGetAlGasStations |             
| getGasStationById            | FR4, FR5.2                               | testGetGasStationById_IdNotInDB, testGetGasStationById_IdInDB |             
| saveGasStation               | FR3.1                                    | testSaveGasStation |             
| deleteGasStation             | FR3.2                                    | testDeleteGasStation_IdNotInDB, testDeleteGasStation_IdInDB |             
| getGasStationByGasolineType  | FR4.4, FR5.2                             | testGetGasStationByGasolineType_Diesel, testGetGasStationByGasolineType_Super, testGetGasStationByGasolineType_SuperPlus, testGetGasStationByGasolineType_Gas, testGetGasStationByGasolineType_Methane |
| getGasStationByProximity     | FR4.1, FR4.2, FR5.2                      | testGetGasStationByProximity_NoGS, testGetGasStationByProximity_someGS |
| getGasStationWithCoordinates | FR4.1, FR4.2, FR4.3, FR4.4, FR4.5, FR5.2 | testGetGasStationWithCoordinates_OnlyLatLon, testGetGasStationWithCoordinates_LatLonGt, testGetGasStationWithCoordinates_LatLonCs, testGetGasStationWithCoordinates_Total |             
| setGasStationReport          | FR5.1, FR5.2                             | testSetGasStationReport |     
