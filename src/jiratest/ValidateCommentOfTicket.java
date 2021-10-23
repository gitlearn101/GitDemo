// login > create a new ticket > add comment to that ticket > validate the comment

package jiratest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;

import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class ValidateCommentOfTicket {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "http://localhost:8080/";
		SessionFilter session = new SessionFilter();
		
		//login
		given().header("Content-Type","application/json")
		.body("{ \"username\": \"anand.learn101\", \"password\": \"Test@123\" }")
		.filter(session)
		.when().post("rest/auth/1/session")
		.then().assertThat().statusCode(200);
		
		// create a new ticket
		String createNewTicketResponse = given().header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"AUT\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"Create ticket to test multiComment & validate - automate\",\r\n"
				+ "        \"issuetype\": {\r\n"
				+ "            \"name\": \"Bug\"\r\n"
				+ "        },\r\n"
				+ "        \r\n"
				+ "        \"description\": \"description\"\r\n"
				+ "        }\r\n"
				+ "}")
		.filter(session)
		.when().post("rest/api/2/issue")
		.then().assertThat().statusCode(201).extract().response().asString();
		
		System.out.println("create NewTicket Response"+createNewTicketResponse);
		
		JsonPath js1 = new JsonPath(createNewTicketResponse);
		String respParam = js1.get("key");
		System.out.println("respParam > "+respParam);
		
		// add comment to an existing ticket
		
		String testComment = "Automated comment generated";
		
		String addCommentResponse = given().header("Content-Type","application/json").pathParam("key", respParam)
		.body("{\r\n"
				+ "    \"body\": \""+testComment+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}")
		.filter(session)
		.when().post("rest/api/2/issue/{key}/comment")
		.then().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js2 = new JsonPath(addCommentResponse);
		String readComment = js2.get("body").toString();
		
		System.out.println("readComment > "+readComment);
		
		Assert.assertEquals(testComment, readComment);  // Will throw error if the matches fails
		
	}

}
