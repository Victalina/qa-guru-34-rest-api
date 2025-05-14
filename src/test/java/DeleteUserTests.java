import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class DeleteUserTests {

  @Test
  void deleteUserTest() {
    String name = "Tom";
    String jod = "leader";
    String userData = "{\"name\": \"" + name + "\", \"jod\": \"" + jod + "\"}";
    Response response =
            given()
                    .header("x-api-key", "reqres-free-v1")
                    .body(userData)
                    .contentType(JSON)
                    .log().uri()
                    .when()
                    .post("https://reqres.in/api/users")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(201)
                    .body("name", is(name))
                    .body("jod", is(jod))
                    .body("id", notNullValue())
                    .extract().response();
    String json = response.asString();
    int id = from(json).getInt("id");

    given()
            .header("x-api-key", "reqres-free-v1")
            .log().uri()
            .when()
            .delete("https://reqres.in/api/users/" + id)
            .then()
            .log().status()
            .log().body()
            .statusCode(204);
  }
}
