// Handle authentication using Oauth 2.0 (refers to postman collection-Oauth_explore_self) followed by PoJo validation

package oauthtest;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OauthTestPojo {

	public static void main(String[] args) throws InterruptedException {
		
		
		
		
		
		// Got url from manually login to the GetCode endpoint
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWiEHD5RlBgdl7mq_4KSr6LrUSdnxwdApRHP8xOxo6XLaQf8JSGgJUkxE2t3iJB5iw&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none#";
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		
		System.out.println("code >>"+code);
		
		
		
		// GetAccessToken
		// to prevent RestAssured from modifying special chars, use urlEncoding
		String getAccessTokenResp = given().urlEncodingEnabled(false) 
		.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(getAccessTokenResp);
		String accessToken = js.get("access_token");
		
		System.out.println("accessToken>>"+accessToken);
				
		
		
		// actualRequest
		GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		
		System.out.println("POJO GC>>"+gc);
		System.out.println("GC expertise from POJO>>"+gc.getExpertise());
		
		// find out price of courses given under API category by using Index		
		System.out.println("The price of API course>>"+gc.getCourses().getApi().get(1).getCourseTitle());
		
		// find out price of a particular course under API based on Title search
		List<Api> apiCourses = gc.getCourses().getApi();
		String testCourseName = "SoapUI Webservices testing";
		
		for(int i=0;i<apiCourses.size();i++) {
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase(testCourseName))
			{
				System.out.println("Price of "+testCourseName+ " is >>"+apiCourses.get(i).getPrice());
			}
		}
		
		// Display Titlename under WebAutomation course section
		List<WebAutomation> WebAutomationCourses = gc.getCourses().getWebAutomation();
		
		for(int j=0;j<WebAutomationCourses.size();j++) {
			
			System.out.println("The title under WebAutomation section are >>"+WebAutomationCourses.get(j).getCourseTitle());
		}
		

		// Compare actual courseTitle with expected/test courseTitle
		String[] testCourseTitlesOfWebAutomation = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		List<String> testCourseTitlesWA = Arrays.asList(testCourseTitlesOfWebAutomation); // converting array into arraylist because comparison of array with arraylist is not posible
		
		ArrayList<String> collectActualCourseTitlesWebAutomation = new ArrayList<>();
		
		for (int k =0;k<WebAutomationCourses.size();k++) {
			collectActualCourseTitlesWebAutomation.add(gc.getCourses().getWebAutomation().get(k).getCourseTitle());
			
		}
		
		
		Assert.assertTrue(collectActualCourseTitlesWebAutomation.equals(testCourseTitlesWA));
	}

}
