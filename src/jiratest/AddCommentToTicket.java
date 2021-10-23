// Login > save session > add new comment to an existing jira ticket

package jiratest;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

public class AddCommentToTicket {

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
		
		// add comment to an existing ticket
		given().pathParam("id", "10002").log().all().header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"body\": \"Commet-1 from Automated REST API\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}")
		.filter(session)
		.when().post("rest/api/2/issue/{id}/comment")
		.then().log().all().assertThat().statusCode(201);
		

	}

}
