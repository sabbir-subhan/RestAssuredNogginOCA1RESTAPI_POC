package test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;



public class RESTHealthDirect {
public static void main(String[] args) {
buildAndExecuteClientAPIReq();
}

public static void buildAndExecuteClientAPIReq() {
		String clientLocationSearchUrl = "https://servicefinder.myagedcare.gov.au/api/acg/v1/helpAtHomeCarePackageFinder";
		String jsonBody = "{\"helpAtHomeCarePackageFinderRequest\":{\"helpAtHomeCarePackageFinderInput\":{\"serviceTypes\":{serviceType\":[\"Home Care Package Level 1\",\"Home Care Package Level 2\",\"Home Care Package Level 3\",\"Home Care Package Level 4\"]},\"clientLocationSearch\":{\"localitySearch\":{\"suburb\":\"SYDNEY\",\"state\":\"NSW\",\"postcode\":\"2000\"}}}}}";
		
		JsonPath jsonPath = furnishBodyAndGetResponse(clientLocationSearchUrl, jsonBody);
		jsonPath.prettyPeek();

}

public static JsonPath furnishBodyAndGetResponse(String searchUrl, String jsonBody) {
	//RestAssured.proxy("proxy.lan.noggin.com.au", 3128);
	RestAssured.useRelaxedHTTPSValidation();
	System.out.println(buildAPIRequestSpec().get().asString());
	Response resp = buildAPIRequestSpec().body(jsonBody).post(searchUrl);
	resp.prettyPeek();
	
	
	boolean logRestAssured = Boolean.getBoolean("restassured.log.all");
	
	ValidatableResponse validatableResp = resp.then();
	
	if (logRestAssured) {
			validatableResp.log().all();
		}

		validatableResp.statusCode(200);
		
		JsonPath jsonPath = resp.jsonPath();
		
		return jsonPath;
}

public static RequestSpecification buildAPIRequestSpec() {
		boolean logRestAssured = Boolean.getBoolean("restassured.log.all");
		
		RequestSpecification apiRequest=given();
		
		if (logRestAssured) {
		apiRequest.log().all();
		}

		apiRequest.contentType("application/json; charset=UTF-8")
		.accept("application/json")
		//.baseUri("https://")
		//.basePath("servicefinder.myagedcare.gov.au/api/acg/v1/helpAtHomeCarePackageFinder")
		.header("x-api-key", "7ca4c25771c54ca283c682a185e72277")		
		.when();
		
		return apiRequest;
}


}