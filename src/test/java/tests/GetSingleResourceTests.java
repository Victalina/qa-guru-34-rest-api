package tests;

import models.ResourceDataModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetSingleResourceTests extends TestBase {

  @Test
  void getSingleResourceTest() {

    ResourceDataModel response = given()
            .header("x-api-key", "reqres-free-v1")
            .log().uri()
            .when()
            .get("/unknown/2")
            .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .extract().as(ResourceDataModel.class);

    assertThat(response.getData().getId(), is(2));
    assertThat(response.getData().getName(), is("fuchsia rose"));
    assertThat(response.getData().getYear(), is(2001));
    assertThat(response.getData().getColor(), is("#C74375"));
    assertThat(response.getData().getPantoneValue(), is("17-2031"));
  }

  @Test
  void singleResourceNotFoundTest() {
    given()
            .header("x-api-key", "reqres-free-v1")
            .log().uri()
            .when()
            .get("/unknown/23")
            .then()
            .log().status()
            .log().body()
            .statusCode(404);
  }
}
