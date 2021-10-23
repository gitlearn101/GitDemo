// First REST ASSURED learning programs - ADD Place
// Validate if ADD place API is working as expected

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;


public class Basics {

	public static void main(String[] args) {
		
		// given - all input details
		// when - submit the API (resource+http method)
		// then - validate the response
		
		
		/* Add place */
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String responseETH= given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println("The response are ::::");
		System.out.println(responseETH);
		
		//JsonPath > take string as input and converts into JSON
		JsonPath js = new JsonPath(responseETH); // for parsing Json
		String placeId=js.getString("place_id");
		
		System.out.println("PlaceID ::: "+placeId);
		
		/* Update Place*/
		String newAddress = "Summer Walk, Africa";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().body("msg", equalTo("Address successfully updated"));
		
		/* Get Place*/
		
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		System.out.println("getPlaceResponse :: "+getPlaceResponse);
		
		JsonPath js2 = ReUsableMethods.rawToJson(getPlaceResponse);  // using concept of reusable method here
		//JsonPath js2 = new JsonPath(getPlaceResponse);
		String actualAdress = js2.get("address");
		
		System.out.println("The Actual address from JSON response :::"+actualAdress);
		
		// TestNG
		Assert.assertEquals(actualAdress, newAddress);
		
	}

}
