import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTests extends TestBase {

  @Test
  void successfulLoginTest() {
    String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";
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
            .statusCode(200)
            .body("token", notNullValue());
  }

  @Test
  void unsuccessfulLogin400Test() {
    String authData = "";
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
            .statusCode(400)
            .body("error", is("Missing email or username"));
  }

  @Test
  void userNotFoundTest() {
    String authData = "{\"email\": \"eve.holt1@reqres.in\", \"password\": \"cityslicka\"}";
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
            .statusCode(400)
            .body("error", is("user not found"));
  }

  @Test
  void missingPasswordTest() {
    String authData = "{\"email\": \"eve.holt@reqres.in\"}";
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
            .statusCode(400)
            .body("error", is("Missing password"));
  }

  @Test
  void missingLoginTest() {
    String authData = "{\"password\": \"cityslicka\"}";
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
            .statusCode(400)
            .body("error", is("Missing email or username"));
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
