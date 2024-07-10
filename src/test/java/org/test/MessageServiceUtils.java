package org.test;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class MessageServiceUtils {

	static {
		RestAssured.baseURI = "http://localhost:3000";
	}

	public static String createUser(String name) {
		Response response=given().contentType("application/json")
				.body("{\"name\":\"" + name +"\"}")
				.when()
				.post("/users")
				.then()
				.statusCode(201)
				.body("id")
				.extract()
				.response();
return response.jsonPath().getString("id");
		
	}
	
	public static String createMessage(String fromUserId,String toUserId,String messageContent) {
		Response response= given().contentType("application/json").body("{\r\n"
				+ "    \"from\": {\r\n"
				+ "        \"id\": \""+fromUserId + "\"\r\n"
				+ "    },\r\n"
				+ "    \"to\": {\r\n"
				+ "        \"id\": \""+toUserId+"\"\r\n"
				+ "    },\r\n"
				+ "    \"message\": \""+messageContent+ "\",")
				.when().post("/message").then().statusCode(201).body("id").extract().response();
		
		return response.jsonPath().getString("id");
	}
	
	public static void main(String[] args) {
		String userId1 = createUser("Anu Raj");
		System.out.println("Created with ID:"+userId1);
		
		String userId2 = createUser("Anusha Rajendran");
		System.out.println("Created with user Id:"+userId2);
		
		String messageId = createMessage(userId1, userId2,"Hello Anu,This is Anusha");
		System.out.println("Created message with Id "+messageId);
	}

}
