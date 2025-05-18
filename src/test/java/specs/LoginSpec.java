package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class LoginSpec {
  public static RequestSpecification loginRequestSpec = with()
          .filter(withCustomTemplates())
          .header("x-api-key", "reqres-free-v1")
          .log().uri()
          .log().body()
          .log().headers()
          .contentType(JSON)
          .basePath("/api/login");

  public static RequestSpecification loginRequestSpecWithoutContentType = with()
          .filter(withCustomTemplates())
          .header("x-api-key", "reqres-free-v1")
          .log().uri()
          .log().body()
          .log().headers()
          .basePath("/api/login");

  public static ResponseSpecification loginResponseSpec = new ResponseSpecBuilder()
          .expectStatusCode(200)
          .log(STATUS)
          .log(BODY)
          .build();

  public static ResponseSpecification loginResponseStatus400Spec = new ResponseSpecBuilder()
          .expectStatusCode(400)
          .log(STATUS)
          .log(BODY)
          .build();

  public static ResponseSpecification loginResponseStatus415Spec = new ResponseSpecBuilder()
          .expectStatusCode(415)
          .log(STATUS)
          .log(BODY)
          .build();
}




