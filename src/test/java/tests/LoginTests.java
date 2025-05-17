package tests;

import models.ErrorResponseModel;
import models.LoginBodyModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTests extends TestBase {

  @Test
  void successfulLoginTest() {

    LoginBodyModel authData = new LoginBodyModel();
    authData.setEmail("eve.holt@reqres.in");
    authData.setPassword("cityslicka");

    LoginResponseModel response = given()
            .header("x-api-key", "reqres-free-v1")
            .body(authData)
            .contentType(JSON)
            .log().uri()
            .when()
            .post("/login")
            .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .extract().as(LoginResponseModel.class);

    assertThat(response.getToken(), notNullValue());
  }

  @Test
  void unsuccessfulLogin400Test() {
    LoginBodyModel authData = new LoginBodyModel();
    authData.setEmail("");
    authData.setPassword("");

    ErrorResponseModel response = given()
            .header("x-api-key", "reqres-free-v1")
            .body(authData)
            .contentType(JSON)
            .log().uri()
            .when()
            .post("/login")
            .then()
            .log().status()
            .log().body()
            .statusCode(400)
            .extract().as(ErrorResponseModel.class);

    assertThat(response.getError(), is("Missing email or username"));
  }

  @Test
  void userNotFoundTest() {

    LoginBodyModel authData = new LoginBodyModel();
    authData.setEmail("eve.holt1@reqres.in");
    authData.setPassword("cityslicka");

    ErrorResponseModel response = given()
            .header("x-api-key", "reqres-free-v1")
            .body(authData)
            .contentType(JSON)
            .log().uri()
            .when()
            .post("/login")
            .then()
            .log().status()
            .log().body()
            .statusCode(400)
            .extract().as(ErrorResponseModel.class);

    assertThat(response.getError(), is("user not found"));
  }

  @Test
  void missingPasswordTest() {

    LoginBodyModel authData = new LoginBodyModel();
    authData.setEmail("eve.holt1@reqres.in");
    authData.setPassword("");

    ErrorResponseModel response = given()
            .header("x-api-key", "reqres-free-v1")
            .body(authData)
            .contentType(JSON)
            .log().uri()
            .when()
            .post("/login")
            .then()
            .log().status()
            .log().body()
            .statusCode(400)
            .extract().as(ErrorResponseModel.class);

    assertThat(response.getError(), is("Missing password"));
  }

  @Test
  void missingLoginTest() {

    LoginBodyModel authData = new LoginBodyModel();

    authData.setEmail("");
    authData.setPassword("cityslicka");

    ErrorResponseModel response = given()
            .header("x-api-key", "reqres-free-v1")
            .body(authData)
            .contentType(JSON)
            .log().uri()
            .when()
            .post("/login")
            .then()
            .log().status()
            .log().body()
            .statusCode(400)
            .extract().as(ErrorResponseModel.class);

    assertThat(response.getError(), is("Missing email or username"));
  }

  @Test
  void wrongBodyTest() {
    String authData = "%}";
    given()
            .header("x-api-key", "reqres-free-v1")
            .body(authData)
            .contentType(JSON)
            .log().uri()
            .when()
            .post("/login")
            .then()
            .log().status()
            .log().body()
            .statusCode(400);
  }

  @Test
  void unsuccessfulLogin415Test() {
    given()
            .log().uri()
            .post("/login")
            .then()
            .log().status()
            .log().body()
            .statusCode(415);
  }
}
