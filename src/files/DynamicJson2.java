// Parameterization using @DataProvider

package files;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson2 {
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String aisle) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String addBookResp = given().header("Content-Type", "application/json")
		.body(payload.Addbook(isbn,aisle))
		.when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200).body("Msg",equalTo("successfully added")).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(addBookResp);
		String ID = js.get("ID");
		
		System.out.println("After Adding book, the ID : "+ID);
		
	}
	
	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		
		return new Object[][] {{"ISBN2000","2000"},{"ISBN3000","3000"}};
	}
	

}
