package tests;

import models.UserDataModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetSingleUserTests extends TestBase {

  @Test
  void getSingleUserTest() {
    UserDataModel response = given()
            .header("x-api-key", "reqres-free-v1")
            .log().uri()
            .when()
            .get("/users/2")
            .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .extract().as(UserDataModel.class);
    assertThat(response.getData().getId(), is(2));
    assertThat(response.getData().getEmail(), is("janet.weaver@reqres.in"));
    assertThat(response.getData().getFirstName(), is("Janet"));
    assertThat(response.getData().getLastName(), is("Weaver"));
    assertThat(response.getData().getAvatar(), is("https://reqres.in/img/faces/2-image.jpg"));
  }

  @Test
  void singleUserNotFoundTest() {
    given()
            .header("x-api-key", "reqres-free-v1")
            .log().uri()
            .when()
            .get("/users/23")
            .then()
            .log().status()
            .log().body()
            .statusCode(404);
  }
}
