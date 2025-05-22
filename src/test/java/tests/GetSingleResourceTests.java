package tests;

import io.restassured.response.Response;
import models.ResourceDataModel;
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
public class GetSingleResourceTests extends TestBase {

  @DisplayName("Get existing single resource")
  @Test
  void getSingleResourceTest() {
    int id = 2;

    ResourceDataModel response = step("Make request with resource id = " + id, () ->
            given(requestSpec)
                    .when()
                    .get("/unknown/" + id)
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ResourceDataModel.class));

    step("Check response", () -> {
      assertThat(response.getData().getId(), is(2));
      assertThat(response.getData().getName(), is("fuchsia rose"));
      assertThat(response.getData().getYear(), is(2001));
      assertThat(response.getData().getColor(), is("#C74375"));
      assertThat(response.getData().getPantoneValue(), is("17-2031"));
    });
  }

  @DisplayName("Single resource not found")
  @Test
  void singleResourceNotFoundTest() {
    int id = 23;

    Response response = step("Make request with resource id = " + id, () ->
            given(requestSpec)
                    .when()
                    .get("/unknown/" + id)
                    .then()
                    .spec(responseSpecStatusCode(404))
                    .extract().response());
  }
}
