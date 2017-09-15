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


public class HelloWord_RestAssured_NogginOCA1RESTAPI_POC {
	
	public static Response response;
	public static String jsonAsString;
	
	//Global variables
	String sessionID=null;
	String TermsAckCode=null;
	
	@BeforeClass
	
	public  void setProxy() 
	{
		RestAssured.proxy("proxy.lan.noggin.com.au", 3128);
		//RestAssured.useRelaxedHTTPSValidation();
		
	}
	
	
	@Test
	public void NogginOCARESTTestCreateSession() {
	     
 //Initializing payload or API body
		String APIBody = "{\"Username\":\"sabbir\",\"Password\":\"123test\",\"MobilePIN\":\"\"}"; //e.g.- "{\"key1\":\"value1\",\"key2\":\"value2\"}"
		  
	
// Building request using requestSpecBuilder
		RequestSpecBuilder builder = new RequestSpecBuilder();

//Setting API's body
		builder.setBody(APIBody);
	
//Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");

		RequestSpecification requestSpec = builder.build();	
		System.out.println("**************************Start of Create Session POST*************");
		requestSpec.log().all();
		
//lets print RequestSpecification
		//System.out.println(requestSpec.head().asString());
	

//Sending the POST request with 	

				
				Response response=given()
						.spec(requestSpec)
						.header("Accept", "application/json")
						.when()
						.post("https://bitnoiseqa.nogginoca.com:443/api/v1/session");
				
		
				
		//ResponseBody<?> resBobdy=response.body();
		//print response
		 //response.prettyPeek();
		//status code 
		 System.out.println("Status code:"+response.getStatusCode());
		 //status line
		 System.out.println("Status line:"+response.statusLine());
		 
		 //Header code 
		 System.out.println("Header Content-Type:"+response.getHeader("Content-Type"));
		 System.out.println("Header Expires:"+response.getHeader("Expires"));
		 System.out.println("Header Date:"+response.getHeader("Date"));
		 
		 
		 //Assertions
		 JsonPath jsonPath = new JsonPath(response.getBody().asString()); //casting  Rest Assured Response to JSonPath object
		 String stateCode=jsonPath.getString("responsePayloads.StateCode").replace("[", "").replace("]", "").trim();
		 sessionID=jsonPath.getString("responsePayloads.SessionID").replace("[", "").replace("]", "").trim();//removing start [ and end ] from the string
		 TermsAckCode=jsonPath.getString("responsePayloads.TermsAckCode").replace("[", "").replace("]", "").trim();
		 System.out.println("State code from JSon Path "+stateCode);
		 System.out.println("Session ID from JSon Path "+sessionID);
		 System.out.println("Terms code from JSon Path "+TermsAckCode);
		 //List 
		 
		 List<Object> responsePayloads = jsonPath.getList("responsePayloads");
		 System.out.println("size:" +responsePayloads.size());
		 System.out.println("As tring" +responsePayloads.toString());
		 
		 response.
		 then()
		 .body("responsePayloads.StateCode",contains("WAIT_TERMS_AND_CONDITIONS"));//using JSON Path
		 
		 System.out.println("**************************END of Create Session POST*************\n");
		 
		 
						 

	}
	
	@Test(dependsOnMethods = { "NogginOCARESTTestCreateSession" })
	public void NogginOCARESTTestAcceptTermsAndConditions() {
	     
 //Initializing payload or API body
		String APIBody = "{\"TermsAccept\": ["+TermsAckCode+"]}"; //e.g.- "{\"key1\":\"value1\",\"key2\":\"value2\"}"
		  
	
// Building request using requestSpecBuilder
		RequestSpecBuilder builder = new RequestSpecBuilder();

//Setting API's body
		builder.setBody(APIBody);
	
//Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");
		builder.addHeader("X-Session-ID",sessionID);//adding Session ID to header

		RequestSpecification requestSpec = builder.build();
		System.out.println("**************************Start of Create Session PUT*************");
		requestSpec.log().all();
		
//lets print RequestSpecification
		//System.out.println(requestSpec.head().asString());
	

//Sending the POST request with 	

				
				Response response=given()
						.spec(requestSpec)
						.header("Accept", "application/json")
						.when()
						.put("https://bitnoiseqa.nogginoca.com:443/api/v1/session");
				
		
				
		//ResponseBody<?> resBobdy=response.body();
		//print response
		 //response.prettyPeek();
		//status code 
		 System.out.println("Status code:"+response.getStatusCode());
		 //status line
		 System.out.println("Status line:"+response.statusLine());
		
		 
		 //Assertions

		 response.
		 then()
		 .body("responsePayloads.StateCode",contains("AUTHENTICATED"));	 //checking if session PUT is successful 
		 System.out.println("**************************End of Create Session PUT*************\n");
		 		 

	}
	
	@Test(dependsOnMethods = { "NogginOCARESTTestAcceptTermsAndConditions" })
	public void NogginOCAREStGetContacts() {
	     
 //Initializing payload or API body
		//String APIBody = "{\"TermsAccept\": ["+TermsAckCode+"]}"; //e.g.- "{\"key1\":\"value1\",\"key2\":\"value2\"}"
		  
	
// Building request using requestSpecBuilder
		RequestSpecBuilder builder = new RequestSpecBuilder();

//Setting API's body
		//builder.setBody(APIBody);
	
//Setting content type as application/json or application/xml
		builder.setContentType("application/json; charset=UTF-8");
		builder.addHeader("X-Session-ID",sessionID);//adding Session ID to header

		RequestSpecification requestSpec = builder.build();	
		System.out.println("**************************Start of GET Contacts*************");
		requestSpec.log().all();
//lets print RequestSpecification
		//System.out.println(requestSpec.head().asString());
	

//Sending the POST request with 	

				
				Response response=given()
						.spec(requestSpec)
						.header("Accept", "application/json")
						.when()
						.get("https://bitnoiseqa.nogginoca.com:443/api/v1/contacts");
				
			
		
		//print response
		 response.prettyPeek();
		//status code 
		 System.out.println("Status code:"+response.getStatusCode());
		 //status line
		 System.out.println("Status line:"+response.statusLine());
		
		//Assertions

		 response.
		 then()
		 .body("responsePayloads.Name",hasItems("Appium","Contact")); //check if Name array has Appium and Contact
		 
		//print Name all contacts
		JsonPath jsonPath = new JsonPath(response.getBody().asString()); //casting  Rest Assured Response to JSonPath object
		 List<Object> contactName = jsonPath.getList("responsePayloads.Name");
		 System.out.println("Contact size:" +contactName.size());
		 
		 //for loop
		 for(int i=0;i<contactName.size();i++){
			 System.out.println("Contact Name No:"+(i+1)+":"+contactName.get(i).toString());
		 }
		 
		 String nextPageURL=jsonPath.getString("nextPageURL");
		 System.out.println("nextPageURL:" +nextPageURL);
		 System.out.println("**************************END of GET Contacts*************\n");
		 		 

	}

	

}
