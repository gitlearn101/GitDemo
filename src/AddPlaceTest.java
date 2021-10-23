// Automate API validation of AddPlace 

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import demofiles.DemoPayload;

public class AddPlaceTest {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String addResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(DemoPayload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("status", equalTo("OK"))
		.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println("The response are ::::");
		System.out.println(addResponse);
		
		
		
		
	}

}
