import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class GetSingleUserTests {

  @Test
  void getSingleUserTest() {
    given()
            .header("x-api-key", "reqres-free-v1")
            .log().uri()
            .when()
            .get("https://reqres.in/api/users/2")
            .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .body("data.id", is(2))
            .body("data.email", is("janet.weaver@reqres.in"))
            .body("data.first_name", is("Janet"))
            .body("data.last_name", is("Weaver"))
            .body("data.avatar", is("https://reqres.in/img/faces/2-image.jpg"));
  }
  @Test
  void singleUserNotFoundTest() {
    given()
            .header("x-api-key", "reqres-free-v1")
            .log().uri()
            .when()
            .get("https://reqres.in/api/users/23")
            .then()
            .log().status()
            .log().body()
            .statusCode(404);
  }
}
