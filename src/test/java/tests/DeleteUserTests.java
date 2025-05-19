package tests;

import io.restassured.response.Response;
import models.UserBodyModel;
import models.UserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static specs.CreateUserSpec.createUserRequestSpec;
import static specs.CreateUserSpec.createUserResponseSpec;
import static specs.DeleteUserSpec.deleteUserRequestSpec;
import static specs.DeleteUserSpec.deleteUserResponseSpec;

@Tag("rest_api_tests")
public class DeleteUserTests extends TestBase {

  @DisplayName("Delete user")
  @Test
  void deleteUserTest() {

    UserBodyModel userData = new UserBodyModel();
    userData.setName("Tom");
    userData.setJob("leader");

    UserResponseModel response1 = step("Make request - create user", () ->
            given(createUserRequestSpec)
                    .body(userData)
                    .when()
                    .post()
                    .then()
                    .spec(createUserResponseSpec)
                    .extract().as(UserResponseModel.class));

    step("Check user creation response", () -> {
      assertThat(response1.getName(), is(userData.getName()));
      assertThat(response1.getJob(), is(userData.getJob()));
      assertThat(response1.getId(), notNullValue());
    });

    int id = response1.getId();

    Response response2 = step("Make request - delete user with id = " + id, () ->
            given(deleteUserRequestSpec)
                    .when()
                    .delete("/" + id)
                    .then()
                    .spec(deleteUserResponseSpec)
                    .extract().response());
  }
}
