# Requirements Document

Authors:  

Andrea Bruno (s269705)  
Daniela Di Canio (276062)  
Gaetano Prudente (276096)  
Ruben Rinaldi (278179)

Date:  

14/05/2020

Version:  

1.0 (Initial document)  
1.1 (Modified use cases)   
2.0 (General improvements)

# Contents

- [Abstract](#abstract)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	+ [Context Diagram](#context-diagram)
	+ [Interfaces](#interfaces)

- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	+ [Functional Requirements](#functional-requirements)
	+ [Non functional requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	+ [Use case diagram](#use-case-diagram)
	+ [Use cases](#use-cases)
- [Glossary](#glossary)
- [System design](#system-design)
- [Deployment diagram](#deployment-diagram)


# Abstract
Gas stations are essential for the driver citizen who needs to refuel his car.
When a driver is in an unknown area and needs to refuel, he is probably not aware of the exact position of the stations which surround him; the driver usually asks the locals for directions but these are not always accurate or reliable, and the driver may not have a good memory and remember them all.
When the driver is in a well known area (the place where he lives or works etc.) he may be interested in knowing which station offers the best price, in order to save gas expenses, but to do so he certainly can't visit them all and then decide the most convenient (this would not make sense).

The EZgas developer decides to develop a simple application to support the driver (called user). The application is meant to be used by all kind of citizens who owns a vehicle and is free.
The station manager who wants to register his property on the application will have to request it and his identity must be approved by a controller (called administrator).

# Stakeholders


| Stakeholder name  	| Description |
| ------------------- |:-----------:|
| User (registered)   |Uses the application for knowing where are located stations and which are the fuel prices; is able to report price discrepancies and new stations on the map |
| User (unregistered) |	Can use the application only for knowing where are located stations and which are the fuel prices |
| Gas Station Manager |	Manager of the gas station. Uses the application to update the price he practice |
| EZGas Developer			| Identity who develops the application|
| Administrator				| Verify the managers' identities and that the stations reported by users actually exist |
| Google Maps					| Used for locating stations on the map |

# Context Diagram and interfaces

## Context Diagram
```plantuml
left to right direction
actor "Gas Station Manager" as gsm
actor Developer as dev
actor Administrator as adm
actor "Google Maps" as ms
actor "User (registered)" as usrR
actor "User (unregistered)" as usrUR
rectangle "System"{
	database "Gas Stations DB" as gsdb{
	}
	database "Users DB" as udb {
	}
	(EZGas Application) -left- gsdb
	(EZGas Application) -right- udb
}
System <-down- usrR
System <-down- usrUR
System <-down- gsm
System <-up- ms
System <-up- dev
System <-up- adm
```

## Interfaces

| Actor | Logical Interface | Physical Interface  |
| ------------- |:-------------:| -----:|
|User (registered) 	  |GUI-App         |Screen, keyboard|
|User (unregistered)  |GUI-App         |Screen, keyboard|
|Gas station manager  |GUI-App         |Screen, keyboard|
|Developer  					|GUI-Working Environment |Screen, keyboard|
|Administrator			  |GUI-Working Environment |Screen, keyboard|
|Google Maps 				  |API ([documentation](https://developers.google.com/maps/documentation))    			   |Internet				 |

# Stories and personas
Mike is a citizen who works 30 km from his home and runs the same road every day.
He well knows the nearby petrol stations' position but as he refuels very often he would like to choose the most convenient station.
Usually Mike relies on the information given by his colleagues who travel the same road or he goes to his trusted supplier who, however, does not always have the best price.
Using EZgas Mike will be able to do it easily in one click with his mobile, with no wasting in time.
When Mike enters the app he is asked what is the radius in which he wants to search the stations and and immediately all prices will be shown.
Mike is also a very precise guy and each time he finds out a different price from the one indicated on the app he will communicate it, so that his colleagues (who use the same application) will see the correct price.

Jack is the manager of a gas station and would like to be on the EZgas application so as to increase its clientele. He requests the administrator who will verify his identity and decide whether to insert it or not on the app.
Once the station is registered he will have to keep updated the price of the fuel in his gas station.

Mr. Guest doesn't like driving very much and prefers to take public transports instead but sometimes he does some road trip with his family.
On these occasions he often finds himself having to refuel but he has no idea where to find gas stations.
He usually stops to ask for directions but with EZgas everything would be easier! He will directly set the google maps navigator to reach the chosen station even if he does not have the privilege of having an account.

# Functional and non functional requirements

## Functional Requirements

| ID        | Description  |
| ------------- |:-------------:|
|  FR1     | Record a new gas station with its relative information (location, gas types, gas prices) |
| &nbsp;&nbsp; FR1.1  | Gas station report from a user|
| &nbsp;&nbsp; FR1.2  | Gas station creation from a gas station manager |
|  FR2     | Produce a report about correctness of the information about a gas station (only user side) |
|  FR3     | Manage account |
|  FR4     | Log in |
|  FR5     | Log out |
|  FR6     | Create an account |
| &nbsp;&nbsp; FR6.1  | Create User account |
| &nbsp;&nbsp; FR6.2  | Create Manager account |
|  FR7     | Retrieve password |
|  FR8     | Manage gas station locations in DB and display them on a map according to a defined GPS position and some chosen parameters |
|  FR9     | Update a gas station information (location, gas types, gas prices) |
|  FR10    | Display a defined gas station details |

## Non Functional Requirements

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1     | Usability 	 | Application should be used with no training by any user  | All FR |
|  NFR2     | Performance  | All functions should complete in < 3 sec  | All FR |
|  NFR3     | Portability  | The application runs on Android | All FR |
|  NFR4     | Portability  | The application (functions and data) should be portable from a smartphone to another smartphone in less than 5 minutes | All FR |
|  NFR5     | Localization | Decimal numbers use . (dot) as decimal separator for prices and GPS position (latitude, longitude) |
|  NFR6     | Reliability  | Application statistics should report a crash-free rate > 95% |
|  NFR7     | Reliability  | Check if a gas station is actually located where declared |
|  NFR8     | Reliability  | Check if a gas station manager is actually who he claims to be |


# Use case diagram and use cases

## Use case diagram
```plantuml
left to right direction

actor "User" as user
actor "Registered User" as ru
actor "Gas Station Manager" as manager
actor "Administrator" as admin
actor "Google Maps" as map

usecase "FR1: Record a new gas station" as f1
usecase "FR1.1: Gas station report" as f1_1
usecase "FR1.2: Gas station creation" as f1_2
usecase "FR2: Price report" as f2
usecase "FR3: Manage account" as f3
usecase "FR4: Log in" as f4
usecase "FR5: Log out" as f5
usecase "FR6: Create an account" as f6
usecase "FR6.1: Create User account" as f6_1
usecase "FR6.2: Create Manager account" as f6_2
usecase "FR7: Retrieve password" as f7
usecase "FR8: Display gas station on map" as f8
usecase "FR9: Update a gas station informations" as f9
usecase "FR10: Display a defined gas station details" as f10

user <|-- ru
admin -->f1 
f1_1 .> f1 : extends
f1_1 .> f4 : include
f1_2 .> f1 : extends
f1_2 .> f4 : include
f1_1 <-- ru
manager --> f1_2

f2 <-- ru
f4 <. f2 : include

f3 <-- ru
manager --> f3 
f4 <. f3 : include

f4 --> f5

f6_1 .> f6 : extends
f6_2 .> f6 : extends
f6_1 <-- ru
manager --> f6_2
admin --> f6_2 

f7 <-- ru
manager --> f7 

f8 <-- user
manager --> f8 
map --> f8 

manager --> f9 
f4 <. f9 : include

f10 <-- user
manager --> f10 
map --> f10 

f1 -[hidden]-> f4
f2 -[hidden]-> f4
f3 -[hidden]-> f4
f4 -[hidden]-> f4
f5 -[hidden]-> f4
f4 -[hidden]-> f6
f4 -[hidden]-> f7
f4 -[hidden]-> f8
f4 -[hidden]-> f9
f4 -[hidden]-> f10
```
## Use cases

### **Use case 1, UC1 - FR1, New Gas station profile**

| Actors Involved        |  Administrator, Gas Station Manager|
| ------------- |:-------------:|
|  Precondition     | Gas Station Manager wants to insert a gas station in the system and he has to be authenticated|  
|  Post condition   | The new gas station appears on the map |
|  Nominal Scenario | The Manager register the gas station in app specifying his personal data and wait while the administrator check the corectness |
|  Variants     		|  |

<br>

| Scenario 1.1 | |
| ------------- |:-------------:|
|  Precondition     | Manager is authenticated|
|  Post condition   | New gas station is inserted into database |
|||
| Step#        | Description  |
|  1     | Manager provides information about name, location and prices of the gas station |  
|  2     | Administrator validates the data	 |
|  3  	 | Database is updated|

<br>

### **Use case 2, UC2 - FR1, New Gas station report**

| Actors Involved        |  Administrator, Registered User|
| ------------- |:-------------:|
|  Precondition     | User has to be authenticated|  
|  Post condition   | Counter incremented |
|  Nominal Scenario | User reports a new gas station, the relative counter is incremented. |
|  Variants     		| When enough reports are generated, the new gas station gets evaluated (2.2) |

<br>

| Scenario 2.1 | |
| ------------- |:-------------:|
|  Precondition     | User is authenticated|
|  Post condition   | Counter incremented |
|||
| Step#        | Description  |
|  1     | User provides information about name and location of the gas station |  
|  2     | Counter incremented |

<br>

| Scenario 2.2 | |
| ------------- |:-------------:|
|  Precondition     | User is authenticated, counter reached almost its maximum value |
|  Post condition   | Administrator evaluates new gas station |
|||
| Step#        | Description  |
|  1     | User provides information about name and location of the gas station |  
|  2     | Counter incremented |
|  3  	 | Evaluation request sent to Administrator |
|  4     | Counter frozen |
<br>

### **Use case 3, UC3 - FR8, Display Gas Station on the Map**
| Actors Involved        | Registered User, Google Maps|
| ------------- |:-------------:|
|  Precondition     |User selects the position on the map|  
|  Post condition   |Gas Stations are displayed on the map |
|  Nominal Scenario | User open EZGas which use the Google Maps API to display the locations of gas stations |
|  Variants     | |

<br>

| Scenario 3.1 | |
| ------------- |:-------------:|
|  Precondition   | User select the position on the map|
|  Post condition | Gas Stations locations are displayed on the map |
|||
| Step#        | Description  |
|  1     | User select the position on the map|  
|  2     | User select a buffer  |
|  3  |  EZGas diplay location and information of the Gas Station|

<br>

### **Use case 4, UC4 - FR2, Report wrong fuel price**
| Actors Involved        | Registred User, Administrator|
| ------------- |:-------------:|
|  Precondition     | A gas station has a worng fuel price, User is near the gas station |  
|  Post condition     | Counter incremented |
|  Nominal Scenario     | One user finds different price from EZGas and the Gas Station, thus makes a report with correct price |
|  Variants     |  When enough reports are generated, price information is updated (4.2) |

<br>

| Scenario 4.1 | |
| ------------- |:-------------:|
|  Precondition     | User is authenticated|
|  Post condition     | An alert is generated with correct price|
|||
| Step#        | Description  |
|  1     | User found different prices from EZGas about a specific station |  
|  2     | User makes a report  |
|  3  | Counter of that specific station and price is incremented by 1|

<br>

| Scenario 4.2 | |
| ------------- |:-------------:|
|  Precondition     | User is authenticated, counter reached almost its maximum value|
|  Post condition     | Price information is updated|
|||
| Step#        | Description  |
|  1     | User found different prices from EZGas about a specific station |  
|  2     | User makes a report  |
|  3     | Counter of that specific station and price is incremented by 1|
|  4     | Report handled by the system |

<br>

### **Use case 5, UC5 - FR10, Display the gas station profile**

| Actors Involved        |User|
| ------------- |:-------------:|
|  Precondition     | A gas station is present on the database |  
|  Post condition     |The gas station profile is displayed|
|  Nominal Scenario     | User wants to check details of a specific gas station |
|  Variants     |The gas station has several price alerts |

<br>

| Scenario 5.1 | |
| ------------- |:-------------:|
|  Precondition     | A gas station is present on  database|
|  Post condition     | The gas station profile is displayed|
|||
| Step#        | Description  |
|  1   | User selects a gas station on the map or from the list |  
|  2   | Server send the datails about that gas station|
|  3   |EzGas display the results |

<br>

### **Use case 6, UC6 - FR9, Update Gas station information**
| Actors Involved        |Gas Station Manager|
| ------------- |:-------------:|
|  Precondition     |Gas station manager is authenticated |  
|  Post condition     |Gas station profile is updated  and the alerts counter is reset |
|  Nominal Scenario     | The manager updates his prices of his gas station |
|  Variants     | The price on the report is wrong|

<br>

| Scenario 6.1 | |
| ------------- |:-------------:|
|  Precondition     | Gas station manager is authenticated|
|  Post condition     | Gas station profile is updated  and the alerts counter is reset|
|||
| Step#        | Description  |
|  1   | Manager update the informations of his gas station |  
|  2   | Database is updated|
|  3   |Server reset alert counter  |

<br>

### **Use case 7, UC7 - FR4, User Authentication**
| Actors Involved        |User Authenticated|
| ------------- |:-------------:|
|  Precondition     |User is not authenticated |  
|  Post condition     |User is authenticated |
|  Nominal Scenario     | An user wants to report a gas station or a price but he have to be authenticated|
|  Variants     ||

<br>

| Scenario 7.1 | |
| ------------- |:-------------:|
|  Precondition     | User is not authenticated|
|  Post condition     | User is authenticated|
|||
| Step#        | Description  |
|  1   | User decide to log-in|  
|  2   | User provide email and password|
|  3    |Server checks the credentials|
|  4 | User is logged|

<br>

### **Use case 8, UC8 - FR6, Create an Account**
| Actors Involved        | User |
| ------------- |:-------------:|
|  Precondition     | User does not have an account |  
|  Post condition     | User has a registred account |
|  Nominal Scenario     | An user wants to sign-in in order to make a report |
|  Variants     | Gas Station Manager account (8.2) |

<br>

| Scenario 8.1 | |
| ------------- |:-------------:|
|  Precondition     | User does not have an account |
|  Post condition     | User has a registred account |
|||
| Step#        | Description  |
|  1    | User decides to sign-in |  
|  2    | User provides email and password |
|  3    | Server sends the confirmation e-mail |
|  4    | User logs through confirmation e-mail |

<br>

| Scenario 8.2 | |
| ------------- |:-------------:|
|  Precondition     | Manager does not have an account |
|  Post condition     | Manager has a registred account |
|||
| Step#        | Description  |
|  1    | Manager decides to sign-in |  
|  2    | Manager provides email and password |
|  3    | Server sends the confirmation e-mail |
|  4    | Manager sends legal documents and credentials |
|  5    | Admin checks credentials |
|  6    | Manager logs through confirmation e-mail |

<br>

### **Use case 9, UC9 - FR5, User Log-out**
| Actors Involved        | User/Manager Authenticated |
| ------------- |:-------------:|
|  Precondition     | User/Manager is  authenticated |  
|  Post condition     | User/Manager is not authenticated |
|  Nominal Scenario     | User/Manager wants to log out of his account |
|  Variants     ||

<br>

| Scenario 9.1 | |
| ------------- |:-------------:|
|  Precondition     | User/Manager is  authenticated|
|  Post condition     | User/Manager is not authenticated|
|||
| Step#        | Description  |
|  1 | User/Manager clicks on log-out button|  
|  2 | Server end the current session of that specific account|
|  3 | User/Manager is redirected to EZGas home page|

<br>

### **Use case 10, UC10 - FR7, Retrive Password**
| Actors Involved        |User|
| ------------- |:-------------:|
|  Precondition     | User/Manager forgot his password |  
|  Post condition     | User/Manager can choose an other password |
|  Nominal Scenario     | User/Manager wants to log-in but he forgot his password. He recives an e-mail to set a different password |
|  Variants     ||

<br>

| Scenario 10.1 | |
| ------------- |:-------------:|
|  Precondition     | User/Manager forgot his password |
|  Post condition     | User/Manager can choose an other password |
|||
| Step#        | Description  |
|  1 | User/Manager clicks on "forgot password" button |  
|  2 |Server sends a link to web page to set a new password |
|  3 | User/Manager writes a new password 2 times and confirms the operetion |
| 4 | Server saves the new password under hash in the database |

<br>

### **Use case 11, UC11 - FR3, Manage account**
| Actors Involved        |User Authenticated|
| ------------- |:-------------:|
|  Precondition     | User/Manager is logged |  
|  Post condition     | Information is changed |
|  Nominal Scenario     | User/Manager wants to change his peronal information registred in his account |
|  Variants     ||

<br>

| Scenario 11.1 | |
| ------------- |:-------------:|
|  Precondition     | User/Manager is logged|
|  Post condition     | User/Manager information is changed|
|||
| Step#        | Description  |
|  1 | User/Manager clicks on edit button in his personal page|  
|  2 | User/Manager edits the field that he wants to change and confirms the operation|
| 3 | Server saves the new information in the database |

# Glossary

```plantuml
left to right direction

class EZGas{

}
class Developer{
	+ Name
	+ Surname
	+ SSN
	+ Salary
}
class Administrator{
	+ Name
	+ Surname
	+ SSN
	+ Salary
}
class User {
	+ Phone_Id
}
class PersonalAccount {
	+ Id
	+ Name
	+ Surname
	+ SSN
}
class GasStation{
	+ Latitude
	+ Longitude
}
class Gas{
	+ Type
	+ Price
}
class RegisteredUser{

}
class GasStationManager{

}
class GoogleMaps{

}

note "In this case the user may be not registered" as N1

note "The Administrator verifies if a gas station manager is actually who he claims to be" as N2

User <|-- PersonalAccount : "extend"
PersonalAccount <|-- RegisteredUser : "extend"
PersonalAccount <|-- GasStationManager : "extend"
GasStation "*"--"*" Gas :"can provide"
EZGas --"*" User
EZGas --"*" GasStation
EZGas --"*" Developer : "working on it"
EZGas -- Administrator
EZGas -- GoogleMaps
Administrator --"*	" GasStationManager
User .. N1
(GasStationManager, Administrator) .. N2
PersonalAccount --"*" GasStation : "notifies presence of / update of"
GasStationManager --"*" GasStation : "owns"
```

# System Design

```plantuml
class EZGas{

}

class App{

}

class EZGasServer{

}

EZGas o-- App
EZGas o-- EZGasServer

```

# Deployment Diagram

```plantuml
left to right direction
node "EZGas system" as ezg{
	database "Users DB" as udb
	database "Gas Stations DB" as gsdb
	node "Internal server" as is

	is -- udb
	is -- gsdb
}

node "User device" as device
node "Google Maps" as map

device "*" -- is
map -- is
```
