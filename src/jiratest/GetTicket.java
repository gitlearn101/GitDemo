// login > create new ticket > Read ticket for particular field

package jiratest;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.*;

public class GetTicket {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "http://localhost:8080/";
		SessionFilter session = new SessionFilter();
		
		// login
		String loginResponse = given().log().all().header("Content-Type","application/json")
		.body("{ \"username\": \"anand.learn101\", \"password\": \"Test@123\" }")
		.filter(session)
		.when().post("rest/auth/1/session")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("login Response>>"+loginResponse);
	
		/*
		
		// create new ticket
		given().log().all().header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"AUT\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"Create automated ticket - 9sept 0957\",\r\n"
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
		*/
		
		// read/get ticket with all fields
		
		String getResponse = given().pathParam("id", "10103")
		.filter(session)
		.when().get("/rest/api/2/issue/{id}")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("get Response"+getResponse);
		
		// read ticket with particular fields only
		
		String getSelectedResponse = given().pathParam("id", "10002").queryParam("fields", "comment")
				.filter(session)
				.when().get("/rest/api/2/issue/{id}")
				.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("get Selected Response"+getSelectedResponse);
		
	}

}
