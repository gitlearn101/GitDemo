// Automate ticket creation process using REST API


package jiratest;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.*;

public class CreateTicket {

	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8080/";
		
		// login 
		
		SessionFilter session = new SessionFilter(); // To remember session throughout execution
		
		String loginResponse = given().log().all().header("Content-Type","application/json")
		.body("{ \"username\": \"anand.learn101\", \"password\": \"Test@123\" }")
		.filter(session)
		.when().post("rest/auth/1/session")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("login Response : "+loginResponse);

		// create new ticket
				given().log().all().header("Content-Type","application/json")
				.body("{\r\n"
						+ "    \r\n"
						+ "    \"fields\": {\r\n"
						+ "        \"project\": {\r\n"
						+ "            \"key\": \"AUT\"\r\n"
						+ "        },\r\n"
						+ "        \"summary\": \"Create frontend service-automated\",\r\n"
						+ "        \"issuetype\": {\r\n"
						+ "            \"name\": \"Bug\"\r\n"
						+ "        },\r\n"
						+ "        \r\n"
						+ "        \"description\": \"description\"\r\n"
						+ "        }\r\n"
						+ "}")
				.filter(session)
				.when().post("rest/api/2/issue")
				.then().log().all().assertThat().statusCode(201);
	
	}

}
