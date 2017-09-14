package test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class HelloWord_Google_Boooks_API_Sample {
	

	@BeforeClass
	
	public  void setProxy() 
	{
		RestAssured.proxy("proxy.lan.noggin.com.au", 3128);
		//RestAssured.useRelaxedHTTPSValidation();
		
	}
	
	
	@Test
	public void Get_Book_info() {
	     
 //Initializing payload or API body
		//String APIBody = "{\"Username\":\"sabbir\",\"Password\":\"123test\",\"MobilePIN\":\"\"}"; //e.g.- "{\"key1\":\"value1\",\"key2\":\"value2\"}"
		  
	
// Building request using requestSpecBuilder
		RequestSpecBuilder builder = new RequestSpecBuilder();

//Setting API's body
		//builder.setBody(APIBody);
	
//Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");

		RequestSpecification requestSpec = builder.build();	
		requestSpec.log().all();
		
//lets print RequestSpecification
		//System.out.println(requestSpec.head().asString());
	

//Sending the POST request with 	

				
				Response response=given()
						.spec(requestSpec)
						.header("Accept", "application/json")
						.when()
						.get("https://www.googleapis.com/books/v1/volumes?q=google&startIndex=20");
				
		
				
		//ResponseBody<?> resBobdy=response.body();
		//print response
		 response.prettyPeek();
		//status code 
		 System.out.println("Status code:"+response.getStatusCode());
		 //status line
		 System.out.println("Status line:"+response.statusLine());
		 
		 //Header code 
		 System.out.println("Header Content-Type:"+response.getHeader("Content-Type"));
		 System.out.println("Header Expires:"+response.getHeader("Expires"));
		 System.out.println("Header Date:"+response.getHeader("Date"));
		 
			//print Name all contacts
			JsonPath jsonPath = new JsonPath(response.getBody().asString()); //casting  Rest Assured Response to JSonPath object
			 List<Object> itemsId = jsonPath.getList("items.id");
			 System.out.println("Items size:" +itemsId.size());
			 
			 //for loop
			 for(int i=0;i<itemsId.size();i++){
				 System.out.println("Item ID No:"+(i+1)+":"+itemsId.get(i).toString());
			 }
			 
			 List<Object> title = jsonPath.getList("items.volumeInfo.title");
			 System.out.println("Title size:" +title.size());
			 
			//for loop
			 for(int i=0;i<title.size();i++){
				 System.out.println("Item ID No:"+(i+1)+":"+title.get(i).toString());
			 }
			 
		 
		 
						 

		}
	     


	}

	


