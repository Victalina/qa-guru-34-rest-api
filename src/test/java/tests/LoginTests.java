package tests;

import io.restassured.response.Response;
import models.ErrorResponseModel;
import models.LoginBodyModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static specs.LoginSpec.*;

@Tag("rest_api_tests")
public class LoginTests extends TestBase {

  @DisplayName("Successful Login")
  @Test
  void successfulLoginTest() {

    LoginBodyModel authData = new LoginBodyModel();
    authData.setEmail("eve.holt@reqres.in");
    authData.setPassword("cityslicka");

    LoginResponseModel response = step("Make request", () ->
            given(loginRequestSpec)
                    .body(authData)
                    .when()
                    .post()
                    .then()
                    .spec(loginResponseSpec)
                    .extract().as(LoginResponseModel.class));

    step("Check response", () ->
            assertThat(response.getToken(), notNullValue()));
  }

  @DisplayName("Unsuccessful Login - Missing email and password")
  @Test
  void unsuccessfulLoginMissingEmailAndPasswordTest() {
    LoginBodyModel authData = new LoginBodyModel();
    authData.setEmail("");
    authData.setPassword("");

    ErrorResponseModel response = step("Make request", () ->
            given(loginRequestSpec)
                    .body(authData)
                    .when()
                    .post()
                    .then()
                    .spec(loginResponseStatus400Spec)
                    .extract().as(ErrorResponseModel.class));
    step("Check response", () ->
            assertThat(response.getError(), is("Missing email or username")));
  }

  @DisplayName("Unsuccessful Login - User not found")
  @Test
  void userNotFoundTest() {

    LoginBodyModel authData = new LoginBodyModel();
    authData.setEmail("eve.holt1@reqres.in");
    authData.setPassword("cityslicka");

    ErrorResponseModel response = step("Make request", () ->
            given(loginRequestSpec)
                    .body(authData)
                    .when()
                    .post()
                    .then()
                    .spec(loginResponseStatus400Spec)
                    .extract().as(ErrorResponseModel.class));
    step("Check response", () ->
            assertThat(response.getError(), is("user not found")));
  }

  @DisplayName("Unsuccessful Login - Missing password")
  @Test
  void missingPasswordTest() {

    LoginBodyModel authData = new LoginBodyModel();
    authData.setEmail("eve.holt1@reqres.in");
    authData.setPassword("");

    ErrorResponseModel response = step("Make request", () ->
            given(loginRequestSpec)
                    .body(authData)
                    .when()
                    .post()
                    .then()
                    .spec(loginResponseStatus400Spec)
                    .extract().as(ErrorResponseModel.class));

    step("Check response", () ->
            assertThat(response.getError(), is("Missing password")));
  }

  @DisplayName("Unsuccessful Login - Missing email")
  @Test
  void missingLoginTest() {

    LoginBodyModel authData = new LoginBodyModel();

    authData.setEmail("");
    authData.setPassword("cityslicka");

    ErrorResponseModel response = step("Make request", () ->
            given(loginRequestSpec)
                    .body(authData)
                    .when()
                    .post()
                    .then()
                    .spec(loginResponseStatus400Spec)
                    .extract().as(ErrorResponseModel.class));
    step("Check response", () ->
            assertThat(response.getError(), is("Missing email or username")));
  }

  @DisplayName("Unsuccessful Login - Wrong request body")
  @Test
  void wrongBodyTest() {
    String authData = "%}";
    Response response = step("Make request", () ->
            given(loginRequestSpec)
                    .body(authData)
                    .when()
                    .post()
                    .then()
                    .spec(loginResponseStatus400Spec)
                    .extract().response());
  }

  @DisplayName("Unsuccessful Login - Missing request body and ContentType")
  @Test
  void missingRequestBodyTest() {
    Response response = step("Make request", () ->
            given(loginRequestSpecWithoutContentType)
                    .when()
                    .post()
                    .then()
                    .spec(loginResponseStatus415Spec)
                    .extract().response());
  }
}
