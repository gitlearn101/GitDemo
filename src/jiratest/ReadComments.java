package jiratest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;

import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class ReadComments {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "http://localhost:8080/";
		SessionFilter session = new SessionFilter();
		
		//login
		given().header("Content-Type","application/json")
		.body("{ \"username\": \"anand.learn101\", \"password\": \"Test@123\" }")
		.filter(session)
		.when().post("rest/auth/1/session")
		.then().assertThat().statusCode(200);
		
		// read out all comments of an existing ticket
		String keyUnderObservation = "AUT-13";
		
		String getDetails = given().pathParam("key", keyUnderObservation)
		.filter(session)
		.when().get("/rest/api/2/issue/{key}")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("getDetails>> "+getDetails);
		
		
		// read out only comments
		String getComment=given().pathParam("key", keyUnderObservation).queryParam("fields", "comment")
		.filter(session)
		.when().get("/rest/api/2/issue/{key}")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("getComment>> "+getComment);
		
		JsonPath js1 = new JsonPath(getComment);
		int sizeComment = js1.getInt("fields.comment.comments.size()");
		
		System.out.println("sizeComment >>"+sizeComment); 
		
		for (int i=0;i<sizeComment;i++) {
			System.out.println("the comment body are :"+js1.get("fields.comment.comments["+i+"].body"));
			
			String actualComment = js1.get("fields.comment.comments["+i+"].body");
			
			if(actualComment.equalsIgnoreCase("manual 2"))
			{
				System.out.println("Yes, My comment is present in the ticket.");
				break;
			}
		}
	}

}
