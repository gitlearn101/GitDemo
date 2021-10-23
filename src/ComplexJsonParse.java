// Refers to CoursePrice word doc for questions
// To understand how to work on nested JSON

import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		// Print nos of course returned by API
		int courseSize = js.getInt("courses.size()"); // courses keyword came from JSONEditor online
		System.out.println("Nos. of Courses = "+courseSize);
		
		// Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount ="+purchaseAmount);
		
		// Print title of first course
		String firstTitle = js.get("courses.title[0]");   // or courses[0].title also fine
		System.out.println("First Title = "+ firstTitle);
		
		// Print All course titles and their respective Prices
		for(int i=0;i<courseSize;i++) {
			String courseName = js.get("courses["+i+"].title");
			int respPrice = js.getInt("courses["+i+"].price");
			
			System.out.println("The price of "+courseName +" is "+ respPrice);
		}
		
		// Print no of copies sold by RPA Course
		String particularCourse = null;
		int copyCount = 0;
		for (int i=0;i<courseSize;i++) {
			 particularCourse = js.get("courses["+i+"].title");
					if(particularCourse.equalsIgnoreCase("Cypress"))
					{
						 copyCount = js.get("courses["+i+"].copies");
						 break;
					}
					
		}
		System.out.println("The copies count of "+ particularCourse+ " is "+ copyCount);
		
	
	
	// Verify if Sum of all Course prices matches with Purchase Amount
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
