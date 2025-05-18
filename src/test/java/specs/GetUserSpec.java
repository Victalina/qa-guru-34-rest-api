package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class GetUserSpec {

  public static RequestSpecification getUserRequestSpec = with()
          .filter(withCustomTemplates())
          .header("x-api-key", "reqres-free-v1")
          .log().uri()
          .log().body()
          .log().headers()
          .basePath("/api/users");

  public static ResponseSpecification getUserResponseSpec = new ResponseSpecBuilder()
          .expectStatusCode(200)
          .log(STATUS)
          .log(BODY)
          .build();

  public static ResponseSpecification userNotFoundResponseSpec = new ResponseSpecBuilder()
          .expectStatusCode(404)
          .log(STATUS)
          .log(BODY)
          .build();
}




