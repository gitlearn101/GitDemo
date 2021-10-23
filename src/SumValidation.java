// Basic of TestNG to understand workflow

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

// Verify if Sum of all Course prices matches with Purchase Amount
public class SumValidation {
	
	@Test
	public void sumOfCourses() {
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		int courseSize = js.getInt("courses.size()"); // courses keyword came from JSONEditor online
		System.out.println("Nos. of Courses = "+courseSize);
		
		int priceCheck = 0;
		int noOfCopies = 0;	
		int CourseWiseSales = 0;
		int sum = 0;
	for (int i =0;i<courseSize;i++) {
		 priceCheck = js.getInt("courses["+i+"].price");
		System.out.println("price >>"+priceCheck);
		  noOfCopies = js.getInt("courses["+i+"].copies");
		System.out.println("copies>>"+noOfCopies);
	
		CourseWiseSales = priceCheck * noOfCopies;
		System.out.println(CourseWiseSales);
		sum = sum + CourseWiseSales;
		System.out.println("Total ="+sum);
				
		
	} 
	
	int finalAmount = 0;
	finalAmount = js.get("dashboard.purchaseAmount");
	System.out.println("purchaseAmount :"+finalAmount);
	
	Assert.assertEquals(finalAmount, sum);
		 
		
	}

}
