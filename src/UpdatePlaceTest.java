// Add place > Update with new address > validate new changes

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import demofiles.DemoPayload;


public class UpdatePlaceTest {

	public static void main(String[] args) {
		
		// ADD PLACE
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String resp = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(DemoPayload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("status", equalTo("OK"))
		.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println("The response are ::::");
		System.out.println(resp);
		
		// PARSE JSON
		JsonPath js = new JsonPath(resp);
		String placeId=js.get("place_id");
		
		System.out.println("Place_ID >>"+placeId);
		
		// UPDATE PLACE via address
		String newAdress = "70 Summer walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAdress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		// GET PLACE >> for validation
		
		String getPlaceResp = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json ")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js2 = new JsonPath(getPlaceResp);
		String actualAddress = js2.getString("address");
		
		assertEquals(actualAddress, newAdress);
		
		
	}

}
