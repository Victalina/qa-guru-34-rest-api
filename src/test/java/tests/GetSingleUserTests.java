package tests;

import io.restassured.response.Response;
import models.UserDataModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static specs.Spec.requestSpec;
import static specs.Spec.responseSpecStatusCode;

@Tag("rest_api_tests")
public class GetSingleUserTests extends TestBase {

  @DisplayName("Get existing single user")
  @Test
  void getSingleUserTest() {
    int id = 2;

    UserDataModel response = step("Make request with user id = " + id, () ->
            given(requestSpec)
                    .when()
                    .get("/users/" + id)
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(UserDataModel.class));

    step("Check response", () -> {
      assertThat(response.getData().getId(), is(id));
      assertThat(response.getData().getEmail(), is("janet.weaver@reqres.in"));
      assertThat(response.getData().getFirstName(), is("Janet"));
      assertThat(response.getData().getLastName(), is("Weaver"));
      assertThat(response.getData().getAvatar(), is("https://reqres.in/img/faces/2-image.jpg"));
    });
  }

  @DisplayName("Single user not found")
  @Test
  void singleUserNotFoundTest() {
    int id = 23;

    Response response = step("Make request with user id = " + id, () ->
            given(requestSpec)
                    .when()
                    .get("/users/" + id)
                    .then()
                    .spec(responseSpecStatusCode(404))
                    .extract().response());
  }
}
