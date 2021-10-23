// Handle authentication using Oauth 2.0 (refers to postman collection-Oauth_explore_self)

package oauthtest;

import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;

public class OauthValidate {

	public static void main(String[] args) throws InterruptedException {
		
		// Bypassing google login as automated script is blocked
		/*
		System.setProperty("webdriver.chrome.driver","D:\\tool-setup\\2021\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("elagovank07");
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("NewPass@123");
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		String url = driver.getCurrentUrl();
		System.out.println("URL >>"+url);
		
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope)[0];
		
		System.out.println("code >>"+code);
		*/
		
		
		
		// Got url from manually login to the GetCode endpoint
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWhe_8WM8m4NdTehSXO7XFoUkkaQsGI2F4qjx3uf0UrHLjutj-gNXG6vKcbgN4w9zg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none#";
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		
		System.out.println("code >>"+code);
		
		
		
		// GetAccessToken
		// to prevent RestAssured from modifying special chars
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
		
		/*
		String actualRequestResp = given().queryParam("access_token", "")
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		*/
		// shortcut to above code block is given below
		String actualRequestResp = given().queryParam("access_token", accessToken)
				.when().get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		
		System.out.println("actualRequestResp>>"+actualRequestResp);

	}

}
