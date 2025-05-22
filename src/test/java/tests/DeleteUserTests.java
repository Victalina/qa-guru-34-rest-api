package tests;

import io.restassured.response.Response;
import models.UserBodyModel;
import models.UserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static specs.Spec.requestSpec;
import static specs.Spec.responseSpecStatusCode;

@Tag("rest_api_tests")
public class DeleteUserTests extends TestBase {

  @DisplayName("Delete user")
  @Test
  void deleteUserTest() {

    UserBodyModel userData = new UserBodyModel("Tom", "leader");

    UserResponseModel response1 = step("Make request - create user", () ->
            given(requestSpec)
                    .body(userData)
                    .contentType(JSON)
                    .when()
                    .post("/users")
                    .then()
                    .spec(responseSpecStatusCode(201))
                    .extract().as(UserResponseModel.class));

    step("Check user creation response", () -> {
      assertThat(response1.getName(), is(userData.getName()));
      assertThat(response1.getJob(), is(userData.getJob()));
      assertThat(response1.getId(), notNullValue());
    });

    int id = response1.getId();

    Response response2 = step("Make request - delete user with id = " + id, () ->
            given(requestSpec)
                    .when()
                    .delete("/users" + id)
                    .then()
                    .spec(responseSpecStatusCode(204))
                    .extract().response());
  }
}
