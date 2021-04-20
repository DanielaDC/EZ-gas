package it.polito.ezgas.controllertests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.UserDto;

public class TestController {
	
	private int lastIdGasStation;
	private int lastIdUser;

	@Before
	@Test
	public void testGetAllUsers() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/user/getAllUsers");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			UserDto[] uArray = mapper.readValue(jsonFromResponse, UserDto[].class);
			
			assert(response.getStatusLine().getStatusCode() == 200);
			assert(uArray.length == 2);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetUserById_IdNotInDB() {
		// Id cambia in base alla personale configurazione del database
		HttpUriRequest request = new HttpGet("http://localhost:8080/user/getUser/4/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			
			assert(jsonFromResponse.contentEquals(""));
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testUserById_IdInDB() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/user/getUser/925/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			
			assert(jsonFromResponse.contains("admin"));
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}

	@Test
	public void testSaveUser() {
		HttpPost request = new HttpPost("http://localhost:8080/user/saveUser");
		StringEntity body = new StringEntity("{\"UserId\":1,\"userName\":\"testUserName\",\"password\":\"testPassword\",\r\n" + 
				"\"email\":\"testEmail\",\"reputation\":3,\"admin\":false}",
				ContentType.APPLICATION_JSON);
		request.setEntity(body);
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			boolean result = response.getStatusLine().getStatusCode() == 200;
			assert(result);
			if(result) { 
				HttpUriRequest request2 = new HttpGet("http://localhost:8080/user/getAllUsers");
				HttpResponse response2 = HttpClientBuilder.create().build().execute(request2);
				String jsonFromResponse = EntityUtils.toString(response2.getEntity());
				ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				UserDto[] uArray = mapper.readValue(jsonFromResponse, UserDto[].class);
				lastIdUser = uArray[uArray.length - 1].getUserId();
			}
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	

	@Test
	public void testLogin() {
		HttpPost request = new HttpPost("http://localhost:8080/user/login");
		StringEntity body = new StringEntity("{\"user\":\"admin@ezgas.com\",\"pw\":\"admin\"}",
				ContentType.APPLICATION_JSON);
		request.setEntity(body);
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			
			assert(jsonFromResponse.contains("admin"));
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}		
	}
	
	@Test
	public void testIncreaseUserReputation() {
		HttpPost request = new HttpPost("http://localhost:8080/user/increaseUserReputation/926");
		
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());

			System.out.println(jsonFromResponse);
			//Dipende dai valori correnti di id e valore
			assert(jsonFromResponse.contains("4"));
			System.out.println(response.getStatusLine().getStatusCode());
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}		
	}
	
	@Test
	public void testDecreaseUserReputation() {
		HttpPost request = new HttpPost("http://localhost:8080/user/decreaseUserReputation/925");
		
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());

			//Dipende dai valori correnti di id e valore
			assert(jsonFromResponse.contains("4"));
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}		
	}
	
	@Test
	public void testDeleteUser_IdNotInDB() {
		HttpUriRequest request = new HttpDelete("http://localhost:8080/user/deleteUser/1/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@After
	@Test
	public void testDeleteUser_IdInDB() {
		System.out.println("lastID " + lastIdUser);
		HttpUriRequest request = new HttpDelete("http://localhost:8080/user/deleteUser/" + lastIdUser + "/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Before
	@Test
	public void testGetAllGasStations() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/getAllGasStations");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
			
			assert(response.getStatusLine().getStatusCode() == 200);
			assert(gsArray.length == 5); //ogni volta che si esegue un test la save aggiunge elementi al db, quindi sarà sempre maggiore del giro precedente
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetGasStationById_IdNotInDB() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/getGasStation/1000/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			
			assert(jsonFromResponse.contentEquals(""));
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetGasStationById_IdInDB() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/getGasStation/6/"); //indicare un id che si sa essere nel 
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			
			assert(jsonFromResponse.contains("name"));
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}

	@Test
	public void testSaveGasStation() {
		HttpPost request = new HttpPost("http://localhost:8080/gasstation/saveGasStation");
		StringEntity body = new StringEntity("{\"gasStationId\":1,\"gasStationName\":\"testGasStationName\",\"gasStationAddress\":\"testGasStationAddress\",\r\n" + 
				"\"hasDiesel\":false,\"hasSuper\":false,\"hasSuperPlus\":false,\"hasGas\":false,\"hasMethane\":false,\r\n" + 
				"\"carSharing\":\"Enjoy\",\"lat\":2.0,\"lon\":2.0,\r\n" + 
				"\"dieselPrice\":1.52,\"superPrice\":1.52,\"superPlusPrice\":1.52,\"gasPrice\":1.52,\"methanePrice\":1.52,\r\n" + 
				"\"reportUser\":1,\"reportTimestamp\":\"2019-03-25 16:30:30\",\"reportDependability\":0.0}",
				ContentType.APPLICATION_JSON);
		request.setEntity(body);
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			boolean result = response.getStatusLine().getStatusCode() == 200;
			assert(result);
			if(result) { //metodo per trovare ultimo elemento inserito ed eliminarlo dopo nella delete_IdInDB
				/*N.B.: Affinchè funzioni saveTest va eseguito prima di delete_IdInDB*/
				HttpUriRequest request2 = new HttpGet("http://localhost:8080/gasstation/getAllGasStations");
				HttpResponse response2 = HttpClientBuilder.create().build().execute(request2);
				String jsonFromResponse = EntityUtils.toString(response2.getEntity());
				ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
				lastIdGasStation = gsArray[gsArray.length - 1].getGasStationId();
			}
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testDeleteGasStation_IdNotInDB() {
		HttpUriRequest request = new HttpDelete("http://localhost:8080/gasstation/deleteGasStation/1/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@After
	@Test
	public void testDeleteGasStation_IdInDB() {
		System.out.println("lastID " + lastIdGasStation);
		HttpUriRequest request = new HttpDelete("http://localhost:8080/gasstation/deleteGasStation/" + lastIdGasStation + "/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetGasStationsByGasolineType_Diesel() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/searchGasStationByGasolineType/Diesel/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
			
			assert(gsArray.length == 2);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetGasStationsByGasolineType_Super() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/searchGasStationByGasolineType/Super/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
			
			assert(gsArray.length == 2);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetGasStationsByGasolineType_SuperPlus() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/searchGasStationByGasolineType/SuperPlus/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
			
			assert(gsArray.length == 1);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetGasStationsByGasolineType_Gas() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/searchGasStationByGasolineType/Gas/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
			
			assert(gsArray.length == 1);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetGasStationsByGasolineType_Methane() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/searchGasStationByGasolineType/Methane/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
			
			assert(gsArray.length == 1);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetGasStationsByProximity_NoGS() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/searchGasStationByProximity/1.0/1.0/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
			
			assert(gsArray.length == 0);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetGasStationsByProximity_someGS() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/searchGasStationByProximity/45.06/7.68/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
			
			assert(gsArray.length == 3);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}

	@Test
	public void testGetGasStationsWithCoordinates_OnlyLatLon() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/getGasStationsWithCoordinates/45.06/7.68/null/null/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
			
			assert(gsArray.length == 3);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetGasStationsWithCoordinates_LatLonGt() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/getGasStationsWithCoordinates/45.06/7.68/Gas/null/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
			
			assert(gsArray.length == 1);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetGasStationsWithCoordinates_LatLonCs() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/getGasStationsWithCoordinates/45.06/7.68/null/Car2Go/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
			System.out.println("id" + gsArray.length);
			assert(gsArray.length == 1);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void testGetGasStationsWithCoordinates_Total() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/gasstation/getGasStationsWithCoordinates/49.06/7.68/SuperPlus/Car2Go/");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			String jsonFromResponse = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			GasStationDto[] gsArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
			
			assert(gsArray.length == 1);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}

	@Test
	public void testSetGasStationReport() {
		HttpUriRequest request = new HttpPost("http://localhost:8080/gasstation/setGasStationReport/1/1.50/1.50/1.50/1.50/1.50/1");
		try {
			HttpResponse response = HttpClientBuilder.create().build().execute(request);
			assert(response.getStatusLine().getStatusCode() == 200);
		} catch (IOException e) {
			fail("Exception: " + e);
		}
	}

}
