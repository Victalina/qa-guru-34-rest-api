import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class GetSingleResourceTests {

  @Test
  void getSingleResourceTest() {
    given()
            .header("x-api-key", "reqres-free-v1")
            .log().uri()
            .when()
            .get("https://reqres.in/api/unknown/2")
            .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .body("data.id", is(2))
            .body("data.name", is("fuchsia rose"))
            .body("data.year", is(2001))
            .body("data.color", is("#C74375"))
            .body("data.pantone_value", is("17-2031"));
  }
  @Test
  void singleResourceNotFoundTest() {
    given()
            .header("x-api-key", "reqres-free-v1")
            .log().uri()
            .when()
            .get("https://reqres.in/api/unknown/23")
            .then()
            .log().status()
            .log().body()
            .statusCode(404);
  }
}
