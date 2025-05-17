package tests;

import models.UserBodyModel;
import models.UserResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteUserTests extends TestBase {

  @Test
  void deleteUserTest() {

    UserBodyModel userData = new UserBodyModel();
    userData.setName("Tom");
    userData.setJob("leader");

    UserResponseModel response = given()
                    .header("x-api-key", "reqres-free-v1")
                    .body(userData)
                    .contentType(JSON)
                    .log().uri()
                    .when()
                    .post("/users")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(201)
            .extract().as(UserResponseModel.class);

    assertThat(response.getName(), is(userData.getName()));
    assertThat(response.getJob(), is(userData.getJob()));
    assertThat(response.getId(), notNullValue());

    int id = response.getId();

    given()
            .header("x-api-key", "reqres-free-v1")
            .log().uri()
            .when()
            .delete("/users/" + id)
            .then()
            .log().status()
            .log().body()
            .statusCode(204);
  }
}
