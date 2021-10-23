// Dynamically build JSON payload with external data input

package files;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class DynamicJson {

	@Test
	public void addBook() {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String addBookResp = given().header("Content-Type", "application/json")
		.body(payload.Addbook("ISBN5","005"))
		.when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200).body("Msg",equalTo("successfully added")).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(addBookResp);
		String ID = js.get("ID");
		
		System.out.println("After Adding book, the ID : "+ID);
		
	}
	
}
