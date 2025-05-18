package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class GetResourceSpec {

  public static RequestSpecification getResourceRequestSpec = with()
          .filter(withCustomTemplates())
          .header("x-api-key", "reqres-free-v1")
          .log().uri()
          .log().body()
          .log().headers()
          .basePath("/api/unknown");

  public static ResponseSpecification getResourceResponseSpec = new ResponseSpecBuilder()
          .expectStatusCode(200)
          .log(STATUS)
          .log(BODY)
          .build();

  public static ResponseSpecification resourceNotFoundResponseSpec = new ResponseSpecBuilder()
          .expectStatusCode(404)
          .log(STATUS)
          .log(BODY)
          .build();
}




