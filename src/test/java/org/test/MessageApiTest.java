package org.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import groovy.util.ObservableList.ElementUpdatedEvent;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class MessageApiTest {

	@BeforeAll
	public void setup() {
		RestAssured.baseURI = "";
	}

	@Test
	public void testCreateMessage() {
		String fromUserId = createUser("Anu Raj");
		String toUserId = createUser("Anusha Rajendran");

		String message = "";

		Response response = given().contentType("application/json").body("").when().post().then().statusCode(201)
				.body("id", notNullValue()).extract().response();

		String messageId = response.jsonPath().getString("id");

	}

	@Test
	public void testGetMessages() {
		String fromUserId = createUser("Anu Raj");
		String toUserId = createUser("Anusha Rajendran");

		String messageContent = "";
		createMessage(fromUserId, toUserId, messageContent);
		given().when().get("").then().statusCode(200).body("message", hasItem(messageContent));
	}

	@Test
	public void testGetMessageById() {
		String fromUserId = createUser("Anu Raj");
		String toUserId = createUser("Anusha Rajendran");

		String messageContent = "";
		createMessage(fromUserId, toUserId, messageContent);
		given().when().get("").then().statusCode(200).body("message", equalTo(messageContent));
	}

	@Test
	public void testUpdateMessage() {
		String fromUserId = createUser("Anu Raj");
		String toUserId = createUser("Anusha Rajendran");

		String originalMessageContent = "";
		String messageId = createMessage(fromUserId, toUserId, originalMessageContent);
		String updatedMessageContent = "";
		given().body("").when().put("").then().statusCode(200).body("message", equalTo(updatedMessageContent));
	}

	@Test
	public void testDeleteMessage() {
		String fromUserId = createUser("Anu Raj");
		String toUserId = createUser("Anusha Rajendran");

		String messageContent = "";
		String messageId = createMessage(fromUserId, toUserId, messageContent);
		given().when().delete("/message/" + messageId).then().statusCode(204);
		given().when().get("/message/" + messageId).then().statusCode(404);

	}

	private String createUser(String name) {
		Response response = given().contentType("application/json").body("").when().post().then().statusCode(201)
				.body("id", notNullValue()).extract().response();
		return response.jsonPath().getString("id");
	}

	private String createMessage(String fromUserId, String toUserId, String messageContent) {
		Response response = given().contentType("application/json").body("").when().post("").then().statusCode(201)
				.body("id", notNullValue()).extract().response();
		return response.jsonPath().getString("id");
	}

}
