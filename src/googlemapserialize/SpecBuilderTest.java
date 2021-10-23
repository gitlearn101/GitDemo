// Understand serialize concept using GoogleMap API



package googlemapserialize;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class SpecBuilderTest {

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
		
		
		// RequestSpecification
		RequestSpecification ReqResp = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		
		// ResponseSpecification
		ResponseSpecification ResSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		
		RequestSpecification postResponse = given().spec(ReqResp)
		.body(ap);
		Response response = postResponse.when().post("/maps/api/place/add/json")
		.then().spec(ResSpec).extract().response();
		
		String responseString = response.asString();
		System.out.println("responseString >> "+responseString);

	}

}
