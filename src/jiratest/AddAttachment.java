// Add attachment to existing ticket


package jiratest;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.*;

import java.io.File;

public class AddAttachment {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "http://localhost:8080";
		
		// login 
		
		SessionFilter session = new SessionFilter();
		
		String loginResponse = given().log().all().header("Content-Type","application/json")
		.body("{ \"username\": \"anand.learn101\", \"password\": \"Test@123\" }")
		.filter(session)
		.when().post("/rest/auth/1/session")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("LOgin response >>"+loginResponse);
		
		// Add comment to an existing ticket
		
		given().log().all().pathParam("id","10002").header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"body\": \"Comment-2 from REST API to test attachment\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}")
		.filter(session)
		.when().post("rest/api/2/issue/{id}/comment")
		.then().log().all().assertThat().statusCode(201);

		// Add attachment to above ticket
		given().log().all().header("X-Atlassian-Token","no-check").pathParam("id","10002").multiPart("file",new File("jiraAttachFile")).header("Content-Type","multipart/form-data")
		.filter(session)
		.when().post("/rest/api/2/issue/{id}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		
		
	}

}
