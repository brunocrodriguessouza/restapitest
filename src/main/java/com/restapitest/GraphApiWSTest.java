package com.restapitest;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;


public class GraphApiWSTest {
	
	private String accessToken;

	@Before
	public void setUp(){
		accessToken = "EAACEdEose0cBAAl49hUcVokDh1GqZAnmoyyInAOtJJWvtDRtvsfDYF7ny3c0AyZBTY0BzPAfFKlpmvSkwts7X1iQvjDnWoP3iB5Vdc5ni7vwvckdmtT9OTwZAFcZB5CPLPXP1ZCz9sEIviInPUGDnSsf1QMK4RZCZBoKWmZATD07FO968bGVAO75pb7aOd6MK2t2OZB9K9hqNI8Cj69mzlrZAmD55ED9RZBlJcZD";
	}
	
	@Test
	public void shouldReturnThePostList() {

		JsonPath path = getPosts();
		
		assertTrue(path.getList("data.message").contains("o dia esta ensolarado e bonito !!!!!"));	
	}

	@Test
	public void shouldAddAPost(){
		
		String message = "Parou de ventar agora";
		JSONObject json = new JSONObject();
		json.put("message", message);
		message = json.toString();
		
		JsonPath path = addFacebookPost(message);
		
		System.out.println(path.getString("id"));
		assertTrue(!path.getString("id").isEmpty());
	}

	private JsonPath addFacebookPost(String message) {
		JsonPath jsonPath = given()
				.header("Accept", "application/json")
				.contentType(ContentType.JSON)
				.body(message)
				.post("https://graph.facebook.com/v2.12/me/feed?access_token=" +accessToken)
				.andReturn().jsonPath();
		return jsonPath;
	}
	
	private JsonPath getPosts() {
		JsonPath path = given()
				.header("Accept", "application/json")
				.param("access_token", accessToken)
				.get("https://graph.facebook.com/v2.12/me/feed")
				.andReturn().jsonPath();
		return path;
	}

}
