// Understand serialize concept using GoogleMap API



package googlemapserialize;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

public class SerializeTest {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		// Create an object of Class AddPlace and add all the values for respectives keys
		AddPlace ap = new AddPlace();
		ap.setAccuracy(50);
		ap.setAddress("29, side layout, cohen 09");
		ap.setLanguage("French-IN");
		ap.setName("Frontline house");
		ap.setPhone_number("(+91) 983 893 3937");
		ap.setWebsite("http://google.com");
		
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		ap.setLocation(loc);
		
		
		List<String> types = new ArrayList<>();
		types.add("shoe park");
		types.add("shop");
		ap.setTypes(types);
		
		// Instead of copying body from API contract we will use serialize concept to generate body
		// We will build a class where all its variables is nothing but a key to the contract document
		
		String postResponse = given().queryParam("key", "qaclick123")
		.body(ap) // Adding object to class AddPlace as an argument to body segment
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("postResponse >> "+postResponse);

	}

}
