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
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static specs.Spec.requestSpec;
import static specs.Spec.responseSpecStatusCode;

@Tag("rest_api_tests")
public class LoginTests extends TestBase {

  @DisplayName("Successful Login")
  @Test
  void successfulLoginTest() {

    LoginBodyModel authData = new LoginBodyModel("eve.holt@reqres.in", "cityslicka");

    LoginResponseModel response = step("Make request", () ->
            given(requestSpec)
                    .body(authData)
                    .contentType(JSON)
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(LoginResponseModel.class));

    step("Check response", () ->
            assertThat(response.getToken(), notNullValue()));
  }

  @DisplayName("Unsuccessful Login - Missing email and password")
  @Test
  void unsuccessfulLoginMissingEmailAndPasswordTest() {
    LoginBodyModel authData = new LoginBodyModel("", "");

    ErrorResponseModel response = step("Make request", () ->
            given(requestSpec)
                    .body(authData)
                    .contentType(JSON)
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(400))
                    .extract().as(ErrorResponseModel.class));
    step("Check response", () ->
            assertThat(response.getError(), is("Missing email or username")));
  }

  @DisplayName("Unsuccessful Login - User not found")
  @Test
  void userNotFoundTest() {

    LoginBodyModel authData = new LoginBodyModel("eve.holt1@reqres.in", "cityslicka");

    ErrorResponseModel response = step("Make request", () ->
            given(requestSpec)
                    .body(authData)
                    .contentType(JSON)
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(400))
                    .extract().as(ErrorResponseModel.class));
    step("Check response", () ->
            assertThat(response.getError(), is("user not found")));
  }

  @DisplayName("Unsuccessful Login - Missing password")
  @Test
  void missingPasswordTest() {

    LoginBodyModel authData = new LoginBodyModel("eve.holt1@reqres.in", "");

    ErrorResponseModel response = step("Make request", () ->
            given(requestSpec)
                    .body(authData)
                    .contentType(JSON)
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(400))
                    .extract().as(ErrorResponseModel.class));

    step("Check response", () ->
            assertThat(response.getError(), is("Missing password")));
  }

  @DisplayName("Unsuccessful Login - Missing email")
  @Test
  void missingLoginTest() {

    LoginBodyModel authData = new LoginBodyModel("", "cityslicka");

    ErrorResponseModel response = step("Make request", () ->
            given(requestSpec)
                    .body(authData)
                    .contentType(JSON)
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(400))
                    .extract().as(ErrorResponseModel.class));
    step("Check response", () ->
            assertThat(response.getError(), is("Missing email or username")));
  }

  @DisplayName("Unsuccessful Login - Wrong request body")
  @Test
  void wrongBodyTest() {
    String authData = "%}";
    Response response = step("Make request", () ->
            given(requestSpec)
                    .body(authData)
                    .contentType(JSON)
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(400))
                    .extract().response());
  }

  @DisplayName("Unsuccessful Login - Missing request body and ContentType")
  @Test
  void missingRequestBodyTest() {
    Response response = step("Make request", () ->
            given(requestSpec)
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecStatusCode(415))
                    .extract().response());
  }
}
